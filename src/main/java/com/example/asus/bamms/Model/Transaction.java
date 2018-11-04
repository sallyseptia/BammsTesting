package com.example.asus.bamms.Model;

public class Transaction {
    private String transactionId;
    private String type;
    private String name;
    private String userId;
    private String date;
    private Integer amount;
    private String receivernum;


    public Transaction() {
    }

    public Transaction(String transactionId, String type, String name, String userId, String date, Integer amount, String receivernum) {
        this.transactionId = transactionId;
        this.type = type;
        this.name = name;
        this.userId = userId;
        this.date = date;
        this.amount = amount;
        this.receivernum = receivernum;
    }

    public String getReceivernum() {
        return receivernum;
    }

    public void setReceivernum(String receivernum) {
        this.receivernum = receivernum;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
