import React from "react";
import "./Footer.scss";

function Footer() {
  return (
    <footer className="footer">
      <div className="footer-top">
        <ul className="footer-links">
          <li><a href="/terms">Terms Of Use</a></li>
          <li><a href="/refund-policy">Refund & Cancellation Policy</a></li>
          <li><a href="/support">Help And Support</a></li>
        </ul>
      </div>

      <div className="footer-middle">
        <ul className="footer-emp">
          <li><a href="/emp-login">EMP Login</a></li>
          <li><a href="/emp-register">EMP Register</a></li>
        </ul>
      </div>

      <div className="footer-bottom">
        <p>©2026 Luffa Import and Export LLP. All rights reserved.</p>
      </div>
    </footer>
  );
}

export default Footer;
