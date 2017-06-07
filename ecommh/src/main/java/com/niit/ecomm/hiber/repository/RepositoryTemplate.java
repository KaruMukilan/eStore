package com.niit.ecomm.hiber.repository;

import java.util.List;

import com.niit.ecomm.hiber.domain.MProduct;

public interface RepositoryTemplate <T>{
	public void persist(T entity);
	public void update(T entity);
	public void delete(T entity);
	public T findById(int id);
	public List<T> findAll();
}
