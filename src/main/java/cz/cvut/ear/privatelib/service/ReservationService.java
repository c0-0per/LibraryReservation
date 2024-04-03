package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.CartDao;
import cz.cvut.ear.privatelib.dao.ReservationDao;
import cz.cvut.ear.privatelib.dao.UserDao;
import cz.cvut.ear.privatelib.model.*;
import cz.cvut.ear.privatelib.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationDao dao;
    private final CartDao cartDao;
    private final UserService userService;
    private final UserDao userDao;

    @Autowired
    public ReservationService(ReservationDao dao, CartDao cartDao, UserService userService, UserDao userDao) {
        this.dao = dao;
        this.cartDao = cartDao;
        this.userService = userService;
        this.userDao = userDao;
    }

    //доделать
    @Transactional
    public Reservation create() {
        String loginName = Utils.getCurrentUserLogin().get();
        User user = userDao.findByUsername(loginName);
        Reservation reservation = new Reservation();
        reservation.setCustomer(user);
        List<Cart> carts = cartDao.findAllCartsByOwner(user);
        List<Book> books = new ArrayList<>();
        for (Cart cart : carts) {
            books.add(cart.getBook());
            reservation.setItems(books);
            cart.getBook().setReserved(true);
            cartDao.remove(cart);
        }
        reservation.setCreated(LocalDateTime.now());
        //finish here
        dao.persist(reservation);
        return reservation;
    }

    @Transactional
    public void delete(Reservation reservation) {
        reservation.getItems().forEach(book -> {
            book.setReserved(false);
        });
        reservation.setItems(null);
        dao.remove(reservation);
    }

    @Transactional(readOnly = true)
    public Reservation find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllReservationsByUser(User user) {
        return dao.findAllReservationsByUser(user);
    }
}