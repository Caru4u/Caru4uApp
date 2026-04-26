package com.Caru4u.Customer_Registration.Dao;

import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRegistorRepository extends JpaRepository<CustomerRegistor,Long> {
    Optional<CustomerRegistor> findByMobileNumber(String mobileNumber);
    Optional<CustomerRegistor> findByMailid(String mailid);
    boolean existsByMobileNumber(String mobileNumber);
    boolean existsByMailid(String mailid);
    Optional<CustomerRegistor> findByMailidAndOtp(String mailid, String otp);
    Optional<CustomerRegistor> findByMobileNumberAndOtp(String mobileNumber, String otp);
}
