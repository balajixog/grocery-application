import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";

function OrderDetailsPage() {
  const { id } = useParams();

  const [order, setOrder] = useState(null);

  const fetchOrder = async () => {
    try {
      const res = await api.get(`/admin/orders/${id}`);

      // console.log(res.data);

      setOrder(res.data);
    } catch (err) {
      toast.error("Failed to load order");
    }
  };

  useEffect(() => {
    fetchOrder();
  }, []);

  if (!order) return null;

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <Navbar />

      <h1 className="text-3xl font-bold mt-20 mb-8">Order Details</h1>

      <div className="bg-white p-6 rounded-xl shadow max-w-xl">

        <h2 className="font-semibold mb-4">
          Order #{order.orderId || order.id}
        </h2>


        {order.items?.map((item, index) => (
          <div
            key={item.productId || item.id || index}
            className="flex justify-between border-b py-3"
          >
            <span>
              {item.productName || item.name} x {item.quantity}
            </span>

            <span>₹ {item.price}</span>
          </div>
        ))}

        {/* Total */}

        <div className="mt-4 font-bold text-lg">
          Total: ₹ {order.totalAmount}
        </div>
      </div>
    </div>
  );
}

export default OrderDetailsPage;
