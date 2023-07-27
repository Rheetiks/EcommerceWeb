package com.ecom.ecom.Model;

public class User {
    private int userId;
    private String userName;
    private long userPhoneNo;
    private String userEmail;
    private String userPassword;
    private String userAddress;
    
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public long getUserPhoneNo() {
        return userPhoneNo;
    }
    public void setUserPhoneNo(long userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
