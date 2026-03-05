import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";


function ProductList() {
  const [products, setProducts] = useState([]);

  const fetchProducts = async () => {
    try {
      const res = await api.get("/products");
      setProducts(res.data);
    } catch (err) {
      toast.error("Failed to load products ");
      console.error(err);
    }
  };

  const addToCart = async (productId) => {
    try {
      await api.post("/user/cart/add", {
        productId: productId,
        quantity: 1,
      });
      toast.success("Added to cart 🛒");
    } catch (err) {
      toast.error("Failed to add to cart ");
      console.error(err);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-2xl font-bold mb-6">Products</h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((p) => (
          <div key={p.id} className="bg-white p-4 rounded-lg shadow">
            <h2 className="font-semibold text-lg">{p.name}</h2>
            <p className="text-gray-600">₹ {p.price}</p>
            <p className="text-sm text-gray-500">Stock: {p.stockQuantity}</p>

            <button
              onClick={() => addToCart(p.id)}
              className="mt-3 w-full bg-green-600 text-white py-2 rounded hover:bg-green-700"
            >
              Add to Cart
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ProductList;
