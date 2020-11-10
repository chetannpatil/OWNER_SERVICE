package io.chetan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;
import io.chetan.owner.model.Address;
import io.chetan.owner.model.Owner;
import io.chetan.service.OwnerService;

@SpringBootApplication
@EnableEurekaClient
public class OwnerServiceApplication {

	//@Autowired
	//private OwnerService ownerService; 
	
	private static Logger LOGGER = LogManager.getLogger(OwnerServiceApplication.class);
	
	@Bean
	public Sampler defaultSampler() 
	{
		LOGGER.info("\n\n OwnerServiceApplication - defaultSampler- \n\n");
		return Sampler.ALWAYS_SAMPLE;
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate()
	{
		LOGGER.info("\n\n OwnerServiceApplication - getRestTemplate- \n\n");
		
		return new RestTemplate();
	}
	
	public static void main(String[] args)
	{
		LOGGER.info("\n\n OwnerServiceApplication - main\n\n");
		
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
		
		LOGGER.info("\n\n OwnerServiceApplication - main- creating owner = \n\n"+owner);
		
		ownerService.createOwner(owner);
		
		
	}

}
