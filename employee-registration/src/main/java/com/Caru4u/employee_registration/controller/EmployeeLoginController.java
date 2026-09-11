package com.Caru4u.employee_registration.controller;

import com.Caru4u.employee_registration.model.ChangeMobileRequest;
import com.Caru4u.employee_registration.model.LoginRequest;
import com.Caru4u.employee_registration.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth/employee")
public class EmployeeLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String response = loginService.login(request);
        if (response.equals("Login successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/change-mobile")
    public ResponseEntity<String> changeMobile(@RequestBody ChangeMobileRequest request) {
        String response = loginService.changeMobileNumber(request);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}