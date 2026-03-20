import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";
import ProductCard from "../../components/ProductCard"
import ProductImageUpload from "../../components/ProductImageUpload"

function ProductList() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);

  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("");

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchProducts = async () => {
    try {
      const res = await api.get("/products", {
        params: {
          search: search || undefined,
          categoryId: category || undefined,
          page: page,
          size: 8,
        },
      });

      setProducts(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch {
      toast.error("Failed to load products");
    }
  };

  const fetchCategories = async () => {
    try {
      const res = await api.get("/categories");

      setCategories(res.data);
    } catch {
      toast.error("Failed to load categories");
    }
  };

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

  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, []);

  useEffect(() => {
    fetchProducts();
  }, [search, category, page]);

  const addToCart = async (productId) => {
    try {
      await api.post("/user/cart/add", {
        productId,
        quantity: 1,
      });

      toast.success("Added to cart");
    } catch {
      toast.error("Failed to add product");
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <Navbar />

      <h1 className="text-3xl font-bold mt-20 mb-8">Products</h1>

      {/* Search + Filter */}

      <div className="flex gap-4 mb-8">
        <input
          type="text"
          placeholder="Search products..."
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
            setPage(0);
          }}
          className="border p-2 rounded w-full"
        />

        <select
          value={category}
          onChange={(e) => {
            setCategory(e.target.value);
            setPage(0);
          }}
          className="border p-2 rounded"
        >
          <option value="">All Categories</option>

          {categories.map((c) => (
            <option key={c.id} value={c.id}>
              {c.name}
            </option>
          ))}
        </select>
      </div>

      {/* Product Grid */}

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((product) => (
          <ProductCard
            key={product.id}
            product={product}
            addToCart={addToCart}
          />
        ))}
      </div>
      {/* Pagination */}

      <div className="flex justify-center mt-10 gap-4">
        <button
          disabled={page === 0}
          onClick={() => setPage(page - 1)}
          className="px-4 py-2 bg-gray-200 rounded disabled:opacity-50"
        >
          Previous
        </button>

        <span className="font-semibold">
          Page {page + 1} of {totalPages}
        </span>

        <button
          disabled={page === totalPages - 1}
          onClick={() => setPage(page + 1)}
          className="px-4 py-2 bg-gray-200 rounded disabled:opacity-50"
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default ProductList;
