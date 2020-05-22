package io.chetan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.chetan.model.Address;
import io.chetan.model.Owner;
import io.chetan.service.OwnerService;

@SpringBootApplication
public class OwnerServiceApplication {

	//@Autowired
	//private OwnerService ownerService; 
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	public static void main(String[] args)
	{
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(OwnerServiceApplication.class, args);
		
		OwnerService ownerService = configurableApplicationContext.getBean("ownerService", OwnerService.class);
		
		Owner owner = new Owner();
		owner.setFirstName("Chetan");
		owner.setPhoneNumber("1");
		owner.setPassword("1");
		owner.setRepeatPassword("1");
		owner.setMyPg(1L);
		
		//address
		Address address = new Address();
		address.setCountry("India");
		owner.setAddress(address);
		
		ownerService.createOwner(owner);
		
		
	}

}
