import { useState, useEffect } from "react";
import axios from "axios";

const PetList = () => {
    const [pets, setPets] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/api/pets")
            .then(response => setPets(response.data))
            .catch(error => console.error("Error fetching pets:", error));
    }, []);

    return (
        <div>
            <h2>Pet List</h2>
            <ul>
                {pets.map(pet => (
                    <li key={pet.id}>{pet.name} - {pet.species} - {pet.age} years old</li>
                ))}
            </ul>
        </div>
    );
};

export default PetList;
