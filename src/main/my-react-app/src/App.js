import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import NoPage from "./pages/NoPage";
import Login from "./pages/Login";
import BookPage from './pages/BookPage';
import ReservationPage from "./pages/UserReservation";
import Cart from "./pages/CartPage";

export default function App() {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route index element={<Home />} />
                    <Route path = "/login" element={<Login />} />
                    <Route path = "/home" element={<Home />} />

                    <Route path = "/no-page" element={<NoPage />} />
                    <Route path = "/book/:id" element={<BookPage />} />
                    <Route path = "/reservation" element={<ReservationPage />} />
                    <Route path = "/cart" element={<Cart />} />
                    <Route path= "*" element={<NoPage />} />
                </Routes>
            </BrowserRouter>
        </div>
    )
}