import React from "react";

import {
  useNavigate
} from "react-router-dom";

import "./ServiceCard.css";

function ServiceCard({
  service
}) {

  const navigate =
    useNavigate();

  const handleClick = () => {

    navigate(
      service.path
    );

  };


  return (
    <div className="service-card">

      <img
        src={service.image}
        alt={service.name}
      />

      <div className="service-content">

        <h2>
          {service.name}
        </h2>

        <p>
          {service.description}
        </p>

        <button
          onClick={handleClick}
        >
          View Plans →
        </button>

      </div>

    </div>
  );

}

export default ServiceCard;