import React from "react";
import Layout from "../components/Layout";
import DashboardForm from "../components/DashboardForm";

function DashboardPage({appointments}) {
    return (

        <DashboardForm appointments={appointments}/>

    );
}

export default DashboardPage;
