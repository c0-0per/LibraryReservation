package cz.cvut.ear.privatelib.rest;

import cz.cvut.ear.privatelib.model.Cart;
import cz.cvut.ear.privatelib.model.Reservation;
import cz.cvut.ear.privatelib.model.User;
import cz.cvut.ear.privatelib.service.ReservationService;
import cz.cvut.ear.privatelib.service.UserService;
import cz.cvut.ear.privatelib.util.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    private final UserService userService;


    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Void> createReservation() {
        final Reservation reservation = reservationService.create();
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", reservation.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // только юзер которьій сделал и админ
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservation(@PathVariable Integer id) {
        final Reservation reservation = reservationService.find(id);
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
        return reservation;
    }
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getReservationsByUser() {
        try {
            String loginName = Utils.getCurrentUserLogin().get();
            User user = userService.findOne(loginName);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            List<Reservation> reservations = reservationService.findAllReservationsByUser(user);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to get reservations", e);
        }
    }




    @DeleteMapping(value = "/{id}/delete")
    public void deleteReservation(@PathVariable Integer id) {
        Reservation reservation = reservationService.find(id);
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
        reservationService.delete(reservation);
    }

}
