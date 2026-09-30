package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Model.ApartmentOrVilla;
import com.Caru4u.Customer_Registration.Model.CustomerRegistor;

import java.util.List;
import java.util.Optional;

public interface CustomerRegistorService {
    public String registerCustomer(CustomerRegistor customer);
    public List<ApartmentOrVilla> getAll();
}