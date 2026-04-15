package com.Caru4u.employee_registration.service;

import com.Caru4u.employee_registration.dao.EmployeeRepository;
import com.Caru4u.employee_registration.model.ChangeMobileRequest;
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

    @Override
    public String changeMobileNumber(ChangeMobileRequest request) {
        // 1. Validate old mobile exists
        Optional<EmployeeModel> empOptional =
                employeeRepository.findByEmpMobileNumber(request.getOldMobileNumber());

        if (empOptional.isEmpty()) {
            return Constants.Old_mobile_NumberNot_found;
        }

        EmployeeModel employee = empOptional.get();

        // 2. Validate password (empId)
        if (!employee.getEmpId().equals(request.getPassword())) {
            return Constants.Invalid_password;
        }

        // 3. Validate new mobile format
        if (request.getNewMobileNumber() == null ||
                !request.getNewMobileNumber().matches("^[6-9]\\d{9}$")) {
            return Constants.Invalid_new_Mobile_number;
        }

        // 4. Check new mobile already exists
        if (employeeRepository.existsByEmpMobileNumber(request.getNewMobileNumber())) {
            return Constants.New_mobile_already_registerd;
        }

        // 5. Update mobile number
        employee.setEmpMobileNumber(request.getNewMobileNumber());
        employeeRepository.save(employee);
        return Constants.Moblie_number_updatedSusscefully;
    }
}
