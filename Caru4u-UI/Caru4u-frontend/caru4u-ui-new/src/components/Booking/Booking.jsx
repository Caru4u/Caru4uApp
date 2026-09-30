import React from "react";

import {
  useLocation,
  useNavigate
} from "react-router-dom";

import Header
  from "../../components/Header/Header";

import "./Booking.css";

function Booking() {

  const location = useLocation();

  const navigate = useNavigate();

  const bookingData =
    location.state;

  if (!bookingData) {

    return (
      <>

        <Header />

        <div className="empty-booking">

          <h2>
            No wash plan selected
          </h2>

          <button
            onClick={() =>
              navigate("/car-wash")
            }
          >
            Select a Plan
          </button>

        </div>

      </>
    );

  }

  return (
    <>

      <Header />

      <main className="booking-page">

        <div className="booking-form">

          <h1>
            Complete Your Booking
          </h1>

          <p>
            Tell us when and where
            we should clean your vehicle.
          </p>

          <label>
            Service Address
          </label>

          <input
            type="text"
            placeholder="Enter your address"
          />

          <label>
            Start Date
          </label>

          <input
            type="date"
          />

          <label>
            Preferred Time
          </label>

          <input
            type="time"
          />

          <button>
            Continue to Payment →
          </button>

        </div>

        <div className="order-summary">

          <h2>
            Order Summary
          </h2>

          <div className="summary-row">

            <span>
              Vehicle
            </span>

            <strong>
              {
                bookingData.vehicleType
              }
            </strong>

          </div>

          <div className="summary-row">

            <span>
              Package
            </span>

            <strong>
              {
                bookingData.packageName
              }
            </strong>

          </div>

          <div className="summary-row">

            <span>
              Frequency
            </span>

            <strong>
              {
                bookingData
                  .selectedPrice
                  .frequency
              }
            </strong>

          </div>

          <div className="summary-row">

            <span>
              Schedule
            </span>

            <strong>
              {
                bookingData
                  .selectedPrice
                  .description
              }
            </strong>

          </div>

          <hr />

          <div className="
            summary-row
            total-row
          ">

            <span>
              Monthly Total
            </span>

            <strong>
              ₹
              {
                Number(
                  bookingData
                    .selectedPrice
                    .price
                )
                  .toLocaleString(
                    "en-IN"
                  )
              }
            </strong>

          </div>

        </div>

      </main>

    </>
  );
}

export default Booking;