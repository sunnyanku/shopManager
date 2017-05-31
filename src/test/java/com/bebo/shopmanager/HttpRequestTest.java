package com.bebo.shopmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bebo.shopmanager.dto.ManagerDTO;
import com.bebo.shopmanager.dto.Position;
import com.bebo.shopmanager.dto.ShopDTO;
import com.bebo.shopmanager.entity.Manager;
import com.bebo.shopmanager.entity.Shop;
import com.bebo.shopmanager.util.CustomErrorType;
import com.bebo.shopmanager.util.CustomSuccessMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	
	public static final String REST_SERVICE_URI = "http://localhost:8080/ShopmanagerRestApi/api";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject(REST_SERVICE_URI+"/manager/",
                List.class).size()).isGreaterThan(0);
    }
    
    @Test
    public void getManager() throws Exception {
        Manager manager = this.restTemplate.getForObject(REST_SERVICE_URI+"/manager/abhishek",
                Manager.class);
		assertThat(manager.getUsername()).isEqualTo("abhishek");
    }
    
    @Test
    public void createManager() throws Exception {
    	ManagerDTO manager = new ManagerDTO(null,"Abhishek", "Kumar","abhi"+System.currentTimeMillis());
    	ResponseEntity<CustomSuccessMessage> created= restTemplate.postForEntity(REST_SERVICE_URI+"/manager/", manager, CustomSuccessMessage.class);
        
		assertThat(created.getBody().getMessage()).contains("Success ::");
    }
    
    @Test
    public void createManagerForError() throws Exception {
    	Manager manager = new Manager("Abhishek", "Kumar","abhishek");
    	ResponseEntity<CustomErrorType> created= restTemplate.postForEntity(REST_SERVICE_URI+"/manager/", manager, CustomErrorType.class);
        
		assertThat(created.getBody().getErrorMessage()).contains("Error ::");
    }
    
    @Test
    public void createShop() throws Exception {
    	ShopDTO shop = new ShopDTO("Dhaba"+System.currentTimeMillis(), "India", "160062","abhishek");
    	ResponseEntity<CustomSuccessMessage> created= restTemplate.postForEntity(REST_SERVICE_URI+"/shop/false", shop, CustomSuccessMessage.class);
        
		assertThat(created.getBody().getMessage()).contains("Success ::");
    }
    
    @Test
    public void createShopSameNameException() throws Exception {
    	Shop shop = new Shop("YWC", "India", "160062");
    	ResponseEntity<CustomErrorType> created= restTemplate.postForEntity(REST_SERVICE_URI+"/shop/false", shop, CustomErrorType.class);
        
		assertThat(created.getBody().getErrorMessage()).contains("Error ::");
    }
    
    @Test
    public void nearbyshops() throws Exception {
    	Position pos = new Position("30.743624", "76.785689", 10);
    	ResponseEntity<List> created= restTemplate.postForEntity(REST_SERVICE_URI+"/nearby/", pos, List.class);
        
		assertThat(created.getBody().size()).isGreaterThanOrEqualTo(0);
    }
}