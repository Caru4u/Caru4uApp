package com.Caru4u.Customer_Registration.Dao;

import com.Caru4u.Customer_Registration.Model.CallbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallbackRepository extends JpaRepository<CallbackEntity, Long> {
}
