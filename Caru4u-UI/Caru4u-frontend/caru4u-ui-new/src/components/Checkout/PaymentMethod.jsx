import React from "react";


const PaymentMethod = ({
  selectedPayment,
  setSelectedPayment
}) => {


  const paymentMethods = [

    {
      id: "CARD",

      icon: "💳",

      title:
        "Credit / Debit Card",

      description:
        "Visa, Mastercard, Rupay, Amex"
    },


    {
      id: "UPI",

      icon: "UPI",

      title:
        "UPI Payment",

      description:
        "Pay using any UPI app (GPay, PhonePe, Paytm, etc.)"
    },


    {
      id: "NET_BANKING",

      icon: "🏦",

      title:
        "Net Banking",

      description:
        "Pay using your bank account"
    },


    {
      id: "WALLET",

      icon: "👛",

      title:
        "Wallet",

      description:
        "Pay using your wallet balance"
    }

  ];


  return (

    <section className="checkout-box payment-box">


      <div className="checkout-box-header">


        <div>

          <h2>
            💳 Payment Method
          </h2>

          <p>
            Choose your preferred
            payment method
          </p>

        </div>


      </div>


      <div className="payment-list">


        {
          paymentMethods.map(
            (payment) => {


              const selected =
                selectedPayment ===
                payment.id;


              return (

                <div

                  key={
                    payment.id
                  }

                  className={
                    `payment-row ${
                      selected
                        ? "selected"
                        : ""
                    }`
                  }

                  onClick={() =>
                    setSelectedPayment(
                      payment.id
                    )
                  }

                >


                  <input

                    type="radio"

                    name="paymentMethod"

                    checked={
                      selected
                    }

                    onChange={() =>
                      setSelectedPayment(
                        payment.id
                      )
                    }

                  />


                  <div className="payment-icon">

                    {
                      payment.icon
                    }

                  </div>


                  <div className="payment-content">


                    <strong>

                      {
                        payment.title
                      }

                    </strong>


                    <span>

                      {
                        payment.description
                      }

                    </span>


                  </div>


                  {
                    payment.id ===
                      "CARD" && (

                      <div className="payment-brands">

                        VISA &nbsp;
                        Mastercard &nbsp;
                        RuPay

                      </div>

                    )
                  }


                </div>
              );
            }
          )
        }


      </div>


    </section>
  );
};


export default PaymentMethod;