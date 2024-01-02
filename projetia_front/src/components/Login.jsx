import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginPage = ({ setUser,apiUrl }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const history = useNavigate();

    const handleLogin = async () => {
        checkAdminRights()
        history('/')
    };

    const checkAdminRights = async () => {
        try {

            const response = await fetch( apiUrl + '/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: username,
                    password: password,
                }),
            });

            if (!response.ok) {
                throw new Error('Invalid response from server');
            }

            const result = await response.json();

            console.log('Result:', result);
            // Check if the user has admin rights based on the role or any other criteria.
            const isAdmin = result.user.typeUtilisateur === 'administrateur';
            setUser(result.user);

            return isAdmin;
        } catch (error) {
            console.error('Error checking admin rights:', error);
            return false;
        }
    };

    return (
        <div>
            <h2>Page de Connexion</h2>
            <form    style={{ display: 'flex', flexDirection: 'column', width: '10%' , left: '50%', top: '50%', position: 'absolute', transform: 'translate(-50%, -50%)'}}>
                <label>
                    Nom d'utilisateur:
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                </label>
                <br />
                <label>
                    Mot de passe:
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </label>
                <br />
                <button type="button" onClick={handleLogin}>
                    Se connecter
                </button>
            </form>
        </div>
    );
};

export default LoginPage;
