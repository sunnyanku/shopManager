package com.bebo.shopmanager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bebo.shopmanager.dto.ManagerDTO;
import com.bebo.shopmanager.dto.Position;
import com.bebo.shopmanager.dto.ShopDTO;
import com.bebo.shopmanager.service.ManagerService;
import com.bebo.shopmanager.service.ShopService;
import com.bebo.shopmanager.util.CustomErrorType;
import com.bebo.shopmanager.util.CustomSuccessMessage;
import com.bebo.shopmanager.util.GoogleGeoCode;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private ShopService shopService;
	

	//---------------Retrieve manager and shops--------------------//
	
	/**
	 * To fetch all managers
	 * @return List of managers
	 */
	@RequestMapping(value = "/manager/", method = RequestMethod.GET)
	public ResponseEntity<List<ManagerDTO>> listAllManagers() {
		List<ManagerDTO> managers = managerService.findAll();
		
		if (managers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ManagerDTO>>(managers, HttpStatus.OK);
	}

	/**
	 * Find manager using username.
	 * @param username manager username
	 * @return List of managers.
	 */
	@RequestMapping(value = "/manager/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> getManager(@PathVariable("username") String username) {
		logger.info("Fetching Manager with username {}", username);
		ManagerDTO manager = managerService.findByUsername(username);
		if (manager == null) {
			logger.error("Error :: Manager with username {} not found.", username);
			return new ResponseEntity(new CustomErrorType("Error :: Manager with username " + username 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ManagerDTO>(manager, HttpStatus.OK);
	}

	/**
	 * Create manager
	 * @param manager manager data
	 * @param ucBuilder 
	 * @return success and failure message
	 */
	@RequestMapping(value = "/manager/", method = RequestMethod.POST)
	public ResponseEntity<?> createManager(@RequestBody ManagerDTO manager, UriComponentsBuilder ucBuilder) {
		ResponseEntity<?> responseEntity = null;
		logger.info("Creating manager : {}", manager);
		if(manager==null || manager.getUsername()==null ){
			logger.error("Error :: Unable to create. Username is required.");
			responseEntity = new ResponseEntity<CustomErrorType>(new CustomErrorType("Error :: Unable to create. Username is required."),HttpStatus.CONFLICT);
		}else if ( managerService.findByUsername(manager.getUsername())!=null) {
			logger.error("Unable to create. A manager with name {} already exist", manager.getFirstName() + " "+manager.getLastName() );
			responseEntity = new ResponseEntity<CustomErrorType>(new CustomErrorType("Error :: Unable to create. A manager with username " +manager.getUsername()+ " already exist"),HttpStatus.CONFLICT);
			
		}else{
			manager =  managerService.save(manager);
	
			
			responseEntity =new ResponseEntity<CustomSuccessMessage>(new CustomSuccessMessage("Success :: A Manager with username " + 
				manager.getUsername() + " created with id "+manager.getId()),HttpStatus.CREATED);
		}
		return responseEntity;
	}
	
	
	/*--------------- Shop --------------------*/
	
	/**
	 * Create shop.
	 * @param force true to force update already existing shop and false to create new shop.
	 * @param shop Shop data
	 * @param ucBuilder
	 * @return success and failure message
	 */
	@RequestMapping(value = "/shop/{force}", method = RequestMethod.POST)
	public ResponseEntity<?> createShop(@PathVariable("force") boolean force, @RequestBody ShopDTO shop, UriComponentsBuilder ucBuilder) {
		ResponseEntity<?> responseEntity = null;
		logger.info("Creating shop : {}", shop);
		if(shop.getUsername()==null){
			logger.error("Unable to create. Manager username is required.");
			responseEntity = new ResponseEntity<CustomErrorType>(new CustomErrorType("Error :: Unable to create. Manager username is required."),HttpStatus.CONFLICT);
		}else if(shop==null || shop.getShopName()==null ){
			logger.error("Unable to create. shop name is required.");
			responseEntity = new ResponseEntity<CustomErrorType>(new CustomErrorType("Error ::Unable to create. shop name is required."),HttpStatus.CONFLICT);
		}else if (!force && shopService.findByShopName(shop.getShopName())!=null) {
			logger.error("Unable to create. A shop with name {} already exist", shop.getShopName()  );
			responseEntity = new ResponseEntity<CustomErrorType>(new CustomErrorType("Error :: Unable to create. A shop with name "+shop.getShopName() + " already exist"),HttpStatus.CONFLICT);
			
		}else{
			CustomSuccessMessage body = null;
			ShopDTO existingShop = null;
			if(force){
				existingShop = shopService.findByShopName(shop.getShopName());
			}
			String response = GoogleGeoCode.getLocation(shop.getPostcode()+", "+shop.getCountry());

	        String[] result = GoogleGeoCode.parseLocation(response);
	        shop.setLatitude(result[0]);
	        shop.setLongitude(result[1]);
			try {
				ShopDTO newShop = shopService.createShop(shop, force);
				body = new CustomSuccessMessage("Success :: A shop with name " + 
						shop.getShopName() + " created with id "+shop.getId());
				Map<String,ShopDTO> oldNew =  new HashMap<String,ShopDTO>();
				oldNew.put("Existing Shop",existingShop);
				oldNew.put("Updated Shop",newShop);
				body.setDetail(oldNew);
				responseEntity =new ResponseEntity<CustomSuccessMessage>(body,HttpStatus.CREATED);
			} catch (ObjectOptimisticLockingFailureException e) {
				CustomErrorType error = new CustomErrorType("Error :: A shop with same name is being edited by some other manager. Please try later.");
				e.printStackTrace();
				responseEntity =new ResponseEntity<CustomErrorType>(error,HttpStatus.CREATED);
			}
			
	
		}
		return responseEntity;
	}
	
	
	/**
	 * Find nearby shops using user lat, long and radius. 
	 * @param position position info (lat. long and radius (default radius is 10))
	 * @param ucBuilder
	 * @return List of shops near user location under given radius.
	 */
	@RequestMapping(value = "/nearby/", method = RequestMethod.POST)
	public ResponseEntity<?> findShops(@RequestBody Position position, UriComponentsBuilder ucBuilder) {
		ResponseEntity<?> responseEntity = null;
		if(position==null || position.getLatitude()==null || position.getLongitude()==null){
			logger.error("Location is not valid");
			responseEntity = new ResponseEntity<CustomErrorType>(new CustomErrorType("Location is not valid"),HttpStatus.CONFLICT);
		}else {
			List<ShopDTO> shops = shopService.findAll();
			List<ShopDTO> result = new ArrayList<ShopDTO>();
			for (ShopDTO shop : shops) {
				logger.info(shop.toString());
				if(shop.getLatitude()!=null && shop.getLongitude()!=null){
					Double radius = GoogleGeoCode.calculateDistanceBetweenPoints(Double.parseDouble(position.getLatitude()), Double.parseDouble(position.getLongitude()), Double.parseDouble(shop.getLatitude()), Double.parseDouble(shop.getLongitude()));
					if(radius<=position.getRadius()){
						result.add(shop);
					}
				}
				
			}
			
			responseEntity = new ResponseEntity<List<ShopDTO>>(result, HttpStatus.OK);
		}
		return responseEntity;
	}

}