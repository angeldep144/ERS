package models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Date;

public class ReimbursementDTO {
    private Integer id;
    private Double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp submitted;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp resolved;

    private String description;
    private byte[] receipt;
    private String author;
    private String resolver;
    private String status;
    private String type;

    public ReimbursementDTO(){}

    public ReimbursementDTO(Reimbursement reimb){
        this.id = reimb.getReimb_id();
        this.amount = reimb.getAmount();
        this.submitted = reimb.getDate_submitted();
        this.resolved = reimb.getDate_resolved();
        this.description = reimb.getDescription();
        this.receipt = reimb.getReceipt();
        this.status = resolveStatusId(reimb.getStatus_id());
        this.type = resolveTypeId(reimb.getType_id());
    }

    private String resolveStatusId(Integer status_id){

        String result;

        switch (status_id) {
            case 1:
                result = "APPROVED";
                break;

            case 2:
                result = "REJECTED";
                break;

            default:
                result = "PENDING";
        }
        return result;
    }

    private String resolveTypeId(Integer type_id){

        String result;

        switch (type_id) {
            case 1:
                result = "LODGING";
                break;

            case 2:
                result = "TRAVEL";
                break;

            case 3:
                result = "FOOD";
                break;

            default:
                result = "OTHER";
        }
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Date getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*public static Timestamp localDateFromTimestamp(Timestamp timestamp) {
        return LocalDateTime
                .ofInstant(timestamp.toInstant(), ZoneOffset.ofHours(0));
    }*/
}
