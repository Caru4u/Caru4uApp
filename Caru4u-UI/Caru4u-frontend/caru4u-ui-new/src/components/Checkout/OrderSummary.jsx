import React from "react";

import {
  useNavigate
} from "react-router-dom";


const OrderSummary = ({
  checkoutData,
  onPlaceOrder,
  placingOrder
}) => {


  const navigate =
    useNavigate();


  const items =
    checkoutData?.items || [];


  // ==========================================
  // SUBTOTAL
  // ==========================================

  const calculatedSubtotal =
    items.reduce(
      (total, item) => {

        return (
          total +
          Number(
            item.totalPrice || 0
          )
        );

      },
      0
    );


  const subtotal =
    checkoutData?.subtotal != null

      ? Number(
          checkoutData.subtotal
        )

      : calculatedSubtotal;


  // ==========================================
  // DISCOUNT
  // ==========================================

  const discount =
    Number(
      checkoutData?.discount || 0
    );


  // ==========================================
  // TOTAL
  // ==========================================

  const total =

    checkoutData?.total != null

      ? Number(
          checkoutData.total
        )

      : subtotal - discount;


  // ==========================================
  // PRICE FORMAT
  // ==========================================

  const formatPrice = (
    value
  ) => {

    return new Intl.NumberFormat(
      "en-IN",
      {
        style: "currency",

        currency: "INR",

        maximumFractionDigits: 0
      }
    ).format(
      Number(value || 0)
    );
  };


  return (

    <aside className="order-summary">


      {/* HEADER */}

      <div className="order-summary-header">


        <h2>
          Order Summary
        </h2>


        <button
          type="button"

          onClick={() =>
            navigate("/cart")
          }
        >

          ✎ Edit Cart

        </button>


      </div>


      {/* ITEMS */}

      <div className="summary-items">


        {
          items.map(
            (item, index) => (

              <div

                className="summary-item"

                key={
                  item.cartItemId ||
                  item.itemId ||
                  index
                }

              >


                <div className="summary-car">

                  🚗

                </div>


                <div className="summary-info">


                  <strong>

                    {
                      item.productName ||
                      "Car Wash"
                    }

                    {" - "}

                    {
                      item.packageName
                    }

                  </strong>


                  <span>

                    {
                      item.vehicleType
                    }

                    {" | "}

                    {
                      item.frequency
                    }

                  </span>


                  <small>

                    Qty: {
                      item.quantity
                    }

                  </small>


                </div>


                <div className="summary-item-price">

                  {
                    formatPrice(
                      item.totalPrice
                    )
                  }

                </div>


              </div>

            )
          )
        }


      </div>


      {/* PRICE */}

      <div className="summary-line" />


      <div className="price-row">

        <span>

          Subtotal (
          {
            checkoutData
              ?.itemCount ??
            items.length
          } items)

        </span>


        <strong>

          {
            formatPrice(
              subtotal
            )
          }

        </strong>

      </div>


      <div className="price-row">


        <span>
          Discount
        </span>


        <strong className="discount">

          - {
            formatPrice(
              discount
            )
          }

        </strong>


      </div>


      <div className="summary-line" />


      <div className="total-row">


        <strong>
          Total Amount
        </strong>


        <strong className="total-price">

          {
            formatPrice(
              total
            )
          }

        </strong>


      </div>


      {/* PLACE ORDER */}

      <button

        type="button"

        className="place-order-btn"

        onClick={
          onPlaceOrder
        }

        disabled={
          placingOrder ||
          items.length === 0
        }

      >


        {
          placingOrder

            ? "Placing Order..."

            : "🔒 Place Order  →"
        }


      </button>


      <div className="secure-text">

        🛡 Secure & Safe Payment

      </div>


      {/* INFO */}

      <div className="important-box">


        <strong>

          ⓘ Important Information

        </strong>


        <ul>

          <li>

            Your booking will be
            confirmed after successful
            payment.

          </li>

          <li>

            You will receive an SMS
            and Email confirmation.

          </li>

          <li>

            Our team will contact you
            before the service.

          </li>

          <li>

            For any queries, contact
            our support team.

          </li>

        </ul>


      </div>


    </aside>
  );
};


export default OrderSummary;