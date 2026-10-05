import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  getCheckout,
  placeOrder
} from "../../components/api/checkoutApi";

import {
  createPayment,
  verifyPayment
} from "../../components/api/paymentApi";

import { loadRazorpayScript } from "../../components/utils/razorpay";

import "./CheckoutPage.css";

const CheckoutPage = () => {

  const navigate = useNavigate();

  const [checkoutData, setCheckoutData] = useState(null);
  const [selectedAddress, setSelectedAddress] = useState(null);

  const [preferredDate, setPreferredDate] = useState("");
  const [preferredTime, setPreferredTime] = useState("");

  const [selectedPayment, setSelectedPayment] = useState("CARD");

  const [loading, setLoading] = useState(true);
  const [placingOrder, setPlacingOrder] = useState(false);
  const [error, setError] = useState("");


  // =====================================================
  // LOAD CHECKOUT
  // =====================================================

  useEffect(() => {
    loadCheckout();
  }, []);


  const loadCheckout = async () => {

    try {

      setLoading(true);
      setError("");

      const response = await getCheckout();

      console.log("Checkout response:", response);

      setCheckoutData(response);

      if (response?.address) {
        setSelectedAddress(response.address);
      }

    } catch (err) {

      console.error(err);

      setError(
        err.message ||
        "Unable to load checkout."
      );

    } finally {

      setLoading(false);

    }
  };


  // =====================================================
  // PRICE
  // =====================================================

  const calculateSubtotal = () => {

    if (!checkoutData?.items) {
      return 0;
    }

    return checkoutData.items.reduce(
      (total, item) =>
        total + Number(item.totalPrice || 0),
      0
    );
  };


  const calculateDiscount = () =>
    Number(checkoutData?.discount || 0);


  const calculateTotal = () => {

    if (
      checkoutData?.totalAmount !== undefined &&
      checkoutData?.totalAmount !== null
    ) {
      return Number(checkoutData.totalAmount);
    }

    return Math.max(
      calculateSubtotal() -
      calculateDiscount(),
      0
    );
  };


  const formatMoney = (amount) => {

    return Number(amount || 0)
      .toLocaleString("en-IN", {
        minimumFractionDigits: 0,
        maximumFractionDigits: 2
      });
  };


  // =====================================================
  // DATE
  // =====================================================

  const getMinimumDate = () => {

    const today = new Date();

    const year = today.getFullYear();

    const month =
      String(today.getMonth() + 1)
        .padStart(2, "0");

    const day =
      String(today.getDate())
        .padStart(2, "0");

    return `${year}-${month}-${day}`;
  };


  // =====================================================
  // PAYMENT
  // =====================================================

  const startRazorpayPayment = async (
    orderResponse
  ) => {

    try {

      const loaded =
        await loadRazorpayScript();

      if (!loaded) {
        throw new Error(
          "Unable to load Razorpay."
        );
      }


      const orderId =
        orderResponse?.orderId ??
        orderResponse?.id;


      if (!orderId) {
        throw new Error(
          "Order ID not received."
        );
      }


      const customerId =
        orderResponse?.customerId ??
        checkoutData?.customerId;


      const amount =
        orderResponse?.totalAmount ??
        calculateTotal();


      // ===============================================
      // CREATE RAZORPAY ORDER
      // ===============================================

      const paymentResponse =
        await createPayment(
          orderId,
          customerId,
          amount,
          selectedPayment
        );


      const options = {

        key:
          paymentResponse.keyId,

        amount:
          paymentResponse.amount,

        currency:
          paymentResponse.currency ||
          "INR",

        name:
          "Caru4u",

        description:
          `Payment for Order #${orderId}`,

        order_id:
          paymentResponse.razorpayOrderId,


        // =============================================
        // PAYMENT SUCCESS
        // =============================================

        handler: async function (
          razorpayResponse
        ) {

          try {

            await verifyPayment(
              razorpayResponse
            );


            navigate(
              `/order-success/${orderId}`,
              {
                replace: true,

                state: {

                  orderId,

                  customerId,

                  amount,

                  paymentId:
                    razorpayResponse
                      .razorpay_payment_id,

                  razorpayOrderId:
                    razorpayResponse
                      .razorpay_order_id,

                  paymentMethod:
                    selectedPayment,

                  preferredDate,

                  preferredTime,

                  paymentStatus:
                    "PAID",

                  orderStatus:
                    "CONFIRMED"
                }
              }
            );

          } catch (verifyError) {

            console.error(
              verifyError
            );

            alert(
              "Payment verification failed."
            );

            setPlacingOrder(false);
          }
        },


        prefill: {

          name:
            checkoutData?.customerName ||
            "",

          email:
            checkoutData?.email ||
            "",

          contact:
            checkoutData?.mobileNumber ||
            ""
        },


        theme: {
          color: "#1687f8"
        },


        modal: {

          ondismiss: function () {

            setPlacingOrder(false);

          }
        }
      };


      const razorpay =
        new window.Razorpay(options);


      // ===============================================
      // PAYMENT FAILED
      // ===============================================

      razorpay.on(
        "payment.failed",
        function (response) {

          console.error(
            response.error
          );

          alert(
            response?.error?.description ||
            "Payment failed. Please try again."
          );

          setPlacingOrder(false);
        }
      );


      razorpay.open();

    } catch (err) {

      console.error(err);

      alert(
        err.message ||
        "Unable to start payment."
      );

      setPlacingOrder(false);
    }
  };


  // =====================================================
  // PLACE ORDER
  // =====================================================

  const handlePlaceOrder = async () => {

    if (!selectedAddress) {

      alert(
        "Please select an address."
      );

      return;
    }


    if (!preferredDate) {

      alert(
        "Please select service date."
      );

      return;
    }


    if (!preferredTime) {

      alert(
        "Please select service time."
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


      const addressId =
        selectedAddress
          ?.apartmentOrVillaId ??
        selectedAddress
          ?.addressId ??
        selectedAddress
          ?.id;


      const request = {

        addressId,

        preferredDate,

        preferredTime,

        paymentMethod:
          selectedPayment
      };


      const orderResponse =
        await placeOrder(request);


      await startRazorpayPayment(
        orderResponse
      );

    } catch (err) {

      console.error(err);

      alert(
        err.message ||
        "Unable to place order."
      );

      setPlacingOrder(false);
    }
  };


  // =====================================================
  // PAYMENT OPTION
  // =====================================================

  const PaymentOption = ({
    value,
    icon,
    title,
    subtitle,
    logos
  }) => {

    const active =
      selectedPayment === value;

    return (

      <label
        className={
          active
            ? "reference-payment-option active"
            : "reference-payment-option"
        }
      >

        <input
          type="radio"
          name="payment"
          value={value}
          checked={active}
          onChange={(event) =>
            setSelectedPayment(
              event.target.value
            )
          }
        />

        <div className="payment-icon">
          {icon}
        </div>

        <div className="payment-copy">

          <strong>
            {title}
          </strong>

          <span>
            {subtitle}
          </span>

        </div>

        <div className="payment-logos">
          {logos}
        </div>

      </label>
    );
  };


  // =====================================================
  // LOADING
  // =====================================================

  if (loading) {

    return (
      <div className="checkout-screen-message">
        Loading checkout...
      </div>
    );
  }


  if (error) {

    return (

      <div className="checkout-screen-message">

        <h2>
          Unable to load checkout
        </h2>

        <p>{error}</p>

        <button onClick={loadCheckout}>
          Retry
        </button>

      </div>
    );
  }


  return (

    <div className="reference-checkout-page">


      {/* ================================================= */}
      {/* HEADER */}
      {/* ================================================= */}

      <header className="reference-header">

        <div
          className="reference-brand"
          onClick={() => navigate("/")}
        >

          <div className="brand-car">
            🚙
          </div>

          <div>
            <strong>
              Caru<span>4u</span>
            </strong>

            <small>
              CLEAN CAR. HAPPIER YOU
            </small>
          </div>

        </div>


        <nav>

          <button
            onClick={() => navigate("/")}
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


        <div className="header-actions">

          <span>
            📍 Bengaluru⌄
          </span>

          <button
            className="cart-header-button"
            onClick={() =>
              navigate("/cart")
            }
          >
            🛒
          </button>

          <button
            className="login-button"
            onClick={() =>
              navigate("/login")
            }
          >
            Login
          </button>

          <button className="signup-button">
            Sign Up
          </button>

        </div>

      </header>


      {/* ================================================= */}
      {/* HERO */}
      {/* ================================================= */}

      <section className="checkout-hero">

        <div>

          <div className="breadcrumbs">

            <span
              onClick={() =>
                navigate("/")
              }
            >
              Home
            </span>

            <b>›</b>

            <span
              onClick={() =>
                navigate("/cart")
              }
            >
              Cart
            </span>

            <b>›</b>

            <span>
              Checkout
            </span>

          </div>


          <h1>
            Checkout
          </h1>

          <p>
            Complete your booking and get
            ready for a cleaner ride!
          </p>

        </div>


        <div className="hero-message">

          <strong>
            We Care
            <br />
            For Your Ride
          </strong>

          <span>
            🚗 ✨
          </span>

        </div>

      </section>


      {/* ================================================= */}
      {/* PROGRESS */}
      {/* ================================================= */}

      <section className="checkout-progress">

        <div className="progress-line" />

        <div className="progress-step active">

          <div>1</div>

          <span>
            Address
          </span>

        </div>


        <div className="progress-step">

          <div>2</div>

          <span>
            Payment
          </span>

        </div>


        <div className="progress-step">

          <div>3</div>

          <span>
            Confirmation
          </span>

        </div>

      </section>


      {/* ================================================= */}
      {/* CONTENT */}
      {/* ================================================= */}

      <main className="reference-checkout-layout">


        {/* ================================================= */}
        {/* LEFT */}
        {/* ================================================= */}

        <div className="checkout-left-column">


          {/* ADDRESS */}

          <section className="reference-card">

            <div className="reference-section-heading">

              <div className="heading-icon">
                ♧
              </div>

              <div>

                <h2>
                  Delivery Address
                </h2>

                <p>
                  Where should we provide
                  the service?
                </p>

              </div>

              <button>
                + Add New Address
              </button>

            </div>


            {checkoutData?.address ? (

              <label className="reference-address active">

                <input
                  type="radio"
                  checked={
                    selectedAddress !== null
                  }
                  onChange={() =>
                    setSelectedAddress(
                      checkoutData.address
                    )
                  }
                />

                <div className="address-home-icon">
                  🏠
                </div>


                <div className="reference-address-info">

                  <strong>

                    {
                      checkoutData.address
                        .apartmentOrVillaName
                    }

                  </strong>

                  <span>

                    {
                      checkoutData.address
                        .blockOrCrossName
                    }

                    {
                      checkoutData.address
                        .plotNumber
                        ? `, Plot No: ${checkoutData.address.plotNumber}`
                        : ""
                    }

                    {
                      checkoutData.address
                        .areaName
                        ? `, ${checkoutData.address.areaName}`
                        : ""
                    }

                    {
                      checkoutData.address
                        .city
                        ? `, ${checkoutData.address.city}`
                        : ""
                    }

                    {
                      checkoutData.address
                        .pincode
                        ? ` - ${checkoutData.address.pincode}`
                        : ""
                    }

                  </span>

                </div>


                <button className="edit-address">
                  ✎ Edit
                </button>

              </label>

            ) : (

              <p>
                No delivery address found.
              </p>

            )}

          </section>


          {/* SCHEDULE */}

          <section className="reference-card">

            <div className="reference-section-heading">

              <div className="heading-icon">
                📅
              </div>

              <div>

                <h2>
                  Service Schedule
                </h2>

                <p>
                  Choose your preferred
                  service date and time
                </p>

              </div>

            </div>


            <div className="reference-schedule">

              <div>

                <label>
                  Preferred Date
                </label>

                <input
                  type="date"
                  min={getMinimumDate()}
                  value={preferredDate}
                  onChange={(event) =>
                    setPreferredDate(
                      event.target.value
                    )
                  }
                />

              </div>


              <div>

                <label>
                  Preferred Time
                </label>

                <select
                  value={preferredTime}
                  onChange={(event) =>
                    setPreferredTime(
                      event.target.value
                    )
                  }
                >

                  <option value="">
                    Select Time
                  </option>

                  <option value="08:00 AM - 10:00 AM">
                    08:00 AM - 10:00 AM
                  </option>

                  <option value="10:00 AM - 12:00 PM">
                    10:00 AM - 12:00 PM
                  </option>

                  <option value="12:00 PM - 02:00 PM">
                    12:00 PM - 02:00 PM
                  </option>

                  <option value="02:00 PM - 04:00 PM">
                    02:00 PM - 04:00 PM
                  </option>

                  <option value="04:00 PM - 06:00 PM">
                    04:00 PM - 06:00 PM
                  </option>

                </select>

              </div>

            </div>

          </section>


          {/* PAYMENT */}

          <section className="reference-card">

            <div className="reference-section-heading">

              <div className="heading-icon">
                ▣
              </div>

              <div>

                <h2>
                  Payment Method
                </h2>

                <p>
                  Choose your preferred
                  payment method
                </p>

              </div>

            </div>


            <div className="reference-payment-list">

              <PaymentOption
                value="CARD"
                icon="💳"
                title="Credit / Debit Card"
                subtitle="Visa, Mastercard, Rupay, Amex"
                logos="VISA  🔴🟠  RuPay"
              />


              <PaymentOption
                value="UPI"
                icon="UPI"
                title="UPI Payment"
                subtitle="Pay using any UPI app (GPay, PhonePe, Paytm, etc.)"
                logos="GPay  PhonePe  Paytm"
              />


              <PaymentOption
                value="NETBANKING"
                icon="🏦"
                title="Net Banking"
                subtitle="Pay using your bank account"
                logos="🏦  🏦  🏦"
              />


              <PaymentOption
                value="WALLET"
                icon="👛"
                title="Wallet"
                subtitle="Pay using your wallet balance"
                logos="Paytm"
              />

            </div>

          </section>

        </div>


        {/* ================================================= */}
        {/* ORDER SUMMARY */}
        {/* ================================================= */}

        <aside className="reference-summary">

          <div className="summary-heading">

            <h2>
              Order Summary
            </h2>

            <button
              onClick={() =>
                navigate("/cart")
              }
            >
              ✎ Edit Cart
            </button>

          </div>


          <div className="reference-order-items">

            {checkoutData?.items?.map(
              (item) => (

                <div
                  className="reference-order-item"
                  key={
                    item.cartItemId ??
                    item.id
                  }
                >

                  <div className="order-car-image">
                    🚗
                  </div>


                  <div className="order-item-copy">

                    <strong>

                      {
                        item.productName ||
                        "Car Wash"
                      }

                      {
                        item.packageName
                          ? ` - ${item.packageName}`
                          : ""
                      }

                    </strong>


                    <span>

                      {
                        item.vehicleType ||
                        ""
                      }

                      {
                        item.frequency
                          ? `   |   ${item.frequency}`
                          : ""
                      }

                    </span>


                    <small>

                      Qty: {
                        item.quantity || 1
                      }

                    </small>

                  </div>


                  <strong className="order-item-price">

                    ₹
                    {
                      formatMoney(
                        item.totalPrice
                      )
                    }

                  </strong>

                </div>

              )
            )}

          </div>


          <div className="reference-price-summary">

            <div>

              <span>
                Subtotal ({
                  checkoutData?.items?.length ||
                  0
                } items)
              </span>

              <strong>

                ₹
                {
                  formatMoney(
                    calculateSubtotal()
                  )
                }

              </strong>

            </div>


            <div>

              <span>
                Discount
              </span>

              <strong className="discount-value">

                - ₹
                {
                  formatMoney(
                    calculateDiscount()
                  )
                }

              </strong>

            </div>


            <div className="reference-total">

              <span>
                Total Amount
              </span>

              <strong>

                ₹
                {
                  formatMoney(
                    calculateTotal()
                  )
                }

              </strong>

            </div>

          </div>


          <button
            className="reference-place-order"
            disabled={placingOrder}
            onClick={handlePlaceOrder}
          >

            🔒

            {
              placingOrder
                ? " Processing..."
                : " Place Order"
            }

            <span>→</span>

          </button>


          <div className="safe-payment">
            🛡️ Secure & Safe Payment
          </div>


          <div className="important-information">

            <div className="info-icon">
              i
            </div>

            <div>

              <strong>
                Important Information
              </strong>

              <ul>
                <li>
                  Your booking will be
                  confirmed after successful
                  payment.
                </li>

                <li>
                  You will receive confirmation
                  after booking.
                </li>

                <li>
                  Our team will contact you
                  before the service.
                </li>
              </ul>

            </div>

          </div>

        </aside>

      </main>

    </div>
  );
};

export default CheckoutPage;