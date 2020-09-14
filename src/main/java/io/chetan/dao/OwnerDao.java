package io.chetan.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.chetan.owner.model.Owner;

public interface OwnerDao extends CrudRepository<Owner, Long>
{

	 List<Owner> findByPhoneNumber(String phoneNumber);
	//public List<User> findByPhoneNumber(String phoneNumber);
	//public List<User> findByLastName(String lastName);
	 
	 //phoneNumber and password
	 //  List<User> findByEmailAddressAndLastname(String emailAddress, String lastname);
	 List<Owner> findByPhoneNumberAndPassword(String phoneNumber, String password);
	
	
}
