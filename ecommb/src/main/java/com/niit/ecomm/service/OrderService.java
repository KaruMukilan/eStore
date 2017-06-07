package com.niit.ecomm.service;

import com.niit.ecomm.domain.Order;

public interface OrderService {
	
	void processOrder(String  productId, long quantity);
	
	Long saveOrder(Order order);
}
