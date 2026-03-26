import { useEffect, useState } from "react";
import api from "../../api/axios";
import { useNavigate } from "react-router-dom";
import ProductImageUpload from "../../components/ProductImageUpload";

function AdminProducts() {
  const navigate = useNavigate();

  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [editProduct, setEditProduct] = useState(null);

  const role = localStorage.getItem("role");

  //  Fetch products
  const fetchProducts = async () => {
    try {
      const res = await api.get("/products");
      setProducts(res.data.content);
    } catch (err) {
      console.error(err);
    }
  };

  //  Fetch categories
  const fetchCategories = async () => {
    try {
      const res = await api.get("/categories");
      setCategories(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, []);

  //  Update stock
  const updateStock = async (id, qty) => {
    if (!qty) return;

    try {
      await api.patch(`/admin/products/${id}/stock`, {
        stockQuantity: Number(qty),
      });

      fetchProducts();
    } catch (err) {
      console.error(err);
    }
  };

  // Save edited product
  const saveEdit = async () => {
    try {
      await api.put(`/admin/products/${editProduct.id}`, {
        name: editProduct.name,
        description: editProduct.description,
        price: Number(editProduct.price),
        stockQuantity: Number(editProduct.stockQuantity),
        categoryId: Number(editProduct.categoryId),
      });

      setEditProduct(null);
      fetchProducts();
    } catch (err) {
      console.error(err);
    }
  };
  const deleteProduct = async (id) => {
    if (!confirm("Delete this product?")) return;

    try {
      await api.delete(`/admin/products/${id}`);

      alert("Deleted");

      fetchProducts();
    } catch {
      alert("Failed");
    }
  };

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-6">Admin Product Dashboard</h1>

      {/* Add Product */}
      <button
        onClick={() => navigate("/admin/create-product")}
        className="bg-green-600 text-white px-4 py-2 mb-4 rounded"
      >
        Add Product
      </button>

      {/* Product Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((p) => (
          <div key={p.id} className="bg-white p-4 rounded shadow">
            {/* Image */}
            {p.imageUrl ? (
              <img
                src={p.imageUrl}
                alt={p.name}
                className="w-full h-40 object-cover rounded"
              />
            ) : (
              <div className="w-full h-40 flex items-center justify-center bg-gray-200 rounded">
                No Image
              </div>
            )}

            {/* Details */}
            <h2 className="mt-2 font-semibold">{p.name}</h2>

            <p className="text-green-600 font-bold">₹ {p.price}</p>

            <p className="text-sm text-gray-500">Stock: {p.stockQuantity}</p>

            {/* Admin only */}
            {role === "ROLE_ADMIN" && (
              <>
                {/* Upload Image */}
                <ProductImageUpload productId={p.id} refresh={fetchProducts} />

                {/* Update Stock */}
                <input
                  type="number"
                  placeholder="Stock"
                  className="border p-1 w-full mt-2"
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      updateStock(p.id, e.target.value);
                    }
                  }}
                />

                {/* Edit */}
                <button
                  onClick={() =>
                    setEditProduct({
                      ...p,
                      categoryId: p.categoryId || "",
                    })
                  }
                  className="mt-2 w-full bg-yellow-500 text-white py-1 rounded"
                >
                  Edit
                </button>
                <button
                  onClick={() => deleteProduct(p.id)}
                  className="mt-2 w-full bg-red-600 text-white py-1 rounded"
                >
                  Delete
                </button>
              </>
            )}
          </div>
        ))}
      </div>

      {/* EDIT MODAL */}
      {editProduct && (
        <div className="fixed inset-0 bg-black/50 flex justify-center items-center">
          <div className="bg-white p-6 rounded w-96">
            <h2 className="text-xl mb-4">Edit Product</h2>

            {/* Name */}
            <input
              value={editProduct.name}
              onChange={(e) =>
                setEditProduct({
                  ...editProduct,
                  name: e.target.value,
                })
              }
              className="border p-2 w-full mb-2"
              placeholder="Name"
            />

            {/* Description */}
            <input
              value={editProduct.description || ""}
              onChange={(e) =>
                setEditProduct({
                  ...editProduct,
                  description: e.target.value,
                })
              }
              className="border p-2 w-full mb-2"
              placeholder="Description"
            />

            {/* Price */}
            <input
              value={editProduct.price}
              onChange={(e) =>
                setEditProduct({
                  ...editProduct,
                  price: e.target.value,
                })
              }
              className="border p-2 w-full mb-2"
              placeholder="Price"
            />

            {/* Stock */}
            <input
              value={editProduct.stockQuantity}
              onChange={(e) =>
                setEditProduct({
                  ...editProduct,
                  stockQuantity: e.target.value,
                })
              }
              className="border p-2 w-full mb-2"
              placeholder="Stock"
            />

            {/* Category */}
            <select
              value={editProduct.categoryId}
              onChange={(e) =>
                setEditProduct({
                  ...editProduct,
                  categoryId: e.target.value,
                })
              }
              className="border p-2 w-full mb-2"
            >
              <option value="">Select Category</option>

              {categories.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>

            {/* Buttons */}
            <div className="flex gap-2 mt-4">
              <button
                onClick={saveEdit}
                className="bg-green-600 text-white px-4 py-2 rounded"
              >
                Save
              </button>

              <button
                onClick={() => setEditProduct(null)}
                className="bg-gray-400 text-white px-4 py-2 rounded"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default AdminProducts;
