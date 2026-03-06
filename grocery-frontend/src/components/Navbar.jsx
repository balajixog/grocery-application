import { Link } from "react-router-dom";

function Navbar() {
  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <div className="bg-white/30 backdrop-blur-md border border-white/20 shadow-md rounded-xl p-4 mb-8 flex justify-between items-center">

      <Link to="/products">
        <h1 className="text-2xl font-bold text-gray-800">🛒 Grocery Store</h1>
      </Link>

      <div className="flex gap-4">
        <Link
          to="/cart"
          className="bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition"
        >
          Cart
        </Link>

        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition"
        >
          Logout
        </button>
      </div>
    </div>
  );
}

export default Navbar;
