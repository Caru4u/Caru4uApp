package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.CustomerRegistorRepository;
import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import com.Caru4u.Customer_Registration.Utails.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final CustomerRegistorRepository repository;
    private final JavaMailSender mailSender;

    // 🔢 Send OTP
    @Override
    public void sendOtp(String email) {

        CustomerRegistor customer = repository.findByMailid(email)
                .orElseThrow(() -> new RuntimeException(Constants.Email_not_found));

        String otp = String.format("%06d", new Random().nextInt(999999));

        customer.setOtp(otp);
        customer.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        repository.save(customer);

        sendEmail(email, otp);
    }

    // 📧 Send Email
    private void sendEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(Constants.Password_Reset_Otp);
            message.setText(Constants.Your_otp + otp + Constants.Valid_for_minutes);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(Constants.Failed_send_otp+e.getMessage());
        }
    }

    // 🔐 Verify OTP and Reset Password
    @Override
    @Transactional
    public String verifyOtpAndResetPassword(String email, String otp, String newPassword) {
        CustomerRegistor customer = repository.findByMailidAndOtp(email, otp).orElse(null);

        if (customer == null) {
            return Constants.Invalid_otp;
        }

        if (customer.getOtpExpiry() == null || customer.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return Constants.Otp_Expired;
        }

        if (!isValidPassword(newPassword)) {
            return Constants.Invalid_Password;
        }

        // WARNING: storing plain text passwords is insecure.
        customer.setPassword(newPassword);

        // Clear OTP
        customer.setOtp(null);
        customer.setOtpExpiry(null);

        repository.save(customer);

        return Constants.Password_reset_SUccessfully;
    }

    private boolean isValidPassword(String password) {
        if (password == null) return false;
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false; // must contain uppercase
        if (!password.matches(".*\\d.*")) return false;   // must contain digit
        return true;
    }
}