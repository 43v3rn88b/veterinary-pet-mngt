import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "./ui/Card";
import SectionTitle from "./ui/SectionTitle";
import FormInput from "./ui/FormInput";
import {DangerButton, PrimaryButton, SecondaryButton} from "./ui/Buttons";
import api from "./api";
function AppointmentForm({ pets = [] } ) {

    //const api = axios.create({baseURL: "http://localhost:8080"});

    // -------------------------
    // APPOINTMENT state & helpers
    // -------------------------
    const [search, setSearch] = useState("");
    const [appointments, setAppointments] = useState([]);
    const [appointmentForm, setAppointmentForm] = useState({
        date: "",
        petId: "",
        reason: "",
        ownerId: ""
    });
    const [editingAppointmentId, setEditingAppointmentId] = useState(null);

    // -------------------------
    // APPOINTMENT CRUD
    // -------------------------
    async function fetchAppointments() {
        try {
            const res = await api.get("/appointments");
            setAppointments(res.data || []);
            return res.data;
        } catch (err) {
            console.error("fetchAppointments error", err);
            setAppointments([]);
            throw err;
        }
    }

    async function submitAppointment(e) {
        e.preventDefault();
        if (!appointmentForm.date || !appointmentForm.petId) return alert("Please select date and pet.");

        try {
            if (editingAppointmentId) {
                await api.put(`/appointments/${editingAppointmentId}`, appointmentForm);
                setEditingAppointmentId(null);
            } else {
                await api.post("/appointments", appointmentForm);
            }
            setAppointmentForm({date: "", petId: "", reason: "", ownerId: ""});
            fetchAppointments();
        } catch (err) {
            console.error("submitAppointment error", err);
            alert("Failed to save appointment.");
        }
    }

    function editAppointment(appt) {
        // If API returns nested pet object, support both shapes

        const petId = appt.petId ?? (appt.pet && appt.pet.id) ?? "";
        const ownerId = appt.ownerId ?? (appt.pet && appt.pet.ownerId) ?? "";
        // Format LocalDateTime string for <input type="datetime-local">
        let formattedDate = "";
        if (appt.date) {
            // Remove seconds if present (e.g. "2025-08-25T10:30:00" → "2025-08-25T10:30")
            formattedDate = appt.date.slice(0, 16);
        }
        setAppointmentForm({
            date: formattedDate,
            petId: petId,
            ownerId: ownerId,
            reason: appt.reason || ""
        });
        setEditingAppointmentId(appt.id);
    }

    async function removeAppointment(id) {
        if (!window.confirm("Delete this appointment?")) return;
        try {
            await api.delete(`/appointments/${id}`);
            fetchAppointments();
        } catch (err) {
            console.error("removeAppointment error", err);
            alert("Failed to delete appointment.");
        }
    }


    const filteredAppointments = appointments.filter((a) => {
        const term = search.toLowerCase();
        return (
            a.ownerName?.toLowerCase().includes(term) ||
            a.petName?.toLowerCase().includes(term) ||
            a.date?.toLowerCase().includes(term) ||
            a.reason?.toLowerCase().includes(term)
        );
    });
    
    const KeyValue = ({label, value}) => (
        <div style={{marginBottom: 6}}>
            <strong>{label}:</strong> <span>{value}</span>
        </div>
    );
    useEffect(() => {
        fetchAppointments();
    }, []);
    return(
        <Card>
            <SectionTitle>Appointments</SectionTitle>

            <form onSubmit={submitAppointment} className="space-y-4 mb-6">

                <FormInput
                    type="datetime-local"
                    value={appointmentForm.date}
                    onChange={(e) => setAppointmentForm({...appointmentForm, date: e.target.value})}
                />
                <select
                    value={appointmentForm.petId}
                    onChange={(e) => {
                        const petId = e.target.value;
                        const selectedPet = pets.find((p) => String(p.id) === petId);

                        setAppointmentForm((a) => ({
                            ...a, petId: petId,
                            ownerId: selectedPet ? selectedPet.ownerId : ""
                        }));
                    }}>
                    <option value="">Select Pet</option>
                    {pets.map((pet) => (
                        <option key={pet.id}
                                value={pet.id}>{pet.name} ({pet.species})</option>
                    ))}
                </select>
                <FormInput
                    placeholder="Reason (optional)"
                    value={appointmentForm.reason}
                    onChange={(e) => setAppointmentForm({
                        ...appointmentForm,
                        reason: e.target.value
                    })}
                />

                <PrimaryButton type="submit">Save Appointment</PrimaryButton>
            </form>

            <h3 className="text-xl font-semibold mt-6 mb-2 text-gray-700">Search</h3>
            {/* ✅ Search input */}
            <FormInput
                type="text"
                placeholder="Search by owner, pet, date, reason..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />

            {/* Owners List */}
            <h3 className="text-lg font-semibold mt-3">Existing Appointments</h3>
            <div className="max-w-2xl mx-auto p-6 bg-white rounded-2xl">{
            appointments.length === 0 && <div>No appointments scheduled.</div>}</div>
            <ul className="space-y-4">
                {filteredAppointments.map((a) => (
                    <li
                        key={a.id}
                        className="r-4 bg-gray-50 border border-gray-200 rounded-lg shadow-sm flex flex-col md:flex-row md:justify-between md:items-center">
                        <div className="space-y-1">
                            <KeyValue label="Date"
                                      value={a.date ? a.date.replace("T", " ") : ""}/>
                            {/*<KeyValue label="Pet" value={(a.pet && a.pet.name) || a.petName || a.petId}/>*/}
                            {/*<KeyValue label="OwnerId" value={a.ownerId || (a.pet && a.pet.ownerId)}/>*/}
                            <KeyValue label="Pet" value={a.petName}/>
                            <KeyValue label="Owner" value={a.ownerName}/>
                            <KeyValue label="Reason" value={a.reason}/>
                        </div>
                        <div className="flex space-x-2 mt-3 md:mt-0">
                            <SecondaryButton onClick={() => editAppointment(a)}>Edit</SecondaryButton>
                            <DangerButton onClick={() => removeAppointment(a.id)}>Delete</DangerButton>
                        </div>
                    </li>
                ))}
            </ul>
        </Card>


    );
}
export default AppointmentForm;