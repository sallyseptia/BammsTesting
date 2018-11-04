package com.example.asus.bamms.Model;

public class Account {
    private String accountId;
    private String description;
    private String accountNumber;
    private String type;
    private String userId;
    private Integer amount;

    public Account() {
    }

    public Account(String accountId, String accountNumber, String description, String type, String userId, Integer amount) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.description = description;
        this.type = type;
        this.userId = userId;
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
