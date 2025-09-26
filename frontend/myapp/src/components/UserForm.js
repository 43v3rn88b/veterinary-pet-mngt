import React, { useState, useEffect } from "react";
import api from "./api";
import axios from "axios";

//const api = axios.create({baseURL: "http://localhost:8080"});
function UserForm() {
    const [form, setForm] = useState({
        id: null,
        username: "",
        role: "STAFF",
        password: "", // only used on create or update
    });

    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        try {
            const res = await api.get("/users");
            setUsers(res.data);
        } catch (err) {
            console.error("Error fetching users", err);
        }
    };

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const submitUser = async (e) => {
        e.preventDefault();
        try {
            if (form.id) {
                // ✅ Update existing user
                await api.put(`/users/${form.id}?password=${form.password}`, {
                    id: form.id,
                    username: form.username,
                    role: form.role,
                });
            } else {
                // ✅ Create new user
                await api.post(`/users?password=${form.password}`, {
                    username: form.username,
                    role: form.role,
                });
            }
            setForm({ id: null, username: "", role: "STAFF", password: "" });
            fetchUsers();
        } catch (err) {
            console.error("Error saving user", err);
        }
    };

    const editUser = (user) => {
        setForm({
            id: user.id,
            username: user.username,
            role: user.role,
            password: "", // don’t preload password
        });
    };

    const removeUser = async (id) => {
        try {
            await api.delete(`/users/${id}`);
            fetchUsers();
        } catch (err) {
            console.error("Error deleting user", err);
        }
    };

    return (
        <div>
            <h2>{form.id ? "Edit User" : "Create User"}</h2>
            <form onSubmit={submitUser}>
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={form.username}
                    onChange={handleChange}
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={form.password}
                    onChange={handleChange}
                    required={!form.id} // required on create, optional on update
                />
                <select name="role" value={form.role} onChange={handleChange}>
                    <option value="ADMIN">ADMIN</option>
                    <option value="STAFF">STAFF</option>
                    <option value="VET">VET</option>
                </select>
                <button type="submit">{form.id ? "Update User" : "Save User"}</button>
            </form>

            <h3>Users</h3>
            <ul>
                {users.map((u) => (
                    <li key={u.id}>
                        {u.username} ({u.role})
                        <br />
                        <button onClick={() => editUser(u)}>Edit</button>
                        <button onClick={() => removeUser(u.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default UserForm;
