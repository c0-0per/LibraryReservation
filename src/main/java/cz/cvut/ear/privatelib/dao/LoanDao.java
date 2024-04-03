package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Loan;
import org.springframework.stereotype.Repository;

@Repository
public class LoanDao extends AbstractDao<Loan> {
    public LoanDao() {
        super(Loan.class);
    }
}
