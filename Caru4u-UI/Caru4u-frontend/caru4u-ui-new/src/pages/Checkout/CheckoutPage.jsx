import React, {
  useEffect,
  useState
} from "react";

import {
  useNavigate
} from "react-router-dom";

import Header
  from "../../components/Header/Header";

import CheckoutSteps
  from "../../components/Checkout/CheckoutSteps";

import DeliveryAddress
  from "../../components/Checkout/DeliveryAddress";

import PaymentMethod
  from "../../components/Checkout/PaymentMethod";

import OrderSummary
  from "../../components/Checkout/OrderSummary";

import {
  getCheckout,
  placeOrder
} from "../../components/api/checkoutApi";

import "./CheckoutPage.css";


const CheckoutPage = () => {

  const navigate = useNavigate();


  // ==========================================
  // STATE
  // ==========================================

  const [checkoutData, setCheckoutData] =
    useState(null);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [
    selectedAddress,
    setSelectedAddress
  ] = useState(null);

  const [
    selectedPayment,
    setSelectedPayment
  ] = useState("CARD");

  const [
    placingOrder,
    setPlacingOrder
  ] = useState(false);


  // ==========================================
  // LOAD CHECKOUT
  // ==========================================

  useEffect(() => {

    loadCheckout();

  }, []);


  const loadCheckout = async () => {

    try {

      setLoading(true);
      setError("");

      const response =
        await getCheckout();

      console.log(
        "FULL CHECKOUT RESPONSE:",
        response
      );

      console.log(
        "CHECKOUT ADDRESS:",
        response?.address
      );

      console.log(
        "CHECKOUT ITEMS:",
        response?.items
      );

      setCheckoutData(response);


      // Automatically select address
      if (response?.address) {

        setSelectedAddress(
          response.address
        );
      }

    } catch (error) {

      console.error(
        "Checkout load error:",
        error
      );

      setError(
        error.message ||
        "Unable to load checkout."
      );

    } finally {

      setLoading(false);
    }
  };


  // ==========================================
  // PLACE ORDER
  // ==========================================

  const handlePlaceOrder = async () => {

    if (!checkoutData) {

      alert(
        "Checkout information not available."
      );

      return;
    }


    if (!selectedAddress) {

      alert(
        "Please select delivery address."
      );

      return;
    }


    if (!selectedPayment) {

      alert(
        "Please select payment method."
      );

      return;
    }


    try {

      setPlacingOrder(true);


      // ---------------------------------------
      // This must match PlaceOrderRequest.java
      // ---------------------------------------

      const request = {

        addressId:
          selectedAddress
            .apartmentOrVillaId,

        paymentMethod:
          selectedPayment

        /*
        Add these when you add date/time UI:

        preferredDate:
          selectedDate,

        preferredTime:
          selectedTime
        */
      };


      console.log(
        "PLACE ORDER REQUEST:",
        request
      );


      const response =
        await placeOrder(request);


      console.log(
        "ORDER CREATED:",
        response
      );


      navigate(
        "/order-confirmation",
        {
          state: {
            order: response
          }
        }
      );

    } catch (error) {

      console.error(
        "Place order failed:",
        error
      );

      alert(
        error.message ||
        "Unable to place order."
      );

    } finally {

      setPlacingOrder(false);
    }
  };


  // ==========================================
  // LOADING
  // ==========================================

  if (loading) {

    return (

      <>

        <Header />

        <div className="checkout-message">

          <div className="checkout-loader"></div>

          <p>
            Loading checkout...
          </p>

        </div>

      </>
    );
  }


  // ==========================================
  // ERROR
  // ==========================================

  if (error) {

    return (

      <>

        <Header />

        <div className="checkout-message error">

          <h3>
            Unable to load checkout
          </h3>

          <p>
            {error}
          </p>

          <button
            onClick={loadCheckout}
          >
            Try Again
          </button>

        </div>

      </>
    );
  }


  // ==========================================
  // PAGE
  // ==========================================

  return (

    <div className="checkout-page">

      <Header />


      {/* ====================================
          HERO
      ==================================== */}

      <section className="checkout-hero">


        <div className="checkout-hero-left">


          <div className="breadcrumbs">

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


            <span
              onClick={() =>
                navigate("/cart")
              }
            >
              Cart
            </span>


            <b>
              ›
            </b>


            <span>
              Checkout
            </span>

          </div>


          <h1>
            Checkout
          </h1>


          <p>
            Complete your booking and
            get ready for a cleaner ride!
          </p>

        </div>


        <div className="checkout-hero-design">

          <strong>

            We Care

            <br />

            For Your Ride

          </strong>


          <span className="checkout-car">
            🚗
          </span>


          <span className="checkout-stars">
            ✨
          </span>

        </div>

      </section>


      {/* ====================================
          STEPS
      ==================================== */}

      <CheckoutSteps />


      {/* ====================================
          MAIN
      ==================================== */}

      <main className="checkout-layout">


        {/* LEFT */}

        <div className="checkout-left">


          <DeliveryAddress

            address={
              checkoutData?.address
            }

            selectedAddress={
              selectedAddress
            }

            setSelectedAddress={
              setSelectedAddress
            }

          />


          <PaymentMethod

            selectedPayment={
              selectedPayment
            }

            setSelectedPayment={
              setSelectedPayment
            }

          />

        </div>


        {/* RIGHT */}

        <OrderSummary

          checkoutData={
            checkoutData
          }

          onPlaceOrder={
            handlePlaceOrder
          }

          placingOrder={
            placingOrder
          }

        />


      </main>

    </div>
  );
};

export default CheckoutPage;