import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import ProductCard from "../../components/ProductCard";
import Navbar from "../../components/Navbar";

function ProductList() {
  const [products, setProducts] = useState([]);

  const fetchProducts = async () => {
    try {
      const res = await api.get("/products");
      setProducts(res.data);
    } catch (err) {
      toast.error("Failed to load products");
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
      toast.error("Failed to add to cart");
      console.error(err);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div className="min-h-screen bg-linear-to-br from-green-100 via-white to-green-200 p-8">
      <Navbar/>
      <div className="grid gap-6 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
        {products.map((p) => (
          <ProductCard key={p.id} product={p} addToCart={addToCart} />
        ))}
      </div>
    </div>
  );
}

export default ProductList;
