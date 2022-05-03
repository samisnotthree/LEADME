package com.leadme.api.repository;

import com.leadme.api.entity.Orders;
import com.leadme.api.entity.ProgDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByProgDaily(ProgDaily progDaily);
}
