import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "./ui/Card";
import SectionTitle from "./ui/SectionTitle";
import FormInput from "./ui/FormInput";
import {DangerButton, PrimaryButton, SecondaryButton} from "./ui/Buttons";
import api from "./api";
function MedicalRecordForm({appointments  = []}) {
    //const api = axios.create({baseURL: "http://localhost:8080"});


    // -------------------------
    // records state & helpers
    // -------------------------,
    const [search, setSearch] = useState("");
    const [records, setRecords] = useState([]);
    const [editingId, setEditingId] = useState(null); // ✅ track if editing
    const [form, setForm] = useState({
        appointmentId: "",
        appointmentDate: "",
        diagnosis: "",
        treatment: "",
    });
    const fetchRecords = async () => {
        try {
            const res = await api.get("/medicalRecords");
            console.log("✅ records:", res.data);
            setRecords(Array.isArray(res.data) ? res.data : []);
        } catch (err) {
            console.error("Error loading records", err);
        }
    };
    const submitRecord = async (e) => {
        e.preventDefault();
        try {
            if (editingId) {
                // ✅ update existing record
                await api.put(`/medicalRecords/${editingId}`, form);
                alert("✅ Medical record updated successfully");
                setEditingId(null);
            } else {
                // ✅ create new record
                await api.post("/medicalRecords", form);
                alert("✅ Medical record saved successfully");
            }

            // reset form
            setForm({
                appointmentId: "",
                diagnosis: "",
                treatment: "",
            });

            await fetchRecords();
        } catch (err) {
            if (err.response && err.response.status === 400) {
                alert("⚠️ A medical record already exists for this appointment.");
            } else {
                alert("❌ Failed to save record. Please try again.");
            }
        }
    };

    async function removeRecord(id) {
        if (!window.confirm("Delete this record?")) return;
        try {
            await api.delete(`/medicalRecords/${id}`);
            fetchRecords();
        } catch (err) {
            console.error("remove record error", err);
            alert("Failed to delete record.");
        }
    }


    const filteredRecords = records.filter((r) => {
        const term = search.toLowerCase();
        return (
            r.ownerName?.toLowerCase().includes(term) ||
            r.petName?.toLowerCase().includes(term) ||
            r.diagnosis?.toLowerCase().includes(term) ||
            r.appointmentDate?.toLowerCase().includes(term)
        );
    });

    const KeyValue = ({label, value}) => (
        <div style={{marginBottom: 6}}>
            <strong>{label}:</strong> <span>{value}</span>
        </div>
    );

    useEffect(() => {
        fetchRecords();
    }, []);

    function editRecord(r) {
        setEditingId(r.id); // ✅ mark as editing this record
        setForm({
            appointmentId: r.appointmentId,
            diagnosis: r.diagnosis,
            treatment: r.treatment,
        });
        window.scrollTo({ top: 0, behavior: 'smooth' });

    }

    return(
        <Card>
            <SectionTitle>Medical Records</SectionTitle>

            {/* Form */}
            <form onSubmit={submitRecord} className="space-y-4 mb-6">
                <select
                    value={form.appointmentId}
                    onChange={(e) => setForm({
                        ...form,
                        appointmentId: e.target.value
                    })}
                    required
                    className="border rounded-lg r-2 focus:ring focus:ring-blue-300"
                >
                    <option value="">-- Select Appointment --</option>
                    {appointments.map((appt) => (
                        <option key={appt.id} value={appt.id}>
                            {appt.date.replace("T", " ")} - {appt.petName}
                        </option>
                    ))}
                </select>

                <FormInput
                    placeholder="Diagnosis"
                    value={form.diagnosis}
                    onChange={(e) => setForm({ ...form, diagnosis: e.target.value })}
                />
                <FormInput
                    placeholder="Treatment"
                    value={form.treatment}
                    onChange={(e) => setForm({ ...form, treatment: e.target.value })}
                />

                <PrimaryButton type="submit">Save Record</PrimaryButton>
            </form>

            <h3 className="text-xl font-semibold mt-6 mb-2 text-gray-700">Search</h3>
            {/* ✅ Search input */}
            <FormInput
                placeholder="Search by owner, pet, diagnosis, or date"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                />

            {/* Owners List */}
            <h3 className="text-lg font-semibold mt-3">Existing Records</h3>
            <div className="max-w-2xl mx-auto p-6 bg-white rounded-2xl">{
                records?.length === 0 && <div>No record items.</div>}</div>
            <ul className="space-y-4">
                {filteredRecords.map((r) => (
                    <li
                        key={r.id}
                        className="r-4 bg-gray-50 border border-gray-200 rounded-lg shadow-sm flex flex-col md:flex-row md:justify-between md:items-center"
                    >
                        <div className="space-y-1">
                            <KeyValue label="Appointment Date"
                                      value={r.appointmentDate.replace("T", " ") }/>
                            <KeyValue label="Pet Name" value={r.petName} />
                            <KeyValue label="Owner" value={r.ownerName} />
                            <KeyValue label="Diagnosis" value={r.diagnosis} />
                            <KeyValue label="Treatment" value={r.treatment} />
                        </div>
                        <div className="flex space-x-2 mt-3 md:mt-0">
                            <SecondaryButton onClick={() => editRecord(r)}>Edit</SecondaryButton>
                            <DangerButton onClick={() => removeRecord(r.id)}>Delete</DangerButton>
                        </div>
                    </li>
                ))}
            </ul>
        </Card>
    );
}
export default MedicalRecordForm;