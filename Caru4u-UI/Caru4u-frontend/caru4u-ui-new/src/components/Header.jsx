import { MapPin } from "lucide-react";

function Header() {
  return (
    <header className="header">

      <div className="logo">
        <div className="logo-icon">🚙</div>

        <div>
          <div className="logo-name">
            Caru<span>4u</span>
          </div>
          <small>CLEANER RIDES, HAPPIER YOU</small>
        </div>
      </div>

      <nav className="navbar">
        <a className="active" href="/">Home</a>
        <a href="#products">Our Services</a>
        <a href="#how-it-works">How It Works</a>
        <a href="#about">About Us</a>
        <a href="#contact">Contact</a>
      </nav>

      <div className="header-actions">

        <div className="location">
          <MapPin size={17} />
          <span>Bengaluru⌄</span>
        </div>

        <button className="login-btn">
          Login
        </button>

        <button className="signup-btn">
          Sign Up
        </button>

      </div>

    </header>
  );
}

export default Header;