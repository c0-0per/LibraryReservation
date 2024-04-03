package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.CartDao;
import cz.cvut.ear.privatelib.dao.LoanDao;
import cz.cvut.ear.privatelib.dao.ReservationDao;
import cz.cvut.ear.privatelib.dao.UserDao;
import cz.cvut.ear.privatelib.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    private final LoanDao dao;
    private final CartDao cartDao;
    private final UserDao userDao;
    private final ReservationDao reservationDao;


    @Autowired
    public LoanService(LoanDao dao, CartDao cartDao, UserDao userDao, ReservationDao reservationDao) {
        this.dao = dao;
        this.cartDao = cartDao;
        this.userDao = userDao;
        this.reservationDao = reservationDao;
    }

    @Transactional
    public Loan makeLoan(Reservation reservation) {
//        String loginName = Utils.getCurrentUserLogin().get();
        User user  = reservation.getCustomer();
//        User user = userDao.findByUsername(loginName);
        Loan loan = new Loan();
        loan.setCustomer(user);

        loan.setItems(reservation.getItems());
        reservation.setItems(null);
        loan.setDueDate(LocalDateTime.now().plusDays(14));
        reservation.setApprovedByLibrarianBecauseItWasPhysicallyTaken(true);
        for(Book book : loan.getItems()){
            book.setReserved(false);
            book.setLoaned(true);
        }
        reservation.setApprovedByLibrarianBecauseItWasPhysicallyTaken(true);
        reservationDao.update(reservation);

        dao.persist(loan);
        return loan;
    }

    @Transactional
    public void closeLoan(Loan loan) {
        loan.setReturned(true);
        loan.getItems().forEach(book -> {
            book.setLoaned(false);
        });
        loan.setItems(null);
    }

    @Transactional(readOnly = true)
    public Loan find(Integer id) {
        return dao.find(id);
    }
}
