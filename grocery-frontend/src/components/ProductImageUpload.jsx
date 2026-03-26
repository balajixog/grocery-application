import { useState } from "react";
import api from "../api/axios";
import toast from "react-hot-toast";

function ProductImageUpload({ productId, refresh }) {
  const [file, setFile] = useState(null);
  const [preview, setPreview] = useState(null);

  const handleSelect = (e) => {
    const selected = e.target.files[0];

    if (!selected) return;

    setFile(selected);

    // ✅ Preview image before upload
    setPreview(URL.createObjectURL(selected));
  };

  const handleUpload = async () => {
    if (!file) {
      toast.error("Select image first");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      await api.post(`/admin/products/${productId}/image`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      toast.success("Image uploaded");

      setFile(null);
      setPreview(null);

      refresh();
    } catch (err) {
      console.error(err);

      toast.error("Upload failed");
    }
  };

  return (
    <div className="mt-2">
      {/* Select file */}
      <input type="file" onChange={handleSelect} />

      {/* Preview */}
      {preview && (
        <img
          src={preview}
          alt="preview"
          className="mt-2 w-full h-32 object-cover rounded"
        />
      )}

      {/* Upload button */}
      <button
        onClick={handleUpload}
        className="mt-2 w-full bg-blue-500 text-white py-1 rounded hover:bg-blue-600"
      >
        Upload Image
      </button>
    </div>
  );
}

export default ProductImageUpload;
