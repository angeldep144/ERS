package services;

import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import kotlin.Pair;
import models.Reimbursement;
import models.ReimbursementDTO;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {
    ReimbursementDao reimbDao;
    UserDao userDao;

    static final int MAX_DESCRIPTION_LENGTH = 250;

    // CONSTRUCTORS
    public ReimbursementService() {
        this.reimbDao = new ReimbursementDaoImpl();
        this.userDao = new UserDaoImpl();
    }
    public ReimbursementService(ReimbursementDao reimbDao) {
        this.reimbDao = reimbDao;
        this.userDao = new UserDaoImpl();
    }

    // CREATE
    public Pair<Boolean, String> createReimbursement(Reimbursement reimb){

        Integer user_id = reimb.getAuthor_id();
        User user = userDao.getUser(user_id);
        if(user == null)
            return new Pair<>(Boolean.FALSE, "404 User with ID: " +user_id+ " does not exist");

        Pair<Boolean, String> check_reimb = verifyReimbursementFields(reimb);
        if(!check_reimb.getFirst())
            return check_reimb;     // returns Boolean and String: "{status code} + "message"

        reimbDao.createReimbursement(reimb);

        return new Pair<>(Boolean.TRUE, "200 Success");
    }

    // READ
    public Pair<Boolean, ReimbursementDTO> getReimbursement(Integer reimb_id) {

        Reimbursement reimb = reimbDao.getReimbursement(reimb_id);

        if(reimb == null)
            return new Pair<>(Boolean.FALSE, null);

        User author = userDao.getUser(reimb.getAuthor_id());
        User resolver = userDao.getUser(reimb.getResolver_id());

        if (author == null)
            return new Pair<>(Boolean.FALSE, null);

        ReimbursementDTO _reimb = new ReimbursementDTO(reimb);
        _reimb.setAuthor(author.getFullName());

        if (resolver == null)
            return new Pair<>(Boolean.TRUE, _reimb);

        _reimb.setResolver(resolver.getFullName());
        return new Pair<>(Boolean.TRUE, _reimb);
    }

    public Pair<Boolean, List<ReimbursementDTO>> getAllReimbursements() {
        List<Reimbursement> reimb = reimbDao.getAllReimbursements();

        // check if history of reimbursement data exists
        if(reimb.isEmpty())
            return new Pair<>(Boolean.FALSE, new ArrayList<>());

        List<ReimbursementDTO> reimbList = new ArrayList<>();

        // add all reimbursements to ReimbursementDTO list
        for(Reimbursement r : reimb){
            reimbList.add(getReimbursement(r.getReimb_id()).getSecond());
        }

        return new Pair<>(Boolean.TRUE, reimbList);
    }

    public Pair<Boolean, List<ReimbursementDTO>> getPastReimbursements() {
        List<Reimbursement> reimb = reimbDao.getPastReimbursements();

        // check if history of reimbursement data exists
        if(reimb.isEmpty())
            return new Pair<>(Boolean.FALSE, new ArrayList<>());

        List<ReimbursementDTO> reimbList = new ArrayList<>();

        // add all reimbursements to ReimbursementDTO list
        for(Reimbursement r : reimb){
            reimbList.add(getReimbursement(r.getReimb_id()).getSecond());
        }

        return new Pair<>(Boolean.TRUE, reimbList);
    }

    public Pair<Boolean, List<ReimbursementDTO>> getUserReimbursements(Integer user_id) {
        User user = userDao.getUser(user_id);

        // check if user exists. if user is null, return FALSE
        if(user == null)
            return new Pair<>(Boolean.FALSE, null);

        List<Reimbursement> reimb = reimbDao.getUserReimbursements(user_id);

        // check if history of reimbursement data exists
        if(reimb.isEmpty())
            return new Pair<>(Boolean.FALSE, null);

        List<ReimbursementDTO> reimbList = new ArrayList<>();

        // add all reimbursements to ReimbursementDTO list
        for(Reimbursement r : reimb){
            reimbList.add(getReimbursement(r.getReimb_id()).getSecond());
        }

        return new Pair<>(Boolean.TRUE, reimbList);
    }

    // UPDATE
    public Pair<Boolean, String> updateStatus(Integer reimb_id, Integer status_id, Integer resolver_id){
        User resolver = userDao.getUser(resolver_id);

        if(resolver == null)
            return new Pair<>(Boolean.FALSE, "404 User (Resolver) with ID: " +resolver_id+ " does not exist");

        // check user's role ID to scan employee position (EMPLOYEE ROLE ID = 1, MANAGER ROLE ID = 2)
        if(resolver.getRole_id() != 2)
            return new Pair<>(Boolean.FALSE, "403 Employee does not have access to this feature");

        // check by REIMB_ID if reimbursement exists in the system
        if(reimbDao.getReimbursement(reimb_id) == null)
            return new Pair<>(Boolean.FALSE, "404 Reimbursement with ID: " +reimb_id+ " does not exist");

        reimbDao.updateStatus(reimb_id, status_id, resolver_id);

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
