package com.Caru4u.employee_registration.model;

public class LoginRequest {
    private String mobileNumber;

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    private Long password;
}
