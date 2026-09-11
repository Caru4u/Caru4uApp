import React, { useState } from "react";
import axios from "axios";
import "./ContactUs.scss";

const ContactUs = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    message: ""
  });
  const [response, setResponse] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setResponse("");
    setError("");
    try {
      const res = await axios.post(
        "http://localhost:8080/auth/Customer/request-callback",
        formData
      );
      setResponse(res.data);   // ✅ success message from backend
    } catch (err) {
      setError(err.response?.data || "Error submitting request ❌");
    }
  };

  return (
    <div className="contact-container">
      <div className="contact-info">
        <h2>Contact Us</h2>
        <p><strong>Our head office address</strong><br/>
          No 32/33 Pushpa Nilaya Sai Ram Layout,<br/>
          Sampangi Hills nationla Park Road Jiagani Hobali,<br/>
          Anekal Taluk Bengaluru, Karnataka-560083
        </p>
        <p><strong>Call for help:</strong> +91 9035803009</p>
        <p><strong>Mail us:</strong> supportus@caru4u.com</p>
      </div>

      <div className="contact-form">
        <h2>Request a Callback</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="name"
            placeholder="Your name"
            value={formData.name}
            onChange={handleChange}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Your email"
            value={formData.email}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="phone"
            placeholder="Phone number"
            value={formData.phone}
            onChange={handleChange}
            required
          />
          <textarea
            name="message"
            placeholder="Your message"
            value={formData.message}
            onChange={handleChange}
            required
          />
          <button type="submit">Submit</button>
        </form>

        {/* ✅ Show success or error */}
        {response && <p className="message success">{response}</p>}
        {error && <p className="message error">{error}</p>}
      </div>
    </div>
  );
};

export default ContactUs;
