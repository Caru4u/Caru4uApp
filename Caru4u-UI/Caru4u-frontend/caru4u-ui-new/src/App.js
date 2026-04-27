import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./components/LoginPage";
import SignupPage from "./components/SignupPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/user/registor" element={<SignupPage />} />
        <Route path="*" element={<LoginPage />} /> {/* Default route */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
