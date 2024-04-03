package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Reservation;
import cz.cvut.ear.privatelib.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReservationDao extends AbstractDao<Reservation>{
    @PersistenceContext
    private EntityManager manager;
    public ReservationDao() {
        super(Reservation.class);
    }

    public List<Reservation> findAllReservationsByUser(User user) {
        return manager.createQuery("SELECT r FROM Reservation r WHERE r.customer = :user", Reservation.class)
                .setParameter("user", user)
                .getResultList();
    }
}
