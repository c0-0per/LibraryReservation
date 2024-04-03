import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from "./Header";
import styles from './BookPage.module.css'; // Reuse Home page styles for consistency
import homestyles from './Home.module.css';
import { useAuth } from './AuthContext'; // Import useAuth
import bookGPTImage from './bookGPT.webp';
import collapse from "bootstrap/js/src/collapse";
import { useNavigate } from 'react-router-dom';

function BookPage() {
    const { id } = useParams();
    const [book, setBook] = useState(null);
    const { isLoggedIn } = useAuth(); // Use isLoggedIn from AuthContext
    const navigate = useNavigate();


    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('You musts be logged');
            return;
        }

        fetchBook();
    }, [ isLoggedIn, navigate]);

    const fetchBook = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/titles/${id}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setBook(data);
        } catch (error) {
            console.error("There was a problem fetching the book details:", error);
        }
    };

    const findBookId = async () => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('No authentication token found. Please log in again.');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/titles/${id}/getbooks`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                }
            });

            if (!response.ok) {
                throw new Error('Failed to fetch available books');
            }

            const book = await response.json();
            return book.id; // Assuming that the response is an array of books
        } catch (error) {
            console.error('Error during fetching book ID:', error);
            alert(error.message);
            return null;
        }
    };

    const addBookToCart = async (bookId) => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('No authentication token found. Please log in again.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/cart/book_items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify({ id: bookId }),
            });

            if (!response.ok) {
                throw new Error('Failed to add book to cart');
            }

            navigate('/cart');
        } catch (error) {
            console.error('Error during add to cart operation:', error);
            alert(error.message);
        }
    };

    const handleAddToCart = async () => {
        if (!isLoggedIn) {
            alert('You must be logged in to add items to the cart.');
            return;
        }

        const bookId = await findBookId();
        if (bookId) {
            await addBookToCart(bookId);
        }
    };


    return (
        <main  className={styles.bookPage}>
            <Header isHomePage={false} searchQuery="" handleSearchChange={() => {}} />
            {book ? (
                <article className={styles.bookDetails}>
                    <section className={styles.bookCard}>
                        <img
                            src={book.imageUrl || bookGPTImage}
                            alt={book.nameOfBook}
                            className={styles.bookCover}
                        />
                        <div className={styles.bookInfo}>
                            <h1 className={styles.bookTitle}>{book.nameOfBook}</h1>
                            <h2 className={styles.bookPublisher}>{book.publishier || 'Unknown Publisher'}</h2>
                            <p className={styles.bookDescription}>{book.descriptionOfBook}</p>
                            <p className={styles.bookGenre}>{`Genre: ${book.genre?.nameOfGenre || 'N/A'}`}</p>
                            <p className={styles.bookYear}>{`Year: ${book.publicationYear}`}</p>
                            <p className={styles.bookYear}>{`Number of free items: ${book.numberOfFreeItems}`}</p>
                            {book.numberOfFreeItems > 0 && (
                                <button className={homestyles.viewDetailsButton} onClick={handleAddToCart}>Create Reservation</button>
                            )}
                        </div>
                    </section>
                </article>
            ) : (
                <p>Loading...</p>
            )}
        </main>
    );
}

export default BookPage;
