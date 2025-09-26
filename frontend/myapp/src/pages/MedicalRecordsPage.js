import React from "react";
import Layout from "../components/Layout";
import MedicalRecordForm from "../components/MedicalRecordForm";

function MedicalRecordsPage({appointments}) {
    return (
        
            <MedicalRecordForm appointments={appointments}/>
        
    );
}

export default MedicalRecordsPage;
