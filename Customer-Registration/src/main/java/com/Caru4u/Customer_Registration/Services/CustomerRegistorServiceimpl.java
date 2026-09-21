package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.ApartmentOrVillaRepository;
import com.Caru4u.Customer_Registration.Dao.CustomerRegistorRepository;
import com.Caru4u.Customer_Registration.Model.ApartmentOrVilla;
import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import com.Caru4u.Customer_Registration.Utails.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerRegistorServiceimpl implements CustomerRegistorService {

    @Autowired
    private CustomerRegistorRepository customerRegistorRepository;

    @Autowired
    private ApartmentOrVillaRepository apartmentOrVillaRepository;


    private final PasswordEncoder passwordEncoder;

    public CustomerRegistorServiceimpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerCustomer(CustomerRegistor customer) {
        if (customerRegistorRepository.existsByMobileNumber(customer.getMobileNumber())) {
            return Constants.Number_already_registered;
        }
        if (customerRegistorRepository.existsByMailid(customer.getMailid())) {
            return Constants.Email_already_registered;
        }

        // 1. Get raw password entered by customer
        String rawPassword = customer.getPassword();
// 2. Validate password rules
        if (!isValidPassword(rawPassword)) {

            return Constants.Password_validation_message;
        }
        String encodedPassword =
                passwordEncoder.encode(
                        rawPassword
                );


        // Replace raw password with BCrypt hash
        customer.setPassword(
                encodedPassword
        );


        // ---------------------------------------
        // Save customer
        // ---------------------------------------

        customerRegistorRepository.save(
                customer
        );
        return Constants.Customer_Regilyster_succesful;
    }

    private boolean isValidPassword(String password) {
        if (password == null) return false;
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false; // must contain uppercase
        if (!password.matches(".*\\d.*")) return false;   // must contain digit
        return true;
    }

    public List<ApartmentOrVilla> getAll() {
        return apartmentOrVillaRepository.findAll();
    }
}