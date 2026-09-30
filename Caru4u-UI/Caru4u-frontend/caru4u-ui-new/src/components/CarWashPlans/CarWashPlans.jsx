import React, {
  useEffect,
  useState
} from "react";

import {
  useSearchParams,
  useNavigate
} from "react-router-dom";

import Header
  from "../../components/Header/Header";

import PlanCard
  from "../../components/PlanCard/PlanCard";

import {
  getCarWashPlans
} from "../../api/carWashApi";

import "./CarWashPlans.css";

function CarWashPlans() {

  const navigate = useNavigate();

  const [
    searchParams,
    setSearchParams
  ] = useSearchParams();

  const initialVehicle =
    searchParams.get("vehicleType")
    || "HATCHBACK";

  const [vehicleType, setVehicleType] =
    useState(initialVehicle);

  const [data, setData] =
    useState(null);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const vehicles = [
    "HATCHBACK",
    "SEDAN",
    "LUXURY",
    "MID_SUV",
    "SUV"
  ];

  useEffect(() => {

    loadPlans();

  }, [vehicleType]);

  const loadPlans = async () => {

    try {

      setLoading(true);

      setError("");

      const response =
        await getCarWashPlans(
          vehicleType
        );

      setData(response);

    }
    catch (err) {

      console.error(err);

      setError(
        "Unable to load car wash plans."
      );

    }
    finally {

      setLoading(false);

    }

  };

  const handleVehicleChange =
    (vehicle) => {

      setVehicleType(vehicle);

      setSearchParams({
        vehicleType: vehicle
      });

    };

  const displayVehicleName =
    (vehicle) => {

      return vehicle
        .replace("_", "-")
        .replace(
          /\b\w/g,
          letter =>
            letter.toUpperCase()
        );

    };

  return (
    <>

      <Header />

      <main className="carwash-page">

        <section className="plans-hero">

          <div className="hero-left">

            <button
              className="back-button"
              onClick={() =>
                navigate("/")
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
              and plan that suits you best.
            </p>

          </div>

          <div className="hero-car">

            <img
              src="/assets/images/car-wash-banner.png"
              alt="Car Wash"
            />

          </div>

          <div className="hero-features">

            <div>
              🛡️
              <span>
                <strong>
                  Professional Cleaning
                </strong>
                Trained Experts
              </span>
            </div>

            <div>
              🍃
              <span>
                <strong>
                  Eco-friendly Products
                </strong>
                Safe for You & Environment
              </span>
            </div>

            <div>
              🏠
              <span>
                <strong>
                  At Your Doorstep
                </strong>
                Home/Office/Anywhere
              </span>
            </div>

          </div>

        </section>

        <section className="vehicle-tabs">

          {vehicles.map(vehicle => (

            <button
              key={vehicle}

              className={
                vehicleType === vehicle
                  ? "vehicle-button active"
                  : "vehicle-button"
              }

              onClick={() =>
                handleVehicleChange(
                  vehicle
                )
              }
            >

              🚗

              {displayVehicleName(
                vehicle
              )}

            </button>

          ))}

        </section>

        {loading && (

          <div className="status">
            Loading plans...
          </div>

        )}

        {error && (

          <div className="error">
            {error}
          </div>

        )}

        {!loading &&
         !error &&
         data && (

          <section className="content-layout">

            <div className="plans-grid">

              {data.packages.map(
                packageData => (

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

            <aside className="sidebar">

              <div className="why-card">

                <h2>
                  Why Choose Caru4u?
                </h2>

                <SidebarItem
                  icon="🚚"
                  title="Doorstep Service"
                  description="We come to you"
                />

                <SidebarItem
                  icon="👨‍🔧"
                  title="Trained Professionals"
                  description="Verified experts"
                />

                <SidebarItem
                  icon="🍃"
                  title="Safe & Eco-friendly"
                  description="Non-toxic products"
                />

                <SidebarItem
                  icon="📅"
                  title="Flexible Plans"
                  description="Daily, alternate or weekly"
                />

                <SidebarItem
                  icon="📱"
                  title="Easy Booking"
                  description="Book in minutes"
                />

              </div>

              <div className="testimonial-card">

                <div className="customer-avatar">
                  👨
                </div>

                <p>
                  “Amazing service!
                  My car looks brand new.
                  Highly recommended!”
                </p>

                <strong>
                  Rahul Sharma
                </strong>

                <div className="stars">
                  ★★★★★
                </div>

              </div>

              <div className="help-card">

                <span className="headphone">
                  🎧
                </span>

                <div>

                  <strong>
                    Need Help?
                  </strong>

                  <p>
                    Contact our support
                    team.
                  </p>

                </div>

              </div>

            </aside>

          </section>

        )}

        <section className="bottom-benefits">

          <Benefit
            icon="🚙"
            title="10,000+"
            description="Happy Customers"
          />

          <Benefit
            icon="🍃"
            title="Eco-Friendly"
            description="Cleaning Products"
          />

          <Benefit
            icon="🛡️"
            title="100% Satisfaction"
            description="Guaranteed"
          />

          <Benefit
            icon="🕒"
            title="Flexible Scheduling"
            description="At Your Convenience"
          />

          <div className="greener-card">

            🌍

            <span>
              Clean Today
              <strong>
                Greener Tomorrow
              </strong>
            </span>

            →

          </div>

        </section>

      </main>

    </>
  );
}

function SidebarItem({
  icon,
  title,
  description
}) {

  return (

    <div className="sidebar-item">

      <span className="sidebar-icon">
        {icon}
      </span>

      <div>

        <strong>
          {title}
        </strong>

        <p>
          {description}
        </p>

      </div>

    </div>

  );
}

function Benefit({
  icon,
  title,
  description
}) {

  return (

    <div className="benefit">

      <span>
        {icon}
      </span>

      <div>

        <strong>
          {title}
        </strong>

        <p>
          {description}
        </p>

      </div>

    </div>

  );
}

export default CarWashPlans;