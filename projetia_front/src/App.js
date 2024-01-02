import React, {useEffect} from 'react';
import './App.css';
import {Route,BrowserRouter as Router, Routes} from "react-router-dom";
import LoginPage from "./components/Login";
import SignupPage from "./components/Signin";
import NotFoundPage from "./components/NotFound";
import Home from "./components/Home";
import { ToastContainer } from 'react-toastify';
import NavBar from "./components/Utils/Navbar";
import StudentDetails from "./components/StudentDetails";
const { createBrowserHistory } = require("history");
function App() {
    const history = createBrowserHistory();
    const apiUrl = 'http://localhost:8080/api';
    const [user, setUser] = React.useState({
        login: '',
        id: '',
        email: '',
        typeUtilisateur: 'administrateur',
        directories: {
            id: '',
            name: '',
            ouvertures: '',
            nb_tests: '',
            nb_success: '',
            color: '',
        }
    });

    useEffect(() => {
        console.log(user);
    } , [user]);
    return (
        <Router >
            <div>
                <ToastContainer />
                < NavBar />
                <Routes>
                    <Route path="/" element={<Home user={user} setUser={setUser} apiUrl={apiUrl} />} />
                    <Route path="/" element={<Home user={user} setUser={setUser} apiUrl={apiUrl} />} />
                    <Route path="/login" element={<LoginPage setUser={setUser} apiUrl={apiUrl} />} />
                    <Route path="/signin" element={<SignupPage  apiUrl={apiUrl} />} />
                    <Route path="*" element={<NotFoundPage />} />
                    <Route path="/student-details/:id" element={<StudentDetails apiUrl={apiUrl} />} />

                </Routes>
            </div>
        </Router>
    );
}

export default App;
