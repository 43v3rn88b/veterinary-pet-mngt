import { useState } from "react";
import axios from "axios"; // axios instance

export default function LoginPage({ setAuthenticated }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const api = axios.create({baseURL: "http://localhost:8080"});

    // const handleLogin2 = async (e) => {
    //     e.preventDefault();
    //     try {
    //         // Spring Security formLogin expects x-www-form-urlencoded
    //         const formData = new URLSearchParams();
    //         formData.append("username", username);
    //         formData.append("password", password);
    //
    //         await api.post("/api/auth/login", formData, {
    //             headers: { "Content-Type": "application/json" },
    //             body: JSON.stringify({ username, password }),
    //         });
    //
    //         setAuthenticated(true);
    //     } catch (err) {
    //         setError("Invalid username or password");
    //     }
    // };
    //const token = localStorage.getItem("token");

    // const res = await fetch("http://localhost:8080/pets", {
    //     headers: {
    //         Authorization: `Bearer ${token}`,
    //     },
    // });

    async function handleLogin(e) {
        e.preventDefault();
        try {
            const res = await fetch("http://localhost:8080/api/auth/login", {//http://localhost:8080
                method: "POST",
                headers: { "Content-Type": "application/json",
                    "Accept": "application/json",
                    "Cache-Control": "no-store",},
                credentials: "include",
                cache: "no-store",
                body: JSON.stringify({ username, password }),
            });

            if (res.ok) {
                const data = await res.json();
                localStorage.setItem("token", data.token); // store token
                setAuthenticated(true);
                alert("Login successful ✅");
                // store token/session
                window.location.href = "/dashboard";
            } else {
                setError("Invalid credentials ❌");
            }
        } catch (err) {
            console.error("Login error:", err);
            setError("Login failed. Try again.");
        }
    }


    return (
        <div className="flex items-center justify-center h-screen bg-gray-50">
            <form
                onSubmit={handleLogin}
                className="bg-white p-6 rounded-lg shadow-md w-80 space-y-4"
            >
                <h2 className="text-xl font-bold text-center">Login</h2>

                {error && <p className="text-red-500 text-sm">{error}</p>}

                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="w-full border rounded px-3 py-2"
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="w-full border rounded px-3 py-2"
                />

                <button
                    type="submit"
                    className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
                >
                    Login
                </button>
            </form>
        </div>
    );
}
