// AuthContext.js
import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');

    const fetchUserDetails = () => {
        const token = localStorage.getItem('token');
        if (!token) {
            setIsLoggedIn(false);
            return;
        }

        fetch('http://localhost:8080/users/info', { // Adjust the URL to your user info endpoint
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch user details');
                }
                return response.json();
            })
            .then(data => {
                setIsLoggedIn(true);
                setUsername(data.username); // Adjust according to the structure of your response
            })
            .catch(error => {
                console.error(error);
                setIsLoggedIn(false);
                localStorage.removeItem('token'); // Consider logging out the user if the token is invalid
            });
    };

    useEffect(() => {
        fetchUserDetails();
    }, []);

    // useEffect(() => {
    //     const token = localStorage.getItem('token');
    //     console.log('Token from localStorage:', token); // Debug logging
    //     if (token) {
    //         setIsLoggedIn(true);
    //         setUsername(username);
    //         console.log('User is considered logged in');
    //     }
    // }, []);

    const login = async (loginUsername, loginPassword) => {
        try {
            const response = await fetch('http://localhost:8080/users/authenticate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username: loginUsername, password: loginPassword }),
            });

            if (!response.ok) throw new Error('Authentication failed');

            const data = await response.json();
            localStorage.setItem('token', data.token);
            localStorage.setItem('username', loginUsername)
            setIsLoggedIn(true);
            setUsername(loginUsername); // Optionally, set username based on response
            return true;
        } catch (error) {
            console.error('Login Error:', error);
            return false;
        }

    };

    const logout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        setUsername('');
    };


    return (
        <AuthContext.Provider value={{ isLoggedIn, username, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};