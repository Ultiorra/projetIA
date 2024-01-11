import React, {useEffect} from 'react';
import {Link, useNavigate} from "react-router-dom";
import StudentInterface from "./StudentPage";
import AdminPage from "./AdminPage";


const Home = ({ user, setUser, apiUrl }) => {
    console.log(user)
    const history = useNavigate();

    useEffect(() => {
        if (user.typeUtilisateur === "nonconnected")
            history("/login")
    }, []);
    
    return (
        <div>
            {
                user?(
                user.typeUtilisateur === "administrateur" ? (
                    <div>
                        <AdminPage user={user} apiUrl={apiUrl}/>
                    </div>
                ) : (
                    <div>
                       <StudentInterface user={user} apiUrl={apiUrl}/>
                    </div>
                ) )
                    :
                    <div>
                        <h2>Bienvenue</h2>
                        <p>Veuillez vous connecter</p>²
                        <Link to={"/login"}>Se connecter</Link>
                    </div>
            }
        </div>
    );
};

export default Home;
