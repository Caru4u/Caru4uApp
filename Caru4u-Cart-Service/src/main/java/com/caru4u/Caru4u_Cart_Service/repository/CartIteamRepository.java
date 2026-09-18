package com.caru4u.Caru4u_Cart_Service.repository;

import com.caru4u.Caru4u_Cart_Service.dto.CartIteam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartIteamRepository extends JpaRepository<CartIteam,Long> {
    Optional<CartIteam> findByIdAndCart_CustomerId(Long itemId,Long customerId);
}
