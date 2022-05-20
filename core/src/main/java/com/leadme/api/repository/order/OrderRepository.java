package com.leadme.api.repository.order;

import com.leadme.api.entity.Orders;
import com.leadme.api.entity.ProgDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByProgDaily(ProgDaily progDaily);
}
