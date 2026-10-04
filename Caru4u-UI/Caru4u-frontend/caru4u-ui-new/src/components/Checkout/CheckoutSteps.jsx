import React from "react";


const CheckoutSteps = () => {

  return (

    <div className="checkout-steps">


      <div className="checkout-progress-line" />


      <div className="checkout-step active">

        <div className="step-number">
          1
        </div>

        <span>
          Address
        </span>

      </div>


      <div className="checkout-step">

        <div className="step-number">
          2
        </div>

        <span>
          Payment
        </span>

      </div>


      <div className="checkout-step">

        <div className="step-number">
          3
        </div>

        <span>
          Confirmation
        </span>

      </div>


    </div>
  );
};


export default CheckoutSteps;