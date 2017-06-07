package com.niit.ecomm.hiber.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.ecomm.hiber.domain.MProduct;
import com.niit.ecomm.hiber.repository.MProductRepository;

@Repository
public class MProductImpl implements MProductRepository {

	@Autowired
	private SessionFactory sessionFactory;


	public void persist(MProduct p) 
	{	
		Session s=sessionFactory.openSession();
		s.beginTransaction();
		s.save(p);
		s.getTransaction().commit();
		s.close();
	}  

	public void update(MProduct p) 
	{
		Session s=sessionFactory.openSession();
		s.beginTransaction();
		s.update(p);
		s.getTransaction().commit();
		s.close();
		
	}

	public MProduct findById(int id) {		
		return (MProduct)sessionFactory.openSession().get(MProduct.class,id);
	}

	public void delete(MProduct p) {
		Session s=sessionFactory.openSession();
		s.beginTransaction();
		s.delete(p);
		s.getTransaction().commit();
		s.close();sessionFactory.openSession().delete(p);
		
	}

	public List<MProduct> findAll() {
		Session s=sessionFactory.openSession();
		s.beginTransaction();
		Query query=s.createQuery("from MProduct");
		List<MProduct> list=query.list();
		System.out.println(list);
		s.getTransaction().commit();
		return list;
	}

	public void deleteAll() {
				
	}


}
