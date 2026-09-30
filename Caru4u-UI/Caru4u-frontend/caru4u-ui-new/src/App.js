import React from "react";

import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import Home from "./pages/Home/Home";
import CarWashPlans from "./pages/CarWashPlans/CarWashPlans";
import Booking from "./pages/Booking/Booking";
import Login from "./pages/Login/Login";
import CartPage from "./pages/Cart/CartPage";

function App() {

  return (

    <BrowserRouter>

      <Routes>

        <Route
          path="/"
          element={<Home />}
        />

        <Route
          path="/car-wash"
          element={<CarWashPlans />}
        />

        <Route
          path="/booking"
          element={<Booking />}
        />

        <Route
          path="/login"
          element={<Login />}
        />

        <Route
          path="/cart"
          element={<CartPage />}
        />

      </Routes>

    </BrowserRouter>

  );
}

export default App;