import React, { useState } from "react";
import "./Header.scss";
import logo from "../../assets/images/Caru4uLogo.png";
import LoginModal from "../../pages/login/LoginModal";

const Header = () => {
  const [showLogin, setShowLogin] = useState(false);

  return (
    <>
      <header className="header">

        {/* LEFT - LOGO */}
        <div className="header__logo">
          <img src={logo} alt="Caru4u Logo" />
        </div>

        {/* CENTER - NAV */}
        <nav className="header__nav">
          <ul>
            <li><a href="#whyus">About us</a></li>
            <li><a href="#services">Services</a></li>
            <li><a href="#pricing">Pricing</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </nav>

        {/* RIGHT - ACTIONS */}
        <div className="header__actions">
          <button
            className="btn btn--login"
            onClick={() => setShowLogin(true)}
          >
            Login
          </button>

          <button className="btn btn--register">
            Register
          </button>
        </div>

      </header>

      {showLogin && (
        <LoginModal onClose={() => setShowLogin(false)} />
      )}
    </>
  );
};

export default Header;