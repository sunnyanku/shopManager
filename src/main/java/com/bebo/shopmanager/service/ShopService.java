package com.bebo.shopmanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.bebo.shopmanager.dto.ManagerDTO;
import com.bebo.shopmanager.dto.ShopDTO;
import com.bebo.shopmanager.entity.Manager;
import com.bebo.shopmanager.entity.Shop;
import com.bebo.shopmanager.repository.RepositoryFactory;
import com.bebo.shopmanager.util.CustomErrorType;
import com.bebo.shopmanager.util.CustomSuccessMessage;
import com.bebo.shopmanager.util.GoogleGeoCode;

@Service
public class ShopService {
	
	@Autowired
	private RepositoryFactory repositoryFactory;
	
	@Autowired
	ManagerService managerService;

	public ShopDTO findByShopName(String shopName) {
		Shop shop =  repositoryFactory.getShopRepository().findByShopName(shopName);
		ShopDTO shopDTO = null;
		if(shop!=null){
			String username = "";
			if(shop.getManager()!=null){
				username = shop.getManager().getUsername();
			}
			shopDTO = new ShopDTO(shop.getId(), shop.getShopName(), shop.getCountry(), shop.getPostcode(), shop.getLatitude(), shop.getLongitude(), username);
			shopDTO.setVersion(shop.getVersion());
		}
		
		return shopDTO;
	}

	public ShopDTO createShop(ShopDTO shop, boolean force) throws ObjectOptimisticLockingFailureException {
		ShopDTO existingShop = null;
		if(force){
			existingShop = findByShopName(shop.getShopName());
		}
		
		String response = GoogleGeoCode.getLocation(shop.getPostcode()+", "+shop.getCountry());

        String[] result = GoogleGeoCode.parseLocation(response);
        shop.setLatitude(result[0]);
        shop.setLongitude(result[1]);
       
		if(existingShop!=null){
			shop.setId(existingShop.getId());
			shop.setVersion(existingShop.getVersion());
		}
		try {
			shop = save(shop);
			
		} catch (ObjectOptimisticLockingFailureException e) {
			e.printStackTrace();
			throw e;
		}
		return shop;
		

	
		
	}

	private ShopDTO save(ShopDTO shopDTO) {
		 Shop shop= new Shop(shopDTO.getShopName(), shopDTO.getCountry(), shopDTO.getPostcode());
		 if(shopDTO.getId()!=null){
			shop.setId(shopDTO.getId());
			shop.setVersion(shopDTO.getVersion());
		 }
		 
		 Manager manager = repositoryFactory.getManagerRepository().findByUsername(shopDTO.getUsername());
		 shop.setManager(manager);
		 repositoryFactory.getShopRepository().save(shop);
		 shopDTO.setId(shop.getId());
		 shopDTO.setVersion(shop.getVersion());
		 return shopDTO;
		
	}

	public List<ShopDTO> findAll() {
		List<Shop> shops =  repositoryFactory.getShopRepository().findAll();

		List<ShopDTO> shopDTOs = new ArrayList<ShopDTO>();

		for (Shop shop : shops) {
			ShopDTO shopDTO =new ShopDTO(shop.getId(), shop.getShopName(), shop.getCountry(), shop.getPostcode(), shop.getLatitude(), shop.getLongitude(), shop.getManager().getUsername());
			shopDTOs.add(shopDTO);
		}

		return shopDTOs;
	}

}
