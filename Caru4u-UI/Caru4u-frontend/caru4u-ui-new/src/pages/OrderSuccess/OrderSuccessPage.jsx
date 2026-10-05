import React from "react";

import {
  useLocation,
  useNavigate,
  useParams
} from "react-router-dom";

import "./OrderSuccessPage.css";


const OrderSuccessPage = () => {

  const navigate = useNavigate();

  const location = useLocation();

  const { orderId } = useParams();


  const details =
    location.state;


  if (!details) {

    return (

      <div className="order-success-page">

        <div className="order-success-card">

          <h2>
            Order #{orderId}
          </h2>

          <p>
            Booking details are not
            available after refreshing
            this page.
          </p>

          <button
            onClick={() =>
              navigate("/")
            }
          >
            Go Home
          </button>

        </div>

      </div>

    );

  }


  return (

    <div className="order-success-page">


      <div className="order-success-card">


        <div className="success-check">

          ✓

        </div>


        <h1>
          Payment Successful!
        </h1>


        <p className="success-description">

          Your Caru4u service has been
          booked successfully.

        </p>


        <div className="success-details">


          <div>

            <span>
              Order ID
            </span>

            <strong>
              #{details.orderId}
            </strong>

          </div>


          <div>

            <span>
              Payment ID
            </span>

            <strong>
              {details.paymentId}
            </strong>

          </div>


          <div>

            <span>
              Amount Paid
            </span>

            <strong>

              ₹
              {
                Number(
                  details.amount || 0
                ).toLocaleString(
                  "en-IN",
                  {
                    minimumFractionDigits: 2
                  }
                )
              }

            </strong>

          </div>


          <div>

            <span>
              Payment Method
            </span>

            <strong>
              {details.paymentMethod}
            </strong>

          </div>


          <div>

            <span>
              Service Date
            </span>

            <strong>
              {details.preferredDate}
            </strong>

          </div>


          <div>

            <span>
              Service Time
            </span>

            <strong>
              {details.preferredTime}
            </strong>

          </div>


          <div>

            <span>
              Payment Status
            </span>

            <strong className="status-success">

              PAID

            </strong>

          </div>


          <div>

            <span>
              Order Status
            </span>

            <strong className="status-success">

              CONFIRMED

            </strong>

          </div>


        </div>


        <button
          className="success-home-button"
          onClick={() =>
            navigate("/")
          }
        >

          Back to Home

        </button>


      </div>


    </div>

  );

};


export default OrderSuccessPage;