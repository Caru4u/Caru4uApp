import axios from "axios";

const CART_API =
  "http://localhost:8086/api/cart";

export const addToCart = async (
  customerId,
  request
) => {

  const response = await axios.post(
    `${CART_API}/items`,
    request,
    {
      params: {
        customerId: customerId
      }
    }
  );

  return response.data;
};

export const getCart = async () => {

  const response = await axios.get(
    CART_API
  );

  return response.data;
};

export const removeCartItem = async (
  itemId
) => {

  const response = await axios.delete(
    `${CART_API}/items/${itemId}`
  );

  return response.data;
};

export const clearCart = async () => {

  await axios.delete(CART_API);

};