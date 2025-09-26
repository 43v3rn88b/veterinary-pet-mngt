import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "./ui/Card";
import SectionTitle from "./ui/SectionTitle";
import FormInput from "./ui/FormInput";
import {DangerButton, PrimaryButton, SecondaryButton} from "./ui/Buttons";
import api from "./api";

function InvoiceForm({appointments = []}) {
    //const api = axios.create({baseURL: "http://localhost:8080"});

    // -------------------------
    // invoice state & helpers
    // -------------------------

    const [invoiceForm, setInvoiceForm] = useState({
        date: "",
        amount: "",
        status: "PENDING",
        appointmentId: "",
    });
    //const [appointments, setAppointments] = useState([]);
    const [invoices, setInvoices] = useState([]);

    const [search, setSearch] = useState("");
    const fetchInvoices = async () => {
        try {
            const res = await api.get("/invoices");
            setInvoices(res.data);
        } catch (err) {
            console.error("Error fetching invoices", err);
        }
    };
    // ✅ Apply filter
    const filteredInvoices = invoices.filter((inv) => {
        const term = search.toLowerCase();
        return (
            inv.ownerName?.toLowerCase().includes(term) ||
            inv.petName?.toLowerCase().includes(term) ||
            inv.date?.toLowerCase().includes(term) ||
            inv.status?.toLowerCase().includes(term)
        );
    });


    const handleChange = (e) => {
        setInvoiceForm({...invoiceForm, [e.target.name]: e.target.value});
    };

    const submitInvoice = async (e) => {
        e.preventDefault();
        try {
            await api.post("/invoices", invoiceForm);
            setInvoiceForm({
                date: "",
                amount: "",
                status: "PENDING",
                appointmentId: ""
            });
            fetchInvoices();
        } catch (err) {
            console.error("Error creating invoice", err);
        }
    };
    const editInvoice = (inv) => {
        setInvoiceForm({
            id: inv.id,
            amount: inv.amount,
            status: inv.status,
            appointmentId: inv.appointmentId,
        });
    };

    async function removeInvoice(id) {
        if (!window.confirm("Delete this invoice?")) return;
        try {
            await api.delete(`/invoices/${id}`);
            fetchInvoices();
        } catch (err) {
            console.error("remove invoice error", err);
            alert("Failed to delete invoice.");
        }
    }

    useEffect(() => {
        fetchInvoices();
    }, []);

    const KeyValue = ({label, value}) => (
        <div style={{marginBottom: 6}}>
            <strong>{label}:</strong> <span>{value}</span>
        </div>
    );

    return(
        <Card>
            <SectionTitle>Invoices</SectionTitle>

            {/* Form */}
            <form onSubmit={submitInvoice} className="space-y-4 mb-6">

                <FormInput
                    type="number"
                    step="0.01"
                    name="amount"
                    placeholder="Amount"
                    value={invoiceForm.amount}
                    onChange={(e) => setInvoiceForm({
                        ...invoiceForm,
                        amount: e.target.value
                    })}
                />
                <select name="status" value={invoiceForm.status}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                    <option value="PENDING">PENDING</option>
                    <option value="PAID">PAID</option>
                </select>
                <select
                    name="appointmentId"
                    value={invoiceForm.appointmentId}
                    onChange={handleChange}
                    required
                    className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                    <option value="">Select Appointment</option>
                    {appointments.map((a) => (
                        <option key={a.id} value={a.id}>
                            {a.date.replace("T", " ")} — {a.petName} ({a.ownerName})
                        </option>
                    ))}
                </select>

                <PrimaryButton type="submit">Save Invoice</PrimaryButton>
            </form>

            <h3 className="text-xl font-semibold mt-6 mb-2 text-gray-700">Search</h3>
            {/* ✅ Search input */}
            <FormInput
                type="text"
                placeholder="Search by owner, pet, date, status..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}/>
            {/* Owners List */}
        <h3 className="text-lg font-semibold mt-3">Existing Invoices</h3>
        <div className="max-w-2xl mx-auto p-6 bg-white rounded-2xl ">{
            invoices.length === 0 && <div>No invoices.</div>}</div>
        <ul className="space-y-4">
            {filteredInvoices.map((i) => (
                <li
                    key={i.id}
                    className="r-4 bg-gray-50 border border-gray-200 rounded-lg shadow-sm flex flex-col md:flex-row md:justify-between md:items-center"
                >
                    <div className="space-y-1">
                        <KeyValue label="Date"
                                  value={i.date}/>
                        <KeyValue label="Amount ($)" value={i.amount}/>
                        <KeyValue label="Status" value={i.status}/>
                        <KeyValue label="Appointment"
                                  value={i.appointmentDate ? i.appointmentDate.replace("T", " ") : ""}/>
                        <KeyValue label="Pet Name" value={i.petName}/>
                        <KeyValue label="Owner Name" value={i.ownerName}/>
                    </div>
                    <div className="flex space-x-2 mt-3 md:mt-0">
                        <SecondaryButton
                            onClick={() => editInvoice(i)}>Edit</SecondaryButton>
                        <DangerButton
                            onClick={() => removeInvoice(i.id)}>Delete</DangerButton>
                    </div>
                </li>
            ))}
        </ul>
        </Card>
    );
}

export default InvoiceForm;