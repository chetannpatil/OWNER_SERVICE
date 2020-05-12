package io.chetan.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


import io.chetan.exception.CouldNotLoadYourPGException;
import io.chetan.exception.DuplicateOwnerException;
import io.chetan.exception.DuplicatePGException;
import io.chetan.exception.InvalidCredentialsException;
import io.chetan.exception.PGAddressCanNotBeEmptyException;
import io.chetan.exception.PasswordMissMatchException;
import io.chetan.model.Address;
import io.chetan.model.Owner;
import io.chetan.model.Pg;
import io.chetan.service.OwnerService;




//@RestController
@RequestMapping(value = "/mypg")
@Controller
public class OwnerController 
{
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private RestTemplate restTemplate;

	private static String pgServiceUri = "http://localhost:8082/pg/";
	
	//private Logger
	@GetMapping("/")
	public ModelAndView welcome()
	{
		System.out.println("\n OwnerController welcome()");
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("Welcome");
		//modelAndView.addObject("signOutMessage", "Chetan's  signOutMessage");
		return modelAndView;
	}
	@GetMapping("/contactMe")
	public ModelAndView contact()
	{
		
		  ModelAndView modelAndView = new ModelAndView();
		  
		  modelAndView.setViewName("Contact");
		 
		  return modelAndView;
		  
	}


	      
	//openPGOwnerRegistrationView
	        @GetMapping("/openPGOwnerRegistrationView")
			public ModelAndView openPGOwnerRegistrationView()
			{
	        	ModelAndView modelAndView = new ModelAndView();
				
	        	Owner owner = new Owner();
				
				modelAndView.setViewName("RegisterPGOwner");
				
				modelAndView.addObject("owner",owner);
				
				
				return modelAndView ;
			}


	        @PostMapping(value="/registerPGOwner")
	    	public ModelAndView registerPGOwner(@Valid Owner owner,
	    			BindingResult br,HttpSession hs,ModelAndView modelAndView)
	    	{
	    		System.out.println("\n OwnerController registerPGOwner()\n");
	    	//	ModelAndView modelAndView = new ModelAndView();
	    		if(br.hasErrors())
	    		{
	    			System.out.println("\n OwnerController registerPGOwner has errors \n");
	    			modelAndView.setViewName("RegisterPGOwner");
	    	
	    		//	System.out.println("pgOwnerBean = \n "+pgOwnerBean);
	    			modelAndView.addObject("owner",owner);
	    		
	    			
	    			return modelAndView ;
	    		}
	    		else
	    		{
	 	System.out.println("\n OwnerController registerPGOwner has passed with pgownerbean =  \n"+owner);
			
	    			try
	    			{
	    				String pwd = owner.getPassword();
	    				String repeatPwd = owner.getRepeatPassword();
	    				//removeExtra Space from each has a
	    				if(Owner.isPasswordMatchingRepeatPassword(pwd, repeatPwd) == false)
	    				{
	    				String errorStr = "Do you think password = \""+pwd+"\" "+" and repeat password = \" "+repeatPwd+"\" are same ?";
	    					throw new PasswordMissMatchException(errorStr);
	    				}
	    				// check for duplicate owner here
					    				
	    		   List<Owner> ownerslist = ownerService.findByPhoneNumber(owner.getPhoneNumber());
	    		   
	    		   if(ownerslist == null || ownerslist.size() == 0 )
	    		   {
	    			   //owner does not exist , carry on
	    			    //do not create a pgOwnerbean now
	    				//redirect to crate pg page
	    				
	    				//need to add pgbean to M
	    				modelAndView.addObject("pg",new Pg());
		    				hs.setAttribute("owner", owner);
		    				
		    				modelAndView.setViewName("CreatePG");
		    				
		    				return modelAndView;
	    			   
	    		   }
	    		   else
	    		   {
	    			   throw new DuplicateOwnerException("A account allready exist  with same phone number = "
	  				 
	  				     +owner.getPhoneNumber());
	    		   }
	    				//do not create a pgOwnerbean now
	    				//redirect to crate pg page
	    				
	    				//need to add pgbean to M
	    		
	    				//return "OwnerHome";
	    			}
	    			
	    			catch(DuplicateOwnerException e)
	    			{
	    				e.printStackTrace();
	    				//m.addAttribute("dupUserErrorMessage", e.getLocalizedMessage());
	    				modelAndView.addObject("dupUserErrorMessage", e.getLocalizedMessage());
	    				modelAndView.setViewName("RegisterPGOwner");
	    				return modelAndView;
	    			}
	    			catch(PasswordMissMatchException e)
	    			{
	    				modelAndView.addObject("pswErrorMessage", e.getLocalizedMessage());
	    				modelAndView.setViewName("RegisterPGOwner");
	    				return modelAndView;
	    			}
	    			catch(Exception e)
	    			{
	    				System.out.println("HC registerPGOwner catch e =");
	    				System.out.println(e);
	    				e.printStackTrace();
	    			//	String errorStr = "Due to some raesons we couldnt create your account very sorry";
	    				//m.addAttribute("errorMessage",errorStr+" i.e."+e.getLocalizedMessage());
	    			//	return "Error";
	    				
	    				modelAndView.addObject("errorMessage", e.getLocalizedMessage());
	    				modelAndView.setViewName("Error");
	    				return modelAndView;
	    			}
			 
	    		}
	    	}
	       
	        
	        //@Valid Owner owner
	        //@ModelAttribute("pgOwnerBean")@Valid PGOwner pgOwnerBean
	    	 //   @Valid @ModelAttribute("data") FormData data
	    	//cretaePG     
	        //@{/mypg/createPG}
	    	@PostMapping("/createPG")
	    	public ModelAndView createPG(@Valid Pg pg,BindingResult br,
	    			ModelAndView modelAndView,HttpSession hs)
	    	{
	    		////ModelMap mp = new ModelMap();
	    		//mp.add
	    		//find pgOwnerBean from HSR pgOwnerBean
	    		Owner owner = (Owner)hs.getAttribute("owner");
	    		
	    		System.out.println("from HS owner = "+owner);
	    		if(br.hasErrors())
	    		{
	    			System.out.println("\n createPG() br.hasErrors\n");
	    			modelAndView.addObject("pg",pg);
    				modelAndView.setViewName("CreatePG");
    				return modelAndView;
	    		}
	    		else
	    		{
	    			System.out.println("\n createPG() br.has no Errors \n");
	    			try
	    			{
	    		
	    				//PG foundPG = restTemplate.getForObject("http://localhost:8082/pg/findPG",PG.class,pg.getAddress());
	    				
	    				if(pg.getAddress() != null)
	    				{
	    					System.out.println("\n  pg.getAddress() != null & pg.getAddress() = \n"+pg.getAddress());
	    					
                           //checking any field is empty for Address
	    					List<String> emptyAddressFieldsList = Address.addressValidation(pg.getAddress().getHouseNumber(),
		    						pg.getAddress().getStreet(),
		    						pg.getAddress().getDisrtict(),
		    						pg.getAddress().getState(),
		    						pg.getAddress().getCountry(),
		    						pg.getAddress().getPin());
		    				if(emptyAddressFieldsList == null || (emptyAddressFieldsList.isEmpty() == false))
		    				{
		    					//there are some fields are empty
		    					throw new PGAddressCanNotBeEmptyException(emptyAddressFieldsList.toString());
		    				}
		    				
		    				//do not check for duplicate owner here
		    				//check for duplicate Pg call PG-micorservice
	    					//requesting PGservice for PG to verify is this PG already exist with PGservice
		    				
	    					/*Pg foundPG = restTemplate.postForObject("http://localhost:8082/pg/findPG",
		    						pg.getAddress(),
		    						Pg.class);*/
	    					//example
//	    					restTemplate.getForObject(
//	    							   "http://example.com/hotels/{hotel}/rooms/{room}", String.class, "42", "21");
	    					
	    					String findPgUri = pgServiceUri+ "findPG/houseNumber/street/disrtict/state/country/pin/"
	    							+ "{houseNumber}/{street}/{disrtict}/{state}/{country}/{pin}";
	    					Address pgAddress = pg.getAddress();
	    					Pg foundPg = restTemplate.getForObject(findPgUri, Pg.class,pgAddress.getHouseNumber(),
	    							pgAddress.getStreet(),
	    							pgAddress.getDisrtict(),
	    							pgAddress.getState(),
	    							pgAddress.getCountry(),
	    							pgAddress.getPin());
	    				//	Pg foundPG = restTemplate.getForObject("http://localhost:8082/pg/findPG",Pg.class,pg.getAddress());
		    				if(foundPg == null )
		    				{
		    					System.out.println("\n foundPG == null PG doesnt exist , carry on \n");
		    					//PG does not exist , carry on
			    			
		    					//add pg start date to pg
		    					
		    					  pg.setPgStartedDate(new Date()); 
		    					  
		    						//add myPg to PGownerBean collection now
		    					  if(owner == null )
		    					  {
		    						  System.out.println("\n owner retived from HS is  = \n"+owner);
		    					  }
		    					//  boolean isPGAddedToOwnerCollection = owner.addAPG(pg);	
		    					 // owner.setMyPG(pg);
		    					  
		    						  //ceraet pgowneben
		    						System.out.println("\n isPGAddedToOwnerCollection == true \n");
			    					  Owner createdOwner = ownerService.createOwner(owner);
			    					 
			    					  System.out.println("\n createdOwner   ==  \n"+createdOwner);
			    						    					  
			    				
			    					  System.out.println("\nset owner for PG owner.getOwnerId() = \n"+owner.getOwnerId());
			    					  System.out.println("\n createdOwner.getOwnerId()) \n"+createdOwner.getOwnerId());
			    					 
			    					  //set owner for PG 
			    					  pg.setMyOwner(createdOwner.getOwnerId());
			    					  
			    					
			    					  //create PG by calling pgService microservice
			    					  Pg createdPG = restTemplate.postForObject("http://localhost:8082/pg/createPG",
					    						pg,
					    						Pg.class);
			    					  
			    					  //add owner's myPG to session
			  	    				    hs.setAttribute("myPG",createdPG);
			  	    				    
			    					  //set pg for owner
			    					  System.out.println("\nset pg for owner pg.getPgId() = \n"+pg.getPgId());
			    					  System.out.println("\n createdPG.getPgId() \n"+createdPG.getPgId());
			    					  
			    					  owner.setMyPG(createdPG.getPgId());
						/*
						 * // display test owner here to check mypg updted to db or not List<Owner>
						 * ownersList = ownerService.findByPhoneNumber(owner.getPhoneNumber()); Owner
						 * testOwner = null; for(Owner ow : ownersList) {
						 * if(ow.getPhoneNumber().equals(owner.getPhoneNumber())) { testOwner = ow ;
						 * break; } } System.out.println("\ntestOwner for Mypg has a \n = "+testOwner);
						 * System.out.println("\n testOwner.getMyPG() = \n"+testOwner.getMyPG());
						 */
			    				  
			    				 //update owner to db so that owner will have mypg
			    					Owner updatedOwner = ownerService.updateOwner(owner); 
			    					
			    					System.out.println("\n updatedOwner \n"+updatedOwner);
			    				 System.out.println("\n createdPG by calling pg microservice  ==  \n"+createdPG);
		    					
		    				}
		    				else
		    				{
		    					System.out.println("\n duplicate PG, stop here \n");
		    					
		    					//duplicate PG, stop here
			    		 Address address = pg.getAddress() ;
			    				 String addressStr = address.getHouseNumber()+" "+address.getStreet()
			    				  +" "+address.getDisrtict()+" "+address.getState()+" "+address.getCountry()
			    				  +" "+address.getPin();
			    					 
			    		 String duplicatePGErrorMessageStr = "Hello "+owner.getFirstName()+", some one has already " +
			    				  "constructed a PG at same address = "
			    				  +addressStr+", so pls enter your PG's address";
			    				  
			    				  throw new DuplicatePGException(duplicatePGErrorMessageStr);
		    				}
	    				}
	    				else
	    				{
	    					System.out.println("\n couldnt read pg address, pg.getAddress() = "+pg.getAddress());
	    				}
	    			
	    				
	    			
	    				//return "OwnerHome";
	    				//modelAndView.addObject("owner",owner);
	    				
	    				modelAndView.setViewName("OwnerHome");
	    				return modelAndView;
	    			}
	    			catch(PGAddressCanNotBeEmptyException e)
	    			{
	    				System.out.println("e.tostring = ");
	    				System.out.println(e.toString());
	    				String subStr =(e.toString()).substring(2, e.toString().length()-1);
//	    				System.out.println("substr = ");
//	    				System.out.println(subStr);
	    				
	    				String []sa1 = (e.toString()).split(":");
	    	//			System.out.println("sa1 cintent");
	    				//System.out.println(Arrays.toString(sa1));
//	    				for(String s:sa1)
//	    				{
//	    					System.out.println("sa1 content");
//	    					System.out.println(s);
//	    				}
	    				String subS = sa1[1].substring(2, sa1[1].length()-1);
	    				String [] sa = subS.split(" ");
	    				ArrayList<String> al = new ArrayList<>();
	    				for(String str:sa)
	    				{
	    					//System.out.println("sa content");
	    				//	System.out.println(str);
	    					al.add(str);
	    				}
	    				
	    					if(al.contains("HOUSE-NUMBER"))
	    					{
	    						modelAndView.addObject("houseNumberErrorMessage","HOUSE-NUMBER can not be empty");
	    					}
	    					if(al.contains("STREET"))
	    					{
	    						modelAndView.addObject("streetErrorMessage","STREET can not be empty");
	    					}
	    					if(al.contains("DISTRICT"))
	    					{
	    						modelAndView.addObject("districtErrorMessage","DISTRICT can not be empty");
	    					}
	    					if(al.contains("STATE"))
	    					{
	    						modelAndView.addObject("stateErrorMessage","STATE can not be empty");
	    					}
	    					if(al.contains("COUNTRY"))
	    					{
	    						modelAndView.addObject("countryErrorMessage","COUNTRY can not be empty");
	    					}
	    					if(al.contains("PIN"))
	    					{
	    						modelAndView.addObject("pinErrorMessage","PIN can not be empty");
	    					}
	    				modelAndView.addObject("errorMessage", e.getLocalizedMessage());
	    				modelAndView.setViewName("CreatePG");
	    				return modelAndView;	
	    				
	    			}
			  catch (DuplicatePGException e) 
	    			{
				     modelAndView.addObject("duplicatePGErrorMessage",
			         e.getLocalizedMessage());
				     modelAndView.addObject("pg", pg);
				     modelAndView.setViewName("CreatePG");
				     return modelAndView ; 
				  }
			  
			  catch(Exception e) 
	    			{
				    e.printStackTrace();
	    			System.out.println("e = "+e);
	    			modelAndView.addObject("errorMessage", e.getLocalizedMessage()); 
	    			
	    			//need to add Error.html
	    			modelAndView.setViewName("CreatePG");
	    			return modelAndView ;
	    			}
			 
	    		}
	    	}
	    	
	    	//logout
	    	//@DeleteMapping("/logOut")
	    	@GetMapping(value = "/logOut")
	    	public ModelAndView logOut(ModelAndView modelAndView,HttpSession hs)
	    	{
	    		try
	    		{
	    			hs.invalidate();
	    			String signOutStr = "You are signed out";
	    	        
	    			modelAndView.addObject("signOutMessage", signOutStr);
	    			modelAndView.setViewName("Welcome");
	    			return modelAndView;
	    		}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			System.out.println("\n Ownercontroller logOut excetion = \n"+e.getLocalizedMessage());
	    			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
	    			
	    			modelAndView.setViewName("Error");
	    			
	    			return modelAndView;
	    		}
	    	} 	
	    	
	    ////back to owenHome from viewpgdetaisl
	    	@GetMapping("/back")
	    	public ModelAndView backToOwnerHome(ModelAndView modelAndView)
	    	{
	    		try
	    		{
	    			modelAndView.setViewName("OwnerHome");
	    			return modelAndView;
	    		}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			System.out.println("\n Ownercontroller backToOwnerHome() excetion = \n"+e.getLocalizedMessage());
	    			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
	    			
	    			modelAndView.setViewName("Error");
	    			
	    			return modelAndView;
	    		}
	    	}  
	    	
	    	//logIn
	    	@PostMapping("/logIn")
	    	public ModelAndView logIn(@RequestParam("phoneNumber") String phoneNumber,
	    			            @RequestParam("password") String password,
	    			            ModelAndView modelAndView,HttpSession hs)
	    	{
	    		try
	    		{
	    			//check for validations
	    			if(phoneNumber == null || phoneNumber.trim().length() == 0)
	    			{
	    				throw new IllegalArgumentException("Phone number can not be empty");
	    			}
	    			if(password == null || password.trim().length() == 0)
	    			{
	    				//m.addAttribute("phoneNumber", phoneNumber);
	    				modelAndView.addObject("phoneNumber", phoneNumber);
	    				throw new IllegalArgumentException("Password is also required");
	    			}
	    			
	    			//try to find a owner with given credentials
	    			
	    			//PGOwner pgOwnerBean  = pgOwnerService.findPGOwner(phoneNumber, password);
	    			
	    			List<Owner> ownersList = ownerService.findByPhoneNumberAndPassword(phoneNumber, password);
	    			if(ownersList != null && ownersList.size() > 0)
	    			{
	    				System.out.println("\n ownersList != null && ownersList.size() > 0 , ownersList = \n"+ownersList);
	    				
	    				//ownersList.forEach(System.out::println);
	    				
	    				System.out.println("\n ownersList.get(0) = \n"+ownersList.get(0));
	    				Owner owner = ownersList.get(0);
	    				
	    				if(owner != null)
	    				{
		    				//GET respective PG of owner being logged in using pgservice
		    				 
	    					//Pg pg = new Pg();
	    					long pgId = owner.getMyPG();
	    			   //pgServiceUri = http://localhost:8082/pg/
	    					                                //"http://localhost:8080/employee/{id}";
	    					Pg pg = restTemplate.getForObject(pgServiceUri+"findPgById/{pgId}",
    						Pg.class,
    						pgId);
	    					System.out.println("\n loaded pg from microservice pg = \n"+pg);
	    					
	    					if(pg != null)
			    			{
	    						//add pg to session
			    				hs.setAttribute("myPG", pg);
			    				//add owner to session
			    				hs.setAttribute("owner", owner);
			    				
    				    		modelAndView.setViewName("OwnerHome");
				    			
				    			return modelAndView ;
			    			}
			    			else
			    			{
			    				//pg cant be loaded
			    				System.out.println("\n culdnt load owenr's pg\n");
			    				String errorMessage = "Could Not Load Your PG" 
			    						+" Please try again after some time";
			    				throw new CouldNotLoadYourPGException(errorMessage);
			    			}
	    				}
	    				else
	    				{
	    					//owner == null
//	    					modelAndView.setViewName("OwnerHome");
//			    			return modelAndView ;
	    					throw new Exception("we couldn't find your record, try again");
	    				}
	    			}
	    			else
	    			{
	    				throw new InvalidCredentialsException("Incorrect Phone number OR/AND incorrect password");
	    			}
	    			
	    			
	    		}
	    		catch(IllegalArgumentException e)
	    		{
	    			e.printStackTrace();

	    			//m.addAttribute("loginErrorMessage",e.getLocalizedMessage());
	    			System.out.println(" IllegalArgumentException = "+e.getLocalizedMessage());
	    			modelAndView.addObject("loginErrorMessage",e.getLocalizedMessage());
	    			//return "Welcome";
	    			modelAndView.setViewName("Welcome");
	    			return modelAndView ;
	    		}
	    		catch(InvalidCredentialsException e)
	    		{
	    			//.addAttribute("loginErrorMessage",e.getLocalizedMessage());
	    			//return "Welcome";
	    			e.printStackTrace();

	    			modelAndView.addObject("loginErrorMessage",e.getLocalizedMessage());

	    			modelAndView.setViewName("Welcome");
	    			
	    			return modelAndView ;
	    		}
	    		catch (CouldNotLoadYourPGException e) 
	    		{
	    			e.printStackTrace();
	    			System.out.println(" \n CouldNotLoadYourPGException = "+e.getLocalizedMessage());
	    			
	    			modelAndView.addObject("loginErrorMessage",e.getLocalizedMessage());

	    			modelAndView.setViewName("Welcome");
	    			
	    			return modelAndView ;
	    			

				}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			//return to welcome page
	    			String loginErrorMessageStr = "Sorry we could not allow you to login due to some reasons,i.e"
	    					+ e.getLocalizedMessage()+ " printstacktrace = "+e.getMessage()+e.fillInStackTrace()
	    			+e.getCause()+e.getClass();
	    			//m.addAttribute("loginErrorMessage",loginErrorMessageStr+" ,"+ e.getLocalizedMessage());
	    			
	    			//return "Welcome";
	    			modelAndView.addObject("loginErrorMessage",loginErrorMessageStr);
	    			//return "Welcome";
	    			modelAndView.setViewName("Welcome");
	    			return modelAndView ;
	    		}
	    	}
	    	
	    	@GetMapping(value = "/show")
	    	public ModelAndView diplayAllOwners(ModelAndView modelAndView)
	    	{
	    		Iterable<Owner> ownersIterable = ownerService.findAllOwners();
	    		
	    		System.out.println("\nall owners are \n");
	    		ownersIterable.forEach(System.out::println);
	    		
	    		//modelAndView.addObject("owner",);
	    		modelAndView.setViewName("Welcome");
    			return modelAndView ;
	    	}
}
