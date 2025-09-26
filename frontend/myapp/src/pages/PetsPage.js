import React from "react";
import Layout from "../components/Layout";
import PetsForm from "../components/PetsForm";

function PetsPage({ owners, pets }) {
    return (
        
            <PetsForm owners={owners} pets={pets} />

    );
}

export default PetsPage;
