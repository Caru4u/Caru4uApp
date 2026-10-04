const CHECKOUT_BASE_URL = "http://localhost:8085/api";

const getToken = () => {
  return localStorage.getItem("token");
};


// =====================================================
// GET CHECKOUT DETAILS
// GET http://localhost:8085/api/checkout
// =====================================================

export const getCheckout = async () => {

  const token = getToken();

  if (!token) {
    throw new Error("Please login before checkout.");
  }

  const response = await fetch(
    `${CHECKOUT_BASE_URL}/checkout`,
    {
      method: "GET",

      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      }
    }
  );

  if (!response.ok) {

    const errorText = await response.text();

    console.error(
      "GET CHECKOUT ERROR:",
      response.status,
      errorText
    );

    throw new Error(
      errorText || "Unable to load checkout."
    );
  }

  const data = await response.json();

  console.log(
    "GET CHECKOUT RESPONSE:",
    data
  );

  return data;
};


// =====================================================
// PLACE ORDER
// POST http://localhost:8085/api/place-order
// =====================================================

export const placeOrder = async (request) => {

  const token = getToken();

  if (!token) {
    throw new Error("Please login before placing order.");
  }

  console.log(
    "PLACE ORDER REQUEST:",
    request
  );

  const response = await fetch(
    `${CHECKOUT_BASE_URL}/place-order`,
    {
      method: "POST",

      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },

      body: JSON.stringify(request)
    }
  );

  if (!response.ok) {

    const errorText = await response.text();

    console.error(
      "PLACE ORDER ERROR:",
      response.status,
      errorText
    );

    throw new Error(
      errorText || "Unable to place order."
    );
  }

  const data = await response.json();

  console.log(
    "PLACE ORDER RESPONSE:",
    data
  );

  return data;
};