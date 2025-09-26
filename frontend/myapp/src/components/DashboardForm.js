import React, {useEffect, useState} from "react";
import axios from "axios";
import api from "./api";

/**
 * DashboardPage - Tailwind-styled dashboard showing totals and appointments.
 */
export default function DashboardPage() {

    //const api = axios.create({baseURL: "http://localhost:8080"});

    const [summary, setSummary] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        api
            .get("/dashboard/summary")
            .then((res) => setSummary(res.data))
            .catch((err) => {
                console.error("Error fetching dashboard summary", err);
                setSummary(null);
            })
            .finally(() => setLoading(false));
    }, []);

    // Helpers
    const fmtCurrency = (value) => {
        if (value == null) return "$0.00";
        try {
            return new Intl.NumberFormat("en-US", {
                style: "currency",
                currency: "USD",
            }).format(value);
        } catch {
            return `$${value}`;
        }
    };

    const fmtDateTime = (iso) => {
        if (!iso) return "—";
        const d = new Date(iso);
        return d.toLocaleString(undefined, {
            year: "numeric",
            month: "short",
            day: "numeric",
            hour: "2-digit",
            minute: "2-digit",
        });
    };

    if (loading) {
        return (
            <div className="p-6">
                <div className="animate-pulse space-y-4">
                    <div className="h-8 bg-gray-200 rounded w-1/3"/>
                    <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
                        <div className="h-24 bg-gray-200 rounded"/>
                        <div className="h-24 bg-gray-200 rounded"/>
                        <div className="h-24 bg-gray-200 rounded"/>
                    </div>
                    <div className="h-40 bg-gray-200 rounded"/>
                </div>
            </div>
        );
    }

    // Safety: use empty arrays if not present
    const pending = summary?.pendingAppointments ?? [];
    const todays = summary?.todaysAppointments ?? [];

    return (
        <div className="p-6">
            <div className="flex items-center justify-between mb-6">
                <h2 className="text-2xl font-bold text-gray-800">Dashboard</h2>
                <div className="text-sm text-gray-600">Summary overview</div>
            </div>

            {/* Stats cards */}
            <div
                className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
                <div
                    className="p-4 bg-white rounded-2xl shadow-sm border border-gray-100">
                    <div className="text-sm text-gray-500">Total Owners</div>
                    <div className="mt-2 text-2xl font-semibold text-gray-800">
                        {summary?.totalOwners ?? 0}
                    </div>
                </div>

                <div
                    className="p-4 bg-white rounded-2xl shadow-sm border border-gray-100">
                    <div className="text-sm text-gray-500">Total Pets</div>
                    <div className="mt-2 text-2xl font-semibold text-gray-800">
                        {summary?.totalPets ?? 0}
                    </div>
                </div>

                <div
                    className="p-4 bg-white rounded-2xl shadow-sm border border-gray-100">
                    <div className="text-sm text-gray-500">Total Appointments
                    </div>
                    <div className="mt-2 text-2xl font-semibold text-gray-800">
                        {summary?.totalAppointments ?? 0}
                    </div>
                </div>

                <div
                    className="p-4 bg-white rounded-2xl shadow-sm border border-gray-100">
                    <div className="text-sm text-gray-500">Total Revenue</div>
                    <div className="mt-2 text-2xl font-semibold text-gray-800">
                        {fmtCurrency(summary?.totalRevenue)}
                    </div>
                </div>
            </div>

            {/* Two-column lists */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                {/* Upcoming Appointments */}
                <div
                    className="bg-white rounded-2xl shadow-sm border border-gray-100 p-4">
                    <div className="flex items-center justify-between mb-3">
                        <h3 className="text-lg font-semibold text-gray-700">
                            Upcoming Appointments
                        </h3>
                        <span className="text-sm text-gray-500">
              {pending.length} shown
            </span>
                    </div>

                    {pending.length === 0 ? (
                        <p className="text-sm text-gray-500">No upcoming
                            appointments.</p>
                    ) : (
                        <ul className="space-y-3">
                            {pending.map((a) => (
                                <li
                                    key={a.id}
                                    className="flex items-start justify-between p-3 bg-gray-50 border border-gray-100 rounded-lg"
                                >
                                    <div>
                                        <div
                                            className="text-sm font-medium text-gray-800">
                                            {a.petName ?? "Unknown pet"}{" "}
                                            <span
                                                className="text-xs text-gray-500">
                        ({a.ownerName ?? "Unknown owner"})
                      </span>
                                        </div>
                                        <div
                                            className="text-xs text-gray-500 mt-1">
                                            {fmtDateTime(a.date)}
                                        </div>
                                        <div
                                            className="text-sm text-gray-600 mt-1">{a.reason}</div>
                                    </div>

                                    <div
                                        className="text-sm text-gray-400">{/* placeholder */}</div>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                {/* Today's Appointments */}
                <div
                    className="bg-white rounded-2xl shadow-sm border border-gray-100 p-4">
                    <div className="flex items-center justify-between mb-3">
                        <h3 className="text-lg font-semibold text-gray-700">
                            Today's Appointments
                        </h3>
                        <span
                            className="text-sm text-gray-500">{todays.length}</span>
                    </div>

                    {todays.length === 0 ? (
                        <p className="text-sm text-gray-500">No appointments
                            today.</p>
                    ) : (
                        <ul className="space-y-3">
                            {todays.map((a) => (
                                <li
                                    key={a.id}
                                    className="flex items-start justify-between p-3 bg-gray-50 border border-gray-100 rounded-lg"
                                >
                                    <div>
                                        <div
                                            className="text-sm font-medium text-gray-800">
                                            {a.petName ?? "Unknown pet"}{" "}
                                            <span
                                                className="text-xs text-gray-500">
                        ({a.ownerName ?? "Unknown owner"})
                      </span>
                                        </div>
                                        <div
                                            className="text-xs text-gray-500 mt-1">
                                            {fmtDateTime(a.date)}
                                        </div>
                                        <div
                                            className="text-sm text-gray-600 mt-1">{a.reason}</div>
                                    </div>

                                    <div className="flex flex-col items-end">
                                        <button
                                            onClick={() => {
                                                /* optional: open appointment detail or edit */
                                            }}
                                            className="text-xs text-blue-600 hover:underline"
                                        >
                                            View
                                        </button>
                                        <span
                                            className="text-xs text-gray-400 mt-2">
                      {a.id}
                    </span>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
}
