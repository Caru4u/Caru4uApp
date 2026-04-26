package com.Caru4u.Customer_Registration.Services;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

@Service
public class OtpService {

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);

    private final Map<String, OtpEntry> otpCache = new ConcurrentHashMap<>();
    private final Map<String, Instant> lastSentAt = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Random random = new SecureRandom();

    @Value("${twilio.accountSid:}")
    private String accountSid;

    @Value("${twilio.authToken:}")
    private String authToken;

    @Value("${twilio.fromNumber:}")
    private String fromNumber;

    @Value("${twilio.messagingServiceSid:}")
    private String messagingServiceSid; // optional, recommended for India/DLT

    @Value("${otp.expiry.seconds:300}")
    private long otpExpirySeconds;

    @Value("${otp.rateLimit.seconds:60}")
    private long rateLimitSeconds; // minimum seconds between OTPs to same number

    public OtpService() {
        scheduler.scheduleAtFixedRate(this::cleanupExpired, 1, 1, TimeUnit.MINUTES);
    }

    @PostConstruct
    public void initTwilio() {
        if (accountSid == null || accountSid.isBlank() || authToken == null || authToken.isBlank()) {
            log.warn("Twilio credentials not set. SMS sending will fail until configured.");
            return;
        }
        Twilio.init(accountSid, authToken);
        log.info("Twilio initialized");
    }

    public String sendMobileOtp(String mobile) {
        if (mobile == null || mobile.isBlank()) {
            return "Invalid mobile number";
        }

        String normalized = normalizeToE164(mobile);
        if (normalized == null) {
            return "Invalid mobile format. Provide E.164 or a local 10-digit number.";
        }

        // simple rate limiting per number
        Instant now = Instant.now();
        Instant last = lastSentAt.get(normalized);
        if (last != null && now.isBefore(last.plusSeconds(rateLimitSeconds))) {
            long wait = last.plusSeconds(rateLimitSeconds).getEpochSecond() - now.getEpochSecond();
            return "Too many requests. Try again in " + wait + " seconds.";
        }

        String otp = generateOtp();

        try {
            // ensure Twilio is initialized (idempotent)
            if (accountSid != null && !accountSid.isBlank()) {
                Twilio.init(accountSid, authToken);
            }

            Message message;
            String body = "Your verification code is: " + otp;

            if (messagingServiceSid != null && !messagingServiceSid.isBlank()) {
                // correct overload: Message.creator(PhoneNumber to, String messagingServiceSid, String body)
                message = Message.creator(new PhoneNumber(normalized), messagingServiceSid, body).create();
            } else if (fromNumber != null && !fromNumber.isBlank()) {
                // correct overload: Message.creator(PhoneNumber to, PhoneNumber from, String body)
                message = Message.creator(new PhoneNumber(normalized), new PhoneNumber(fromNumber), body).create();
            } else {
                return "SMS sender not configured";
            }

            // store OTP only after successful send
            Instant expiresAt = Instant.now().plusSeconds(otpExpirySeconds);
            otpCache.put(normalized, new OtpEntry(otp, expiresAt));
            lastSentAt.put(normalized, Instant.now());

            log.info("OTP sent to {} messageSid={}", normalized, message.getSid());
            return "OTP sent";
        } catch (ApiException apiEx) {
            log.warn("Twilio ApiException code={} msg={}", apiEx.getCode(), apiEx.getMessage());
            return "Failed to send OTP: " + apiEx.getMessage();
        } catch (Exception ex) {
            log.error("Unexpected error sending OTP to {}", normalized, ex);
            return "Failed to send OTP: " + ex.getMessage();
        }
    }

    public boolean verifyOtp(String mobile, String code) {
        if (mobile == null || code == null) return false;
        String normalized = normalizeToE164(mobile);
        if (normalized == null) return false;
        OtpEntry entry = otpCache.get(normalized);
        if (entry == null) return false;
        if (Instant.now().isAfter(entry.expiresAt)) {
            otpCache.remove(normalized);
            return false;
        }
        boolean match = entry.code.equals(code);
        if (match) otpCache.remove(normalized);
        return match;
    }

    private String generateOtp() {
        int number = random.nextInt(900000) + 100000; // 6-digit
        return String.valueOf(number);
    }

    private void cleanupExpired() {
        Instant now = Instant.now();
        otpCache.entrySet().removeIf(e -> now.isAfter(e.getValue().expiresAt));
    }

    private String normalizeToE164(String mobile) {
        if (mobile == null) return null;
        String digits = mobile.replaceAll("[^0-9]", "");
        if (mobile.startsWith("+")) {
            return "+" + digits;
        }
        // heuristic: if 10 digits assume India +91
        if (digits.length() == 10) {
            return "+91" + digits;
        }
        if (digits.length() >= 11 && digits.length() <= 15) {
            return "+" + digits;
        }
        return null;
    }

    private static class OtpEntry {
        final String code;
        final Instant expiresAt;
        OtpEntry(String code, Instant expiresAt) {
            this.code = code;
            this.expiresAt = expiresAt;
        }
    }
}