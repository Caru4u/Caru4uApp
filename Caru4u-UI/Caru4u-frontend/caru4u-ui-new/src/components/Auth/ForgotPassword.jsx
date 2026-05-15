import React, { useState } from "react";
import axios from "axios";
import "./Auth.scss";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");
  const [otpSent, setOtpSent] = useState(false);
  const [otp, setOtp] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [message, setMessage] = useState("");

  // Step 1: Send OTP
  const handleSendOtp = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8080/auth/forgot-password", { email });
      setMessage(res.data || "OTP sent successfully ✅");
      setOtpSent(true);
    } catch (err) {
      setMessage(err.response?.data || "Error sending OTP ❌");
    }
  };

  // Step 2: Verify OTP and Reset Password
  const handleResetPassword = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8080/auth/reset-password", {
        email,
        otp,
        newPassword
      });

      const responseMessage = res.data || "Password reset successful ✅";
      setMessage(responseMessage);

      // ✅ No redirect — just show the message
    } catch (err) {
      setMessage(err.response?.data || "Error resetting password ❌");
    }
  };

  return (
    <div className="auth-container">
      <h2>Forgot Password</h2>

      {!otpSent ? (
        <form onSubmit={handleSendOtp}>
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <button type="submit">Send OTP</button>
        </form>
      ) : (
        <form onSubmit={handleResetPassword}>
          <input
            type="text"
            placeholder="Enter OTP"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Enter new password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
          <button type="submit">Reset Password</button>
        </form>
      )}

      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default ForgotPassword;
