package models;

import java.io.File;
import java.sql.Timestamp;

public class Reimbursement {

    private File img;

    private Integer reimb_id;
    private Double amount;
    private Timestamp date_submitted;
    private Timestamp date_resolved;
    private String description;
    private byte[] receipt;
    private Integer author_id;      // user who sent reimbursement request
    private Integer resolver_id = 0;    // user who resolves reimbursement request
    private Integer status_id = 0;      // status of reimbursement from predefined table 0: PENDING, 1: APPROVED, 2: RESOLVED
    private Integer type_id;        // "reasons for reimbursement" from predefined table

    public Reimbursement(){}
    public Reimbursement(Integer reimb_id, Double amount, Timestamp submitted,
                         Timestamp resolved, String description, byte[] receipt,
                         Integer author_id, Integer resolver_id, Integer status_id, Integer type_id) {

        this.reimb_id = reimb_id;
        this.amount = amount;
        this.date_submitted = submitted;
        this.date_resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.status_id = status_id;
        this.type_id = type_id;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public Integer getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(Integer reimb_id) {
        this.reimb_id = reimb_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDate_submitted() {
        return date_submitted;
    }

    public void setDate_submitted(Timestamp date_submitted) {
        this.date_submitted = date_submitted;
    }

    public Timestamp getDate_resolved() {
        return date_resolved;
    }

    public void setDate_resolved(Timestamp date_resolved) {
        this.date_resolved = date_resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public Integer getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(Integer resolver_id) {
        this.resolver_id = resolver_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }
}
