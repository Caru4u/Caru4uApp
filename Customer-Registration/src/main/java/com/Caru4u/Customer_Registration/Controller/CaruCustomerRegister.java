package com.Caru4u.Customer_Registration.Controller;

import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import com.Caru4u.Customer_Registration.Services.CustomerRegistorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Customer")
public class CaruCustomerRegister {

   @Autowired
    private CustomerRegistorService service;


    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegistor customer) {
        String result = service.registerCustomer(customer);
        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
