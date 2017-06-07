package com.niit.ecomm.domain.repository;

import com.niit.ecomm.domain.Cart;

public interface CartRepository {

	Cart create(Cart cart);
	
	Cart read(String cartId);
	
	void update(String cartId, Cart cart);
	
	void delete(String cartId);

}
