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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;



import io.chetan.exception.CouldNotLoadYourPgException;
import io.chetan.exception.DuplicateOwnerException;
import io.chetan.exception.DuplicatePgException;
import io.chetan.exception.InvalidCredentialsException;
import io.chetan.exception.PasswordMissMatchException;
import io.chetan.exception.PgAddressCanNotBeEmptyException;
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


	      
	//openOwnerRegistrationView
	        @GetMapping("/openOwnerRegistrationView")
			public ModelAndView openOwnerRegistrationView()
			{
	        	ModelAndView modelAndView = new ModelAndView();
				
	        	Owner owner = new Owner();
				
				modelAndView.setViewName("RegisterOwner");
				
				modelAndView.addObject("owner",owner);
				
				
				return modelAndView ;
			}


	        @PostMapping(value="/registerOwner")
	    	public ModelAndView registerOwner(@Valid Owner owner,
	    			BindingResult br,HttpSession hs,ModelAndView modelAndView)
	    	{
	    		System.out.println("\n OwnerController registerOwner()\n");
	    	//	ModelAndView modelAndView = new ModelAndView();
	    		if(br.hasErrors())
	    		{
	    			System.out.println("\n OwnerController registerOwner has errors \n");
	    			modelAndView.setViewName("RegisterOwner");
	    	
	    		//	System.out.println("pgOwnerBean = \n "+pgOwnerBean);
	    			modelAndView.addObject("owner",owner);
	    		
	    			
	    			return modelAndView ;
	    		}
	    		else
	    		{
	 	System.out.println("\n OwnerController registerOwner has passed with pgownerbean =  \n"+owner);
			
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
		    				
					/*
					 * //testcing copy by value or address in session //Owner ownerFromSession =
					 * (Owner)hs.getAttribute("owner");
					 * 
					 * //ownerFromSession.setEmailId("owner@owner.com");
					 * //owner.setEmailId("owner@owner.com");
					 * //System.out.println("\nownerFromSession = \n "+ownerFromSession);
					 * 
					 * Owner ownerFromSessionAgain = (Owner)hs.getAttribute("owner");
					 * 
					 * System.out.println("\n check email id  in ownerFromSessionAgain = \n"
					 * +ownerFromSessionAgain);
					 */
		    	    		
		    	    		
		    				modelAndView.setViewName("CreatePg");
		    				
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
	    				modelAndView.setViewName("RegisterOwner");
	    				return modelAndView;
	    			}
	    			catch(PasswordMissMatchException e)
	    			{
	    				modelAndView.addObject("pswErrorMessage", e.getLocalizedMessage());
	    				modelAndView.setViewName("RegisterOwner");
	    				return modelAndView;
	    			}
	    			catch(Exception e)
	    			{
	    				System.out.println("HC registerOwner catch e =");
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
	        //@ModelAttribute("pgOwnerBean")@Valid Owner pgOwnerBean
	    	 //   @Valid @ModelAttribute("data") FormData data
	    	//cretaePg     
	        //@{/mypg/createPg}
	    	@PostMapping("/createPg")
	    	public ModelAndView createPg(@Valid Pg pg,BindingResult br,
	    			ModelAndView modelAndView,HttpSession hs) throws Exception
	    	{
	    		////ModelMap mp = new ModelMap();
	    		//mp.add
	    		//find pgOwnerBean from HSR pgOwnerBean
	    		Owner owner = (Owner)hs.getAttribute("owner");
	    		
	    	
	    		System.out.println("from HS owner = "+owner);
	    		if(br.hasErrors())
	    		{
	    			System.out.println("\n createPg() br.hasErrors\n");
	    			modelAndView.addObject("pg",pg);
    				modelAndView.setViewName("CreatePg");
    				return modelAndView;
	    		}
	    		else
	    		{
	    			System.out.println("\n createPg() br.has no Errors \n");
	    			try
	    			{
	    		
	    				//Pg foundPg = restTemplate.getForObject("http://localhost:8082/pg/findPg",Pg.class,pg.getAddress());
	    				
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
		    					throw new PgAddressCanNotBeEmptyException(emptyAddressFieldsList.toString());
		    				}
		    				
		    				//do not check for duplicate owner here
		    				//check for duplicate Pg call Pg-micorservice
	    					//requesting Pgservice for Pg to verify is this Pg already exist with Pgservice
		    				
	    					/*Pg foundPg = restTemplate.postForObject("http://localhost:8082/pg/findPg",
		    						pg.getAddress(),
		    						Pg.class);*/
	    					//example
//	    					restTemplate.getForObject(
//	    							   "http://example.com/hotels/{hotel}/rooms/{room}", String.class, "42", "21");
	    					
	    					String findPgUri = pgServiceUri+ "findPg/houseNumber/street/disrtict/state/country/pin/"
	    							+ "{houseNumber}/{street}/{disrtict}/{state}/{country}/{pin}";
	    					Address pgAddress = pg.getAddress();
	    					Pg foundPg = restTemplate.getForObject(findPgUri, Pg.class,pgAddress.getHouseNumber(),
	    							pgAddress.getStreet(),
	    							pgAddress.getDisrtict(),
	    							pgAddress.getState(),
	    							pgAddress.getCountry(),
	    							pgAddress.getPin());
	    				//	Pg foundPg = restTemplate.getForObject("http://localhost:8082/pg/findPg",Pg.class,pg.getAddress());
		    				if(foundPg == null )
		    				{
		    					System.out.println("\n foundPg == null Pg doesnt exist , carry on \n");
		    					//Pg does not exist , carry on
			    			
		    					//add pg start date to pg
		    					
		    					  pg.setPgStartedDate(new Date()); 
		    					  
		    						//add myPg to PgownerBean collection now
//		    					  if(owner == null )
//		    					  {
//		    						  System.out.println("\n owner retived from HS is  = \n"+owner);
//		    					  }
		    					//  boolean isPgAddedToOwnerCollection = owner.addAPg(pg);	
		    					 // owner.setMyPg(pg);
		    					  
		    					  
		    					  //first create Pg the owner
			    					  //create Pg by calling pgService microservice
			    					  Pg createdPg = restTemplate.postForObject("http://localhost:8082/pg/createPg",
					    						pg,
					    						Pg.class);
			    					  
			    					  //if pg created successfully then only proceed to create owner else stop
			    					  if(createdPg != null && createdPg instanceof Pg)
			    					  {
			    						  //yes pg creted successfully
						    				 System.out.println("\n createdPg by calling pg microservice  ==  \n"+createdPg);
				    					  
				    					  System.out.println("\nset pg for owner pg.getPgId() = \n"+pg.getPgId());
				    					  System.out.println("\n createdPg.getPgId() \n"+createdPg.getPgId());
				    					  
				    					  //set pg for owner
//				    					  owner.setMyPg(createdPg.getPgId());
				    					  owner.setMyPg(createdPg.getPgId());
				    					  System.out.println("\n  b4 creating owner = \n"+owner);
				    					  
				    					  //ceraet pgowneben after pg has been created
				    					  Owner createdOwner = ownerService.createOwner(owner);
				    					 
				    					  if(createdOwner != null && createdOwner instanceof Owner)
				    					  {
				    						  System.out.println("\n createdOwner   ==  \n"+createdOwner);
					    					  
					    					  System.out.println("\nset owner for Pg owner.getOwnerId() = \n"+owner.getOwnerId());
					    					  System.out.println("\n createdOwner.getOwnerId()) \n"+createdOwner.getOwnerId());
					    					  
					    					  //set owner for createdPg 
					    					  createdPg.setMyOwner(createdOwner.getOwnerId());
					    					  
					    					  System.out.println("\n after seting owner to createdPg = \n"+createdPg);
					    					  //saveback pg/update pg
					    					  
					    					  String updatePgUrl = pgServiceUri+"updatePg";
					    					  restTemplate.put(updatePgUrl,createdPg);
					    					  
					    					  //add owner's myPg to session
					  	    				    hs.setAttribute("myPg",createdPg);
					  	    				  
								/*
								 * //check wether session has copied attribute by value or address //sesion
								 * copies address so changs will reflect Owner sessionCopiedOwner =
								 * (Owner)hs.getAttribute("owner");
								 * System.out.println("\nsessionCopiedOwner = \n"+sessionCopiedOwner);
								 */
				    					  }
				    					  else
				    					  {
				    						  //either createdowenr is null or its not instance of Owner
				    						  throw new Exception("Couldn't create your account ,try again");

				    					  }
				    									    					  
				    					  
				    					  //update owner not necessry since  pg creted first and after owner,owner has pg
					    					//Owner updatedOwner = ownerService.updateOwner(owner); 
					    					
					    					//System.out.println("\n updatedOwner \n"+updatedOwner);
					    				 
					    				    
			    					  }
			    					  else
			    					  {
			    						  //either createdPg is null or its not instance of Pg
			    						  throw new Exception("Could not create Pg for you as of now , try again");
			    					  }
			    				  
			    					
						/*
						 * // display test owner here to check mypg updted to db or not List<Owner>
						 * ownersList = ownerService.findByPhoneNumber(owner.getPhoneNumber()); Owner
						 * testOwner = null; for(Owner ow : ownersList) {
						 * if(ow.getPhoneNumber().equals(owner.getPhoneNumber())) { testOwner = ow ;
						 * break; } } System.out.println("\ntestOwner for Mypg has a \n = "+testOwner);
						 * System.out.println("\n testOwner.getMyPg() = \n"+testOwner.getMyPg());
						 */
			    				  
			    				
		    					
		    				}
		    				else
		    				{
		    					System.out.println("\n duplicate Pg, stop here \n");
		    					
		    					//duplicate Pg, stop here
			    		 Address address = pg.getAddress() ;
			    				 String addressStr = address.getHouseNumber()+" "+address.getStreet()
			    				  +" "+address.getDisrtict()+" "+address.getState()+" "+address.getCountry()
			    				  +" "+address.getPin();
			    					 
			    		 String duplicatePgErrorMessageStr = "Hello "+owner.getFirstName()+", some one has already " +
			    				  "constructed a Pg at same address = "
			    				  +addressStr+", so pls enter your Pg's address";
			    				  
			    				  throw new DuplicatePgException(duplicatePgErrorMessageStr);
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
	    			catch(PgAddressCanNotBeEmptyException e)
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
	    				modelAndView.setViewName("CreatePg");
	    				return modelAndView;	
	    				
	    			}
			  catch (DuplicatePgException e) 
	    			{
				     modelAndView.addObject("duplicatePgErrorMessage",
			         e.getLocalizedMessage());
				     modelAndView.addObject("pg", pg);
				     modelAndView.setViewName("CreatePg");
				     return modelAndView ; 
				  }
			  //no need of generic exception if something failed to create Pg then it should stop creating owner as well
//			  catch(Exception e) 
//	    			{
//				    e.printStackTrace();
//	    			System.out.println("e = "+e);
//	    			modelAndView.addObject("errorMessage", e.getLocalizedMessage()); 
//	    			
//	    			//need to add Error.html
//	    			modelAndView.setViewName("CreatePg");
//	    			return modelAndView ;
//	    			}
//			 
//	    		}
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
	    			
	    			//Owner pgOwnerBean  = pgOwnerService.findOwner(phoneNumber, password);
	    			
	    			List<Owner> ownersList = ownerService.findByPhoneNumberAndPassword(phoneNumber, password);
	    			if(ownersList != null && ownersList.size() > 0)
	    			{
	    				System.out.println("\n ownersList != null && ownersList.size() > 0 , ownersList = \n"+ownersList);
	    				
	    				//ownersList.forEach(System.out::println);
	    				
	    				System.out.println("\n ownersList.get(0) = \n"+ownersList.get(0));
	    				Owner owner = ownersList.get(0);
	    				
	    				if(owner != null)
	    				{
		    				//GET respective Pg of owner being logged in using pgservice
		    				 
	    					//Pg pg = new Pg();
	    					long pgId = owner.getMyPg();
	    			   //pgServiceUri = http://localhost:8082/pg/
	    					                                //"http://localhost:8080/employee/{id}";
	    					Pg pg = restTemplate.getForObject(pgServiceUri+"findPgById/{pgId}",
    						Pg.class,
    						pgId);
	    					System.out.println("\n loaded pg from microservice pg = \n"+pg);
	    					
	    					if(pg != null)
			    			{
	    						//add pg to session
			    				hs.setAttribute("myPg", pg);
			    				//add owner to session
			    				hs.setAttribute("owner", owner);
			    				
    				    		modelAndView.setViewName("OwnerHome");
				    			
				    			return modelAndView ;
			    			}
			    			else
			    			{
			    				//pg cant be loaded
			    				System.out.println("\n culdnt load owenr's pg\n");
			    				String errorMessage = "Could Not Load Your Pg" 
			    						+" Please try again after some time";
			    				throw new CouldNotLoadYourPgException(errorMessage);
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
	    		catch (CouldNotLoadYourPgException e) 
	    		{
	    			e.printStackTrace();
	    			System.out.println(" \n CouldNotLoadYourPgException = "+e.getLocalizedMessage());
	    			
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
	    	
	    	//viewOwnerDetails
	    	@GetMapping(value = "/viewOwnerDetails")
	    	public ModelAndView viewOwnerDetails(ModelAndView modelAndView)
	    	{
	    		try
	    		{
	    			modelAndView.setViewName("ViewOwnerDetails");
	    			return modelAndView ;
				}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			modelAndView.setViewName("OwnerHome");
	    			return modelAndView ;
				}
	    	}
	    	
	    	//openEditOwnerDetails view
	    	@GetMapping(value = "/openEditOwnerDetails")
	    	public ModelAndView openEditOwnerDetails(ModelAndView modelAndView, HttpSession hs)
	    	{
	    		try
	    		{
	    			Owner owner = (Owner) hs.getAttribute("owner");
	    			
	    			modelAndView.setViewName("EditOwnerDetails");
	    			modelAndView.addObject("owner", owner);
	    			return modelAndView ;
				}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			System.out.println("\n openEditOwnerDetails catch (Exception e) ()\n");

	    			modelAndView.setViewName("OwnerHome");
	    			return modelAndView ;
				}
	    	}
	    	
	/*
	 * public ModelAndView registerOwner(@Valid Owner owner, BindingResult
	 * br,HttpSession hs,ModelAndView modelAndView)
	 */
	    	//updateOwner
	    	//PUT not supported in html fomr :( @PutMapping(value = "/updateOwner")
	    	//public ModelAndView updateOwner(@Valid Owner owner,BindingResult br,ModelAndView modelAndView)
	    	@PostMapping(value = "/updateOwner")
	    	public ModelAndView updateOwner(@Valid Owner owner,BindingResult br,
	    			ModelAndView modelAndView, HttpSession hs)
	    	{
	    		if(br.hasErrors())
	    		{
	    			System.out.println("\n updateOwner br.hasErrors() with Invalid owner = \n"+owner);
					//return "EditOwnerDetails";
					modelAndView.setViewName("EditOwnerDetails");
					modelAndView.addObject("owner", owner);
					return modelAndView;
 
	    		}
	    		else
	    		{
	    			System.out.println("\n updateOwner br.passesd () with valid owner = \n"+owner);

	    			try
	    			{
	    				//System.out.println(" updateOwner() ownerId = \n"+ownerId);
		    			//Owner sessionOwner = (Owner) hs.getAttribute("owner");

	    				//pgOwnerService.update(pgOwnerBean);
						//set back to HS
						//hs.setAttribute("pgOwnerBean", pgOwnerBean);
						//String ownerDetailsUpdatedSuccesMessageStr = "Your details have been updated successfully as follows";
						//m.addAttribute("ownerDetailsUpdatedSuccesMessage", ownerDetailsUpdatedSuccesMessageStr);
						//return "ViewOwnerDetails";
	    				
		    			//System.out.println("\n sessionOwner.getOwnerId() = \n"+sessionOwner.getOwnerId());
		    			//System.out.println("\nsessionOwner = \n"+sessionOwner);
		    			//set states which are not supposd to be edited
	    				//owner.setOwnerId(sessionOwner.getOwnerId());
	    				//owner.setPhoneNumber(phoneNumber);
	    				//owner.setFirstName("firstName");
	    				//owner.setPassword(sessionOwner.getPassword());
	    				//owner.setRepeatPassword(sessionOwner.getRepeatPassword());

	    				Owner updatedOwner = ownerService.updateOwner(owner);
	    				
	    				System.out.println("\n updatedOwner =\n"+updatedOwner);
	    				
	    				//set back to HS
						hs.setAttribute("owner", updatedOwner);
						
						String ownerDetailsUpdatedSuccesMessageStr = "Your details have been updated successfully as follows";
						modelAndView.addObject("ownerDetailsUpdatedSuccesMessage", ownerDetailsUpdatedSuccesMessageStr);
						
						modelAndView.setViewName("ViewOwnerDetails");

						
						return modelAndView;
					}
	    			catch (Exception e)
	    			{
		    			System.out.println("\n updateOwner catch (Exception e)  ()\n");

	    				e.printStackTrace();
	    				System.out.println("\n e.getMessage() \n"+e.getMessage());
	    				System.out.println("\n e.getCause()() \n"+e.getCause());
	    				
	    				System.out.println("\n e.getClass()() \n"+e.getClass());

	    				System.out.println("\n e.getStackTrace()() \n"+e.getStackTrace());

	    			//	m.addAttribute("errorMessage", e.getLocalizedMessage());
	    				modelAndView.addObject("errorMessage", e.getMessage());
	    				//return "Error";
	    				modelAndView.setViewName("ViewOwnerDetails");
						return modelAndView;
					}
	    		}
	    	}
	    	
	    	//viewPgDetails
	    	@GetMapping(value = "/viewPgDetails")
	    	public ModelAndView viewPgDetails(ModelAndView modelAndView)
	    	{
	    		try 
	    		{
	    			//Owner pgOwnerBean  = (Owner)hs.getAttribute("pgOwnerBean");
	    			//Pg pgBean = pgService.read(pgOwnerBean.getMyPg().getPgId());
	    			
	    			//m.addAttribute("pgBean", pgBean);
	    			
	    			//return "ViewPgDetails";
	    			modelAndView.setViewName("ViewPgDetails");
					return modelAndView;
	    		}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			System.out.println("\n viewPgDetails() exception e = \n"+e.getMessage());
	    			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
	    			
	    			//return "OwnerHome";
	    			
	    			modelAndView.setViewName("OwnerHome");
					return modelAndView;
	    		}
	    	}
	    	
	    	//openViewAndEditPgDetails
	    	@GetMapping(value = "/openEditPgDetails")
	    	public ModelAndView openViewAndEditPgDetails(ModelAndView modelAndView,HttpSession session)
	    	{
	    		try 
	    		{
	    			//myPg
	    			Pg pg = (Pg)session.getAttribute("myPg");
	    			modelAndView.setViewName("EditPgDetails");
	    			modelAndView.addObject("pg",pg);
					return modelAndView;
				}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			modelAndView.setViewName("OwnerHome");
					return modelAndView;
				}
	    	}
	    	
	    	//updatePg
	    	//@RequestMapping(value="/updatePG",method=RequestMethod.POST)
	    	@PostMapping(value = "/updatePg")
	    	public ModelAndView updatePGDetails(@Valid Pg pg,BindingResult br,
	    			ModelAndView modelAndView ,HttpSession session)
	    	{
	    		try
	    		{
	    			if(br.hasErrors())
	    			{
	    				System.out.println("updatePGDetails () yes br had errors & invalid pg  = \n "+pg);
	    				System.out.println(br);
	    				///return "EditPgDetails";
	    				
	    				modelAndView.setViewName("EditPgDetails");
						return modelAndView;
	    			}
	    			else
	    			{
	    				System.out.println("updatePGDetails () no b rdidnt have errors with valid pg = \n"+pg);
	    				
	    				Pg sessionPg = (Pg)session.getAttribute("myPg");
	    				//set back pgstartedDate
	    				pg.setPgStartedDate(sessionPg.getPgStartedDate());

	    				
	    				//check duplicate pg
	    				//step 1 load pg from db with given address 
	    				//step 2 if found then 
	    				//step 2a compare id of loaded and updated
	    			    //step 2bif equals then he is not changed any details so save it directly
	    				//step 2c else then address he want o change is of someone else's so stop
	    				//step 3 else means he has changed details and tht is not colliding with any1 else's so
	    				// update it
	    	
	    				//m.addAttribute("pgUpdatedSuccessMessage",pgUpdatedSuccessMessageStr);
	    				//return "OwnerHome";
	    				String pgUpdatedSuccessMessageStr = "Your PG details have been updated successfully";	    				modelAndView.setViewName("OwnerHome");
	    				modelAndView.addObject("pgUpdatedSuccessMessage", pgUpdatedSuccessMessageStr);
	    				
	    				modelAndView.setViewName("OwnerHome");

	    				//return modelAndView;
						
	    			}
	    			
	    		}
//	    		catch (DuplicatePgException e)
//	    		{
//	    			m.addAttribute("duplicatePGErrorMessage", e.getLocalizedMessage());
//	    			m.addAttribute("pgBean", pgBean);
//	    			return "ViewAndEditPGDetails";
//	    		}
	    		catch (Exception e) 
	    		{
	    			e.printStackTrace();
	    			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
	    			//return "Error";
					return modelAndView;

	    		}
	    	}
}
