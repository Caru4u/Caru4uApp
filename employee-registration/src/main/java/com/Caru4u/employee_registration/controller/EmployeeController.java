package com.Caru4u.employee_registration.controller;

import com.Caru4u.employee_registration.model.EmployeeModel;
import com.Caru4u.employee_registration.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping("/register")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeModel employeeModel) {
        String response = service.saveEmployee(employeeModel);
        if (response.contains("already")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
