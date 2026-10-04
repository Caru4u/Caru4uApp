import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import "./CartPage.css";


function CartPage() {

  const navigate = useNavigate();


  // =========================================================
  // STATE
  // =========================================================

  const [cart, setCart] = useState(null);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");


  // =========================================================
  // CART SERVICE URL
  // =========================================================

  const CART_API =
    "http://localhost:8086/api/cart";


  // =========================================================
  // GET JWT AUTHORIZATION HEADER
  // =========================================================

  const getAuthHeaders = () => {

    const token =
      localStorage.getItem("token");


    console.log(
      "CART JWT TOKEN:",
      token
    );


    if (!token) {

      throw new Error(
        "Customer is not logged in"
      );
    }


    return {

      Authorization:
        `Bearer ${token}`,

      "Content-Type":
        "application/json"

    };
  };


  // =========================================================
  // LOAD CART WHEN PAGE OPENS
  // =========================================================

  useEffect(() => {

    fetchCart();

  }, []);


  // =========================================================
  // GET CART
  // =========================================================
  //
  // GET
  // http://localhost:8086/api/cart
  //
  // NO customerId parameter.
  //
  // customerId comes from JWT.
  // =========================================================

  const fetchCart = async () => {

    try {

      setLoading(true);

      setError("");


      console.log(
        "GET CART URL:",
        CART_API
      );


      const response =
        await axios.get(

          CART_API,

          {
            headers:
              getAuthHeaders()
          }

        );


      console.log(
        "CART RESPONSE:",
        response.data
      );


      setCart(
        response.data
      );


    } catch (error) {

      console.error(
        "GET CART ERROR:",
        error
      );


      console.error(
        "GET CART STATUS:",
        error.response?.status
      );


      console.error(
        "GET CART BACKEND RESPONSE:",
        error.response?.data
      );


      // -----------------------------------------
      // No JWT in localStorage
      // -----------------------------------------

      if (
        error.message ===
        "Customer is not logged in"
      ) {

        setError(
          "Please login to view your cart."
        );

        return;
      }


      // -----------------------------------------
      // Invalid / expired JWT
      // -----------------------------------------

      if (
        error.response?.status === 401 ||
        error.response?.status === 403
      ) {

        setError(
          "Your login session is invalid. Please login again."
        );

        return;
      }


      // -----------------------------------------
      // Other error
      // -----------------------------------------

      setError(
        "Unable to load your cart."
      );


    } finally {

      setLoading(false);

    }
  };


  // =========================================================
  // REMOVE ITEM
  // =========================================================
  //
  // DELETE
  // /api/cart/items/{itemId}
  //
  // customerId comes from JWT.
  // =========================================================

  const removeItem = async (itemId) => {

    try {

      console.log(
        "REMOVE CART ITEM:",
        itemId
      );


      const response =
        await axios.delete(

          `${CART_API}/items/${itemId}`,

          {
            headers:
              getAuthHeaders()
          }

        );


      console.log(
        "REMOVE ITEM RESPONSE:",
        response.data
      );


      setCart(
        response.data
      );


    } catch (error) {

      console.error(
        "REMOVE ITEM ERROR:",
        error
      );


      console.error(
        "REMOVE STATUS:",
        error.response?.status
      );


      console.error(
        "REMOVE RESPONSE:",
        error.response?.data
      );


      if (
        error.response?.status === 401 ||
        error.response?.status === 403
      ) {

        alert(
          "Your login session is invalid. Please login again."
        );

        navigate("/login");

        return;
      }


      alert(
        error.response?.data?.message ||
        "Unable to remove item."
      );

    }
  };


  // =========================================================
  // CLEAR CART
  // =========================================================
  //
  // DELETE
  // http://localhost:8086/api/cart
  //
  // customerId comes from JWT.
  // =========================================================

  const clearCart = async () => {

    try {

      console.log(
        "CLEAR CART"
      );


      await axios.delete(

        CART_API,

        {
          headers:
            getAuthHeaders()
        }

      );


      console.log(
        "CART CLEARED SUCCESSFULLY"
      );


      // Reload empty cart
      await fetchCart();


    } catch (error) {

      console.error(
        "CLEAR CART ERROR:",
        error
      );


      console.error(
        "CLEAR CART STATUS:",
        error.response?.status
      );


      console.error(
        "CLEAR CART RESPONSE:",
        error.response?.data
      );


      if (
        error.response?.status === 401 ||
        error.response?.status === 403
      ) {

        alert(
          "Your login session is invalid. Please login again."
        );

        navigate("/login");

        return;
      }


      alert(
        error.response?.data?.message ||
        "Unable to clear cart."
      );

    }
  };


  // =========================================================
  // MONEY FORMAT
  // =========================================================

  const formatPrice = (price) => {

    return Number(
      price || 0
    ).toLocaleString(

      "en-IN",

      {
        maximumFractionDigits: 2
      }

    );
  };


  // =========================================================
  // SERVICE IMAGE
  // =========================================================

  const getServiceImage = (item) => {

    return "/assets/images/cart-car.png";

  };


  // =========================================================
  // LOADING
  // =========================================================

  if (loading) {

    return (

      <div className="cart-status-page">

        <div className="cart-loader">
        </div>

        <h2>
          Loading your cart...
        </h2>

      </div>

    );
  }


  // =========================================================
  // ERROR
  // =========================================================

  if (error) {

    return (

      <div className="cart-status-page">

        <h2>
          {error}
        </h2>


        <button
          onClick={fetchCart}
        >

          Try Again

        </button>


        <button
          onClick={() =>
            navigate("/login")
          }
        >

          Login

        </button>

      </div>

    );
  }


  // =========================================================
  // CART ITEMS
  // =========================================================

  const items =
    cart?.items || [];


  // =========================================================
  // PAGE
  // =========================================================

  return (

    <div className="cart-page">


      {/* =====================================================
          HEADER
      ===================================================== */}


      <header className="cart-header">


        {/* LOGO */}

        <div

          className="cart-logo"

          onClick={() =>
            navigate("/")
          }

        >

          <div className="logo-car">
            🚙
          </div>


          <div>

            <strong>

              Caru<span>4u</span>

            </strong>


            <small>

              CLEAN CARS. HAPPIER YOU

            </small>

          </div>

        </div>



        {/* NAVIGATION */}

        <nav className="cart-nav">


          <button
            onClick={() =>
              navigate("/")
            }
          >

            Home

          </button>


          <button
            onClick={() =>
              navigate("/car-wash")
            }
          >

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



        {/* HEADER ACTIONS */}

        <div className="header-actions">


          <span className="location">

            📍 Bengaluru⌄

          </span>


          <button

            className="header-cart-button"

            onClick={() =>
              navigate("/cart")
            }

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

            onClick={() =>
              navigate("/login")
            }

          >

            Login

          </button>


          <button
            className="signup-button"
          >

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


            <span
              onClick={() =>
                navigate("/")
              }
            >

              Home

            </span>


            <b>
              ›
            </b>


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


          <span>
            🚗✨
          </span>


        </div>


      </section>



      {/* =====================================================
          MAIN CART
      ===================================================== */}


      <main className="cart-main">


        {/* =================================================
            LEFT SIDE
        ================================================= */}


        <section className="selected-services">


          <div className="section-title-row">


            <h2>

              Selected Services (
              {cart?.totalItems || 0}
              )

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



          {/* =================================================
              EMPTY CART
          ================================================= */}


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


            items.map(
              (item) => (


                <div

                  className="service-item"

                  key={
                    item.cartItemId
                  }

                >


                  {/* IMAGE */}

                  <div className="service-image">


                    <img

                      src={
                        getServiceImage(item)
                      }

                      alt={
                        item.packageName
                      }

                      onError={
                        (event) => {

                          event.currentTarget.style.display =
                            "none";

                        }
                      }

                    />


                    <span className="image-fallback">

                      🚗

                    </span>


                  </div>



                  {/* INFORMATION */}

                  <div className="service-info">


                    <div className="service-top-row">


                      <div>


                        <h3>

                          Car Wash -{" "}
                          {item.packageName}

                        </h3>


                        <p>

                          Professional car cleaning
                          service for a fresh and
                          shiny look.

                        </p>


                      </div>



                      <div className="service-price">


                        ₹
                        {formatPrice(
                          item.totalPrice
                        )}


                        <button

                          className="remove-button"

                          onClick={() =>
                            removeItem(
                              item.cartItemId
                            )
                          }

                          title="Remove service"

                        >

                          ×

                        </button>


                      </div>


                    </div>



                    {/* DETAILS */}

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


              )
            )


          )}



          {/* =================================================
              ADD MORE SERVICES
          ================================================= */}


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


          {/* =================================================
              ORDER SUMMARY
          ================================================= */}


          <div className="summary-card">


            <h2>

              Order Summary

            </h2>



            <div className="summary-row">


              <span>

                Subtotal (
                {cart?.totalItems || 0}
                {" "}
                items)

              </span>


              <strong>

                ₹
                {formatPrice(
                  cart?.subtotal
                )}

              </strong>


            </div>



            <div className="summary-row discount-row">


              <span>

                Discount

              </span>


              <strong>

                - ₹
                {formatPrice(
                  cart?.discount
                )}

              </strong>


            </div>



            <div className="summary-divider">
            </div>



            <div className="total-row">


              <strong>

                Total

              </strong>


              <strong>

                ₹
                {formatPrice(
                  cart?.total
                )}

              </strong>


            </div>



            {/* =================================================
                PROCEED TO CHECKOUT
            ================================================= */}


            <button

              className="checkout-button"

              disabled={
                items.length === 0
              }

              onClick={() =>
                navigate("/checkout")
              }

            >

              Proceed to Checkout

              <span>
                →
              </span>

            </button>



            <div className="safe-payment">

              🛡 Secure & Safe Payment

            </div>


          </div>



          {/* =================================================
              PROMO
          ================================================= */}


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



          {/* =================================================
              WHY CHOOSE
          ================================================= */}


          <div className="why-card">


            <h3>

              Why Choose Caru4u?

            </h3>



            <div className="why-grid">


              <div className="why-item">


                <span>
                  👤
                </span>


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


                <span>
                  🍃
                </span>


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


                <span>
                  📅
                </span>


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


                <span>
                  🛡
                </span>


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


          <span>
            🍃
          </span>


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


          <span>
            👥
          </span>


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


          <span>
            ⭐
          </span>


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


          <span>
            🎧
          </span>


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