package com.Caru4u.Customer_Registration.Services;

public interface ForgotPasswordService {
    public void sendOtp(String email);
    public String verifyOtpAndResetPassword(String email, String otp, String newPassword);
}
