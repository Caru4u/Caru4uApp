import {
  UserRound,
  Leaf,
  CalendarDays,
  ShieldCheck
} from "lucide-react";

function Hero() {

  return (
    <section className="hero">

      <div className="hero-content">

        <h1>
          A Cleaner Car
          <br />
          A <span>Happier You</span>
        </h1>

        <p className="hero-subtitle">
          Professional car care at your doorstep
        </p>

        <div className="benefits">

          <div className="benefit">
            <div className="benefit-icon">
              <UserRound />
            </div>
            <span>Trusted<br />Professionals</span>
          </div>

          <div className="benefit">
            <div className="benefit-icon">
              <Leaf />
            </div>
            <span>Eco Friendly<br />Products</span>
          </div>

          <div className="benefit">
            <div className="benefit-icon">
              <CalendarDays />
            </div>
            <span>Convenient<br />Booking</span>
          </div>

          <div className="benefit">
            <div className="benefit-icon">
              <ShieldCheck />
            </div>
            <span>Quality<br />Assurance</span>
          </div>

        </div>

        <button className="book-btn">
          Book a Service
          <span>→</span>
        </button>

      </div>

      <div className="hero-image">

        <img
          src="/images/hero-car-wash.jpg"
          alt="Caru4u doorstep car washing"
        />

        <div className="hero-note">
          We Care
          <br />
          For Your Ride
        </div>

      </div>

    </section>
  );
}

export default Hero;