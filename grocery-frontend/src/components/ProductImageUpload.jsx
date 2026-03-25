import { useState } from "react";
import api from "../api/axios";
import toast from "react-hot-toast";

function ProductImageUpload({ productId }) {
  const [file, setFile] = useState(null);

  const uploadImage = async () => {
    if (!file) {
      toast.error("Select image first");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      await api.post(`/admin/products/${productId}/image`, formData);

      toast.success("Image uploaded");
    } catch {
      toast.error("Upload failed");
    }
  };

  return (
    <div className="flex gap-3">
      <input type="file" onChange={(e) => setFile(e.target.files[0])} />

      <button
        onClick={uploadImage}
        className="bg-blue-500 text-white px-3 py-1 rounded"
      >
        Upload
      </button>
    </div>
  );
}

export default ProductImageUpload;
