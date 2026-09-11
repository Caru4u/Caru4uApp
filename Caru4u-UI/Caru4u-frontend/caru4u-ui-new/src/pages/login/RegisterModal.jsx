import React, { useEffect, useState } from "react";
import "./Register.scss";
import logo from "../../assets/images/Caru4uLogo.png";
import carBg from "../../assets/images/redCarBg.png";

const RegisterModal = ({ onClose }) => {
  const [formData, setFormData] = useState({
    firstname: "",
    lastname: "",
    mobileNumber: "",
    mailid: "",
    password: "",
    confirmPassword: "",
    blockOrCrossName: "",
    plotNumber: "",
    apartmentOrVillaId: "",
  });

  const [options, setOptions] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/auth/Customer/apartment-or-villa")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch apartments");
        return res.json();
      })
      .then((data) => setOptions(data))
      .catch(() => setOptions([]));
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const validateForm = () => {
    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match ❌");
      return false;
    }
    if (formData.mobileNumber.length !== 10) {
      setError("Mobile number must be 10 digits ❌");
      return false;
    }
    if (!formData.apartmentOrVillaId) {
      setError("Please select Apartment/Villa ❌");
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setMessage("");

    if (!validateForm()) return;

    const payload = {
      firstname: formData.firstname,
      lastname: formData.lastname,
      mobileNumber: formData.mobileNumber,
      mailid: formData.mailid,
      password: formData.password,
      blockOrCrossName: formData.blockOrCrossName,
      plotNumber: formData.plotNumber,
      apartmentOrVilla: { id: formData.apartmentOrVillaId },
    };

    try {
      const response = await fetch("http://localhost:8080/auth/Customer/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      const result = await response.text();
      if (!response.ok) throw new Error(result || "Registration failed");

      setMessage("Registration Successful ✅");
      setFormData({
        firstname: "",
        lastname: "",
        mobileNumber: "",
        mailid: "",
        password: "",
        confirmPassword: "",
        blockOrCrossName: "",
        plotNumber: "",
        apartmentOrVillaId: "",
      });
    } catch (err) {
      setError(err.message || "Registration Failed ❌");
    }
  };

  return (
    <div className="register-overlay">
      <div className="register-card">
        {/* Close Button */}
        <button className="close-btn" onClick={onClose}>
          &times;
        </button>

        {/* 🔴 LEFT PANEL */}
        <div className="register-left" style={{ backgroundImage: `url(${carBg})` }}>
          <div className="overlay">
            {/* <img src={logo} alt="Caru4u Logo" className="logo" /> */}
            <h2 className="brand-title">
              Join <span>Caru4u</span>
            </h2>
            <p className="brand-subtitle">
              Create your account and experience premium car care services.
            </p>
          </div>
        </div>

        {/* ⚪ RIGHT PANEL */}
        <div className="register-right">
          <h2 className="register-title">Customer Registration</h2>
          <p className="register-subtitle">Fill in your details to get started</p>

          <form onSubmit={handleSubmit}>
            <div className="register-row">
              <input name="firstname" placeholder="First Name" value={formData.firstname} onChange={handleChange} required />
              <input name="lastname" placeholder="Last Name" value={formData.lastname} onChange={handleChange} required />
            </div>

            <input type="email" name="mailid" placeholder="Email Address" value={formData.mailid} onChange={handleChange} required />
            <input name="mobileNumber" placeholder="Enter 10 digit mobile number" value={formData.mobileNumber} onChange={handleChange} required />

            <div className="register-row">
              <input type="password" name="password" placeholder="Password" value={formData.password} onChange={handleChange} required />
              <input type="password" name="confirmPassword" placeholder="Confirm Password" value={formData.confirmPassword} onChange={handleChange} required />
            </div>

            <select name="apartmentOrVillaId" value={formData.apartmentOrVillaId} onChange={handleChange} required>
              <option value="">Select Apartment / Villa</option>
              {options.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.name}
                </option>
              ))}
            </select>

            <div className="register-row">
              <input name="blockOrCrossName" placeholder="Block / Cross" value={formData.blockOrCrossName} onChange={handleChange} />
              <input name="plotNumber" placeholder="Plot Number" value={formData.plotNumber} onChange={handleChange} />
            </div>

            <button type="submit">Register</button>

            {message && <p className="success">{message}</p>}
            {error && <p className="error">{error}</p>}
          </form>
        </div>
      </div>
    </div>
  );
};

export default RegisterModal;
