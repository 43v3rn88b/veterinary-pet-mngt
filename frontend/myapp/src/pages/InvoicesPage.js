import React from "react";
import Layout from "../components/Layout";
import InvoiceForm from "../components/InvoiceForm";

function InvoicesPage({appointments}) {
    return (
        
            <InvoiceForm appointments={appointments}/>
        
    );
}

export default InvoicesPage;
