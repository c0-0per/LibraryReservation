package cz.cvut.ear.privatelib.rest;

import cz.cvut.ear.privatelib.model.Loan;
import cz.cvut.ear.privatelib.model.Reservation;
import cz.cvut.ear.privatelib.service.LoanService;
import cz.cvut.ear.privatelib.service.ReservationService;
import cz.cvut.ear.privatelib.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;
    private final UserService userService;
    private final ReservationService reservationService;

    @Autowired
    public LoanController(LoanService loanService, UserService userService, ReservationService reservationService) {
        this.loanService = loanService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/make/{id}")
    public ResponseEntity<Void> makeLoan(@PathVariable Integer id) {
        Reservation reservation = reservationService.find(id);
        final Loan loan = loanService.makeLoan(reservation);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", loan.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/close/{id}")
    public ResponseEntity<Void> closeLoan(@PathVariable Integer id) {
        Loan loan = loanService.find(id);
        loanService.closeLoan(loan);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public Loan getLoan(@PathVariable Integer id) {
        Loan loan = loanService.find(id);
        if (loan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
        }
        return loan;
    }
}
