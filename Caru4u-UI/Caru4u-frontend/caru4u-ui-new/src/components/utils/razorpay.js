export const loadRazorpayScript = () => {

    return new Promise((resolve) => {

        if (window.Razorpay) {
            resolve(true);
            return;
        }

        const script = document.createElement("script");

        script.src =
            "https://checkout.razorpay.com/v1/checkout.js";

        script.async = true;

        script.onload = () => {
            console.log("Razorpay SDK loaded");
            resolve(true);
        };

        script.onerror = () => {
            console.error("Razorpay SDK failed");
            resolve(false);
        };

        document.body.appendChild(script);
    });
};