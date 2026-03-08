import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";
import { useNavigate } from "react-router-dom";


function CartPage() {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const fetchCart = async () => {
    try {
      const res = await api.get("/user/cart");

      setCartItems(res.data.items || []);
    } catch (err) {
      toast.error("Failed to load cart");
    }
  };
  const updateQuantity = async (productId, quantity) => {
    if (quantity < 1) return;

    try {
      setLoading(true);

      setCartItems((prev) =>
        prev.map((item) =>
          item.productId === productId
            ? {
                ...item,
                quantity,
                totalPrice: item.price * quantity,
              }
            : item,
        ),
      );

      await api.put("/user/cart/update", {
        productId,
        quantity,
      });
    } catch (err) {
      toast.error("Failed to update quantity");
      fetchCart(); // fallback if API fails
    } finally {
      setLoading(false);
    }
  };
  const removeItem = async (productId) => {
    try {
      setLoading(true);

      // Remove locally first
      setCartItems((prev) =>
        prev.filter((item) => item.productId !== productId),
      );

      await api.delete(`/user/cart/remove/${productId}`);
      toast.success("Item removed");
    } catch (err) {
      toast.error("Failed to remove item");
      fetchCart();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const total = cartItems.reduce((sum, item) => sum + item.totalPrice, 0);

  return (
    <div className="min-h-screen bg-linear-to-br from-green-100 via-white to-green-200 p-8">
      <Navbar />

      <h1 className="text-2xl font-bold mb-6 mt-20">🛒 Your Cart</h1>

      {cartItems.length === 0 ? (
        <p className="text-gray-600">Cart is empty</p>
      ) : (
        <div className="bg-white rounded-xl shadow p-6">
          {cartItems.map((item) => (
            <div
              key={item.productId}
              className="flex justify-between items-center border-b py-4"
            >
              <div>
                <h2 className="font-semibold text-lg">{item.name}</h2>

                <p className="text-gray-600">₹ {item.price}</p>
              </div>
              <div className="flex items-center gap-3">
                <button
                  disabled={loading}
                  onClick={() =>
                    updateQuantity(item.productId, item.quantity - 1)
                  }
                  className="px-3 py-1 bg-gray-200 rounded hover:bg-gray-300 disabled:opacity-50"
                >
                  -
                </button>

                <span className="font-semibold">{item.quantity}</span>

                <button
                  disabled={loading}
                  onClick={() =>
                    updateQuantity(item.productId, item.quantity + 1)
                  }
                  className="px-3 py-1 bg-gray-200 rounded hover:bg-gray-300 disabled:opacity-50"
                >
                  +
                </button>
              </div>
              <div className="flex items-center gap-4">
                <p className="font-semibold">₹ {item.totalPrice}</p>

                <button
                  disabled={loading}
                  onClick={() => removeItem(item.productId)}
                  className="text-red-500 hover:text-red-700 disabled:opacity-50"
                >
                  Remove
                </button>
              </div>
            </div>
          ))}
          <div className="flex justify-between items-center mt-6">
            <h2 className="text-xl font-bold">Total: ₹ {total}</h2>
            <button
              onClick={() => navigate("/checkout")}
              className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700"
            >
              Checkout
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default CartPage;
