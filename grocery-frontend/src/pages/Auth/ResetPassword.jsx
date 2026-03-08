import { useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import api from "../../api/axios";
import toast from "react-hot-toast";

function ResetPassword() {
  const [params] = useSearchParams();
  const navigate = useNavigate();

  const token = params.get("token");

  const [newPassword, setNewPassword] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post("/auth/reset-password", {
        token,
        newPassword,
      });

      toast.success("Password reset successful");

      navigate("/");
    } catch (err) {
      toast.error("Invalid or expired token");
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded-xl shadow w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Reset Password</h2>

        <input
          type="password"
          placeholder="Enter new password"
          className="w-full border p-3 rounded mb-4"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
          required
        />

        <button className="w-full bg-green-600 text-white py-3 rounded hover:bg-green-700">
          Reset Password
        </button>
      </form>
    </div>
  );
}

export default ResetPassword;
