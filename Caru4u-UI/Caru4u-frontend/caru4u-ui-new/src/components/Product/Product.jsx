import React from "react";
import "./Product.scss";

function Product() {
  return (
    <section className="product-section">
      <h2>Our Products</h2>
      <ul className="product-list">
        <li><a href="/products/car-wash">Car Wash</a></li>
        <li><a href="/products/detailing">Detailing</a></li>
        <li><a href="/products/maintenance">Maintenance</a></li>
        <li><a href="/products/accessories">Accessories</a></li>
      </ul>
    </section>
  );
}

export default Product;
