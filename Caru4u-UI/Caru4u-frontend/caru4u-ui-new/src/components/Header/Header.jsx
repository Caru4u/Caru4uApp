import React from "react";
import { useNavigate } from "react-router-dom";
import { MapPin } from "lucide-react";

import "./Header.css";

function Header() {

  const navigate = useNavigate();

  return (
    <header className="caru-header">

      {/* LOGO */}
      <div
        className="caru-header-logo"
        onClick={() => navigate("/")}
      >
        <img
          src="/images/logo.png"
          alt="Caru4u"
        />
      </div>


      {/* NAVIGATION */}
      <nav className="caru-header-nav">

        <button
          onClick={() => navigate("/")}
        >
          Home
        </button>

        <button
          onClick={() => navigate("/#services")}
        >
          Our Services
        </button>

        <button
          onClick={() => navigate("/#how-it-works")}
        >
          How It Works
        </button>

        <button
          onClick={() => navigate("/#about")}
        >
          About Us
        </button>

        <button
          onClick={() => navigate("/#contact")}
        >
          Contact
        </button>

      </nav>


      {/* RIGHT SIDE */}
      <div className="caru-header-actions">

        <div className="caru-location">

          <MapPin size={18} />

          <span>Bengaluru</span>

          <span className="location-arrow">
            ⌄
          </span>

        </div>


        <button
          className="caru-login-button"
          onClick={() => navigate("/login")}
        >
          Login
        </button>


        <button
          className="caru-signup-button"
          type="button"
        >
          Sign Up
        </button>

      </div>

    </header>
  );
}

export default Header;