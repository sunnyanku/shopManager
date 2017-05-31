package com.bebo.shopmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryFactory {
	
	@Autowired
	public ManagerRepository managerRepository;
	
	@Autowired
	public ShopRepository shopRepository;

	public ManagerRepository getManagerRepository() {
		return managerRepository;
	}

	public ShopRepository getShopRepository() {
		return shopRepository;
	}
	
	
	

}
