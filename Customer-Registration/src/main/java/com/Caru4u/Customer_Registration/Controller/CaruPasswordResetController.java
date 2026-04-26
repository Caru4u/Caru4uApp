package com.Caru4u.Customer_Registration.Controller;

import com.Caru4u.Customer_Registration.Model.EmailRequest;
import com.Caru4u.Customer_Registration.Model.ResetPasswordRequest;
import com.Caru4u.Customer_Registration.Services.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CaruPasswordResetController {
    @Autowired
    private  ForgotPasswordService service;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailRequest request) {
        try {
            service.sendOtp(request.getEmail());
            return ResponseEntity.ok("OTP sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {

        return service.verifyOtpAndResetPassword(
                resetPasswordRequest.getEmail(),
                resetPasswordRequest.getOtp(),
                resetPasswordRequest.getNewPassword()
        );
    }
}
