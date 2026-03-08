import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";

function OrderTrackPage() {
  const { id } = useParams();
  const [order, setOrder] = useState(null);
 
  const fetchOrder = async () => {
    
    try {
      console.log("Fetching order:", id);

      const res = await api.get(`/user/orders/${id}/track`);

      console.log("API RESPONSE:", res.data);

      setOrder(res.data);
    } catch (err) {
      console.error(err);
      toast.error("Failed to load order tracking");
    }
  };

  useEffect(() => {
    if (id) {
      fetchOrder();
    }
  }, [id]);

  if (!order) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        Loading order tracking...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <Navbar />

      <h1 className="text-3xl font-bold mt-20 mb-8">Order Tracking</h1>

      <div className="bg-white p-6 rounded-xl shadow max-w-xl">
        <h2 className="text-lg font-semibold mb-4">Order #{order.orderId}</h2>

        <p className="text-gray-600 mb-6">Status: {order.currentStatus}</p>

        {/* Timeline */}

        {order.timeline && order.timeline.length > 0 ? (
          <div className="space-y-4">
            {order.timeline.map((step, index) => (
              <div key={index} className="flex items-center gap-4">
                <div
                  className={`w-4 h-4 rounded-full ${
                    step.completed ? "bg-green-500" : "bg-gray-300"
                  }`}
                />

                <span
                  className={
                    step.completed
                      ? "text-green-600 font-medium"
                      : "text-gray-500"
                  }
                >
                  {step.step}
                </span>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-gray-500">No tracking timeline available</p>
        )}
      </div>
    </div>
  );
}

export default OrderTrackPage;
