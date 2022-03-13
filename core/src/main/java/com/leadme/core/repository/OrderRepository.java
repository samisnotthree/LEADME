package com.leadme.core.repository;

import com.leadme.core.entity.Orders;
import com.leadme.core.entity.ProgDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByProgDaily(ProgDaily progDaily);
}
