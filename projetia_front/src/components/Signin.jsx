import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const SignupPage = ( {apiUrl}) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const history = useNavigate();
    const [newStudent, setNewStudent] = useState({
        nom: '',
        prenom: '',
        email: '',
        motDePasse: '',
        typeUtilisateur: 'etudiant1',
    });
    const handleSignup = async () => {
        // Ajoutez ici la logique pour envoyer les données d'inscription au backend.
        // Vous pouvez également effectuer des validations côté client avant d'envoyer la requête.
        // Après l'inscription réussie, redirigez l'utilisateur vers la page de connexion.
        history('/login');
    };
    const handleAddStudent = async () => {
        try {
            // Envoi de la requête POST pour ajouter un nouvel étudiant
            const response = await fetch(apiUrl + '/usersAdd', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newStudent),
            });

            if (!response.ok) {
                throw new Error('Failed to add a new student');
            }

            // Rafraîchir la liste des étudiants après l'ajout
            const updatedStudents = await response.json();
            setNewStudent({
                nom: '',
                prenom: '',
                email: '',
                motDePasse: '',
                typeUtilisateur: 'etudiant1',

                // Réinitialisez d'autres champs si nécessaire
            });
        } catch (error) {
            console.error('Error adding a new student:', error);
        }
    };
    return (
        <div>
            <h2>Page d'Inscription</h2>
            <div style={{ display: 'flex', flexDirection: 'column', width: '10%' , left: '50%', top: '50%', position: 'absolute', transform: 'translate(-50%, -50%)'}}>
                <h3>Add New Student</h3>
                <div>
                    <label>Nom:
                        <input
                            type="text"
                            value={newStudent.nom}
                            onChange={(e) => setNewStudent({ ...newStudent, nom: e.target.value })}
                        />
                    </label>
                    <label>Prénom:
                        <input
                            type="text"
                            value={newStudent.prenom}
                            onChange={(e) => setNewStudent({ ...newStudent, prenom: e.target.value })}
                        />
                    </label>
                    <label>Email:
                        <input
                            type="text"
                            value={newStudent.email}
                            onChange={(e) => setNewStudent({ ...newStudent, email: e.target.value })}
                        />
                    </label>
                    <label>Password:
                        <input
                            type="text"
                            value={newStudent.motDePasse}
                            onChange={(e) => setNewStudent({ ...newStudent, motDePasse: e.target.value })}
                        />
                    </label>
                    <label>Type d'utilisateur:
                        <select name="typeUtilisateur"
                                value={newStudent.typeUtilisateur}
                                onChange={(e) => setNewStudent({ ...newStudent, typeUtilisateur: e.target.value })} >
                            <option value="etudiant1">Etudiant 1ere année</option>
                            <option value="etudiant2">Etudiant 2eme année</option>
                            <option value="administrateur">Administrateur</option>
                        </select>
                    </label>
                    <button onClick={handleAddStudent}>
                        S'inscrire
                    </button>
                </div>
            </div>
        </div>
    );
};

export default SignupPage;
