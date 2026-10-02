import React, { useEffect, useState } from "react";
import axios from "axios";

import Header from "../../components/Header/Header";
import ServiceCard from "../../components/ServiceCard/ServiceCard";

import "./Home.css";

function Home() {
  const [services, setServices] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadProducts = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await axios.get(
          "http://localhost:8082/api/products/list"
        );

        const products = response.data.map((product) => {
          let path = "/";

          if (product.code === "CAR_WASH") {
            path = "/car-wash?vehicleType=HATCHBACK";
          } else if (product.code === "BIKE_WASH") {
            path = "/bike-wash";
          } else if (product.code === "MAINTENANCE") {
            path = "/maintenance";
          }

          return {
            id: product.id,
            code: product.code,
            name: product.name,
            description: product.description,
            image: product.imageUrl,
            bannerImageUrl: product.bannerImageUrl,
            path
          };
        });

        setServices(products);
      } catch (err) {
        console.error("Product API error:", err);
        setError("Unable to load products.");
      } finally {
        setLoading(false);
      }
    };

    loadProducts();
  }, []);

  const carWashProduct = services.find(
    (service) => service.code === "CAR_WASH"
  );

  return (
    <div className="home-page">
      <Header />

      {/* HERO */}
      <section
        className="hero-section"
        style={{
          backgroundImage: carWashProduct?.bannerImageUrl
            ? `
              linear-gradient(
                90deg,
                rgba(235,248,255,0.98) 0%,
                rgba(235,248,255,0.96) 28%,
                rgba(235,248,255,0.70) 42%,
                rgba(235,248,255,0.22) 58%,
                rgba(235,248,255,0.00) 72%
              ),
              url("${carWashProduct.bannerImageUrl}")
            `
            : "linear-gradient(90deg,#eaf8ff,#ffffff)"
        }}
      >
        <div className="hero-copy">
          <h1>
            A Cleaner Car
            <br />
            <span>A Happier You</span>
          </h1>

          <p className="hero-subtitle">
            Professional car care at your doorstep.
          </p>

          <div className="hero-features">
            <div className="hero-feature">
              <div className="hero-feature-icon">👤</div>
              <div className="hero-feature-text">
                Trusted
                <br />
                Professionals
              </div>
            </div>

            <div className="hero-feature">
              <div className="hero-feature-icon">🌿</div>
              <div className="hero-feature-text">
                Eco Friendly
                <br />
                Products
              </div>
            </div>

            <div className="hero-feature">
              <div className="hero-feature-icon">📅</div>
              <div className="hero-feature-text">
                Convenient
                <br />
                Booking
              </div>
            </div>

            <div className="hero-feature">
              <div className="hero-feature-icon">🛡️</div>
              <div className="hero-feature-text">
                Quality
                <br />
                Assurance
              </div>
            </div>
          </div>

          <button
            className="hero-book-btn"
            onClick={() =>
              document
                .getElementById("products")
                ?.scrollIntoView({ behavior: "smooth" })
            }
          >
            Book a Service
            <span>→</span>
          </button>
        </div>
      </section>

      {/* PRODUCTS */}
      <section className="products-section" id="products">
        <div className="section-heading">
          <h2>Our Products</h2>
          <p>Choose the service you need</p>
        </div>

        {loading && (
          <div className="loading-message">
            Loading products...
          </div>
        )}

        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        {!loading && !error && (
          <div className="service-grid">
            {services.map((service) => (
              <ServiceCard
                key={service.id}
                service={service}
              />
            ))}
          </div>
        )}
      </section>

      {/* STATS */}
      <section className="stats-strip">
        <div className="stat-item">
          <span className="stat-icon">👥</span>
          <div>
            <h3>10K+</h3>
            <p>Happy Customers</p>
          </div>
        </div>

        <div className="stat-item">
          <span className="stat-icon">⭐</span>
          <div>
            <h3>4.8</h3>
            <p>Average Rating</p>
          </div>
        </div>

        <div className="stat-item">
          <span className="stat-icon">👥</span>
          <div>
            <h3>50+</h3>
            <p>Service Partners</p>
          </div>
        </div>

        <div className="stat-item">
          <span className="stat-icon">🛡️</span>
          <div>
            <h3>100%</h3>
            <p>Satisfaction Guarantee</p>
          </div>
        </div>
      </section>

      {/* HOW IT WORKS */}
      <section className="how-section">
        <div className="section-heading">
          <h2>How It Works</h2>
          <p>
            Get your vehicle cleaned in just a few easy steps
          </p>
        </div>

        <div className="steps-row">
          <div className="step-item">
            <div className="step-number">1</div>
            <div className="step-circle">📅</div>
            <div>
              <h3>Book a Service</h3>
              <p>
                Choose your service and schedule a time
              </p>
            </div>
          </div>

          <div className="step-arrow">→</div>

          <div className="step-item">
            <div className="step-number">2</div>
            <div className="step-circle">👨‍🔧</div>
            <div>
              <h3>We Arrive</h3>
              <p>
                Our professional team comes to your location
              </p>
            </div>
          </div>

          <div className="step-arrow">→</div>

          <div className="step-item">
            <div className="step-number">3</div>
            <div className="step-circle">🚗</div>
            <div>
              <h3>We Clean</h3>
              <p>
                Your vehicle gets cleaned with eco-friendly products
              </p>
            </div>
          </div>

          <div className="step-arrow">→</div>

          <div className="step-item">
            <div className="step-number">4</div>
            <div className="step-circle">👍</div>
            <div>
              <h3>You Enjoy</h3>
              <p>
                Drive a cleaner and happier vehicle
              </p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;