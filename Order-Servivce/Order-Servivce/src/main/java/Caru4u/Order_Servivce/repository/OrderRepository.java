package Caru4u.Order_Servivce.repository;

import Caru4u.Order_Servivce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long>{
    List<Order> findByCustomerId(Long customerId);
}