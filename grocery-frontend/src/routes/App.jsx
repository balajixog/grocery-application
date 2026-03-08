import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "../pages/Auth/Login";
import Register from "../pages/Auth/Register";
import ProductList from "../pages/Products/ProductList";
import CartPage from "../pages/Cart/CartPage";
import ForgotPassword from "../pages/Auth/ForgotPassword";
import ResetPassword from "../pages/Auth/ResetPassword";
import CheckoutPage from "../pages/Checkout/CheckoutPage";
import AddressPage from "../pages/Address/AddressPage";
import OrdersPage from "../pages/Orders/OrdersPage";
import OrderDetailsPage from "../pages/Orders/OrderDetailsPage";
import OrderTrackPage from "../pages/Orders/OrderTrackPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/products" element={<ProductList />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/checkout" element={<CheckoutPage />} />
        <Route path="/addresses" element={<AddressPage />} />
        <Route path="/orders" element={<OrdersPage />} />
        <Route path="/orders/:id/track" element={<OrderTrackPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
