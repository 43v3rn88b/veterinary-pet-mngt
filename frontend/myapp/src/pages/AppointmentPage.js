import React from "react";
import Layout from "../components/Layout";
import AppointmentForm from "../components/AppointmentForm";

function AppointmentPage({pets}) {
    return (
        
            <AppointmentForm pets={pets}/>
        
    );
}

export default AppointmentPage;
