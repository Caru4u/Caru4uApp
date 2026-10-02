import React, { useState } from "react";

import Header from "../../components/Header/Header";

import "./Login.css";

const Login = () => {

  const [showPassword, setShowPassword] =
    useState(false);

  const [identifier, setIdentifier] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [loading, setLoading] =
    useState(false);

  const [error, setError] =
    useState("");

  const [success, setSuccess] =
    useState("");


  /* =====================================================
     LOGIN
  ===================================================== */

  const handleLogin = async (event) => {

    event.preventDefault();

    setError("");
    setSuccess("");

    const loginIdentifier =
      identifier.trim();


    /* ======================================
       BASIC VALIDATION
    ====================================== */

    if (!loginIdentifier) {

      setError(
        "Please enter email ID or mobile number"
      );

      return;
    }


    if (!password.trim()) {

      setError(
        "Please enter password"
      );

      return;
    }


    try {

      setLoading(true);


      /* ======================================
         REQUEST BODY

         Java LoginRequest expects:

         identifier
         password
      ====================================== */

      const loginData = {

        identifier: loginIdentifier,

        password: password

      };


      console.log(
        "Login Request:",
        loginData
      );


      /* ======================================
         BACKEND LOGIN API
      ====================================== */

      const response = await fetch(
        "http://localhost:8080/auth/Customer/login",
        {

          method: "POST",

          headers: {

            "Content-Type":
              "application/json"

          },

          body:
            JSON.stringify(loginData)

        }
      );


      /* ======================================
         READ BACKEND RESPONSE
      ====================================== */

      const responseText =
        await response.text();


      let data = {};


      try {

        data = responseText
          ? JSON.parse(responseText)
          : {};

      } catch {

        data = {

          message:
            responseText

        };

      }


      console.log(
        "Login Status:",
        response.status
      );


      console.log(
        "Login Response:",
        data
      );


      /* ======================================
         LOGIN FAILED
      ====================================== */

      if (!response.ok) {

        /*
         * Backend should ideally return:
         *
         * {
         *   "message": "Email ID is not valid"
         * }
         *
         * OR
         *
         * {
         *   "message": "Mobile number is not valid"
         * }
         *
         * OR
         *
         * {
         *   "message": "Password is not valid"
         * }
         */

        if (data?.message) {

          setError(
            data.message
          );

        } else {

          setError(
            "Invalid email/mobile number or password"
          );

        }


        return;

      }


      /* ======================================
         LOGIN SUCCESS
      ====================================== */

      setSuccess(
        data?.message ||
        "Login successful!"
      );


      /* ======================================
         SAVE JWT TOKEN
      ====================================== */

      if (data?.token) {

        localStorage.setItem(
          "token",
          data.token
        );

      }


      /* ======================================
         SAVE CUSTOMER ID
      ====================================== */

      if (data?.customerId) {

        localStorage.setItem(
          "customerId",
          data.customerId
        );

      }


      /* ======================================
         SAVE CUSTOMER DETAILS
      ====================================== */

      localStorage.setItem(
        "customer",
        JSON.stringify(data)
      );


      /* ======================================
         REDIRECT HOME AFTER SUCCESS
      ====================================== */

      setTimeout(() => {

        window.location.href = "/";

      }, 1500);


    } catch (err) {

      console.error(
        "Login Error:",
        err
      );


      setError(
        "Unable to connect to server. Please try again."
      );


    } finally {

      setLoading(false);

    }

  };


  return (

    <div className="login-page">


      {/* ================================================
          HEADER
      ================================================= */}

      <Header />


      {/* ================================================
          MAIN LOGIN AREA
      ================================================= */}

      <main className="login-main">


        <section className="login-container">


          {/* ================================================
              LEFT SIDE
          ================================================= */}

          <div className="login-visual">


            {/* BACKGROUND IMAGE */}

            <img

              src={`${process.env.PUBLIC_URL}/images/login-car.png`}

              alt="Caru4u professional car cleaning"

              className="login-car-image"

            />


            {/* LEFT CONTENT */}

            <div className="visual-content">


              {/* TITLE */}

              <h1>

                Welcome Back

                <br />

                to{" "}

                <span>
                  Caru4u!
                </span>

              </h1>


              {/* DESCRIPTION */}

              <p className="visual-description">

                Log in to manage your bookings,

                <br />

                view services, and keep your car

                <br />

                shining with ease.

              </p>


              {/* FEATURES */}

              <div className="benefits">


                {/* FEATURE 1 */}

                <div className="benefit-item">


                  <div className="benefit-icon">

                    📅

                  </div>


                  <div>

                    <h4>
                      Hassle-free Booking
                    </h4>

                    <p>
                      Schedule in seconds
                    </p>

                  </div>


                </div>


                {/* FEATURE 2 */}

                <div className="benefit-item">


                  <div className="benefit-icon">

                    🍃

                  </div>


                  <div>

                    <h4>
                      Eco Friendly
                    </h4>

                    <p>
                      Safe for your car & planet
                    </p>

                  </div>


                </div>


                {/* FEATURE 3 */}

                <div className="benefit-item">


                  <div className="benefit-icon">

                    🛡

                  </div>


                  <div>

                    <h4>
                      Trusted Professionals
                    </h4>

                    <p>
                      Trained, verified, and reliable
                    </p>

                  </div>


                </div>


                {/* FEATURE 4 */}

                <div className="benefit-item">


                  <div className="benefit-icon yellow">

                    ★

                  </div>


                  <div>

                    <h4>
                      A Cleaner, Happier You
                    </h4>

                    <p>
                      Every ride, every time
                    </p>

                  </div>


                </div>


              </div>


              {/* CARE TEXT */}

              <div className="care-message">

                We Care

                <br />

                For Your Ride

              </div>


            </div>


          </div>


          {/* ================================================
              RIGHT SIDE
          ================================================= */}

          <div className="login-form-section">


            <div className="login-form-wrapper">


              {/* LOGO */}

              <img

                src={`${process.env.PUBLIC_URL}/images/logo.png`}

                alt="Caru4u"

                className="form-logo"

              />


              {/* LOGIN TITLE */}

              <h2>

                Login to Your Account

              </h2>


              <p className="login-subtitle">

                Glad to see you again!
                Please enter your details to continue.

              </p>


              {/* ================================================
                  LOGIN FORM
              ================================================= */}

              <form onSubmit={handleLogin}>


                {/* EMAIL OR MOBILE NUMBER */}

                <div className="auth-input">


                  <span className="input-icon">

                    👤

                  </span>


                  <input

                    type="text"

                    placeholder="Email address or mobile number"

                    value={identifier}

                    onChange={(event) => {

                      setIdentifier(
                        event.target.value
                      );

                      setError("");
                      setSuccess("");

                    }}

                    autoComplete="username"

                    required

                  />


                </div>


                {/* PASSWORD */}

                <div className="auth-input">


                  <span className="input-icon">

                    🔒

                  </span>


                  <input

                    type={
                      showPassword
                        ? "text"
                        : "password"
                    }

                    placeholder="Password"

                    value={password}

                    onChange={(event) => {

                      setPassword(
                        event.target.value
                      );

                      setError("");
                      setSuccess("");

                    }}

                    autoComplete="current-password"

                    required

                  />


                  {/* SHOW/HIDE PASSWORD */}

                  <button

                    type="button"

                    className="eye-button"

                    onClick={() =>
                      setShowPassword(
                        !showPassword
                      )
                    }

                  >

                    {
                      showPassword
                        ? "🙈"
                        : "👁"
                    }

                  </button>


                </div>


                {/* ================================================
                    ERROR MESSAGE
                ================================================= */}

                {
                  error && (

                    <div className="login-error">

                      <span className="message-icon">
                        ✕
                      </span>

                      <span>
                        {error}
                      </span>

                    </div>

                  )
                }


                {/* ================================================
                    SUCCESS MESSAGE
                ================================================= */}

                {
                  success && (

                    <div className="login-success">

                      <span className="message-icon">
                        ✓
                      </span>

                      <span>
                        {success}
                      </span>

                    </div>

                  )
                }


                {/* LOGIN BUTTON */}

                <button

                  type="submit"

                  className="main-login-btn"

                  disabled={loading}

                >

                  {
                    loading
                      ? "Logging in..."
                      : "Login"
                  }

                </button>


              </form>


              {/* ================================================
                  OR
              ================================================= */}

              <div className="separator">


                <span></span>


                <p>
                  OR
                </p>


                <span></span>


              </div>


              {/* ================================================
                  GOOGLE LOGIN
              ================================================= */}

              <button

                type="button"

                className="social-login"

              >


                <span className="google-icon">

                  G

                </span>


                Continue with Google


              </button>


              {/* ================================================
                  APPLE LOGIN
              ================================================= */}

              <button

                type="button"

                className="social-login"

              >


                <span className="apple-icon">

                  ●

                </span>


                Continue with Apple


              </button>


            </div>


          </div>


        </section>


      </main>


      {/* ================================================
          BOTTOM STATS
      ================================================= */}

      <section className="login-stats">


        {/* CUSTOMERS */}

        <div className="stat-item">


          <div className="stat-icon">

            👥

          </div>


          <div>

            <strong>
              10K+
            </strong>

            <span>
              Happy Customers
            </span>

          </div>


        </div>


        {/* RATING */}

        <div className="stat-item">


          <div className="stat-icon">

            ⭐

          </div>


          <div>

            <strong>
              4.8
            </strong>

            <span>
              Average Rating
            </span>

          </div>


        </div>


        {/* PARTNERS */}

        <div className="stat-item">


          <div className="stat-icon">

            👨‍🔧

          </div>


          <div>

            <strong>
              50+
            </strong>

            <span>
              Service Partners
            </span>

          </div>


        </div>


        {/* SATISFACTION */}

        <div className="stat-item">


          <div className="stat-icon">

            🛡️

          </div>


          <div>

            <strong>
              100%
            </strong>

            <span>
              Satisfaction Guarantee
            </span>

          </div>


        </div>


      </section>


    </div>

  );

};


export default Login;