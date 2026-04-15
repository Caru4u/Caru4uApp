package com.Caru4u.employee_registration.model;

public class ChangeMobileRequest {
    public String getOldMobileNumber() {
        return oldMobileNumber;
    }

    public void setOldMobileNumber(String oldMobileNumber) {
        this.oldMobileNumber = oldMobileNumber;
    }

    public String getNewMobileNumber() {
        return newMobileNumber;
    }

    public void setNewMobileNumber(String newMobileNumber) {
        this.newMobileNumber = newMobileNumber;
    }

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    private String oldMobileNumber;
    private String newMobileNumber;
    private Long password;
}
