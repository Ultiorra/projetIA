import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';

const LoginPage = ({ setUser,apiUrl, setConnected , connected}) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const history = useNavigate();

    const handleLogin = async () => {
        await checkAdminRights()
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
            setConnected(true)

            const result = await response.json();

            console.log('Result:', result);
           setUser(result.user);
        } catch (error) {
            console.error('Error checking admin rights:', error);
            return false;
        }
    };

    useEffect(() => {
        if (connected)
            history("/")
    }, []);
    return (
        <div>

            <form style={{
                display: 'flex',
                flexDirection: 'column',
                width: '10%',
                left: '50%',
                top: '50%',
                position: 'absolute',
                transform: 'translate(-50%, -50%)'
            }}>
                <h2>Page de Connexion</h2>
                <label>
                    Email:
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                </label>
                <br/>
                <label>
                    Mot de passe:
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </label>
                <br/>
                <button type="button" onClick={handleLogin}>
                    Se connecter
                </button>
            </form>
        </div>
    );
};

export default LoginPage;
