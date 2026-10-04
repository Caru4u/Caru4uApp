package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.ApartmentOrVillaRepository;
import com.Caru4u.Customer_Registration.Dao.CustomerRegistorRepository;
import com.Caru4u.Customer_Registration.Model.ApartmentOrVilla;
import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import com.Caru4u.Customer_Registration.Model.CustomerRegistrationRequest;
import com.Caru4u.Customer_Registration.Utails.Constants;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRegistorServiceimpl
        implements CustomerRegistorService {

    private final CustomerRegistorRepository customerRegistorRepository;
    private final ApartmentOrVillaRepository apartmentOrVillaRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerRegistorServiceimpl(
            CustomerRegistorRepository customerRegistorRepository,
            ApartmentOrVillaRepository apartmentOrVillaRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.customerRegistorRepository = customerRegistorRepository;
        this.apartmentOrVillaRepository = apartmentOrVillaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerCustomer(
            CustomerRegistrationRequest request
    ) {

        // ============================================
        // 1. Check mobile number
        // ============================================

        if (customerRegistorRepository
                .existsByMobileNumber(request.getMobileNumber())) {

            return Constants.Number_already_registered;
        }


        // ============================================
        // 2. Check email
        // ============================================

        if (customerRegistorRepository
                .existsByMailid(request.getMailid())) {

            return Constants.Email_already_registered;
        }


        // ============================================
        // 3. Validate password
        // ============================================

        String rawPassword =
                request.getPassword();

        if (!isValidPassword(rawPassword)) {

            return Constants.Password_validation_message;
        }


        // ============================================
        // 4. Validate apartment selection
        // ============================================

        if (request.getApartmentOrVillaId() == null) {

            return "Please select Apartment or Villa";
        }


        // ============================================
        // 5. Find apartment selected by customer
        // ============================================

        ApartmentOrVilla apartmentOrVilla =
                apartmentOrVillaRepository
                        .findById(
                                request.getApartmentOrVillaId()
                        )
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Invalid Apartment/Villa ID: "
                                                + request.getApartmentOrVillaId()
                                )
                        );


        // ============================================
        // 6. Encode password
        // ============================================

        String encodedPassword =
                passwordEncoder.encode(
                        rawPassword
                );


        // ============================================
        // 7. Convert DTO -> Entity
        // ============================================

        CustomerRegistor customer =
                new CustomerRegistor();

        customer.setFirstname(
                request.getFirstname()
        );

        customer.setLastname(
                request.getLastname()
        );

        customer.setMobileNumber(
                request.getMobileNumber()
        );

        customer.setMailid(
                request.getMailid()
        );

        customer.setPassword(
                encodedPassword
        );


        // ============================================
        // IMPORTANT:
        // Set apartment selected from database
        // ============================================

        customer.setApartmentOrVilla(
                apartmentOrVilla
        );


        customer.setBlockOrCrossName(
                request.getBlockOrCrossName()
        );

        customer.setPlotNumber(
                request.getPlotNumber()
        );


        // ============================================
        // 8. Save actual Entity
        // ============================================

        customerRegistorRepository.save(
                customer
        );


        return Constants.Customer_Regilyster_succesful;
    }

    @Override
    public List<ApartmentOrVilla> getAll() {
        return apartmentOrVillaRepository.findAll();
    }


    private boolean isValidPassword(
            String password
    ) {

        if (password == null) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        // At least one uppercase
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // At least one number
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        return true;
    }


    public CustomerRegistor getCustomerById(
            Long customerId
    ) {

        return customerRegistorRepository
                .findById(customerId)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Customer not found with ID: "
                                        + customerId
                        )
                );
    }
}