package com.Caru4u.employee_registration.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(unique = true, nullable = false)
    private String empMobileNumber;

    private String empEmergencyNumber;

    @Column(unique = true, nullable = false)
    private String empMailId;

    private String empAddress;

    @Column(unique = true, nullable = false)
    private String empAdharNumber;

    @Column(unique = true, nullable = false)
    private String empPanNumber;

    private String empGender;

    @Column(unique = true, nullable = false)
    private String empBankAccountNumber;

    private String empIfscCode;

    private LocalDate empJoiningDate;


    // =========================
    // IMAGE / DOCUMENT FIELDS
    // =========================

    @JdbcTypeCode(SqlTypes.VARBINARY)
    @Column(name = "emp_bank_pass_book_photo", columnDefinition = "bytea")
    private byte[] empBankPassBookPhoto;

    @JdbcTypeCode(SqlTypes.VARBINARY)
    @Column(name = "emp_photo", columnDefinition = "bytea")
    private byte[] empPhoto;

    @JdbcTypeCode(SqlTypes.VARBINARY)
    @Column(name = "emp_adhar_photo", columnDefinition = "bytea")
    private byte[] empAdharPhoto;

    @JdbcTypeCode(SqlTypes.VARBINARY)
    @Column(name = "emp_pan_photo", columnDefinition = "bytea")
    private byte[] empPanPhoto;
}