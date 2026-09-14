import React from "react";

import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import Home from "./pages/Home/Home";
import CarWashPlans from "./pages/CarWashPlans/CarWashPlans";
import Booking from "./pages/Booking/Booking";

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

      </Routes>
    </BrowserRouter>
  );
}

export default App;