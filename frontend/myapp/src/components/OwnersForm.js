import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "./ui/Card";
import SectionTitle from "./ui/SectionTitle";
import FormInput from "./ui/FormInput";
import {DangerButton, PrimaryButton, SecondaryButton} from "./ui/Buttons";
import api from "./api";
function OwnersForm(){

    //const api = axios.create({baseURL: "http://localhost:8080"});

    // -------------------------
    // OWNER state & helpers
    // -------------------------
    const [search, setSearch] = useState("");
    const [owners, setOwners] = useState([]);
    const [newOwner, setNewOwner] = useState({
        name: "",
        phone: "",
        email: "",
        address: ""
    });
    const [editingOwnerId, setEditingOwnerId] = useState(null);

    async function fetchOwners() {
        try {
            const res = await api.get("/owners");
            console.log("✅ Owners:", res.data);
            setOwners(Array.isArray(res.data) ? res.data : []);
        } catch (err) {
            console.error("❌ fetchOwners error:", err);
            setOwners([]);
        }
    }

    async function addOwner(e) {
        e.preventDefault();
        if (!newOwner.name) return alert("Please enter owner name.");

        try {
            if (editingOwnerId) {
                await api.put(`/owners/${editingOwnerId}`, newOwner);
                setEditingOwnerId(null);
            } else {
                await api.post("/owners", newOwner);
            }
            setNewOwner({name: "", phone: "", email: "", address: ""});
            await fetchOwners();
        } catch (err) {
            console.error("handleAddOwner error", err);
            alert("Failed to save owner.");
        }
    }

    function editOwner(owner) {
        setNewOwner({
            name: owner.name || "",
            phone: owner.phone || "",
            email: owner.email || "",
            address: owner.address || ""
        });
        setEditingOwnerId(owner.id);
    }

    async function removeOwner(id) {
        if (!window.confirm("Delete this owner?")) return;
        try {
            await api.delete(`/owners/${id}`);
            await fetchOwners();
        } catch (err) {
            console.error("removeOwner error", err);
            alert("Failed to delete owner. Ensure no dependent records remain.");
        }
    }
    useEffect(() => {
        fetchOwners();
    }, []);
    const KeyValue = ({label, value}) => (
        <div style={{marginBottom: 6}}>
            <strong>{label}:</strong> <span>{value}</span>
        </div>
    );

    const filteredOwners = owners.filter((o) => {
        const term = search.toLowerCase();
        return (
            o.name?.toLowerCase().includes(term) ||
            o.phone?.toLowerCase().includes(term) ||
            o.email?.toLowerCase().includes(term) ||
            o.address?.toLowerCase().includes(term)
        );
    });
    
    return(

    <Card>
        <SectionTitle>Owners</SectionTitle>

        {/* Form */}
        <form onSubmit={addOwner} className="space-y-4 mb-6">
            <FormInput
                placeholder="Name"
                value={newOwner.name}
                onChange={(e) => setNewOwner({ ...newOwner, name: e.target.value })}
            />
            <FormInput
                placeholder="Phone"
                value={newOwner.phone}
                onChange={(e) => setNewOwner({ ...newOwner, phone: e.target.value })}
            />
            <FormInput
                type="email"
                placeholder="Email"
                value={newOwner.email}
                onChange={(e) => setNewOwner({ ...newOwner, email: e.target.value })}
            />
            <FormInput
                placeholder="Address"
                value={newOwner.address}
                onChange={(e) => setNewOwner({ ...newOwner, address: e.target.value })}
            />
            <PrimaryButton type="submit">Save Owner</PrimaryButton>
        </form>
        <h3 className="text-xl font-semibold mt-6 mb-2 text-gray-700">Search</h3>
        {/* ✅ Search input */}
        <FormInput
            type="text"
            placeholder="Search by name, phone, email, address..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            //className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />

        {/* Owners List */}
        <h3 className="text-lg font-semibold mt-3">Existing Owners</h3>
        <div className="max-w-2xl mx-auto p-6 bg-white rounded-2xl ">{
        owners?.length === 0 && <div>No owners yet.</div>}
        </div>
        <ul className="space-y-4">
            {filteredOwners.map((o) => (
                <li
                    key={o.id}
                    className="p-4 bg-gray-50 border border-gray-200 rounded-lg shadow-sm flex flex-col md:flex-row md:justify-between md:items-center"
                >
                    <div className="space-y-1">
                        <KeyValue label="Name" value={o.name} />
                        <KeyValue label="Phone" value={o.phone} />
                        <KeyValue label="Email" value={o.email} />
                        <KeyValue label="Address" value={o.address} />
                    </div>
                    <div className="flex space-x-2 mt-3 md:mt-0">
                        <SecondaryButton onClick={() => editOwner(o)}>Edit</SecondaryButton>
                        <DangerButton onClick={() => removeOwner(o.id)}>Delete</DangerButton>
                    </div>
                </li>
            ))}
        </ul>
    </Card>
    );
}

export default OwnersForm;