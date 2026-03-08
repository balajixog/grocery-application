import { useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import { Link } from "react-router-dom";

function ForgotPassword() {
  const [email, setEmail] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post("/auth/forgot-password", { email });

      toast.success("Password reset email sent");

      setEmail("");
    } catch (err) {
      toast.error("User not found");
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded-xl shadow w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Forgot Password</h2>

        <input
          type="email"
          placeholder="Enter your email"
          className="w-full border p-3 rounded mb-4"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <button className="w-full bg-green-600 text-white py-3 rounded hover:bg-green-700">
          Send Reset Link
        </button>

        <p className="text-sm text-center mt-4">
          Remember password?{" "}
          <Link to="/" className="text-green-600">
            Login
          </Link>
        </p>
      </form>
    </div>
  );
}

export default ForgotPassword;
