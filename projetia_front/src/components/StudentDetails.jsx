
import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';

const StudentDetails = ({ apiUrl }) => {
    const { id } = useParams();
    const [notes, setNotes] = useState([]);
    const [contacts, setContacts] = useState([]);
    const [prediction, setPrediction] = useState(null);
    const [formData, setFormData] = useState({
        matiere: '',
        notes: 0,
    });

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch(apiUrl + '/notesAdd', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    matiere: formData.matiere,
                    note: formData.notes,
                    idStudent: id,
                }),
            });

            if (response.ok) {
                console.log('Submission successful');
                fetchNotes();
            } else {
                console.error('Submission failed');
            }
        } catch (error) {
            console.error('Error during submission:', error);
        }
    };

    const fetchNotes = async () => {
        try {
            const response = await fetch(apiUrl + '/notes/' + id)
            if (!response.ok) {
                throw new Error('Failed to fetch student notes');
            }

            const data = await response.json();
            setNotes(data);
        } catch (error) {
            console.error('Error fetching student notes:', error);
        }
    };
    const makePrediction = async () => {
        try {
            const response = await fetch(apiUrl + `/performLinearRegression/${id}`);
            if (!response.ok) {
                throw new Error('Failed to make prediction');
            }

            const data = await response.json();
            setPrediction(data);
        } catch (error) {
            console.error('Error making prediction:', error);
        }
    };
    const fetchContacts = async () => {
        try {
            const response = await fetch(apiUrl + '/contacts/' + id);
            if (!response.ok) {
                throw new Error('Failed to fetch student contacts');
            }

            const data = await response.json();
            setContacts(data);
            console.log(data);
        } catch (error) {
            console.error('Error fetching student contacts:', error);
        }
    };
    useEffect(() => {
        fetchNotes();
        fetchContacts();
    }, [id]);

    if (notes === [] || contacts === []) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>Student Details</h2>
            <div>
                <h3>Notes</h3>
                <ul>
                    {notes.map((note) => (
                        <li key={note.noteid}>
                            {note.matiere}: {note.note}
                        </li>
                    ))}
                </ul>
            </div>

            <div>
                <h2>Interface Étudiant</h2>
                <form onSubmit={handleSubmit}>
                    <label>
                       Matiere:
                        <input
                            type="text"
                            name="matiere"
                            value={formData.matiere}
                            onChange={handleInputChange}
                        />
                    </label>
                    <br />
                    <label>
                        Notes:
                        <input
                            type="number"
                            name="notes"
                            value={formData.notes}
                            onChange={handleInputChange}
                        />
                    </label>
                    <button type="submit">Soumettre</button>
                </form>
            </div>

            <div>
                <h3>Contact Enterprises</h3>
                <table style={
                    {
                        backgroundColor: '#f4f4f4',
                    }
                }>
                    <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Date</th>
                        <th>N° entretiens</th>
                        <th>Statut</th>
                        <th>Commentaires</th>
                    </tr>
                    </thead>
                    <tbody>
                    {contacts.map((student) => (
                        <tr key={student.contactID}>
                            <td>{student.nomEntreprise}</td>
                            <td>{student.dateContact}</td>
                            <td>{student.nombreEntretiens}</td>
                            <td>
                                {student.statut === 'rejected' ? 'Rejeté' : student.statut === 'accepted' ? 'Accepté' : 'En attente'}

                            </td>
                            <td>
                                {student.commentairesEntreprise}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <button onClick={makePrediction}>Make Prediction</button>
            {prediction !== null && (
                <div>
                    <h3>Prediction</h3>
                    <p>{prediction}</p>
                </div>
            )}
            <Link to="/">Back to Student List</Link>
        </div>
    );
};

export default StudentDetails;
