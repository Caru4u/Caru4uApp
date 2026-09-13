import React from "react";

import Header from "./components/Header";
import Hero from "./components/Hero";
import Products from "./components/Products";
import HowItWorks from "./components/HowItWorks";
import Footer from "./components/Footer";

import "./styles/Home.css";

function App() {
  return (
    <>
      <Header />
      <Hero />
      <Products />
      <HowItWorks />
      <Footer />
    </>
  );
}

export default App;