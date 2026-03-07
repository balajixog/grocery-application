import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./routes/App";
import { Toaster } from "react-hot-toast";

createRoot(document.getElementById("root")).render(
  <>
   <Toaster position="top-right"/>
    <App/>
  </>
);
