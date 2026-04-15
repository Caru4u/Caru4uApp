package com.Caru4u.employee_registration.service;

import com.Caru4u.employee_registration.dao.EmployeeRepository;
import com.Caru4u.employee_registration.model.EmployeeModel;
import com.Caru4u.employee_registration.model.LoginRequest;
import com.Caru4u.employee_registration.utiles.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceimpl implements LoginService{

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public String login(LoginRequest request) {
        Optional<EmployeeModel> empOptional =
                employeeRepository.findByEmpMobileNumber(request.getMobileNumber());

        if (empOptional.isEmpty()) {
            return Constants.USER_NOT_FOUND;
        }

        EmployeeModel employee = empOptional.get();

        // Compare empId with password
        if (!employee.getEmpId().equals(request.getPassword())) {
            return Constants.Invalid_password;
        }

        return Constants.Login_Successful;
    }
}
