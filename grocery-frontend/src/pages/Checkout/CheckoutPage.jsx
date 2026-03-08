import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";
import { useNavigate } from "react-router-dom";

function CheckoutPage() {
  const [addresses, setAddresses] = useState([]);
  const [selectedAddress, setSelectedAddress] = useState(null);
  const [cartItems, setCartItems] = useState([]);

  const navigate = useNavigate();

  // fetch addresses
  const fetchAddresses = async () => {
    try {
      const res = await api.get("/user/addresses");

      setAddresses(res.data);
    } catch (err) {
      toast.error("Failed to load addresses");
    }
  };

  // fetch cart items
  const fetchCart = async () => {
    try {
      const res = await api.get("/user/cart");

      setCartItems(res.data.items || []);
    } catch (err) {
      toast.error("Failed to load cart");
    }
  };

  // place order
  const placeOrder = async () => {
    if (!selectedAddress) {
      toast.error("Please select address");
      return;
    }

    try {
      await api.post("/user/orders/place", {
        addressId: selectedAddress,
      });

      toast.success("Order placed successfully");

      navigate("/orders");
    } catch (err) {
      toast.error("Failed to place order");
    }
  };

  useEffect(() => {
    fetchAddresses();
    fetchCart();
  }, []);

  // calculate totals

  const itemsTotal = cartItems.reduce((sum, item) => sum + item.totalPrice, 0);

  const delivery = 0;

  const total = itemsTotal + delivery;

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <Navbar />

      <h1 className="text-3xl font-bold mt-20 mb-8">Checkout</h1>

      <div className="grid md:grid-cols-2 gap-8">
        {/* ADDRESS SECTION */}

        <div className="bg-white p-6 rounded-xl shadow">
          <h2 className="text-xl font-semibold mb-4">
            Select Delivery Address
          </h2>

          {addresses.length === 0 && (
            <p className="text-gray-500">No addresses found</p>
          )}

          {addresses.map((addr) => (
            <div
              key={addr.id}
              onClick={() => setSelectedAddress(addr.id)}
              className={`border p-4 rounded mb-3 cursor-pointer transition ${
                selectedAddress === addr.id
                  ? "border-green-500 bg-green-50"
                  : "hover:border-gray-400"
              }`}
            >
              <p className="font-medium">{addr.line1}</p>

              <p className="text-sm text-gray-600">
                {addr.city}, {addr.state}
              </p>

              <p className="text-sm text-gray-600">{addr.pincode}</p>
            </div>
          ))}

          <button
            onClick={() => navigate("/addresses")}
            className="mt-4 text-green-600 font-medium"
          >
            + Add New Address
          </button>
        </div>

        {/* ORDER SUMMARY */}

        <div className="bg-white p-6 rounded-xl shadow">
          <h2 className="text-xl font-semibold mb-4">Order Summary</h2>

          {/* CART ITEMS */}

          <div className="mb-4">
            {cartItems.map((item) => (
              <div
                key={item.productId}
                className="flex justify-between text-sm mb-2"
              >
                <span>
                  {item.name} x {item.quantity}
                </span>

                <span>₹ {item.totalPrice}</span>
              </div>
            ))}
          </div>

          {/* PRICE DETAILS */}

          <div className="flex justify-between mb-2">
            <span>Items Total</span>
            <span>₹ {itemsTotal}</span>
          </div>

          <div className="flex justify-between mb-2">
            <span>Delivery</span>
            <span>₹ {delivery}</span>
          </div>

          <hr className="my-4" />

          <div className="flex justify-between font-bold text-lg mb-6">
            <span>Total</span>
            <span>₹ {total}</span>
          </div>

          {/* PLACE ORDER */}

          <button
            onClick={placeOrder}
            className="w-full bg-green-600 text-white py-3 rounded-lg hover:bg-green-700"
          >
            Place Order
          </button>
        </div>
      </div>
    </div>
  );
}

export default CheckoutPage;
