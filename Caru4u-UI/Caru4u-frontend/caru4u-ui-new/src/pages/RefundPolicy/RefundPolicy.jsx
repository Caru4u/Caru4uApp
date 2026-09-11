import React from "react";
import "./RefundPolicy.scss";

const RefundPolicy = () => {
  return (
    <div className="refund-policy">
      <h1>Refund Policy</h1>
      <p>
        Thank you for choosing CarEasy. We want you to have a satisfying
        experience with our services, and we understand that sometimes a refund
        may be necessary. This Refund Policy outlines the terms and conditions
        under which refunds will be provided for our services.
      </p>

      <section>
        <h2>1. Eligibility for Refunds</h2>
        <ul>
          <li>The service was canceled by CarEasy due to unforeseen circumstances.</li>
          <li>The service was not provided in accordance with the agreed terms and conditions.</li>
          <li>The service was not used by the customer, and a refund request is made within 3 days of the purchase date.</li>
        </ul>
      </section>

      <section>
        <h2>2. Refund Process</h2>
        <ol>
          <li>
            Contact our customer support team at 
            <a href="mailto:support@caru4u.com">support@caru4u.com</a> 
            with your full name, contact info, order number, and reason.
          </li>
          <li>Our team will review and respond within 7–8 business days.</li>
          <li>If approved, refunds are processed within 5–6 business days via the original payment method.</li>
          <li>Refunds can only be sent using the same medium of payment.</li>
          <li>If denied, you’ll receive an explanation via email.</li>
        </ol>
      </section>

      <section>
        <h2>3. Exceptions</h2>
        <ul>
          <li>If the service was provided in accordance with the agreed terms.</li>
          <li>If the refund request is made after the specified time frame.</li>
          <li>If the service was terminated due to violation of terms of service.</li>
        </ul>
      </section>

      <section>
        <h2>4. Contact Us</h2>
        <p>
          If you have any questions or concerns regarding our Refund Policy,
          please contact us at 
          <a href="mailto:supportus@caru4u.com">supportus@caru4u.com</a>.
        </p>
      </section>
    </div>
  );
};

export default RefundPolicy;
