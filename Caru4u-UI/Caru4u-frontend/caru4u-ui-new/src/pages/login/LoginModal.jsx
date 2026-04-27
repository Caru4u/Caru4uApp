import React, { useState } from "react";
import "./Login.scss";
import logo from "../../assets/images/Caru4uLogo.png";

function LoginModal({ onClose }) {
  const [identifier, setIdentifier] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [remember, setRemember] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const res = await fetch("http://localhost:8080/auth/Customer/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          identifier,
          password
        })
      });

      let resultMessage = "";
      const contentType = res.headers.get("content-type");

      if (contentType && contentType.includes("application/json")) {
        const data = await res.json();
        resultMessage = data.message || "Login Successful";

        if (data.token) {
          remember
            ? localStorage.setItem("token", data.token)
            : sessionStorage.setItem("token", data.token);
        }
      } else {
        resultMessage = await res.text();
      }

      if (res.ok) {
        setIsError(false);
        setMessage(resultMessage || "Login Successful ✅");

        setTimeout(() => onClose(), 1200);
      } else {
        setIsError(true);
        setMessage(resultMessage || "Invalid credentials ❌");
      }

    } catch (error) {
      console.error(error);
      setIsError(true);
      setMessage("Server error ❌");
    }

    setLoading(false);
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        {/* ✅ Bigger Logo */}
        <img src={logo} alt="Caru4u Logo" className="logo" />

        <h2>Login</h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            placeholder="Email or Mobile Number"
            value={identifier}
            onChange={(e) => setIdentifier(e.target.value)}
            required
          />

          {/* 👁 Password Field */}
          <div className="password-field">
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <span onClick={() => setShowPassword(!showPassword)}>
              {showPassword ? "🙈" : "👁"}
            </span>
          </div>

          {/* Options */}
          <div className="options">
            <label>
              <input
                type="checkbox"
                checked={remember}
                onChange={(e) => setRemember(e.target.checked)}
              />
              Remember me
            </label>

            <span className="forgot">Forgot password?</span>
          </div>

          <button type="submit" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>

          <button type="button" className="close-btn" onClick={onClose}>
            Close
          </button>

        </form>

        {message && (
          <p className={isError ? "error" : "success"}>
            {message}
          </p>
        )}

      </div>
    </div>
  );
}

export default LoginModal;