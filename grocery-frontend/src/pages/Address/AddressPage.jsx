import { useEffect, useState } from "react";
import api from "../../api/axios";
import toast from "react-hot-toast";
import Navbar from "../../components/Navbar";

function AddressPage() {
  const [addresses, setAddresses] = useState([]);

  const [form, setForm] = useState({
    line1: "",
    city: "",
    state: "",
    pincode: "",
  });

  const fetchAddresses = async () => {
    try {
      const res = await api.get("/user/addresses");

      setAddresses(res.data);
    } catch (err) {
      toast.error("Failed to load addresses");
    }
  };

  const addAddress = async (e) => {
    e.preventDefault();

    try {
      await api.post("/user/addresses", form);

      toast.success("Address added");

      setForm({
        line1: "",
        city: "",
        state: "",
        pincode: "",
      });

      fetchAddresses();
    } catch (err) {
      toast.error("Failed to add address");
    }
  };

  const deleteAddress = async (id) => {
    try {
      await api.delete(`/user/addresses/${id}`);

      toast.success("Address deleted");

      fetchAddresses();
    } catch (err) {
      toast.error("Failed to delete");
    }
  };

  useEffect(() => {
    fetchAddresses();
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <Navbar />

      <h1 className="text-2xl font-bold mt-20 mb-6">Your Addresses</h1>

      {/* Add Address Form */}

      <form
        onSubmit={addAddress}
        className="bg-white p-6 rounded-xl shadow mb-6"
      >
        <h2 className="text-lg font-semibold mb-4">Add Address</h2>

        <input
          placeholder="Address Line"
          className="border p-3 rounded w-full mb-3"
          value={form.line1}
          onChange={(e) => setForm({ ...form, line1: e.target.value })}
        />

        <input
          placeholder="City"
          className="border p-3 rounded w-full mb-3"
          value={form.city}
          onChange={(e) => setForm({ ...form, city: e.target.value })}
        />

        <input
          placeholder="State"
          className="border p-3 rounded w-full mb-3"
          value={form.state}
          onChange={(e) => setForm({ ...form, state: e.target.value })}
        />

        <input
          placeholder="Pincode"
          className="border p-3 rounded w-full mb-3"
          value={form.pincode}
          onChange={(e) => setForm({ ...form, pincode: e.target.value })}
        />

        <button className="bg-green-600 text-white px-6 py-2 rounded">
          Add Address
        </button>
      </form>

      {/* Address List */}

      <div className="bg-white p-6 rounded-xl shadow">
        {addresses.length === 0 ? (
          <p>No addresses found</p>
        ) : (
          addresses.map((addr) => (
            <div
              key={addr.id}
              className="border-b py-4 flex justify-between items-center"
            >
              <div>
                <p>{addr.line1}</p>

                <p>
                  {addr.city}, {addr.state}
                </p>

                <p>{addr.pincode}</p>
              </div>

              <button
                onClick={() => deleteAddress(addr.id)}
                className="text-red-500"
              >
                Delete
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default AddressPage;
