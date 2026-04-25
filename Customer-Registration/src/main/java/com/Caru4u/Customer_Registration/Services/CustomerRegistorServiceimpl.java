package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.CustomerRegistorRepository;
import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import com.Caru4u.Customer_Registration.Utails.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistorServiceimpl implements CustomerRegistorService {

    @Autowired
    private CustomerRegistorRepository customerRegistorRepository;

    @Override
    public String registerCustomer(CustomerRegistor customer) {
        if (customerRegistorRepository.existsByMobileNumber(customer.getMobileNumber())) {
            return Constants.Number_already_registered;
        }
        if (customerRegistorRepository.existsByMailid(customer.getMailid())) {
            return Constants.Email_already_registered;
        }

        // Normal password validation (no hashing)
        String password = customer.getPassword();
        if (!isValidPassword(password)) {
            return Constants.Password_validation_message;
        }

        // Save directly without hashing (not recommended for production)
        customerRegistorRepository.save(customer);
        return Constants.Customer_Regilyster_succesful;
    }

    private boolean isValidPassword(String password) {
        if (password == null) return false;
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false; // must contain uppercase
        if (!password.matches(".*\\d.*")) return false;   // must contain digit
        return true;
    }
}