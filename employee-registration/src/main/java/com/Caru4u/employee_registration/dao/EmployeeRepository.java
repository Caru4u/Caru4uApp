package com.Caru4u.employee_registration.dao;

import com.Caru4u.employee_registration.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeModel,Long> {
    boolean existsByEmpMobileNumber(String empMobileNumber);

    boolean existsByEmpMailId(String empMailId);

    boolean existsByEmpAdharNumber(String empAdharNumber);

    boolean existsByEmpPanNumber(String empPanNumber);

    boolean existsByEmpBankAccountNumber(String empBankAccountNumber);

    boolean existsByEmpEmergencyNumber(String empEmergencyNumber);

    Optional<EmployeeModel> findByEmpMobileNumber(String empMobileNumber);

}
