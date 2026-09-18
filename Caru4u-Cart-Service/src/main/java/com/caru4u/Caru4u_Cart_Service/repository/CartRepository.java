package com.caru4u.Caru4u_Cart_Service.repository;

import com.caru4u.Caru4u_Cart_Service.dto.Cart;
import com.caru4u.Caru4u_Cart_Service.dto.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByCustomerIdAndCartStatus(Long customerId, CartStatus cartStatus);
}
