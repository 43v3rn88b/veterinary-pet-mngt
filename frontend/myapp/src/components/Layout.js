import React, {useState} from "react";
import {Link, Outlet, useNavigate} from "react-router-dom";
import axios from "axios";

function Layout({ children }) {
    //const api = axios.create({baseURL: "http://localhost:8080"});
    const [authenticated, setAuthenticated] = useState(!!localStorage.getItem("token"));
    const navigate = useNavigate();

    return (
        <div className="flex h-screen bg-gray-100">
            {/* Sidebar */}
            <aside className="w-64 bg-blue-800 text-white flex flex-col">
                <div className="px-6 py-4 text-2xl font-bold border-b border-blue-700">
                    Vet System
                </div>
                <nav className="flex-1 px-4 py-6 space-y-2">
                    <Link to="/dashboard" className="block px-3 py-2 rounded hover:bg-blue-700">Dashboard</Link>
                    <Link to="/owners" className="block px-3 py-2 rounded hover:bg-blue-700">Owners</Link>
                    <Link to="/pets" className="block px-3 py-2 rounded hover:bg-blue-700">Pets</Link>
                    <Link to="/appointments" className="block px-3 py-2 rounded hover:bg-blue-700">Appointments</Link>
                    <Link to="/medicalRecords" className="block px-3 py-2 rounded hover:bg-blue-700">Medical Records</Link>
                    <Link to="/invoices" className="block px-3 py-2 rounded hover:bg-blue-700">Invoices</Link>
                    <Link to="/inventory" className="block px-3 py-2 rounded hover:bg-blue-700">Inventory</Link>
                </nav>
            </aside>

            {/* Main Content */}
            <div className="flex-1 flex flex-col">
                {/* Top Navbar */}
                <header className="bg-white shadow px-6 py-4 flex justify-between items-center">
                    <h1 className="text-xl font-semibold text-gray-700"></h1>
                    <div className="flex items-center space-x-4">
                        <span className="text-gray-600">Welcome, Admin</span>
                        <button onClick={async () => {
                            localStorage.removeItem("token"); // clear token
                            setAuthenticated(false);
                            navigate("/login");
                            //window.location.href = "/login";
                        }} className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded">
                            Logout
                        </button>
                    </div>
                </header>

                {/* Page Content */}
                <main className="flex-1 p-6 overflow-y-auto">
                    <Outlet />
                    {children}
                </main>
            </div>
        </div>
    );
}

export default Layout;
