function ProductCard({ product, addToCart }) {
  const role = localStorage.getItem("role");

  return (
    <div className="bg-white/40 backdrop-blur-md border border-white/20 rounded-xl shadow-lg hover:shadow-xl transition hover:-translate-y-1 overflow-hidden">
      <img
        src={product.imageUrl || "https://via.placeholder.com/300"}
        alt={product.name}
        className="w-full h-40 object-cover"
      />

      <div className="p-4">
        <h2 className="text-lg font-semibold text-gray-800">{product.name}</h2>

        <p className="text-green-600 font-bold text-xl mt-1">
          ₹ {product.price}
        </p>

        <p className="text-sm text-gray-500 mb-3">
          Stock: {product.stockQuantity}
        </p>

        {/* Upload image only for admin */}

        {role === "ROLE_ADMIN" && <input type="file" className="mb-2" />}

        <button
          onClick={() => addToCart(product.id)}
          className="w-full bg-green-600 hover:bg-green-700 text-white py-2 rounded-lg transition"
        >
          Add to Cart
        </button>
      </div>
    </div>
  );
}

export default ProductCard;
