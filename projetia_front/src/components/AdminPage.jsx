import React, { useState, useEffect } from 'react';
import {Modal, Select} from "@mui/material";
import {Link} from "react-router-dom";
import SelectInput from "@mui/material/Select/SelectInput";
import '../css/AdminPage.css'
const AdminPage = ( { apiUrl } ) => {
  const [students, setStudents] = useState([]);
  const [newStudent, setNewStudent] = useState({
    nom: '',
    prenom: '',
    email: '',
    motDePasse: '',
    typeUtilisateur: 'etudiant1',
    // Ajoutez d'autres champs nécessaires pour votre formulaire d'ajout
  });
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    // Fetch the list of students from your backend API
    const fetchStudents = async () => {
      try {
        const response = await fetch(apiUrl + '/users');
        if (!response.ok) {
          throw new Error('Failed to fetch student data');
        }

        const data = await response.json();
        setStudents(data);
      } catch (error) {
        console.error('Error fetching student data:', error);
      }
    };

    fetchStudents();
  }, []);

  const handleStudentDetails = (studentID) => {
    console.log(`Open details for student ID: ${studentID}`);
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
      setStudents(students.concat(updatedStudents));
      setNewStudent({
        nom: '',
        prenom: '',
        email: '',
        motDePasse: '',
        typeUtilisateur: 'etudiant1',

        // Réinitialisez d'autres champs si nécessaire
      });
      closeAddStudentModal(); // Fermer la boîte de dialogue après l'ajout
    } catch (error) {
      console.error('Error adding a new student:', error);
    }
  };

  const handleRemoveStudent = async (studentID) => {
    try {
      // Envoi de la requête DELETE pour supprimer un étudiant
      const response = await fetch(apiUrl + `/usersDelete/${studentID}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        throw new Error('Failed to remove the student');
      }

      setStudents(students.filter((student) => student.utilisateurID !== studentID));
    } catch (error) {
      console.error('Error removing the student:', error);
    }
  };

  const openAddStudentModal = () => {
    setIsModalOpen(true);
  };

  const closeAddStudentModal = () => {
    setIsModalOpen(false);
  };
  return (
      <div className="admin-page">
        <h2>Admin Page - Student List</h2>
        <table>
          <thead>
          <tr>
            <th>Student ID</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Type d'utilisateur</th>
            <th>Actions </th>
          </tr>
          </thead>
          <tbody>
          {students.filter((student) => student.typeUtilisateur !== "administrateur").map((student) => (


              <tr key={student.utilisateurID}>
                <td>{student.utilisateurID}</td>
                <td>{`${student.nom} ${student.prenom}`}</td>
                <td>{student.email}</td>
                <td>{
                  student.typeUtilisateur === "etudiant1" ? "Etudiant 1ere année" : student.typeUtilisateur === "etudiant2" ? "Etudiant 2eme année" : student.typeUtilisateur
                }</td>
                  <td>

                    <Link to={`/student-details/${student.utilisateurID}`}>
                    View Details
                  </Link>
                    <br/>
                  <button onClick={() => handleRemoveStudent(student.utilisateurID)}>
                    Supprimer
                  </button>
                </td>
              </tr>
          ))}
          </tbody>
        </table>

        <Modal open={isModalOpen} onClose={closeAddStudentModal}>
          <div className="modal-content">
            <h3>Ajouter un nouvel étudiant</h3>
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
            <label>Mots de passe:
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
              </select>
            </label>
              <button onClick={handleAddStudent}>Add Student</button>
              <button onClick={closeAddStudentModal}>Cancel</button>
            </div>
          </div>
        </Modal>

      <button onClick={openAddStudentModal}>Ajouter un nouvel étudiant</button>
    </div>
  );
};

export default AdminPage;
