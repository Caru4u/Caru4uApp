import axios from "axios";

const API_BASE_URL =
  process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const customerLogin = async (loginData) => {
  const response = await api.post(
    "/auth/Customer/login",
    loginData
  );

  return response.data;
};

export const customerRegister = async (registerData) => {
  const response = await api.post(
    "/auth/Customer/register",
    registerData
  );

  return response.data;
};

export const forgotPassword = async (data) => {
  const response = await api.post(
    "/auth/forgot-password",
    data
  );

  return response.data;
};

export default api;