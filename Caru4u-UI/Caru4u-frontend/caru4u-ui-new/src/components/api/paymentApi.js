const PAYMENT_BASE_URL =
  "http://localhost:8087/api/payments";


const getToken = () => {
  return localStorage.getItem("token");
};


// =====================================================
// CREATE RAZORPAY PAYMENT
// =====================================================

export const createPayment = async (
  orderId,
  customerId,
  amount,
  paymentMethod
) => {

  const token = getToken();

  const request = {
    orderId,
    customerId,
    amount,
    paymentMethod
  };

  console.log(
    "CREATE PAYMENT REQUEST:",
    request
  );

  const response = await fetch(
    `${PAYMENT_BASE_URL}/create`,
    {
      method: "POST",

      headers: {
        "Content-Type": "application/json",

        ...(token && {
          Authorization:
            `Bearer ${token}`
        })
      },

      body: JSON.stringify(request)
    }
  );

  if (!response.ok) {

    const errorText =
      await response.text();

    console.error(
      "CREATE PAYMENT ERROR:",
      response.status,
      errorText
    );

    throw new Error(
      errorText ||
      "Unable to create payment."
    );
  }

  const data =
    await response.json();

  console.log(
    "CREATE PAYMENT RESPONSE:",
    data
  );

  return data;
};


// =====================================================
// VERIFY RAZORPAY PAYMENT
// =====================================================

export const verifyPayment = async (
  razorpayResponse
) => {

  const token = getToken();

  const request = {

    razorpayOrderId:
      razorpayResponse.razorpay_order_id,

    razorpayPaymentId:
      razorpayResponse.razorpay_payment_id,

    razorpaySignature:
      razorpayResponse.razorpay_signature
  };


  console.log(
    "VERIFY PAYMENT REQUEST:",
    request
  );


  const response = await fetch(
    `${PAYMENT_BASE_URL}/verify`,
    {
      method: "POST",

      headers: {
        "Content-Type": "application/json",

        ...(token && {
          Authorization:
            `Bearer ${token}`
        })
      },

      body:
        JSON.stringify(request)
    }
  );


  if (!response.ok) {

    const errorText =
      await response.text();

    console.error(
      "VERIFY PAYMENT ERROR:",
      response.status,
      errorText
    );

    throw new Error(
      errorText ||
      "Payment verification failed."
    );
  }


  return await response.text();
};