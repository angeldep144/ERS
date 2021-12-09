package services;

import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import kotlin.Pair;
import models.Reimbursement;
import models.User;

import java.util.List;

public class ReimbursementService {
    ReimbursementDao reimbDao;
    UserDao userDao;

    static final int MAX_DESCRIPTION_LENGTH = 250;

    // CONSTRUCTORS
    public ReimbursementService() {
        this.reimbDao = new ReimbursementDaoImpl();
        UserDao userDao = new UserDaoImpl();
    }
    public ReimbursementService(ReimbursementDao reimbDao) {
        this.reimbDao = reimbDao;
    }

    // CREATE
    public Pair<Boolean, String> createReimbursement(Integer user_id, Reimbursement reimb){
        User user = userDao.getUser(user_id);
        if(user == null)
            return new Pair<Boolean, String>(Boolean.FALSE, "404 User with ID: " +user_id+ " does not exist");
        Pair<Boolean, String> check_reimb = verifyReimbursementFields(reimb);
        if(!check_reimb.getFirst())
            return check_reimb;     // returns Boolean and String: "{status code} + "message"

        reimbDao.createReimbursement(reimb);
        return new Pair<>(Boolean.TRUE, "200 Success");
    }

    // READ
    public Pair<Boolean, Reimbursement> getReimbursement(Integer reimb_id) {
        Reimbursement reimb = reimbDao.getReimbursement(reimb_id);
        if(reimb == null)
            return new Pair<>(Boolean.FALSE, new Reimbursement());

        return new Pair<>(Boolean.TRUE, reimb);
    }

    public Pair<Boolean, List<Reimbursement>> getAllReimbursements() {
        List<Reimbursement> reimb = reimbDao.getAllReimbursements();
        if(reimb == null)
            return new Pair<>(Boolean.FALSE, null);

        return new Pair<>(Boolean.TRUE, reimb);
    }

    public Pair<Boolean, List<Reimbursement>> getUserReimbursements(Integer user_id) {
        User user = userDao.getUser(user_id);

        // check if user exists. if user is null, return FALSE
        if(user == null)
            return new Pair<>(Boolean.FALSE, null);

        List<Reimbursement> reimb = reimbDao.getUserReimbursements(user_id);

        // check if history of reimbursement data exists
        if(reimb == null)
            return new Pair<>(Boolean.FALSE, null);

        // if user exists and reimbursements exist,
        return new Pair<>(Boolean.TRUE, reimb);
    }

    // UPDATE
    public Pair<Boolean, String> updateStatus(Integer status_id, Integer resolver_id, Integer reimb_id){
        User resolver = userDao.getUser(resolver_id);
        if(resolver == null)
            return new Pair<>(Boolean.FALSE, "404 User (Resolver) with ID: " +resolver_id+ " does not exist");

        // check user's role ID to scan employee position (EMPLOYEE ROLE ID = 1, MANAGER ROLE ID = 2)
        if(resolver.getRole_id() != 2)
            return new Pair<>(Boolean.FALSE, "403 Employee does not have access to this feature");

        // check by REIMB_ID if reimbursement exists in the system
        if(reimbDao.getReimbursement(reimb_id) == null)
            return new Pair<>(Boolean.FALSE, "404 Reimbursement with ID: " +reimb_id+ " does not exist");

        return new Pair<>(Boolean.TRUE, "200 Success");
    }

    // DELETE
    public Pair<Boolean, String> deleteReimbursement(Integer reimb_id){
        if(reimbDao.getReimbursement(reimb_id) == null)
            return new Pair<>(Boolean.FALSE, "404 Reimbursement with ID: " +reimb_id+ "does not exist");

        return new Pair<>(Boolean.TRUE, "200 Success");
    }


    public Pair<Boolean, String> verifyReimbursementFields(Reimbursement reimb) {
        if(reimb == null)
            return new Pair<>(Boolean.FALSE, "404 No reimbursement object found");
        if(userDao.getUser(reimb.getAuthor_id()) == null)
            return new Pair<>(Boolean.FALSE, "404 No such user with ID " +reimb.getAuthor_id()+ " exists");
        if(reimb.getDescription().length() > MAX_DESCRIPTION_LENGTH)
            return new Pair<>(Boolean.FALSE, "400 Descriptiom is too long. Should be maximum " + MAX_DESCRIPTION_LENGTH + " characters");

        return new Pair<>(Boolean.TRUE, "");
    }
}
