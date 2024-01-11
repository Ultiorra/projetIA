import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {toast} from "react-toastify";
const path = "http://localhost/my-app/prochess/";


function NavBar({isConnected, setConnected, setUser}) {
    const navigate = useNavigate();
    const handleLogout = async (e) => {
        e.preventDefault();
        setConnected(false);
        setUser({
            login: '',
            id: '',
            email: '',
            typeUtilisateur: 'nonconnected',
            directories: {
                id: '',
                name: '',
                ouvertures: '',
                nb_tests: '',
                nb_success: '',
                color: '',
            }
        })
        navigate("/login")
    };
    return (
        <AppBar position="relative">
            <Toolbar>
                <Typography variant="h6" component={Link} to="/" style={{textDecoration: 'none', color: 'white'}}>
                    Outil de prédiction des alternances
                </Typography>
                <div style={{flexGrow: 1}}></div>
                {
                    !isConnected ?
                        <div>
                        <Button component={Link} to="/login" color="inherit">
                            Connexion
                        </Button>
                        <Button component={Link} to="/signin" color="inherit">
                            Inscription
                        </Button>
                        </div>
                        :
                        <Button component={Link} to="/" color="inherit" onClick={handleLogout}>
                            Déconnexion
                        </Button>
                }



            </Toolbar>
        </AppBar>
    );
};

export default NavBar;
