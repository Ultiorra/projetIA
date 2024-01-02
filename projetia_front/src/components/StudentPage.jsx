import React, { useState } from 'react';

const StudentInterface = ({user, apiUrl}) => {
    const [formData, setFormData] = useState({
        nomEntreprise: '',
        nombreEntretiens: 0,
        dateContact: '',
        statut: 'pending',
        commentairesEntreprise: '',
    });

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch(apiUrl + '/companyAdd', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    nomEntreprise: formData.nomEntreprise,
                    nombreEntretiens: formData.nombreEntretiens,
                    dateContact: formData.dateContact,
                    statut: formData.statut,
                    commentairesEntreprise: formData.commentairesEntreprise,
                    idStudent: user.utilisateurID, // Assuming user object has an 'id' property
                }),
            });

            if (response.ok) {
                // Handle success, e.g., show a success message or redirect
                console.log('Submission successful');
            } else {
                // Handle errors, e.g., show an error message
                console.error('Submission failed');
            }
        } catch (error) {
            console.error('Error during submission:', error);
        }
    };

    return (
        <div>
            <h2>Interface Étudiant</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Nom de l'entreprise:
                    <input
                        type="text"
                        name="nomEntreprise"
                        value={formData.nomEntreprise}
                        onChange={handleInputChange}
                    />
                </label>
                <br />
                <label>
                    Nombre d'entretiens:
                    <input
                        type="number"
                        name="nombreEntretiens"
                        value={formData.nombreEntretiens}
                        onChange={handleInputChange}
                    />
                </label>
                <br />
                <label>
                    Date de l'entretien:
                    <input
                        type="date"
                        name="dateContact"
                        value={formData.dateContact}
                        onChange={handleInputChange}
                    />
                </label>
                <br />
                <label>
                    Statut:
                    <select
                        name="statut"
                        value={formData.statut}
                        onChange={handleInputChange}
                    >
                        <option value="pending">En attente</option>
                        <option value="accepted">Accepté</option>
                        <option value="rejected">Refusé</option>
                    </select>
                </label>
                <br />
                <label>
                    Commentaires:
                    <textarea
                        name="commentairesEntreprise"
                        value={formData.commentairesEntreprise}
                        onChange={handleInputChange}
                    />
                </label>
                <button type="submit">Soumettre</button>
            </form>
        </div>
    );
};

export default StudentInterface;
