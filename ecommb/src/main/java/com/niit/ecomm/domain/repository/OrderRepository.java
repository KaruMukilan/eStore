package com.niit.ecomm.domain.repository;

import com.niit.ecomm.domain.Order;

public interface OrderRepository {
	Long saveOrder(Order order);
}
