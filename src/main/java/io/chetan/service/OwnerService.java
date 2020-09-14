package io.chetan.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.chetan.dao.OwnerDao;
import io.chetan.owner.model.Owner;

@Service
@Transactional(propagation=Propagation.SUPPORTS,rollbackFor=Exception.class)
public class OwnerService {

	@Autowired
	private OwnerDao ownerDao;
	
	public Owner createOwner(Owner owner)
	{
		return ownerDao.save(owner);
	}
	
	public Iterable<Owner>  findAllOwners()
	{
		return  ownerDao.findAll();
	}
	
	public List<Owner> findByPhoneNumber(String phoneNumber)
	{
		return ownerDao.findByPhoneNumber(phoneNumber);
	}
	 
	 public List<Owner> findByPhoneNumberAndPassword(String phoneNumber, String password)
	 {
		 System.out.println("\n OwnerService findByPhoneNumberAndPassword() with ph = \n"+phoneNumber+" paswd = "+password);
		 return ownerDao.findByPhoneNumberAndPassword(phoneNumber, password);
	 }

	public Owner updateOwner(Owner owner) 
	{
 
//		Optional<Owner> oldOwnerOptional = ownerDao.findById(owner.getOwnerId());
//		
//		Owner oldOwner = oldOwnerOptional.get();
//		
//		oldOwner.setFirstName(owner.getFirstName());
//		
//		Owner updatedOwner = ownerDao.save(oldOwner);
//		
		System.out.println("\n OwnerService updateOwner() b4 update owner recvd in service layer is =  \n"+owner);
		//owner.setOwnerId(420);
		
		//owner.setRepeatPassword(owner.getRepeatPassword());
		System.out.println("\n\n owner aftr seting id = "+owner);
		Owner updatedOwner = ownerDao.save(owner) ;
		System.out.println("\n OwnerService updateOwner() updatedOwner = \n"+updatedOwner);

		return updatedOwner ;
	}
}
