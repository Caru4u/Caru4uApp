import React, {
  useEffect,
  useState
} from "react";

import {
  useSearchParams
} from "react-router-dom";

import Header from "../../components/Header/Header";
import PlanCard from "../../components/PlanCard/PlanCard";

import {
  getCarWashPlans
} from "../../components/api/carWashApi";

import "./CarWashPlans.css";

function CarWashPlans() {

  const [searchParams, setSearchParams] =
    useSearchParams();

  const initialVehicleType =
    searchParams.get("vehicleType")
    || "HATCHBACK";

  const [vehicleType, setVehicleType] =
    useState(initialVehicleType);

  const [data, setData] =
    useState(null);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const vehicles = [
    {
      name: "Hatchback",
      value: "HATCHBACK",
      icon: "🚙"
    },
    {
      name: "Sedan",
      value: "SEDAN",
      icon: "🚘"
    },
    {
      name: "Luxury",
      value: "LUXURY",
      icon: "🚘"
    },
    {
      name: "Mid-SUV",
      value: "MID_SUV",
      icon: "🚙"
    },
    {
      name: "SUV",
      value: "SUV",
      icon: "🚙"
    }
  ];


  useEffect(() => {

    const loadPlans = async () => {

      try {

        setLoading(true);

        setError("");

        const response =
          await getCarWashPlans(
            vehicleType
          );

        setData(response);

      } catch (err) {

        console.error(
          "Error loading plans:",
          err
        );

        setError(
          "Unable to load car wash plans."
        );

      } finally {

        setLoading(false);

      }
    };

    loadPlans();

  }, [vehicleType]);


  const handleVehicleChange =
    (vehicle) => {

      setVehicleType(vehicle);

      setSearchParams({
        vehicleType: vehicle
      });

    };


  return (
    <div className="plans-page">

      <Header />


      {/* HERO */}

      <section className="plans-hero">

        <div className="plans-title">

          <button
            className="back-button"
            onClick={() =>
              window.history.back()
            }
          >
            ← Back to Services
          </button>

          <h1>
            Car Wash
            <span> Plans</span>
          </h1>

          <p>
            Choose your vehicle type
            and plan that suits you best
          </p>

        </div>


        <div className="plans-banner">

          <div className="banner-text">
            A Cleaner
            <br />
            Happier You!
          </div>

          <img
            src="/assets/images/car-wash-plan-banner.png"
            alt="Car wash plans"
          />

        </div>


        <div className="hero-benefits">

          <div>
            <span>🛡️</span>

            <p>
              <strong>
                Professional Cleaning
              </strong>

              Trained Experts
            </p>
          </div>


          <div>
            <span>🌿</span>

            <p>
              <strong>
                Eco-Friendly Products
              </strong>

              Safe for You & Environment
            </p>
          </div>


          <div>
            <span>🏠</span>

            <p>
              <strong>
                At Your Doorstep
              </strong>

              Home/Office/Anywhere
            </p>
          </div>

        </div>

      </section>


      {/* VEHICLE TABS */}

      <div className="vehicle-tabs">

        {vehicles.map(
          (vehicle) => (

            <button
              key={vehicle.value}
              className={
                vehicleType
                === vehicle.value
                  ? "vehicle-tab active"
                  : "vehicle-tab"
              }
              onClick={() =>
                handleVehicleChange(
                  vehicle.value
                )
              }
            >

              <span>
                {vehicle.icon}
              </span>

              {vehicle.name}

            </button>

          )
        )}

      </div>


      {/* CONTENT */}

      <main className="plans-layout">


        {/* PLAN AREA */}

        <section className="plans-content">

          {loading && (
            <div className="loading">
              Loading plans...
            </div>
          )}


          {error && (
            <div className="error-message">
              {error}
            </div>
          )}


          {!loading &&
           !error &&
           data && (

            <div className="plans-grid">

              {data.packages.map(
                (packageData) => (

                  <PlanCard
                    key={
                      packageData.packageId
                    }
                    packageData={
                      packageData
                    }
                    vehicleType={
                      data.vehicleType
                    }
                  />

                )
              )}

            </div>

          )}

        </section>


        {/* SIDEBAR */}

        <aside className="plans-sidebar">

          <div className="why-card">

            <h2>
              Why Choose Caru4u?
            </h2>

            <div className="why-item">

              <span className="why-icon">
                🚚
              </span>

              <div>
                <strong>
                  Doorstep Service
                </strong>

                <small>
                  We come to you
                </small>
              </div>

            </div>


            <div className="why-item">

              <span className="why-icon">
                👥
              </span>

              <div>
                <strong>
                  Trained Professionals
                </strong>

                <small>
                  Verified experts
                </small>
              </div>

            </div>


            <div className="why-item">

              <span className="why-icon">
                🌿
              </span>

              <div>
                <strong>
                  Safe & Eco-friendly
                </strong>

                <small>
                  Non-toxic products
                </small>
              </div>

            </div>


            <div className="why-item">

              <span className="why-icon">
                📅
              </span>

              <div>
                <strong>
                  Flexible Plans
                </strong>

                <small>
                  Daily, alternate
                  or weekly
                </small>
              </div>

            </div>


            <div className="why-item">

              <span className="why-icon">
                📱
              </span>

              <div>
                <strong>
                  Easy Booking
                </strong>

                <small>
                  Book in minutes
                </small>
              </div>

            </div>

          </div>


          {/* REVIEW */}

          <div className="review-card">

            <div className="review-avatar">
              👨
            </div>

            <div>

              <p>
                "Amazing service!
                My car looks brand new.
                Highly recommended!"
              </p>

              <strong>
                Rahul Sharma
              </strong>

              <div className="stars">
                ★★★★★
              </div>

            </div>

          </div>


          {/* HELP */}

          <div className="help-card">

            <span>
              🎧
            </span>

            <div>

              <strong>
                Need Help?
              </strong>

              <p>
                Call us at
                <b>
                  {" "}
                  +91 98765 43210
                </b>
              </p>

            </div>

          </div>

        </aside>

      </main>


      {/* BOTTOM */}

      <section className="plan-footer-benefits">

        <div>
          🚙

          <span>
            <strong>
              10,000+
            </strong>

            Happy Customers
          </span>
        </div>


        <div>
          🌿

          <span>
            <strong>
              Eco-Friendly
            </strong>

            Cleaning Products
          </span>
        </div>


        <div>
          🛡️

          <span>
            <strong>
              100% Satisfaction
            </strong>

            Guaranteed
          </span>
        </div>


        <div>
          🕒

          <span>
            <strong>
              Flexible Scheduling
            </strong>

            At Your Convenience
          </span>
        </div>

      </section>

    </div>
  );
}

export default CarWashPlans;