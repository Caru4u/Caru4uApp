package com.Caru4u.Customer_Registration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_registor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerRegistor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(unique = true, nullable = false, length = 15)
    private String mobileNumber;

    @Column(nullable = false, length = 255)
    private String password; // store hashed password

    @Column(unique = true, nullable = false, length = 100)
    private String mailid;

    @ManyToOne
    @JoinColumn(name = "apartment_or_villa_id")
    private ApartmentOrVilla apartmentOrVilla;

    @Column(length = 100)
    private String blockOrCrossName;

    @Column(length = 20)
    private String plotNumber;

}
