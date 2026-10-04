import axios from "axios";

const CART_API = "http://localhost:8086/api/cart";


// =====================================================
// AUTH HEADER
// =====================================================

const getAuthHeaders = () => {

  const token = localStorage.getItem("token");

  console.log("JWT TOKEN:", token);

  if (!token) {
    throw new Error("Customer is not logged in");
  }

  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  };
};


// =====================================================
// ADD TO CART
// =====================================================

export const addToCart = async (request) => {

  const response = await axios.post(
    `${CART_API}/items`,
    request,
    {
      headers: getAuthHeaders()
    }
  );

  return response.data;
};


// =====================================================
// GET CART
// =====================================================

export const getCart = async () => {

  console.log("GET CART:", CART_API);

  const response = await axios.get(
    CART_API,
    {
      headers: getAuthHeaders()
    }
  );

  return response.data;
};


// =====================================================
// UPDATE QUANTITY
// =====================================================

export const updateCartQuantity = async (
  itemId,
  quantity
) => {

  const response = await axios.put(
    `${CART_API}/items/${itemId}`,
    {
      quantity: quantity
    },
    {
      headers: getAuthHeaders()
    }
  );

  return response.data;
};


// =====================================================
// REMOVE ITEM
// =====================================================

export const removeCartItem = async (itemId) => {

  const response = await axios.delete(
    `${CART_API}/items/${itemId}`,
    {
      headers: getAuthHeaders()
    }
  );

  return response.data;
};


// =====================================================
// CLEAR CART
// =====================================================

export const clearCart = async () => {

  await axios.delete(
    CART_API,
    {
      headers: getAuthHeaders()
    }
  );
};