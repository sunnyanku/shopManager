package com.bebo.shopmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bebo.shopmanager.entity.Manager;
import com.bebo.shopmanager.entity.Shop;
import com.bebo.shopmanager.repository.ManagerRepository;
import com.bebo.shopmanager.repository.RepositoryFactory;
import com.bebo.shopmanager.repository.ShopRepository;




@SpringBootApplication(scanBasePackages={"com.bebo.shopmanager"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class ShopmanagerRestApi {

	private static final Logger log = LoggerFactory.getLogger(ShopmanagerRestApi.class);
	public static void main(String[] args) {
		SpringApplication.run(ShopmanagerRestApi.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(RepositoryFactory repositoryFactory) {
		return (args) -> {
			// save a couple of customers
			ManagerRepository repository =  repositoryFactory.getManagerRepository();
			repository.save(new Manager("Abhishek", "Kumar","abhishek"));
			repository.save(new Manager("Satinder", "Sharma","satinder"));
			repository.save(new Manager("Dixit", "Soni","dixit"));
			repository.save(new Manager("Vivek", "Gautam","vivek"));
			repository.save(new Manager("Michelle", "Dessler","michelle"));
			
			ShopRepository shopRepository =  repositoryFactory.getShopRepository();
			shopRepository.save(new Shop("YWC", "India", "160062", "30.743624", "76.785689", repository.findByUsername("abhishek")));
			shopRepository.save(new Shop("BN Bakers", "India", "160017", "30.691840", "76.728441", repository.findByUsername("abhishek")));
			shopRepository.save(new Shop("Reliance Store", "India", "160026", "30.723288", "76.811061", repository.findByUsername("abhishek")));
			shopRepository.save(new Shop("Patanjali", "India", "160055", "30.733037", "76.689372", repository.findByUsername("abhishek")));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Manager customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Manager customer = repository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (Manager bauer : repository.findByLastName("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");
			
			log.info("Shop found with findAll():");
			log.info("--------------------------------------------");
			for (Shop shop : shopRepository.findAll()) {
				log.info(shop.toString());
			}
			log.info("");
		};
	}
}
