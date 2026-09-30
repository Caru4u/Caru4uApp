import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import "./CartPage.css";

function CartPage() {
  const navigate = useNavigate();

  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Temporary until JWT integration is completed
  const customerId = 1;

  useEffect(() => {
    fetchCart();
  }, []);

  // =========================================================
  // GET CART
  // =========================================================
  const fetchCart = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await axios.get(
        "http://localhost:8086/api/cart",
        {
          params: {
            customerId: customerId
          }
        }
      );

      console.log("CART RESPONSE:", response.data);

      setCart(response.data);

    } catch (error) {
      console.error("GET CART ERROR:", error);
      console.error("STATUS:", error.response?.status);
      console.error("RESPONSE:", error.response?.data);

      setError("Unable to load your cart.");
    } finally {
      setLoading(false);
    }
  };

  // =========================================================
  // REMOVE ITEM
  // =========================================================
  const removeItem = async (itemId) => {
    try {
      const response = await axios.delete(
        `http://localhost:8086/api/cart/items/${itemId}`,
        {
          params: {
            customerId: customerId
          }
        }
      );

      setCart(response.data);

    } catch (error) {
      console.error("REMOVE ITEM ERROR:", error);

      alert("Unable to remove item.");
    }
  };

  // =========================================================
  // CLEAR CART
  // =========================================================
  const clearCart = async () => {
    try {
      await axios.delete(
        "http://localhost:8086/api/cart",
        {
          params: {
            customerId: customerId
          }
        }
      );

      await fetchCart();

    } catch (error) {
      console.error("CLEAR CART ERROR:", error);

      alert("Unable to clear cart.");
    }
  };

  // =========================================================
  // MONEY FORMAT
  // =========================================================
  const formatPrice = (price) => {
    return Number(price || 0).toLocaleString(
      "en-IN",
      {
        maximumFractionDigits: 2
      }
    );
  };

  // =========================================================
  // IMAGE
  // =========================================================
  const getServiceImage = (item) => {
    /*
     * Replace these with your real images later.
     *
     * Put image at:
     * public/assets/images/cart-car.png
     */
    return "/assets/images/cart-car.png";
  };

  if (loading) {
    return (
      <div className="cart-status-page">
        <div className="cart-loader"></div>
        <h2>Loading your cart...</h2>
      </div>
    );
  }

  if (error) {
    return (
      <div className="cart-status-page">
        <h2>{error}</h2>

        <button onClick={fetchCart}>
          Try Again
        </button>
      </div>
    );
  }

  const items = cart?.items || [];

  return (
    <div className="cart-page">

      {/* =====================================================
          HEADER
      ===================================================== */}

      <header className="cart-header">

        <div
          className="cart-logo"
          onClick={() => navigate("/")}
        >
          <div className="logo-car">🚙</div>

          <div>
            <strong>
              Caru<span>4u</span>
            </strong>

            <small>
              CLEAN CARS. HAPPIER YOU
            </small>
          </div>
        </div>


        <nav className="cart-nav">

          <button onClick={() => navigate("/")}>
            Home
          </button>

          <button onClick={() => navigate("/car-wash")}>
            Our Services
          </button>

          <button>
            How It Works
          </button>

          <button>
            About Us
          </button>

          <button>
            Contact
          </button>

        </nav>


        <div className="header-actions">

          <span className="location">
            📍 Bengaluru⌄
          </span>

          <button
            className="header-cart-button"
            onClick={() => navigate("/cart")}
          >
            🛒

            {cart?.totalItems > 0 && (
              <span className="cart-count">
                {cart.totalItems}
              </span>
            )}
          </button>

          <button
            className="login-button"
            onClick={() => navigate("/login")}
          >
            Login
          </button>

          <button className="signup-button">
            Sign Up
          </button>

        </div>

      </header>


      {/* =====================================================
          HERO
      ===================================================== */}

      <section className="cart-hero">

        <div className="cart-hero-content">

          <div className="breadcrumb">

            <span onClick={() => navigate("/")}>
              Home
            </span>

            <b>›</b>

            <span>
              Cart
            </span>

          </div>

          <h1>
            Your Cart
          </h1>

          <p>
            Review your selected services and proceed
            to checkout.
          </p>

        </div>

        <div className="hero-decoration">
          <div>
            We Care
            <br />
            For Your Ride
          </div>

          <span>🚗✨</span>
        </div>

      </section>


      {/* =====================================================
          MAIN CART
      ===================================================== */}

      <main className="cart-main">

        {/* LEFT SIDE */}

        <section className="selected-services">

          <div className="section-title-row">

            <h2>
              Selected Services ({cart?.totalItems || 0})
            </h2>

            {items.length > 0 && (
              <button
                className="clear-cart-button"
                onClick={clearCart}
              >
                🗑 Clear Cart
              </button>
            )}

          </div>


          {/* EMPTY CART */}

          {items.length === 0 ? (

            <div className="empty-cart">

              <div className="empty-cart-icon">
                🛒
              </div>

              <h2>
                Your cart is empty
              </h2>

              <p>
                Add a service to get started.
              </p>

              <button
                onClick={() =>
                  navigate(
                    "/car-wash?vehicleType=HATCHBACK"
                  )
                }
              >
                Browse Services
              </button>

            </div>

          ) : (

            items.map((item) => (

              <div
                className="service-item"
                key={item.cartItemId}
              >

                <div className="service-image">

                  <img
                    src={getServiceImage(item)}
                    alt={item.packageName}
                    onError={(event) => {
                      event.currentTarget.style.display =
                        "none";
                    }}
                  />

                  <span className="image-fallback">
                    🚗
                  </span>

                </div>


                <div className="service-info">

                  <div className="service-top-row">

                    <div>

                      <h3>
                        Car Wash - {item.packageName}
                      </h3>

                      <p>
                        Professional car cleaning service
                        for a fresh and shiny look.
                      </p>

                    </div>


                    <div className="service-price">

                      ₹{formatPrice(item.totalPrice)}

                      <button
                        className="remove-button"
                        onClick={() =>
                          removeItem(item.cartItemId)
                        }
                        title="Remove service"
                      >
                        ×
                      </button>

                    </div>

                  </div>


                  <div className="service-details">

                    <span>
                      🚙 {item.vehicleType}
                    </span>

                    <span>
                      📅 {item.frequency}
                    </span>

                    <span>
                      ☷ Qty: {item.quantity}
                    </span>

                  </div>

                </div>

              </div>

            ))

          )}


          {/* ADD MORE */}

          <div className="add-more-services">

            <div className="add-icon">
              +
            </div>

            <div className="add-more-text">

              <strong>
                Add More Services
              </strong>

              <p>
                Explore our packages and add more to
                your cart.
              </p>

            </div>

            <button
              onClick={() =>
                navigate(
                  "/car-wash?vehicleType=HATCHBACK"
                )
              }
            >
              Browse Services
            </button>

          </div>

        </section>


        {/* =================================================
            RIGHT SIDE
        ================================================= */}

        <aside className="cart-sidebar">

          {/* ORDER SUMMARY */}

          <div className="summary-card">

            <h2>
              Order Summary
            </h2>


            <div className="summary-row">

              <span>
                Subtotal ({cart?.totalItems || 0} items)
              </span>

              <strong>
                ₹{formatPrice(cart?.subtotal)}
              </strong>

            </div>


            <div className="summary-row discount-row">

              <span>
                Discount
              </span>

              <strong>
                - ₹{formatPrice(cart?.discount)}
              </strong>

            </div>


            <div className="summary-divider"></div>


            <div className="total-row">

              <strong>
                Total
              </strong>

              <strong>
                ₹{formatPrice(cart?.total)}
              </strong>

            </div>


            <button
              className="checkout-button"
              disabled={items.length === 0}
              onClick={() => navigate("/booking")}
            >
              Proceed to Checkout
              <span>→</span>
            </button>


            <div className="safe-payment">
              🛡 Secure & Safe Payment
            </div>

          </div>


          {/* PROMO */}

          <div className="promo-card">

            <h3>
              🏷 Have a Promo Code?
            </h3>

            <div className="promo-input-row">

              <input
                type="text"
                placeholder="Enter promo code"
              />

              <button>
                Apply
              </button>

            </div>

          </div>


          {/* WHY CHOOSE */}

          <div className="why-card">

            <h3>
              Why Choose Caru4u?
            </h3>


            <div className="why-grid">

              <div className="why-item">

                <span>👤</span>

                <div>
                  <strong>
                    Trusted Professionals
                  </strong>

                  <small>
                    Verified & Trained
                  </small>
                </div>

              </div>


              <div className="why-item">

                <span>🍃</span>

                <div>
                  <strong>
                    Eco Friendly
                  </strong>

                  <small>
                    Products
                  </small>
                </div>

              </div>


              <div className="why-item">

                <span>📅</span>

                <div>
                  <strong>
                    Convenient Booking
                  </strong>

                  <small>
                    At your doorstep
                  </small>
                </div>

              </div>


              <div className="why-item">

                <span>🛡</span>

                <div>
                  <strong>
                    Quality Assurance
                  </strong>

                  <small>
                    100% Satisfaction
                  </small>
                </div>

              </div>

            </div>

          </div>

        </aside>

      </main>


      {/* =====================================================
          BOTTOM BENEFITS
      ===================================================== */}

      <section className="cart-benefits">

        <div className="benefit">

          <span>🍃</span>

          <div>
            <strong>
              Eco-Friendly
            </strong>

            <small>
              Care for a greener tomorrow
            </small>
          </div>

        </div>


        <div className="benefit">

          <span>👥</span>

          <div>
            <strong>
              1000+ Happy Customers
            </strong>

            <small>
              Our customers love us
            </small>
          </div>

        </div>


        <div className="benefit">

          <span>⭐</span>

          <div>
            <strong>
              Top Rated
            </strong>

            <small>
              4.8/5 average rating
            </small>
          </div>

        </div>


        <div className="benefit">

          <span>🎧</span>

          <div>
            <strong>
              24/7 Support
            </strong>

            <small>
              We're here for you
            </small>
          </div>

        </div>

      </section>

    </div>
  );
}

export default CartPage;