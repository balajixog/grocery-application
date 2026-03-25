import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";

function AdminProducts() {
  const [products, setProducts] = useState([]);

  const fetchProducts = async () => {
    try {
      const res = await api.get("/products");

      setProducts(res.data.content);
    } catch (err) {
      toast.error("Failed to load products");
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  // Upload Image
  const uploadImage = async (productId, file) => {
    const formData = new FormData();
    formData.append("file", file);

    try {
      await api.post(`/admin/products/${productId}/image`, formData);

      toast.success("Image uploaded");

      fetchProducts();
    } catch {
      toast.error("Upload failed");
    }
  };

  // Update Stock
  const updateStock = async (productId, quantity) => {
    try {
      await api.patch(`/admin/products/${productId}/stock`, {
        stockQuantity: quantity,
      });

      toast.success("Stock updated");

      fetchProducts();
    } catch {
      toast.error("Stock update failed");
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <Navbar />

      <h1 className="text-3xl font-bold mt-20 mb-8">Admin Product Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((p) => (
          <div key={p.id} className="bg-white p-4 rounded-xl shadow">
            <img
              src={p.imageUrl || "https://via.placeholder.com/200"}
              className="w-full h-40 object-cover rounded"
            />

            <h2 className="font-semibold text-lg mt-2">{p.name}</h2>

            <p className="text-green-600 font-bold">₹ {p.price}</p>

            <p className="text-sm text-gray-500">Stock: {p.stockQuantity}</p>

            {/* Upload Image */}

            <input
              type="file"
              className="mt-2"
              onChange={(e) => uploadImage(p.id, e.target.files[0])}
            />

            {/* Update Stock */}

            <div className="flex gap-2 mt-2">
              <input
                type="number"
                placeholder="Stock"
                className="border p-1 w-full rounded"
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    updateStock(p.id, e.target.value);
                  }
                }}
              />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default AdminProducts;
