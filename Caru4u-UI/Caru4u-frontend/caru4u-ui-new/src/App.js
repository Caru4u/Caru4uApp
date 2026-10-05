import React from "react";

import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import Home
  from "./pages/Home/Home";

import CarWashPlans
  from "./pages/CarWashPlans/CarWashPlans";

import Booking
  from "./pages/Booking/Booking";

import Login
  from "./pages/Login/Login";

import CartPage
  from "./pages/Cart/CartPage";

import CheckoutPage
  from "./pages/Checkout/CheckoutPage";

// NEW
import OrderSuccessPage
  from "./pages/OrderSuccess/OrderSuccessPage";


function App() {

  return (

    <BrowserRouter>

      <Routes>

        {/* ========================= */}
        {/* HOME */}
        {/* ========================= */}

        <Route
          path="/"
          element={<Home />}
        />


        {/* ========================= */}
        {/* CAR WASH PLANS */}
        {/* ========================= */}

        <Route
          path="/car-wash"
          element={<CarWashPlans />}
        />


        {/* ========================= */}
        {/* BOOKING */}
        {/* ========================= */}

        <Route
          path="/booking"
          element={<Booking />}
        />


        {/* ========================= */}
        {/* LOGIN */}
        {/* ========================= */}

        <Route
          path="/login"
          element={<Login />}
        />


        {/* ========================= */}
        {/* CART */}
        {/* ========================= */}

        <Route
          path="/cart"
          element={<CartPage />}
        />


        {/* ========================= */}
        {/* CHECKOUT */}
        {/* ========================= */}

        <Route
          path="/checkout"
          element={<CheckoutPage />}
        />


        {/* ========================= */}
        {/* PAYMENT / ORDER SUCCESS */}
        {/* ========================= */}

        <Route
          path="/order-success/:orderId"
          element={<OrderSuccessPage />}
        />


      </Routes>

    </BrowserRouter>

  );

}

export default App;