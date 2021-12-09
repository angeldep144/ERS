package models;

import java.awt.image.BufferedImage;
import java.sql.Date;

public class Reimbursement {
    private Integer reimb_id;
    private Double amount;
    private Date date_submitted;
    private Date date_resolved;
    private String description;
    private BufferedImage receipt;

    private Integer author_id;      // user who sent reimbursement request
    private Integer resolver_id;    // user who resolves reimbursement request
    private Integer status_id;      // status of reimbursement from predefined table 0: PENDING, 1: APPROVED, 2: RESOLVED
    private Integer type_id;        // "reasons for reimbursement" from predefined table

    public Reimbursement(){}
    public Reimbursement(Integer reimb_id, Double amount, Date submitted,
                            Date resolved, String description, BufferedImage receipt,
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

    public Date getDate_submitted() {
        return date_submitted;
    }

    public void setDate_submitted(Date date_submitted) {
        this.date_submitted = date_submitted;
    }

    public Date getDate_resolved() {
        return date_resolved;
    }

    public void setDate_resolved(Date date_resolved) {
        this.date_resolved = date_resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BufferedImage getReceipt() {
        return receipt;
    }

    public void setReceipt(BufferedImage receipt) {
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
