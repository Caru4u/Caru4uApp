import React from "react";


const DeliveryAddress = ({
  address,
  selectedAddress,
  setSelectedAddress
}) => {


  console.log(
    "DeliveryAddress received:",
    address
  );


  // ==========================================
  // NO ADDRESS
  // ==========================================

  if (!address) {

    return (

      <section className="checkout-box">


        <div className="checkout-box-header">


          <div>

            <h2>
              📍 Delivery Address
            </h2>

            <p>
              Where should we provide
              the service?
            </p>

          </div>


          <button
            type="button"
            className="checkout-link-button"
          >
            + Add New Address
          </button>


        </div>


        <div className="no-address">

          <p>
            No address found.
          </p>

        </div>


      </section>
    );
  }


  // ==========================================
  // CHECK SELECTED
  // ==========================================

  const selected =

    selectedAddress
      ?.apartmentOrVillaId ===
    address
      ?.apartmentOrVillaId;


  // ==========================================
  // ADDRESS
  // ==========================================

  return (

    <section className="checkout-box">


      <div className="checkout-box-header">


        <div>

          <h2>
            📍 Delivery Address
          </h2>

          <p>
            Where should we provide
            the service?
          </p>

        </div>


        <button
          type="button"
          className="checkout-link-button"
        >
          + Add New Address
        </button>


      </div>


      <div

        className={
          `address-row ${
            selected
              ? "selected"
              : ""
          }`
        }

        onClick={() =>
          setSelectedAddress(
            address
          )
        }

      >


        <input

          type="radio"

          name="deliveryAddress"

          checked={selected}

          onChange={() =>
            setSelectedAddress(
              address
            )
          }

        />


        <div className="address-icon">

          🏠

        </div>


        <div className="address-content">


          <strong>

            {
              address
                .apartmentOrVillaName
            }

          </strong>


          <p>

            {
              address
                .blockOrCrossName
            }


            {
              address.plotNumber
                ? `, Plot No: ${address.plotNumber}`
                : ""
            }


            , Bengaluru, Karnataka

          </p>


        </div>


        <button

          type="button"

          className="edit-button"

          onClick={(event) => {

            event.stopPropagation();

            console.log(
              "Edit address:",
              address
            );
          }}

        >

          ✎ Edit

        </button>


      </div>


    </section>
  );
};


export default DeliveryAddress;