// App.js
import React from "react";
import { Routes, Route } from "react-router-dom";
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Product from "./components/Product/Product";
import ForgotPassword from "./components/Auth/ForgotPassword";
import ContactUs from "./pages/ContactsUs/ContactUs";
import RefundPolicy from "./pages/RefundPolicy/RefundPolicy"; 
import TermsAndConditions from "./pages/Terms/TermsAndConditions";// ✅ add this

function App() {
  return (
    <div>
      <Header />

      <main>
        <Routes>
          <Route path="/" element={<Product />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/contact" element={<ContactUs />} />
          <Route path="/refund-policy" element={<RefundPolicy />} /> {/* ✅ new route */}
          <Route path="/terms" element={<TermsAndConditions />} />
        </Routes>
      </main>

      <Footer />
    </div>
  );
}

export default App;
