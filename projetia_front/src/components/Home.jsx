import React from 'react';
import {Link} from "react-router-dom";
import StudentInterface from "./StudentPage";
import AdminPage from "./AdminPage";


const Home = ({ user, setUser, apiUrl }) => {
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
