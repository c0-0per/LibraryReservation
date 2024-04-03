import React, { useState, useEffect } from 'react';
import Header from './Header';
import styles from './CartPage.module.css'; // Make sure to create this CSS module
import { useNavigate } from 'react-router-dom';

function CartPage() {
    const [cartItems, setCartItems] = useState([]);


    useEffect(() => {
        const token = localStorage.getItem('token');
        fetch('http://localhost:8080/api/cart/book_items', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
                // Include other headers such as authorization if needed
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setCartItems(data);
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }, []);


    const removeBookFromCart = async (bookIdToRemove) => {
        const token = localStorage.getItem('token');
        console.log(JSON.stringify({ id: bookIdToRemove }));
        try {
            const response = await fetch('http://localhost:8080/api/cart/book_items', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify({ id: bookIdToRemove }),
            });
            if (!response.ok) {
                throw new Error('Failed to remove book from cart');
            }
            // Update local cart state
            setCartItems(currentItems => currentItems.filter(item => item.id !== bookIdToRemove));
        } catch (error) {
            console.error('Error removing book from cart:', error);
        }
    };

    const makeReservation = async () => {
        const token = localStorage.getItem('token');
        try {
            const response = await fetch(`http://localhost:8080/api/reservations/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                }
            });
            if (!response.ok) {
                throw new Error('Failed to make a reservation');
            }
            // Optionally, handle the response, for example, show a success message
            alert('Reservation made successfully!');
            setCartItems([]);
            // Update local cart state
        } catch (error) {
            console.error('Error making the reservation:', error);
            alert('Failed to make a reservation.');
        }
    };


    const handleQuantityChange = (itemId, newQuantity) => {
        // Implement logic to handle quantity change
        setCartItems(currentItems => currentItems.map(item =>
            item.id === itemId ? { ...item, quantity: newQuantity } : item
        ));
    };

    return (
        <div className={styles.container}>
            <Header isHomePage={false} searchQuery="" handleSearchChange={() => {}} />
            <h1>Your Cart</h1>
            <section  className={styles.cartItems}>
                {cartItems.length > 0 ? (
                    <>
                        {cartItems.map(item => (
                            <div key={item.id} className={styles.cartItem}>
                                <span>{item.title.nameOfBook}</span>
                                <button onClick={() => removeBookFromCart(item.id)} className={styles.removeButton}>
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className={styles.removeIcon}><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>
                                </button>

                            </div>
                        ))}
                        <div className={styles.reservationButtonContainer}>
                            <button onClick={makeReservation} className={styles.makeReservationButton}>
                                Make Reservation
                            </button>
                        </div>
                    </>
                ) : (
                    <p>Your cart is empty.</p>
                )}
            </section >
        </div>
    );
}

export default CartPage;
