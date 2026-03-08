import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";
import { useNavigate } from "react-router-dom";

function OrdersPage() {
  const [orders, setOrders] = useState([]);
  const navigate = useNavigate();

  const fetchOrders = async () => {
    try {
      const res = await api.get("/user/orders/history");

      setOrders(res.data);
    } catch (err) {
      toast.error("Failed to load orders");
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <Navbar />

      <h1 className="text-3xl font-bold mt-20 mb-8">Order History</h1>

      <div className="bg-white rounded-xl shadow p-6">
        {orders.length === 0 && <p>No orders yet</p>}

        {orders.map((order) => (
          <div
            key={order.orderId}
            className="border-b py-4 flex justify-between items-center"
          >
            <div>
              <p className="font-semibold">Order #{order.orderId}</p>

              <p className="text-sm text-gray-600">
                Total: ₹ {order.totalAmount}
              </p>

              <p className="text-sm text-gray-600">Status: {order.status}</p>
            </div>

            <button
              onClick={() => navigate(`/orders/${order.orderId}/track`)}
              className="text-green-600 font-medium"
            >
              Track
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default OrdersPage;
