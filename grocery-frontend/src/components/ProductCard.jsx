function ProductCard({ product, addToCart }) {
  return (
    <div className="bg-white p-4 rounded-lg shadow">
      {/* ✅ FIXED IMAGE */}
      <img
        src={
          product.imageUrl && product.imageUrl !== ""
            ? product.imageUrl
            : "https://dummyimage.com/300x200/cccccc/000000&text=No+Image"
        }
        alt={product.name}
        className="w-full h-40 object-cover rounded"
      />

      <h2 className="text-lg font-semibold mt-2">{product.name}</h2>

      <p className="text-green-600 font-bold">₹ {product.price}</p>

      <p className="text-sm text-gray-500">Stock: {product.stockQuantity}</p>

      <button
        onClick={() => addToCart(product.id)}
        className="mt-2 w-full bg-green-600 text-white py-2 rounded"
      >
        Add to Cart
      </button>
    </div>
  );
}

export default ProductCard;
