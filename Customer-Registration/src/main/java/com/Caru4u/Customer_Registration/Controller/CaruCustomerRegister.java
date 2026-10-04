package com.Caru4u.Customer_Registration.Controller;

import com.Caru4u.Customer_Registration.Model.*;
import com.Caru4u.Customer_Registration.Services.AuthenticationService;
import com.Caru4u.Customer_Registration.Services.CustomerRegistorService;
import com.Caru4u.Customer_Registration.security.CustomerPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth/Customer")
public class CaruCustomerRegister {


    @Autowired
    private CustomerRegistorService service;


    @Autowired
    private AuthenticationService authenticationService;


    // =====================================================
    // REGISTER CUSTOMER
    // =====================================================

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(
            @RequestBody CustomerRegistrationRequest customer
    ) {

        String result =
                service.registerCustomer(customer);

        if (result.startsWith("Error")) {

            return ResponseEntity
                    .badRequest()
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }


    // =====================================================
    // LOGIN CUSTOMER
    // =====================================================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {

        return ResponseEntity.ok(
                authenticationService.login(loginRequest)
        );
    }


    // =====================================================
    // GET ALL APARTMENTS / VILLAS
    //
    // Registration dropdown only
    //
    // Response:
    // [
    //   {"id":1,"name":"Dx-Max-KoppaRoad"},
    //   {"id":2,"name":"Godrej-Basavanapura"}
    // ]
    // =====================================================

    @GetMapping("/apartment-or-villa")
    public ResponseEntity<List<ApartmentOrVilla>>
    getAllApartments() {

        List<ApartmentOrVilla> apartments =
                service.getAll();

        return ResponseEntity.ok(apartments);
    }


    // =====================================================
    // GET LOGGED-IN CUSTOMER ADDRESS
    //
    // Checkout Service uses this endpoint
    //
    // Response is ONE OBJECT, NOT LIST
    // =====================================================

    @GetMapping("/me/address")
    public ResponseEntity<AddressResponse> getMyAddress(
            Authentication authentication
    ) {

        // =================================================
        // 1. CHECK AUTHENTICATION
        // =================================================

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new SecurityException(
                    "Customer is not authenticated"
            );
        }


        // =================================================
        // 2. GET PRINCIPAL
        // =================================================

        Object principalObject =
                authentication.getPrincipal();


        if (!(principalObject instanceof CustomerPrincipal)) {

            throw new SecurityException(
                    "Invalid customer authentication"
            );
        }


        CustomerPrincipal principal =
                (CustomerPrincipal) principalObject;


        // =================================================
        // 3. GET CUSTOMER ID FROM JWT
        // =================================================

        Long customerId =
                principal.getCustomerId();


        if (customerId == null) {

            throw new SecurityException(
                    "Customer ID not found in authentication token"
            );
        }


        // =================================================
        // 4. FIND CUSTOMER
        // =================================================

        CustomerRegistor customer =
                service.getCustomerById(customerId);


        // =================================================
        // 5. CREATE ADDRESS RESPONSE
        // =================================================

        AddressResponse response =
                new AddressResponse();


        response.setCustomerId(
                customer.getCustomerId()
        );


        // =================================================
        // 6. APARTMENT / VILLA
        // =================================================

        if (customer.getApartmentOrVilla() != null) {

            response.setApartmentOrVillaId(
                    customer
                            .getApartmentOrVilla()
                            .getId()
            );


            response.setApartmentOrVillaName(
                    customer
                            .getApartmentOrVilla()
                            .getName()
            );
        }


        // =================================================
        // 7. BLOCK / CROSS
        // =================================================

        response.setBlockOrCrossName(
                customer.getBlockOrCrossName()
        );


        // =================================================
        // 8. PLOT NUMBER
        // =================================================

        response.setPlotNumber(
                customer.getPlotNumber()
        );


        // =================================================
        // 9. RETURN ONE ADDRESS OBJECT
        // =================================================

        return ResponseEntity.ok(response);
    }
}