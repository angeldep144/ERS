package dao;

import models.Reimbursement;

import java.util.List;

public interface ReimbursementDao {
    // CREATE
    void createReimbursement(Reimbursement reimb);

    // READ
    Reimbursement getReimbursement(Integer reimb_id);
    List<Reimbursement> getAllReimbursements();
    List<Reimbursement> getPastReimbursements();
    List<Reimbursement> getUserReimbursements(Integer user_id);

    // UPDATE
    void updateStatus( Integer reimb_id, Integer status_id, Integer resolver_id);

    // DELETE
    void deleteReimbursement(Integer reimb_id);
}
