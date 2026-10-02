import axios from "axios";

const API_URL = "http://localhost:8082/api/car-wash/plans";

export const getCarWashPlans = async (vehicleType) => {
  const response = await axios.get(API_URL, {
    params: {
      vehicleType
    }
  });

  return response.data;
};