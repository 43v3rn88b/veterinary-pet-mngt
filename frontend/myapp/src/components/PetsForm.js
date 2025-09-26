import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "./ui/Card";
import SectionTitle from "./ui/SectionTitle";
import FormInput from "./ui/FormInput";
import {DangerButton, PrimaryButton, SecondaryButton} from "./ui/Buttons";
import api from "./api";
function PetsForm({owners  = []} ) {
    //const api = axios.create({baseURL: "http://localhost:8080"});
    const [search, setSearch] = useState("");
    const [pets, setPets] = useState([]);
    const [newPet, setNewPet] = useState({
        name: "",
        species: "",
        breed: "",
        age: "",
        ownerId: ""
    });
    const [editingPetId, setEditingPetId] = useState(null);

    async function addPet(e) {
        e.preventDefault();
        if (!newPet.name || !newPet.ownerId)
            return alert("Please enter pet name and select an owner.");

        try {
            if (editingPetId) {
                await api.put(`/pets/${editingPetId}`, newPet);
                setEditingPetId(null);
            } else {
                await api.post("/pets", newPet);
            }
            setNewPet({name: "", species: "", breed: "", age: "", ownerId: ""});
            fetchPets();
        } catch (err) {
            console.error("handleAddPet error", err);
            alert("Failed to save pet.");
        }
    }

    async function fetchPets() {
        try {
            const res = await api.get("/pets");
            console.log("✅ Pets:", res.data);
            setPets(Array.isArray(res.data) ? res.data : []);
        } catch (err) {
            console.error("❌ fetchPets error:", err);
            setPets([]);
        }
    }

    function editPet(pet) {
        setNewPet({
            name: pet.name || "",
            species: pet.species || "",
            breed: pet.breed || "",
            age: pet.age || "",
            ownerId: pet.owner ? pet.owner.id : ""
        });
        setEditingPetId(pet.id);
    }

    async function removePet(id) {
        if (!window.confirm("Delete this pet?")) return;
        try {
            await api.delete(`/pets/${id}`);
            fetchPets();
        } catch (err) {
            console.error("removePet error", err);
            alert("Failed to delete pet.");
        }
    }
    useEffect(() => {
        fetchPets();
    }, []);
    const KeyValue = ({label, value}) => (
        <div style={{marginBottom: 6}}>
            <strong>{label}:</strong> <span>{value}</span>
        </div>
    );
    const filteredPets = pets.filter((p) => {
        const term = search.toLowerCase();
        return (
            p.ownerName?.toLowerCase().includes(term) ||
            p.name?.toLowerCase().includes(term) ||
            p.species?.toLowerCase().includes(term) ||
            p.breed?.toLowerCase().includes(term)
        );
    });

    return (
        <Card>
            <SectionTitle>Pets</SectionTitle>

            {/* Form */}
            <form onSubmit={addPet } className="space-y-4 mb-6">
                <FormInput
                    placeholder="Name"
                    value={newPet.name}
                    onChange={(e) => setNewPet({ ...newPet, name: e.target.value })}
                />
                <FormInput
                    placeholder="Species"
                    value={newPet.species}
                    onChange={(e) => setNewPet({ ...newPet, species: e.target.value })}
                />
                <FormInput
                    //type="email"
                    placeholder="Breed"
                    value={newPet.breed}
                    onChange={(e) => setNewPet({ ...newPet, breed: e.target.value })}
                />
                <FormInput
                    placeholder="Age"
                    value={newPet.age}
                    onChange={(e) => setNewPet({ ...newPet, age: e.target.value })}
                />
                <select
                    value={newPet.ownerId}
                    onChange={(e) => setNewPet({
                        ...newPet,
                        ownerId: Number(e.target.value)

                    })}
                    required
                    className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                    <option value="">Select Owner</option>
                    {owners.length > 0 ? (
                        owners.map((owner) => (
                            <option key={owner.id}
                                    value={owner.id}>{owner.name}</option>
                        ))
                    ) : (
                        <option disabled>No owners found</option>)}
                    {/*    {Array.isArray(owners) && owners.map(owner => (*/}
                    {/*    <option key={owner.id} value={owner.id}>*/}
                    {/*        {owner.name}*/}
                    {/*    </option>*/}
                    {/*))}*/}


                </select>
                <PrimaryButton type="submit">Save Pet</PrimaryButton>
            </form>

            <h3 className="text-xl font-semibold mt-6 mb-2 text-gray-700">Search</h3>
            {/* ✅ Search input */}
            <FormInput
                type="text"
                placeholder="Search by owner, pet, species, breed..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                //className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            {/* Owners List */}
            <h3 className="text-lg font-semibold mt-3">Existing Pets</h3>
            <div className="max-w-2xl mx-auto p-6 bg-white rounded-2xl ">{
            pets?.length === 0 && <div>No pets yet.</div>   }</div>
            <ul className="space-y-4">
                {filteredPets.map((p) => (
                    <li
                        key={p.id}
                        className="p-4 bg-gray-50 border border-gray-200 rounded-lg shadow-sm flex flex-col md:flex-row md:justify-between md:items-center"
                    >
                        <div className="space-y-1">
                            <KeyValue label="Name" value={p.name} />
                            <KeyValue label="Species" value={p.species} />
                            <KeyValue label="Breed" value={p.breed} />
                            <KeyValue label="Age" value={p.age} />
                            <KeyValue label="Owner Name"
                                      value={p.ownerName}/>
                        </div>
                        <div className="flex space-x-2 mt-3 md:mt-0">
                            <SecondaryButton onClick={() => editPet(p)}>Edit</SecondaryButton>
                            <DangerButton onClick={() => removePet(p.id)}>Delete</DangerButton>
                        </div>
                    </li>
                ))}
            </ul>
        </Card>

    );
}

export default PetsForm;


