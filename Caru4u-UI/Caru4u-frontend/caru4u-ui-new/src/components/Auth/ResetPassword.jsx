// import React, { useState } from "react";
// import axios from "axios";
// import "./Auth.scss";

// const ResetPassword = () => {
//   const [formData, setFormData] = useState({
//     email: "",
//     otp: "",
//     newPassword: ""
//   });
//   const [message, setMessage] = useState("");

//   const handleChange = (e) => {
//     setFormData({ ...formData, [e.target.name]: e.target.value });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       const res = await axios.post("http://localhost:8080/auth/reset-password", formData);
//       setMessage(res.data);
//     } catch (err) {
//       setMessage(err.response?.data || "Error resetting password");
//     }
//   };

//   return (
//     <div className="auth-container">
//       <h2>Reset Password</h2>
//       <form onSubmit={handleSubmit}>
//         <input
//           type="email"
//           name="email"
//           placeholder="Enter your email"
//           value={formData.email}
//           onChange={handleChange}
//           required
//         />
//         <input
//           type="text"
//           name="otp"
//           placeholder="Enter OTP"
//           value={formData.otp}
//           onChange={handleChange}
//           required
//         />
//         <input
//           type="password"
//           name="newPassword"
//           placeholder="Enter new password"
//           value={formData.newPassword}
//           onChange={handleChange}
//           required
//         />
//         <button type="submit">Reset Password</button>
//       </form>
//       {message && <p className="message">{message}</p>}
//     </div>
//   );
// };

// export default ResetPassword;
