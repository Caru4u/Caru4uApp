import React from "react";
import { useNavigate } from "react-router-dom";

const OrderSummary = ({ cart }) => {

  const navigate = useNavigate();

  const price = (value) => {

    return Number(value || 0)
      .toLocaleString("en-IN");

  };


  const handleCheckout = () => {

    navigate("/checkout");

  };


  return (

    <div className="order-summary">

      <h2>Order Summary</h2>


      <div className="summary-line">

        <span>
          Subtotal ({cart.totalItems || 0} items)
        </span>

        <span>
          ₹{price(cart.subtotal)}
        </span>

      </div>


      <div className="summary-line">

        <span>
          Discount
        </span>

        <span className="discount">
          - ₹{price(cart.discount)}
        </span>

      </div>


      <hr />


      <div className="summary-total">

        <span>Total</span>

        <span>
          ₹{price(cart.total)}
        </span>

      </div>


      <button
        className="checkout-button"
        disabled={!cart.items?.length}
        onClick={handleCheckout}
      >
        Proceed to Checkout
        <span>→</span>
      </button>


      <p className="secure-text">
        🛡 Secure & Safe Payment
      </p>

    </div>
  );
};

export default OrderSummary;