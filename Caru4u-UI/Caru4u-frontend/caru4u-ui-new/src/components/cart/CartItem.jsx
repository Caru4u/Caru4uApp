import React from "react";

const CartItem = ({
  item,
  onIncrease,
  onDecrease,
  onRemove,
  updatingItemId,
}) => {

  const updating =
    updatingItemId === item.cartItemId;

  return (

    <div className="cart-item">

      {/* IMAGE */}

      <div className="cart-image">

        <img
          src={
            item.imageUrl ||
            "/assets/images/car-wash.png"
          }
          alt={item.productName}
        />

      </div>


      {/* DETAILS */}

      <div className="cart-details">

        <div className="cart-title-row">

          <div>

            <h3>
              {item.productName}
              {item.packageName &&
                ` - ${item.packageName}`}
            </h3>

            <p>
              Professional car care service
              for a fresh and shiny vehicle.
            </p>

          </div>

          <span className="cart-price">
            ₹
            {Number(
              item.totalPrice || 0
            ).toLocaleString("en-IN")}
          </span>

        </div>


        {/* INFORMATION */}

        <div className="cart-meta">

          {item.vehicleType && (
            <span>
              🚗 {item.vehicleType}
            </span>
          )}

          {item.frequency && (
            <span>
              📅 {item.frequency}
            </span>
          )}


          {/* QUANTITY */}

          <div className="quantity-box">

            <button
              disabled={
                updating ||
                item.quantity <= 1
              }
              onClick={() =>
                onDecrease(item)
              }
            >
              −
            </button>

            <span>
              Qty: {item.quantity}
            </span>

            <button
              disabled={updating}
              onClick={() =>
                onIncrease(item)
              }
            >
              +
            </button>

          </div>

        </div>

      </div>


      {/* REMOVE */}

      <button
        className="remove-button"
        disabled={updating}
        onClick={() =>
          onRemove(item.cartItemId)
        }
      >
        ×
      </button>

    </div>
  );
};

export default CartItem;