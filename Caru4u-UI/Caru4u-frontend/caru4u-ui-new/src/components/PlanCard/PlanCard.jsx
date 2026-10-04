import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import "./PlanCard.css";

function PlanCard({ packageData, vehicleType }) {

  const navigate = useNavigate();

  // First price selected by default
  const [selectedPrice, setSelectedPrice] = useState(
    packageData?.prices?.length > 0
      ? packageData.prices[0]
      : null
  );

  const [addingToCart, setAddingToCart] =
    useState(false);


  // =====================================================
  // GET STARTED / ADD TO CART
  // =====================================================

  const handleGetStarted = async () => {

    if (!selectedPrice) {

      alert("Please select a frequency.");

      return;
    }


    try {

      setAddingToCart(true);


      // =================================================
      // GET JWT FROM LOCAL STORAGE
      // =================================================

      const token =
        localStorage.getItem("token");


      console.log(
        "JWT TOKEN:",
        token
      );


      // Customer must login first
      if (!token) {

        alert(
          "Please login before adding service to cart."
        );

        navigate("/login");

        return;
      }


      console.log(
        "PACKAGE DATA:",
        packageData
      );


      console.log(
        "VEHICLE TYPE:",
        vehicleType
      );


      console.log(
        "SELECTED PRICE:",
        selectedPrice
      );


      // =================================================
      // CREATE CART REQUEST
      // =================================================

      const cartRequest = {

        productId:
          packageData.productId || 1,

        packageId:
          packageData.packageId,

        vehicleTypeId:
          selectedPrice.vehicleTypeId,

        frequencyId:
          selectedPrice.frequencyId,

        quantity: 1
      };


      console.log(
        "ADD TO CART REQUEST:",
        cartRequest
      );


      // =================================================
      // CALL CART SERVICE
      // =================================================
      //
      // IMPORTANT:
      //
      // No customerId query parameter.
      //
      // customerId comes from JWT.
      //
      // =================================================

      const response =
        await axios.post(

          "http://localhost:8086/api/cart/items",

          cartRequest,

          {
            headers: {

              Authorization:
                `Bearer ${token}`,

              "Content-Type":
                "application/json"

            }
          }

        );


      console.log(
        "ADD TO CART RESPONSE:",
        response.data
      );


      // =================================================
      // SUCCESS
      // =================================================

      navigate("/cart");


    } catch (error) {

      console.error(
        "ADD TO CART ERROR:",
        error
      );


      if (error.response) {

        console.error(
          "STATUS:",
          error.response.status
        );


        console.error(
          "BACKEND RESPONSE:",
          error.response.data
        );

      }


      // ===============================================
      // Unauthorized / Forbidden
      // ===============================================

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


      // ===============================================
      // Other backend errors
      // ===============================================

      alert(
        error.response?.data?.message ||
        "Unable to add service to cart. Please try again."
      );


    } finally {

      setAddingToCart(false);

    }
  };


  // =====================================================
  // PACKAGE ICON
  // =====================================================

  const getPackageIcon = () => {

    switch (
      packageData?.name?.toLowerCase()
    ) {

      case "life":
        return "🍃";

      case "premium":
        return "⭐";

      case "deluxe":
        return "💎";

      default:
        return "🚗";
    }
  };


  return (

    <div
      className={
        packageData.mostPopular
          ? "plan-card premium-plan"
          : "plan-card"
      }
    >

      {/* ============================================= */}
      {/* MOST POPULAR */}
      {/* ============================================= */}

      {packageData.mostPopular && (

        <div className="popular-badge">
          👑 MOST POPULAR
        </div>

      )}


      {/* ============================================= */}
      {/* PLAN HEADER */}
      {/* ============================================= */}

      <div className="plan-header">

        <div className="plan-icon">
          {getPackageIcon()}
        </div>


        <div>

          <h2>
            {packageData.name}
          </h2>

          <p>
            {packageData.description}
          </p>

        </div>

      </div>


      {/* ============================================= */}
      {/* PRICE OPTIONS */}
      {/* ============================================= */}

      <div className="price-options">

        {packageData.prices?.map(
          (price, index) => {

            const isSelected =
              selectedPrice?.frequency ===
              price.frequency;


            return (

              <div
                key={
                  price.frequencyId ||
                  price.frequency ||
                  index
                }

                className={
                  isSelected
                    ? "price-row selected-price"
                    : "price-row"
                }

                onClick={() =>
                  setSelectedPrice(price)
                }
              >

                <div className="price-frequency">

                  <strong>
                    {price.frequency}
                  </strong>

                  <span>
                    {price.description}
                  </span>

                </div>


                <strong className="price-amount">

                  ₹
                  {Number(
                    price.price
                  ).toLocaleString(
                    "en-IN"
                  )}

                  <small>
                    /mo
                  </small>

                </strong>

              </div>

            );
          }
        )}

      </div>


      {/* ============================================= */}
      {/* FEATURES */}
      {/* ============================================= */}

      <div className="features">

        {packageData.features?.map(
          (feature, index) => (

            <div
              className="feature"
              key={index}
            >

              <span className="check-icon">
                ✓
              </span>

              <span>
                {feature}
              </span>

            </div>

          )
        )}

      </div>


      {/* ============================================= */}
      {/* GET STARTED */}
      {/* ============================================= */}

      <button

        className="get-started-btn"

        onClick={handleGetStarted}

        disabled={
          addingToCart ||
          !selectedPrice
        }

      >

        {addingToCart
          ? "Adding..."
          : "Get Started"
        }


        {!addingToCart && (
          <span>→</span>
        )}

      </button>


      {/* ============================================= */}
      {/* BEST VALUE */}
      {/* ============================================= */}

      {packageData.mostPopular && (

        <div className="best-value">
          ✨ Best Value for a Brighter Ride! ✨
        </div>

      )}

    </div>

  );
}

export default PlanCard;