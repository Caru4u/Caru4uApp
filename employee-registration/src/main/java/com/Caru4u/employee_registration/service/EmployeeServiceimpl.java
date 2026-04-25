package com.Caru4u.employee_registration.service;

import com.Caru4u.employee_registration.dao.EmployeeRepository;
import com.Caru4u.employee_registration.model.EmployeeModel;
import com.Caru4u.employee_registration.utiles.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceimpl implements EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public String saveEmployee(EmployeeModel employee) {
        String mobileError = validateMobile(employee.getEmpMobileNumber());
        if (mobileError != null) return mobileError;

        String emailError = validateEmail(employee.getEmpMailId());
        if (emailError != null) return emailError;

        String aadhaarError = validateAadhaar(employee.getEmpAdharNumber());
        if (aadhaarError != null) return aadhaarError;

        String panError = validatePan(employee.getEmpPanNumber());
        if (panError != null) return panError;

        String emergencyError = validateEmergencyNumber(employee.getEmpEmergencyNumber(),
                employee.getEmpMobileNumber());
        if (emergencyError != null) return emergencyError;

        String bankError = validateBankAccount(employee.getEmpBankAccountNumber());
        if (bankError != null) return bankError;
        repository.save(employee);
        return Constants.Register_Sucessful;
    }

    private String validateMobile(String mobile) {
        if (mobile == null || mobile.isEmpty()) {
            return Constants.MobileNumber_Required;
        }
        if (!mobile.matches("^[6-9]\\d{9}$")) {
            return Constants.Invalid_MobileNumber;
        }
        if (repository.existsByEmpMobileNumber(mobile)) {
            return Constants.MobileNumber_already_Registerd;
        }
        return null;
    }

    private String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Constants.Email_Required;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return Constants.Invalid_Format;
        }
        if (repository.existsByEmpMailId(email)) {
            return Constants.EmailId_alreaday_Registerd;
        }
        return null;
    }

    private String validateAadhaar(String aadhaar) {
        if (aadhaar == null || aadhaar.isEmpty()) {
            return Constants.Adharnumber_required;
        }
        if (!aadhaar.matches("^\\d{12}$")) {
            return Constants.Adharnumber_Must_12;
        }
        if (repository.existsByEmpAdharNumber(aadhaar)) {
            return Constants.AdharNumbaer_already_Registerd;
        }
        return null;
    }

    private String validatePan(String pan) {
        if (pan == null || pan.isEmpty()) {
            return Constants.PanNumber_required;
        }
        if (!pan.matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
            return Constants.PanNumber_Invalid_format;
        }
        if (repository.existsByEmpPanNumber(pan)) {
            return Constants.PanNumber_already_registed;
        }
        return null;
    }

    private String validateEmergencyNumber(String emergency, String mobile) {
        if (emergency == null || emergency.isEmpty()) {
            return Constants.Emergency_number; // optional field
        }
        if (!emergency.matches("^[6-9]\\d{9}$")) {
            return Constants.Invalid_emergency_number;
        }
        if (emergency.equals(mobile)) {
            return Constants.Emergency_and_mobile;
        }
        if (repository.existsByEmpEmergencyNumber(emergency)) {
            return Constants.Emergency_number_already_Registerd;
        }
        return null;
    }

    private String validateBankAccount(String account) {
        if (account == null || account.isEmpty()) {
            return Constants.Bank_account_Number_required;
        }
        if (!account.matches("^\\d{9,18}$")) {
            return Constants.Invalid_account_number;
        }
        if (repository.existsByEmpBankAccountNumber(account)) {
            return Constants.BankAccount_number_already_Registerd;
        }
        return null;
    }
}
