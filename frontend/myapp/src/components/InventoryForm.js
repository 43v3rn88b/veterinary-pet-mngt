import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "./ui/Card";
import SectionTitle from "./ui/SectionTitle";
import FormInput from "./ui/FormInput";
import {DangerButton, PrimaryButton, SecondaryButton} from "./ui/Buttons";
import api from "./api";

function InventoryForm() {
    //const api = axios.create({baseURL: "http://localhost:8080"});

    // -------------------------
    // INVENTORY state & helpers
    // -------------------------
    const [search, setSearch] = useState("");
    const [items, setItems] = useState([]);
    const [itemForm, setItemForm] = useState({name: "", quantity: ""});
    const [editingItemId, setEditingItemId] = useState(null);

    useEffect(() => {
        fetchItems().catch(err => console.error("Error fetching items:", err));
    }, []);

    // -------------------------
    // INVENTORY CRUD
    // -------------------------
    async function fetchItems() {
        try {
            const res = await api.get("/inventory");
            setItems(res.data || []);
            return res.data;
        } catch (err) {
            console.error("fetchItems error", err);
            setItems([]);
            throw err;
        }
    }

    async function submitItem(e) {
        e.preventDefault();
        if (!itemForm.name) return alert("Please enter item name.");

        // Ensure quantity is stored as integer
        const payload = {
            ...itemForm,
            quantity: parseInt(itemForm.quantity || "0", 10)
        };

        try {
            if (editingItemId) {
                await api.put(`/inventory/${editingItemId}`, payload);
                setEditingItemId(null);
            } else {
                await api.post("/inventory", payload);
            }
            setItemForm({name: "", quantity: ""});
            fetchItems();
        } catch (err) {
            console.error("submitItem error", err);
            alert("Failed to save inventory item.");
        }
    }

    function editItem(item) {
        setItemForm({
            name: item.name || "",
            quantity: item.quantity?.toString() || ""
        });
        setEditingItemId(item.id);
    }

    async function removeItem(id) {
        if (!window.confirm("Delete this item?")) return;
        try {
            await api.delete(`/inventory/${id}`);
            fetchItems();
        } catch (err) {
            console.error("removeItem error", err);
            alert("Failed to delete item.");
        }
    }

    const filteredItems = items.filter((it) => {
        const term = search.toLowerCase();
        return (
            it.name?.toLowerCase().includes(term)
            //it.quantity?.toLowerCase().includes(term)
        );
    });

    const KeyValue = ({label, value}) => (
        <div style={{marginBottom: 6}}>
            <strong>{label}:</strong> <span>{value}</span>
        </div>
    );

    return(

        <Card>
            <SectionTitle>Inventory</SectionTitle>

            {/* Form */}
            <form onSubmit={submitItem} className="space-y-4 mb-6">


                <FormInput
                    placeholder="Item name"
                    value={itemForm.name}
                    onChange={(e) => setItemForm({...itemForm, name: e.target.value})}
                />
                <FormInput
                    placeholder="Quantity"
                    value={itemForm.quantity}
                    onChange={(e) => setItemForm({...itemForm, quantity: e.target.value})}
                />

                <PrimaryButton type="submit">Save Item</PrimaryButton>
            </form>

            <h3 className="text-xl font-semibold mt-6 mb-2 text-gray-700">Search</h3>
            {/* ✅ Search input */}
            <FormInput
                placeholder="Search items..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />
            {/* Owners List */}
            <h3 className="text-lg font-semibold mt-3">Existing Items</h3>
            <div className="max-w-2xl mx-auto p-6 bg-white rounded-2xl">{
            items.length === 0 && <div>No inventory items.</div>}</div>
            <ul className="space-y-4">
                {filteredItems.map((it) => (
                    <li
                        key={it.id}
                        className="r-4 bg-gray-50 border border-gray-200 rounded-lg shadow-sm flex flex-col md:flex-row md:justify-between md:items-center"
                    >
                        <div className="space-y-1">
                            <KeyValue label="Name"
                                      value={it.name}/>
                            <KeyValue label="Quantity" value={it.quantity}/>
                        </div>
                        <div className="flex space-x-2 mt-3 md:mt-0">
                            <SecondaryButton onClick={() => editItem(it)}>Edit</SecondaryButton>
                            <DangerButton onClick={() => removeItem(it.id)}>Delete</DangerButton>
                        </div>
                    </li>
                ))}
            </ul>
        </Card>


    );
}
export default InventoryForm;