package com.niit.ecomm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.ecomm.domain.Order;
import com.niit.ecomm.domain.Product;
import com.niit.ecomm.domain.repository.OrderRepository;
import com.niit.ecomm.domain.repository.ProductRepository;
import com.niit.ecomm.service.CartService;
import com.niit.ecomm.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;

	
	public void processOrder(String productId, long quantity) {
		Product productById = productRepository.getProductById(productId);
		
		if(productById.getUnitsInStock() < quantity){
			throw new IllegalArgumentException("Out of Stock. Available Units in stock"+ productById.getUnitsInStock());
		}
		
		productById.setUnitsInStock(productById.getUnitsInStock() - quantity);
	}
	
	public Long saveOrder(Order order) {
		Long orderId = orderRepository.saveOrder(order);
		cartService.delete(order.getCart().getCartId());
		return orderId;
	}

}
