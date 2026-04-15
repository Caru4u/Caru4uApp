package com.Caru4u.employee_registration.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;

@Entity
@Table(name = "caru4u_employes")
@Data
public class EmployeeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    private String empFirstName;
    private String empLastName;

    private LocalDate empDateOfBirth;

    private String empMobileNumber;
    private String empEmergencyNumber;

    private String empMailId;

    private String empAddress;

    private String empAdharNumber;
    private String empPanNumber;
    private String empGender;
    private String empBankAccountNumber;
    private String empIfscCode;

    @JdbcTypeCode(Types.BINARY)
    @Column(name = "emp_bank_pass_book_photo")
    private byte[] empBankPassBookPhoto;

    @JdbcTypeCode(Types.BINARY)
    @Column(name = "emp_photo")
    private byte[] empPhoto;

    @JdbcTypeCode(Types.BINARY)
    @Column(name = "emp_adhar_photo")
    private byte[] empAdharPhoto;

    @JdbcTypeCode(Types.BINARY)
    @Column(name = "emp_pan_photo")
    private byte[] empPanPhoto;

    public String getEmpPanNumber() {
        return empPanNumber;
    }

    public void setEmpPanNumber(String empPanNumber) {
        this.empPanNumber = empPanNumber;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public LocalDate getEmpDateOfBirth() {
        return empDateOfBirth;
    }

    public void setEmpDateOfBirth(LocalDate empDateOfBirth) {
        this.empDateOfBirth = empDateOfBirth;
    }

    public String getEmpMobileNumber() {
        return empMobileNumber;
    }

    public void setEmpMobileNumber(String empMobileNumber) {
        this.empMobileNumber = empMobileNumber;
    }

    public String getEmpEmergencyNumber() {
        return empEmergencyNumber;
    }

    public void setEmpEmergencyNumber(String empEmergencyNumber) {
        this.empEmergencyNumber = empEmergencyNumber;
    }

    public String getEmpMailId() {
        return empMailId;
    }

    public void setEmpMailId(String empMailId) {
        this.empMailId = empMailId;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpAdharNumber() {
        return empAdharNumber;
    }

    public void setEmpAdharNumber(String empAdharNumber) {
        this.empAdharNumber = empAdharNumber;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(String empGender) {
        this.empGender = empGender;
    }

    public String getEmpBankAccountNumber() {
        return empBankAccountNumber;
    }

    public void setEmpBankAccountNumber(String empBankAccountNumber) {
        this.empBankAccountNumber = empBankAccountNumber;
    }

    public String getEmpIfscCode() {
        return empIfscCode;
    }

    public void setEmpIfscCode(String empIfscCode) {
        this.empIfscCode = empIfscCode;
    }

    public byte[] getEmpBankPassBookPhoto() {
        return empBankPassBookPhoto;
    }

    public void setEmpBankPassBookPhoto(byte[] empBankPassBookPhoto) {
        this.empBankPassBookPhoto = empBankPassBookPhoto;
    }

    public byte[] getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(byte[] empPhoto) {
        this.empPhoto = empPhoto;
    }

    public byte[] getEmpAdharPhoto() {
        return empAdharPhoto;
    }

    public void setEmpAdharPhoto(byte[] empAdharPhoto) {
        this.empAdharPhoto = empAdharPhoto;
    }

    public byte[] getEmpPanPhoto() {
        return empPanPhoto;
    }

    public void setEmpPanPhoto(byte[] empPanPhoto) {
        this.empPanPhoto = empPanPhoto;
    }

    public LocalDate getEmpJoiningDate() {
        return empJoiningDate;
    }

    public void setEmpJoiningDate(LocalDate empJoiningDate) {
        this.empJoiningDate = empJoiningDate;
    }

    private LocalDate empJoiningDate;
}
