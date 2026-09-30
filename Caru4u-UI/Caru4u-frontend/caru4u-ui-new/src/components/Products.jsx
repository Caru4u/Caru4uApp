import { useEffect, useState } from "react";
import {
  CarFront,
  Bike,
  Wrench,
  ArrowRight
} from "lucide-react";

function Products() {

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    fetch("http://localhost:8080/api/products/list")
      .then(response => {

        if (!response.ok) {
          throw new Error("Unable to load products");
        }

        return response.json();
      })
      .then(data => {
        setProducts(data);
        setLoading(false);
      })
      .catch(error => {
        console.error(error);
        setLoading(false);
      });

  }, []);

  const getIcon = (code) => {

    switch (code) {

      case "CAR_WASH":
        return <CarFront />;

      case "BIKE_WASH":
        return <Bike />;

      case "MAINTENANCE":
        return <Wrench />;

      default:
        return <CarFront />;
    }
  };

  const openProduct = (product) => {

    switch (product.code) {

      case "CAR_WASH":
        window.location.href = "/car-wash";
        break;

      case "BIKE_WASH":
        window.location.href = "/bike-wash";
        break;

      case "MAINTENANCE":
        window.location.href = "/maintenance";
        break;

      default:
        break;
    }
  };

  return (
    <section
      className="products-section"
      id="products"
    >

      <div className="section-heading">
        <h2>Our Products</h2>
        <p>Choose the service you need</p>
      </div>

      {loading ? (

        <div className="loading">
          Loading services...
        </div>

      ) : (

        <div className="products-grid">

          {products.map(product => (

            <article
              className="product-card"
              key={product.id}
              onClick={() => openProduct(product)}
            >

              <div className="product-image">

                <img
                  src={product.imageUrl}
                  alt={product.name}
                />

              </div>

              <div className="service-icon">
                {getIcon(product.code)}
              </div>

              <div className="product-bottom">

                <div className="product-copy">

                  <h3>{product.name}</h3>

                  <p>
                    {product.description}
                  </p>

                </div>

                <button
                  className="arrow-btn"
                  aria-label={`Open ${product.name}`}
                  onClick={(event) => {
                    event.stopPropagation();
                    openProduct(product);
                  }}
                >
                  <ArrowRight size={20} />
                </button>

              </div>

            </article>

          ))}

        </div>
      )}

    </section>
  );
}

export default Products;