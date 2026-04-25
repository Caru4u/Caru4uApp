package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.CustomerRegistorRepository;
import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import com.Caru4u.Customer_Registration.Utails.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService{

    @Autowired
    private CustomerRegistorRepository customerRegistorRepository;

    @Override
    public String loginCustomer(String identifier, String rawPassword) {

        CustomerRegistor customer = null;

        // Check if identifier is mobile number or email
        if (identifier.matches("\\d+")) { // numeric → mobile number
            customer = customerRegistorRepository.findByMobileNumber(identifier).orElse(null);
            if (customer == null) {
                return Constants.Mobile_not_found;
            }
        } else { // otherwise treat as email
            customer = customerRegistorRepository.findByMailid(identifier).orElse(null);
            if (customer == null) {
                return Constants.Email_not_found;
            }
        }


        if (!rawPassword.equals(customer.getPassword())) {
            return Constants.Invalid_Password;
        }


        return Constants.Login_Successfully;
    }

}
