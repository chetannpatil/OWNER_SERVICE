package io.chetan.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import io.chetan.exception.CanNotRaiseAComplaintException;
import io.chetan.exception.CanNotRemoveTheRoomException;
import io.chetan.exception.CouldNotLoadYourPgException;
import io.chetan.exception.DuplicateInMateException;
import io.chetan.exception.DuplicateOwnerException;
import io.chetan.exception.DuplicatePgException;
import io.chetan.exception.DuplicateRoomException;
import io.chetan.exception.EmptyPgException;
import io.chetan.exception.InMatesOverFlowInARoomException;
import io.chetan.exception.InvalidCredentialsException;
import io.chetan.exception.PasswordMissMatchException;
import io.chetan.exception.PgAddressCanNotBeEmptyException;
import io.chetan.exception.RoomDoesNotExistExcepton;
import io.chetan.exception.SessionTimedOutException;
import io.chetan.exception.ZeroComplaintsException;
import io.chetan.owner.model.Address;
import io.chetan.owner.model.Bill;
import io.chetan.owner.model.Complaint;
import io.chetan.owner.model.InMate;
import io.chetan.owner.model.Owner;
import io.chetan.owner.model.Pg;
import io.chetan.owner.model.Room;
import io.chetan.owner.util.Constants;
import io.chetan.owner.util.Util;
import io.chetan.service.OwnerService;


//@Controller
@RequestMapping(value = "/mypg")
@RestController
public class OwnerController 
{
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private RestTemplate restTemplate;

	private HashMap<String, String> uriVariablesMap;

	

	//private HashMap<String, String> uriVariablesMap = new HashMap<String, String>();


//	private static final String PG_SERVICE_URI = "http://localhost:8082/pg/";
//	private static final String ROOM_SERVICE_URI = "http://localhost:8083/room/";
//
//	private static final String INMATE_SERVICE_URI = "http://localhost:8084/inMate/";
//	//  /bill
//	private static final String BILL_SERVICE_URI = "http://localhost:8085/bill/";
//	
//	private static final String COMPLAINT_SERVICE_URI = "http://localhost:8086/complaint/";
	
	
	
	private static final String PG_SERVICE_URI = "http://PgService/pg/";
	
	//private static final String PG_SERVICE_URI = "http://ZUUL/pg/pg/";
	
	//private static final String PG_SERVICE_URI = "http://ZUUL/pg/";
	
	//"http://localhost:8085/bill/"
	//private static final String PG_SERVICE_URI = "http://localhost:2007/pg/pg/";
	
	private static final String ROOM_SERVICE_URI = "http://RoomService/room/";

	private static final String INMATE_SERVICE_URI = "http://InMateService/inMate/";
	//  /bill
	private static final String BILL_SERVICE_URI = "http://BillService/bill/";
	
	private static final String COMPLAINT_SERVICE_URI = "http://ComplaintService/complaint/";
	
	
	
	
	private static final Logger LOGGER = LogManager.getLogger(OwnerController.class);
	
	//private static final String ERROR_PATH = "/error";
	
	//private static final String ERROR_PATH = "mypg/error";
	
	@Autowired
	private LoadBalancerClient loadBalancer;
	
	/*
	 * @Override public String getErrorPath() {
	 * System.out.println("\n OwnerController  getErrorPath() \n"); return
	 * ERROR_PATH; }
	 */
	
	
	/*
	 * @GetMapping(value = ERROR_PATH) public String defaultErrorMessage() {
	 * System.out.println("\n OwnerController  defaultErrorMessage() \n");
	 * 
	 * return "Requested resource is not found"; }
	 */
	
//	@GetMapping(value = ERROR_PATH)
//	public ModelAndView defaultErrorMessage(ModelAndView modelAndView)
//	{
//		System.out.println("\n OwnerController  defaultErrorMessage() \n");
//		
//		//errorMessage
//		modelAndView.addObject("errorMessage", "Requested resource is not found");
//		modelAndView.setViewName("Error");
//
//		return modelAndView;
//	}
	
	
	
	//redundant methods
	
	private boolean isDuplicateInMate(String phoneNumber)
	{
		LOGGER.info("\n  OC-isDuplicateInMate -phoneNumber \n"+phoneNumber);
		
//		String searchInMateURL = INMATE_SERVICE_URI+"/find/{phoneNumber}";
//		
//		Boolean isInMateExist = restTemplate.getForObject(searchInMateURL,
//				Boolean.class,
//				inMate.getPhoneNumber());
		
//        Boolean isInMateExist = restTemplate.getForObject(INMATE_SERVICE_URI+"find/phoneNumber"+phoneNumber,
//        		Boolean.class);
        
//		Complaint complaint = restTemplate.getForObject(COMPLAINT_SERVICE_URI+"find/{complaintId}",
//    			Complaint.class, 
//    			complaintId);
		
		String findInMateURL = INMATE_SERVICE_URI+"find/phoneNumber/{phoneNumber}";
		
	    Boolean isInMateExist = restTemplate.getForObject(findInMateURL,
	    		Boolean.class, 
	    		phoneNumber);
	    
        		
	    
        if(isInMateExist)
        {
        	LOGGER.info("\n  OC-isDuplicateInMate - yes phoneNumber \n"+phoneNumber+" exist");
        	return true;
        }
        else
        {
        	LOGGER.info("\n  OC-isDuplicateInMate - NO phoneNumber \n"+phoneNumber+" does not exist");
        	return false;
        }
        	
        
	}
	//updatecomplaint
	
	private void deleteComplaint(long complaintId)
	{
		LOGGER.info("\nOC-deleteComplaint with complaintId = "+complaintId); 
		
//		restTemplate.delete(COMPLAINT_SERVICE_URI+"delete/"+complaintId);
		
		//restTemplate.delete(COMPLAINT_SERVICE_URI+"delete/{complaintId}",complaintId);
		
//		restTemplate.delete(INMATE_SERVICE_URI+"delete/"+inMateId);
		
		restTemplate.delete(COMPLAINT_SERVICE_URI+"delete/"+complaintId);
		
		LOGGER.info("\nOC-deleteComplaint after deleting , complaintId = "+complaintId); 
		
		
	}
	
	private List<Complaint> getNonEmptyComplainBox(Complaint [] complaintsArray) throws ZeroComplaintsException
	{
		LOGGER.info("\nOC-getNonEmptyComplainBox"); 
		
		if(complaintsArray == null || complaintsArray.length <= 0)
		{
			LOGGER.info("\nOC-getNonEmptyComplainBox - empty");  
			
			throw new ZeroComplaintsException();
		}
		List<Complaint> complaintsList = Arrays.asList(complaintsArray);
		
		return complaintsList ;
	}
	private List<Complaint> loadMyAllComplaints(HttpSession session) throws ZeroComplaintsException
	{
		        
		LOGGER.info("\nOC-loadMyAllComplaints -   \n");
		
		       InMate inMate  = (InMate) session.getAttribute("inMate");
		
	            String loadComplaintsURL = COMPLAINT_SERVICE_URI+"findAll/inMateId/{inMateId}";
				
				Complaint[] complaintsArray = restTemplate.getForObject(loadComplaintsURL,
						Complaint[].class, 
						inMate.getInMateId());
				
				//check for empty
				//isComplaintBoxEmpty(complaintsArray);
				
				
				//List<Complaint> complaintsList = Arrays.asList(complaintsArray)
				
				List<Complaint> complaintsList = getNonEmptyComplainBox(complaintsArray);
				
				LOGGER.info("\nOC-loadMyAllComplaints -  returning  complaintsList = \n"+complaintsList);
				
			return complaintsList ;
	}
	private Map<Complaint, InMate> makeUpReturnToViewAllComplaints(HttpSession session)
			throws ZeroComplaintsException
	{
		//load all complaints from db
		
		LOGGER.info("\nOC-makeUpReturnToViewAllComplaints -   \n");
		
		List<Complaint> pgAllComplaintsList = loadAllComplaintsOfPg(session);
		
		
									
		Map<Complaint, InMate> complaintedInMatesMap = loadComplaintedInMates(pgAllComplaintsList);
		
		LOGGER.info("\nOC-makeUpReturnToViewAllComplaints - returning complaintedInMatesMap  = \n"+complaintedInMatesMap);
		
		return complaintedInMatesMap ;
		
	}
	private void updateComplaint(Complaint complaint)
	{
		LOGGER.info("\nOC-updateComplaint -  complaint \n"+complaint);
		
		restTemplate.put(COMPLAINT_SERVICE_URI+"update",
				complaint);
		
		LOGGER.info("\nOC-updateComplaint -  updatedcomplaint \n");
	}
	//loadallcomplaintofaPG 
	private List<Complaint> loadAllComplaintsOfPg(HttpSession session) throws ZeroComplaintsException
	{
		LOGGER.info("\nOC-loadAllComplaintsOfPg -  pgId \n");
		
		Pg pg = (Pg) session.getAttribute("pg");
		
		Complaint[] complaintsArray = restTemplate.getForObject(COMPLAINT_SERVICE_URI+"findAll/{pgId}", 
				Complaint[].class, 
				pg.getPgId());
		
		//if there are no complaints then throw ex
		
//		if(complaintsArray == null || complaintsArray.length <= 0)
//		{
//			LOGGER.info("\nOC-loadAllComplaintsOfPg -  complaintsArray or "
//					+ "complaintsArray.length <= 0  \n"+complaintsArray);
//			
//			throw new ZeroComplaintsException()
//		}
		
		//check for empty complaint box
		//isComplaintBoxEmpty(complaintsArray);
		
		//List<Complaint> pgAllComplaintsList = Arrays.asList(complaintsArray);
		
		List<Complaint> pgAllComplaintsList = getNonEmptyComplainBox(complaintsArray);
		
		LOGGER.info("\nOC-loadAllComplaintsOfPg -  returning pgAllComplaintsList  \n"+pgAllComplaintsList);
		
		return pgAllComplaintsList ;
	}
	
	
	
    private Complaint searchComplaint(long complaintId)
    {
    	LOGGER.info("\nOC-searchComplaint -  complaintId \n"+complaintId);
    	
    	Complaint complaint = restTemplate.getForObject(COMPLAINT_SERVICE_URI+"find/{complaintId}",
    			Complaint.class, 
    			complaintId);
    	
    	LOGGER.info("\nOC-searchComplaint -  found complaint \n"+complaint);
    	
    	return complaint ;
    	
    }
	
	private Map<Complaint, InMate> loadComplaintedInMates(List<Complaint>  complaintsList)
	{
		LOGGER.info("\nOC-loadComplaintedInMates -  complaintsList \n"+complaintsList);
		
		Map<Complaint, InMate > complaintsMap = new LinkedHashMap<Complaint, InMate>();
		
		for(Complaint complaint :complaintsList)
		{
			LOGGER.info("oc -loadComplaintedInMates -for  complaint = \n "+complaint);
			
			//for each complaint load its inmate
			InMate complaintsInMate = searchInMate(complaint.getInMate());
			
			LOGGER.info("oc -loadComplaintedInMates -for  complaint  loaded inmate = \n "+complaintsInMate);
			
			
			//complaintsMap.put(complaintsInMate, complaint);
			
			complaintsMap.put(complaint, complaintsInMate);
			
		}
		LOGGER.info("oc -loadComplaintedInMates -returnoing map = \n "+complaintsMap);
		return complaintsMap ;
	}
	
		
	private InMate searchInMate(long inMateId)
	{
		LOGGER.info("\n\nOC-searchInMate - inMateId =  "+inMateId);
		
		InMate inMate = restTemplate.getForObject(INMATE_SERVICE_URI+"find/{inMateId}",
				InMate.class,
				inMateId);
		
		LOGGER.info("\n\nOC-searchInMate - foundinmate = returning inmate= \n "+inMate);
		
		return inMate ;
				
				
	}
	private Object whoIsLoggedInUser(HttpSession session)
	{

		LOGGER.info("\nOC-whoIsUser \n");
		Object object = session.getAttribute("owner");
		
		if(object != null && object instanceof Owner)
		{
			LOGGER.info("\n oc- whoIsUser owner is logged in, owner = \n"+object);
			LOGGER.info("\nso returning owner");
			return object;
		}
		else
		{
			object = session.getAttribute("inMate");
			if(object != null && object instanceof InMate)
			{
				LOGGER.info("\n  oc- whoIsUser -inMate is logged in, inMate = \n"+object);
				LOGGER.info("\nso returning inMate\n");
				return object ;
			}
			else
			{
				LOGGER.info("\n  oc- whoIsUser -neither is is logged in,");
				LOGGER.info("\nso returning null\n");
				return null;
			}
		}
		
    }
	
	private Pg loadMyPg(long pgId)
	{
		LOGGER.info("\nOC -loadMyPg - wit pgid = \n"+pgId);
	
	    ServiceInstance serviceInstance = loadBalancer.choose("PgService");
		
		LOGGER.info("\n OC - loadMyPg - serviceInstance  = \n"+serviceInstance);
		
		System.out.println(serviceInstance.getUri());
		
		LOGGER.info("\n OC - loadMyPg - serviceInstance.getUri  = \n"+serviceInstance.getUri());
		
		
		Pg pg = restTemplate.getForObject(PG_SERVICE_URI + "findPgById/{pgId}", Pg.class, pgId);
		
		return pg;
	}
	private void updateRoom(Room room)
	{
		LOGGER.info("OC -updateRoom-  for room = "+room);
		restTemplate.put(ROOM_SERVICE_URI+"updateRoom", room);
	}
	
	//loadallrooms
		private List<Room> loadAllRoomsOfAPg(HttpSession session)
		{
			Pg pg = (Pg)session.getAttribute("pg");
			
			System.out.println("\n loadAllRoomsOfAPg session pg = \n"+pg);
			
			if(pg.getRooms() == null || pg.getRooms().isEmpty())
			{
				System.out.println("\n loadAllRoomsOfAPg  pg is empty  = \n");
				throw new EmptyPgException();
			}
			List<Room> rooms = restTemplate.getForObject(ROOM_SERVICE_URI+"findAllRoomsByPgId/{pgId}",
					List.class,
					pg.getPgId());
			
			return rooms ;
		}
		
	private List<InMate> loadAllInMatesOfAPg(HttpSession session)
	{
		System.out.println("\n oc loadAllInMatesOfAPg  = \n");
		LOGGER.info("\n oc loadAllInMatesOfAPg  = \n");
		//get pgid from session
		Pg pg = (Pg) session.getAttribute("pg");
		
		//before goingto inmateservice check pg has rooms atleast
		if(pg.getRooms() == null ||pg.getRooms().isEmpty())
		{
			System.out.println("\n loadAllRoomsOfAPg  pg is empty  = \n");
			LOGGER.info("\n oc loadAllRoomsOfAPg  pg is empty  = \n");
			
			throw new EmptyPgException();
		}
		
//		List list = restTemplate.getForObject(INMATE_SERVICE_URI+"findAll/{pgId}",
//				List.class,
//				pg.getPgId());
		
		// List<MyModelClass> myModelClass=(List<MyModelClass>) restTemplate.postForObject(url,mvm,List.class);
		
//		 List<InMate> list = (List<InMate>) restTemplate.getForObject(INMATE_SERVICE_URI+"findAll/{pgId}",
//				List.class,
//				pg.getPgId());
		 
		// MyModelClass[] myModelClasses = restTemplate.postForObject(url,mvm, MyModelClass[].class);

		InMate [] array = restTemplate.getForObject(INMATE_SERVICE_URI+"findAll/{pgId}",
					InMate[].class,
					pg.getPgId());
		 
		LOGGER.info("\n oc loadAllRoomsOfAPg returning aray  = \n"+array);
		//return list ;
		
		LOGGER.info(Arrays.toString(array));
		
		
		 //List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
		List<InMate> list = Arrays.asList(array);
		//List<InMate> list = 
		return list ;
	}
	
	private boolean isDuplicateOwner(String phoneNumber)
	{
		List<Owner> ownerslist = ownerService.findByPhoneNumber(phoneNumber);
		
		if (ownerslist == null || ownerslist.size() == 0)
		{
			return false;
		}
		return true ;
	}
	
	private Room searchRoom(String roomNumber,long myPg)
	{
		System.out.println("\n searchRoom \n");
		
		LOGGER.info("\n OC -searchRoom - with roomnumber= "+roomNumber+ " and \n pgid = "+myPg);
		String findRoomUrl =ROOM_SERVICE_URI+"/findRoomByRoomNumberAndMyPg/"
				+ "roomNumber/{roomNumber}/"
				+ "myPg/{myPg}";
		
		uriVariablesMap = new HashMap<String, String>();
		

		uriVariablesMap.put("roomNumber", roomNumber);
		

		uriVariablesMap.put("myPg",Long.toString(myPg));
		

		
		
		Room foundRoom = restTemplate.getForObject(findRoomUrl,
				Room.class,
				uriVariablesMap);
		
		return foundRoom ;
	}
	
	public Room searchRoom(long roomId)
	{
		System.out.println("\n searchRoomByRoomId \n");

		Room room = restTemplate.getForObject(ROOM_SERVICE_URI+"findRoomById/{roomId}",
				Room.class,
				roomId);
		return room ;
	}
	
	@GetMapping("/readComplaint")
	public Complaint readComplaint() {
		System.out.println("\n OwnerController readComplaint()");
		
		LOGGER.info("\n\n OwnerController - readComplaint \n\n");
				
		//return ownerService.findAllOwners();
		
		  Complaint complaint = restTemplate.getForObject(COMPLAINT_SERVICE_URI+"read",
				Complaint.class);
		
		return complaint;
	}
	
	
	@GetMapping("/readOwner")
	public Iterable<Owner> getOwner()
	{
		System.out.println("\n OwnerController getOwner()");
		
		LOGGER.info("\n\n OwnerController - getOwner- server.port=2004 \n\n");
				
		return ownerService.findAllOwners();
		
//		Pg pg = restTemplate.getForObject(PG_SERVICE_URI+"read",
//				Pg.class);
		
	}
	
	
	// private Logger
	@GetMapping("/")
	public ModelAndView welcome() {
		System.out.println("\n OwnerController welcome()");
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("Welcome");
		// modelAndView.addObject("signOutMessage", "Chetan's signOutMessage");
		return modelAndView;
	}

	//springcloud gateway helper
	@GetMapping("/read")
	public ModelAndView contact_temp_springcloud_helper()
	{

        System.out.println("\n\n\n owner_controller - contact_temp with  =   \n\n\n");
		
		LOGGER.info("\n\n\n owner_controller - contact_temp with  =   \n\n\n");
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("Contact");

		//return "I am response";

		return modelAndView;
	}
	@GetMapping("/contact")
	public ModelAndView contact_temp(@RequestHeader("second-request") String header)
	{

        System.out.println("\n\n\n owner_controller - contact_temp with header =   \n\n\n"+header);
		
		LOGGER.info("\n\n\n owner_controller - contact_temp with header =   \n\n\n"+header);
		
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("Contact");

		return modelAndView;

	}
	
	@GetMapping("/contactMe")
	public ModelAndView contact() {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("Contact");

		return modelAndView;

	}

	// openOwnerRegistrationView
	@GetMapping("/openOwnerRegistrationView")
	public ModelAndView openOwnerRegistrationView() {
		ModelAndView modelAndView = new ModelAndView();

		Owner owner = new Owner();

		modelAndView.setViewName("RegisterOwner");

		modelAndView.addObject("owner", owner);

		return modelAndView;
	}

	@PostMapping(value = "/registerOwner")
	public ModelAndView registerOwner(@Valid Owner owner, BindingResult br,
			HttpSession session, ModelAndView modelAndView) {
		System.out.println("\n OwnerController registerOwner()\n");
		// ModelAndView modelAndView = new ModelAndView();
		if (br.hasErrors()) {
			System.out.println("\n OwnerController registerOwner has errors \n");
			modelAndView.setViewName("RegisterOwner");

			// System.out.println("pgOwnerBean = \n "+pgOwnerBean);
			modelAndView.addObject("owner", owner);

			return modelAndView;
		} else {
			System.out.println("\n OwnerController registerOwner has passed with pgownerbean =  \n" + owner);

			try {
			

				String pwd = owner.getPassword();
				String repeatPwd = owner.getRepeatPassword();
				// removeExtra Space from each has a
				if (Owner.isPasswordMatchingRepeatPassword(pwd, repeatPwd) == false) {
					String errorStr = "Do you think password = \"" + pwd + "\" " + " and repeat password = \" "
							+ repeatPwd + "\" are same ?";
					throw new PasswordMissMatchException(errorStr);
				}
				//check for duplicate user(inmate)
				boolean isDuplicateInMate = isDuplicateInMate(owner.getPhoneNumber());
				
				if(isDuplicateInMate)
				{
					LOGGER.info("OC-registerowner- isDuplicateInMate yes-");
					
					throw new DuplicateInMateException();
					
				}
				// check for duplicate owner here

			
				boolean isDuplicateOwner = isDuplicateOwner(owner.getPhoneNumber());
				
				if(isDuplicateOwner == false )
				{
					// owner does not exist , carry on
					// do not create a pgOwnerbean now
					// redirect to crate pg page

					// need to add pgbean to M
					modelAndView.addObject("pg", new Pg());
					session.setAttribute("owner", owner);

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

				} else {
					throw new DuplicateOwnerException();
				}
				// do not create a pgOwnerbean now
				// redirect to crate pg page

				// need to add pgbean to M

				// return "OwnerHome";
			}

			catch (DuplicateOwnerException |DuplicateInMateException e) 
			{
				e.printStackTrace();
				
				String errorMessage ="A account allready exist  with same phone number = "+ owner.getPhoneNumber();
				// m.addAttribute("dupUserErrorMessage", e.getLocalizedMessage());
				modelAndView.addObject("dupUserErrorMessage",errorMessage);
				modelAndView.setViewName("RegisterOwner");
				return modelAndView;
			} catch (PasswordMissMatchException e) {
				modelAndView.addObject("pswErrorMessage", e.getLocalizedMessage());
				modelAndView.setViewName("RegisterOwner");
				return modelAndView;
			} catch (Exception e) {
				System.out.println("HC registerOwner catch e =");
				System.out.println(e);
				e.printStackTrace();
				// String errorStr = "Due to some raesons we couldnt create your account very
				// sorry";
				// m.addAttribute("errorMessage",errorStr+" i.e."+e.getLocalizedMessage());
				// return "Error";

				//modelAndView.addObject("errorMessage", e.getLocalizedMessage());
				
				modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
				
				return modelAndView;
			}

		}
	}



	// @Valid Owner owner
	// @ModelAttribute("pgOwnerBean")@Valid Owner pgOwnerBean
	// @Valid @ModelAttribute("data") FormData data
	// cretaePg
	// @{/mypg/createPg}
	@PostMapping("/createPg")
	public ModelAndView createPg(@Valid Pg pg, BindingResult br, ModelAndView modelAndView, HttpSession session)
			throws Exception 
	{
		//// ModelMap mp = new ModelMap();
		// mp.add
		// find pgOwnerBean from HSR pgOwnerBean
		Owner owner = (Owner) session.getAttribute("owner");

		System.out.println("from session owner = " + owner);
		if (br.hasErrors())
		{
			System.out.println("\n createPg() br.hasErrors\n");
			modelAndView.addObject("pg", pg);
			modelAndView.setViewName("CreatePg");
			return modelAndView;
		} 
		else
		{
			System.out.println("\n createPg() br.has no Errors \n");
			try {

				// Pg foundPg =
				// restTemplate.getForObject("http://localhost:8082/pg/findPg",Pg.class,pg.getAddress());

				if (pg.getAddress() != null) {
					System.out.println("\n  pg.getAddress() != null & pg.getAddress() = \n" + pg.getAddress());

					// checking any field is empty for Address
					List<String> emptyAddressFieldsList = Address.addressValidation(pg.getAddress().getHouseNumber(),
							pg.getAddress().getStreet(), pg.getAddress().getDisrtict(), pg.getAddress().getState(),
							pg.getAddress().getCountry(), pg.getAddress().getPin());
					if (emptyAddressFieldsList == null || (emptyAddressFieldsList.isEmpty() == false)) {
						// there are some fields are empty
						throw new PgAddressCanNotBeEmptyException(emptyAddressFieldsList.toString());
					}

					/*
					 * //do not check for duplicate owner here //check for duplicate Pg call
					 * Pg-micorservice //requesting Pgservice for Pg to verify is this Pg already
					 * exist with Pgservice
					 * 
					 * Pg foundPg = restTemplate.postForObject("http://localhost:8082/pg/findPg",
					 * pg.getAddress(), Pg.class); //example // restTemplate.getForObject( //
					 * "http://example.com/hotels/{hotel}/rooms/{room}", String.class, "42", "21");
					 */
					String findPgUri = PG_SERVICE_URI + "findPg/houseNumber/street/disrtict/state/country/pin/"
							+ "{houseNumber}/{street}/{disrtict}/{state}/{country}/{pin}";
					Address pgAddress = pg.getAddress();
					Pg foundPg = restTemplate.getForObject(findPgUri, Pg.class, pgAddress.getHouseNumber(),
							pgAddress.getStreet(), pgAddress.getDisrtict(), pgAddress.getState(),
							pgAddress.getCountry(), pgAddress.getPin());
					// Pg foundPg =
					// restTemplate.getForObject("http://localhost:8082/pg/findPg",Pg.class,pg.getAddress());
					if (foundPg == null) {
						System.out.println("\n foundPg == null Pg doesnt exist , carry on \n");
						// Pg does not exist , carry on

						// add pg start date to pg

						pg.setPgStartedDate(new Date());

						// first create  owner then pg
						// ceraet pgowneben after pg has been created
//						Owner createdOwner = ownerService.createOwner(owner);
						owner = ownerService.createOwner(owner);
						if (owner != null && owner instanceof Owner)
						{
							System.out.println("\n createdOwner   ==  \n" + owner);

							//System.out.println("\nset owner for Pg owner.getOwnerId() = \n" + owner.getOwnerId());
							System.out.println("\n createdOwner.getOwnerId()) \n" + owner.getOwnerId());
			
							// set owner for pg
							pg.setMyOwner(owner.getOwnerId());


//							pg = restTemplate.postForObject("http://localhost:8082/pg/createPg", pg, Pg.class);
							
							
							
							
							 ServiceInstance serviceInstance = loadBalancer.choose("PgService");
								
								LOGGER.info("\n OC - createpg - serviceInstance  = \n"+serviceInstance);
								
								System.out.println(serviceInstance.getUri());
								
								LOGGER.info("\n OC - createpg - serviceInstance.getUri  = \n"+serviceInstance.getUri());
								
								
								
								
							
							pg = restTemplate.postForObject(PG_SERVICE_URI+"createPg", pg, Pg.class);

							System.out.println("\n after seting owner to createdPg = \n" + pg);
							// if pg created successfully then only proceed to create owner else stop
							if (pg != null && pg instanceof Pg) 
							{
								// yes pg creted successfully
								//System.out.println("\n createdPg by calling pg microservice  ==  \n" + createdPg);

								System.out.println("\nset pg for owner pg.getPgId() = \n" + pg.getPgId());
								
								// set pg for owner
								owner.setMyPg(pg.getPgId());
								//owner.setMyPG(pg.getPgId());
								// saveback owner

								Owner updatedOwner = ownerService.updateOwner(owner);
							
								 System.out.println("\n updatedOwner \n"+updatedOwner);

								 //no need to set to session coz we r logging out immedeatley after creating Pg
								// session.setAttribute("owner", updatedOwner);
								 //pg als
								// session.setAttribute("pg", pg);
								 
								 //log out
								    session.invalidate();
									String signOutStr = "Hello "+owner.getFirstName()+" You've successfully created ur Pg "
											+pg.getName()+ " & signed out .This completes registration... Pls login if u want to "
													+ "use our sevices, TQ";

									modelAndView.addObject("signOutMessage", signOutStr);
									modelAndView.setViewName("Welcome");
									return modelAndView;
								 
							}
							else
							{
								// either createdPg is null or its not instance of Pg
								throw new Exception("Could not create Pg for you as of now , try again");
							}
						} 
						else
						{
							// either createdowenr is null or its not instance of Owner
							throw new Exception("Couldn't create your account ,try again");

						}

					} else {
						System.out.println("\n duplicate Pg, stop here \n");

						// duplicate Pg, stop here
						Address address = pg.getAddress();
						String addressStr = address.getHouseNumber() + " " + address.getStreet() + " "
								+ address.getDisrtict() + " " + address.getState() + " " + address.getCountry() + " "
								+ address.getPin();

						String duplicatePgErrorMessageStr = "Hello " + owner.getFirstName() + ", some one has already "
								+ "constructed a Pg at same address = " + addressStr
								+ ", so pls enter your Pg's address";

						throw new DuplicatePgException(duplicatePgErrorMessageStr);
					}
				} else {
					System.out.println("\n couldnt read pg address, pg.getAddress() = " + pg.getAddress());
				}

				modelAndView.setViewName("OwnerHome");
				return modelAndView;
			} catch (PgAddressCanNotBeEmptyException e) {
				System.out.println("e.tostring = ");
				System.out.println(e.toString());
				String subStr = (e.toString()).substring(2, e.toString().length() - 1);
//	    				System.out.println("substr = ");
//	    				System.out.println(subStr);

				String[] sa1 = (e.toString()).split(":");
				// System.out.println("sa1 cintent");
				// System.out.println(Arrays.toString(sa1));
//	    				for(String s:sa1)
//	    				{
//	    					System.out.println("sa1 content");
//	    					System.out.println(s);
//	    				}
				String subS = sa1[1].substring(2, sa1[1].length() - 1);
				String[] sa = subS.split(" ");
				ArrayList<String> al = new ArrayList<>();
				for (String str : sa) {
					// System.out.println("sa content");
					// System.out.println(str);
					al.add(str);
				}

				if (al.contains("HOUSE-NUMBER")) {
					modelAndView.addObject("houseNumberErrorMessage", "HOUSE-NUMBER can not be empty");
				}
				if (al.contains("STREET")) {
					modelAndView.addObject("streetErrorMessage", "STREET can not be empty");
				}
				if (al.contains("DISTRICT")) {
					modelAndView.addObject("districtErrorMessage", "DISTRICT can not be empty");
				}
				if (al.contains("STATE")) {
					modelAndView.addObject("stateErrorMessage", "STATE can not be empty");
				}
				if (al.contains("COUNTRY")) {
					modelAndView.addObject("countryErrorMessage", "COUNTRY can not be empty");
				}
				if (al.contains("PIN")) {
					modelAndView.addObject("pinErrorMessage", "PIN can not be empty");
				}
				modelAndView.addObject("errorMessage", e.getLocalizedMessage());
				modelAndView.setViewName("CreatePg");
				return modelAndView;

			} catch (DuplicatePgException e) {
				modelAndView.addObject("duplicatePgErrorMessage", e.getLocalizedMessage());
				modelAndView.addObject("pg", pg);
				modelAndView.setViewName("CreatePg");
				return modelAndView;
			}
			// no need of generic exception if something failed to create Pg then it should
			// stop creating owner as well
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

	// logout
	// @DeleteMapping("/logOut")
	@GetMapping(value = "/logOut")
	public ModelAndView logOut(ModelAndView modelAndView, HttpSession session) {
		try {
			session.invalidate();
			String signOutStr = "You are signed out";

			modelAndView.addObject("signOutMessage", signOutStr);
			modelAndView.setViewName("Welcome");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n Ownercontroller logOut excetion = \n" + e.getLocalizedMessage());
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());

			modelAndView.setViewName("Error");

			return modelAndView;
		}
	}

	//// back to owenHome from viewpgdetaisl
	@GetMapping("/back")
	public ModelAndView backToHome(ModelAndView modelAndView,HttpSession session) {
		try {
			
//			Object object = session.getAttribute("owner");
//			
//			if(object != null && object instanceof Owner)
//			{
//				//back to owner home
//				LOGGER.info("OC-backToHome- yes its owner so returnng t ownerhome");
//				modelAndView.setViewName("OwnerHome");
//			}
//			else
//			{
//				LOGGER.info("OC-backToHome- yes its inmate so returnng t inmatehome");
//				modelAndView.setViewName("InMateHome");
//			}
			
			Object loggedInUser = whoIsLoggedInUser(session);
			
			if(loggedInUser == null)
			{
				throw new NullPointerException(Constants.SESSION_ENDED);
			}
			else if(loggedInUser instanceof Owner)
			{
				LOGGER.info("OC-backToHome- yes its owner so returnng t ownerhome");
				modelAndView.setViewName("OwnerHome");
			}
			else if(loggedInUser instanceof InMate)
			{
				LOGGER.info("OC-backToHome- yes its inmate so returnng t inmatehome");
				modelAndView.setViewName("InMateHome");
			}
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n Ownercontroller backToOwnerHome() excetion = \n" + e.getLocalizedMessage());
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());

			modelAndView.setViewName("Error");

			return modelAndView;
		}
	}

//	//back to InMate home
//	@GetMapping("/back")
//	public ModelAndView backToOwnerHome(ModelAndView modelAndView) {
//		try {
//			modelAndView.setViewName("OwnerHome");
//			return modelAndView;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("\n Ownercontroller backToOwnerHome() excetion = \n" + e.getLocalizedMessage());
//			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
//
//			modelAndView.setViewName("Error");
//
//			return modelAndView;
//		}
//	}
	// logIn
	@PostMapping("/logIn")
	public ModelAndView logIn(@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("password") String password, ModelAndView modelAndView, HttpSession session) {
		try {
			// check for validations
			if (phoneNumber == null || phoneNumber.trim().length() == 0) {
				throw new IllegalArgumentException("Phone number can not be empty");
			}
			if (password == null || password.trim().length() == 0) {
				// m.addAttribute("phoneNumber", phoneNumber);
				modelAndView.addObject("phoneNumber", phoneNumber);
				throw new IllegalArgumentException("Password is also required");
			}

			// try to find a owner with given credentials

			// Owner pgOwnerBean = pgOwnerService.findOwner(phoneNumber, password);

			List<Owner> ownersList = ownerService.findByPhoneNumberAndPassword(phoneNumber, password);
			if (ownersList != null && ownersList.size() > 0) 
			{
				System.out.println("\n ownersList != null && ownersList.size() > 0 , ownersList = \n" + ownersList);

				// ownersList.forEach(System.out::println);

				System.out.println("\n ownersList.get(0) = \n" + ownersList.get(0));
				Owner owner = ownersList.get(0);

				if (owner != null)
				{
					// GET respective Pg of owner being logged in using pgservice

					// Pg pg = new Pg();
//					long pgId = owner.getMyPg()
//					// PG_SERVICE_URI = http://localhost:8082/pg/
//					// "http://localhost:8080/employee/{id}";
//					Pg pg = restTemplate.getForObject(PG_SERVICE_URI + "findPgById/{pgId}", Pg.class, pgId);
					
					Pg pg = loadMyPg(owner.getMyPg());
					
					System.out.println("\n loaded pg from microservice pg = \n" + pg);

					if (pg != null) {
						// add pg to session
						session.setAttribute("pg", pg);
						// add owner to session
						session.setAttribute("owner", owner);

						modelAndView.setViewName("OwnerHome");

						//return modelAndView;
					} else {
						// pg cant be loaded
						System.out.println("\n culdnt load owenr's pg\n");
						String errorMessage = "Could Not Load Your Pg" + " Please try again after some time";
						throw new CouldNotLoadYourPgException(errorMessage);
					}
				} else {
					// owner == null
//	    					modelAndView.setViewName("OwnerHome");
//			    			return modelAndView ;
					//throw new Exception("we couldn't find your record, try again");
					
					//try for inmate login
					LOGGER.info("OC - login- owner == null");
				
				}
			} 
			else
			{
				
				//throw new InvalidCredentialsException("Incorrect Phone number OR/AND incorrect password");
				
				LOGGER.info("OC - login- trying to login as inmate \n\n");
				
				String searchInMateURL = INMATE_SERVICE_URI+"find/phoneNumber/{phoneNumber}/password/{password}";
				
				InMate inMate = restTemplate.getForObject(searchInMateURL,
						InMate.class, 
						phoneNumber,password);
				
				LOGGER.info("OC - login-  loaded inmate = \n\n"+inMate);
				
				if(inMate == null )
				{
					LOGGER.info("OC - login-  loaded inmate is null i.e  = \n\n"+inMate);
					throw new Exception("we couldn't find your record, try again");
				}
				else
				{
					LOGGER.info("OC - login-  loaded inmate is not null and i.e.= \n\n"+inMate);
					
					//load pg
					Pg pg = loadMyPg(inMate.getMyPg());
					
					if (pg != null) {
						// add pg to session
						session.setAttribute("pg", pg);
						// add owner to session
						session.setAttribute("inMate", inMate);

						//modelAndView.setViewName("OwnerHome");

						//return modelAndView;
						
						modelAndView.setViewName("InMateHome");
					} else {
						// pg cant be loaded
						System.out.println("\n culdnt load inmates pg\n");
						String errorMessage = "Could Not Load Your Pg" + " Please try again after some time";
						throw new CouldNotLoadYourPgException(errorMessage);
					}
					
					
					//login
					//session.setAttribute("inMate", inMate);
					
					//modelAndView.setViewName("InMateHome");
					
					LOGGER.info("OC - login-  \n");
					//return modelAndView;
				}
			}
			return modelAndView;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

			// m.addAttribute("loginErrorMessage",e.getLocalizedMessage());
			System.out.println(" IllegalArgumentException = " + e.getLocalizedMessage());
			modelAndView.addObject("loginErrorMessage", e.getLocalizedMessage());
			// return "Welcome";
			modelAndView.setViewName("Welcome");
			return modelAndView;
		} catch (InvalidCredentialsException e) {
			// .addAttribute("loginErrorMessage",e.getLocalizedMessage());
			// return "Welcome";
			e.printStackTrace();

			modelAndView.addObject("loginErrorMessage", e.getLocalizedMessage());

			modelAndView.setViewName("Welcome");

			return modelAndView;
		} catch (CouldNotLoadYourPgException e) {
			e.printStackTrace();
			System.out.println(" \n CouldNotLoadYourPgException = " + e.getLocalizedMessage());

			modelAndView.addObject("loginErrorMessage", e.getLocalizedMessage());

			modelAndView.setViewName("Welcome");

			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
			// return to welcome page
			
			String loginErrorMessageStr = "Sorry we could not allow you to login due to some reasons,i.e"
					+ e.getLocalizedMessage() + " printstacktrace = " + e.getMessage() + e.fillInStackTrace()
					+ e.getCause() + e.getClass();
			
			// m.addAttribute("loginErrorMessage",loginErrorMessageStr+" ,"+
			// e.getLocalizedMessage());

			// return "Welcome";
			modelAndView.addObject("loginErrorMessage", loginErrorMessageStr);
			// return "Welcome";
			modelAndView.setViewName("Welcome");
			return modelAndView;
		}
	}

	@GetMapping(value = "/show")
	public ModelAndView diplayAllOwners(ModelAndView modelAndView) {
		Iterable<Owner> ownersIterable = ownerService.findAllOwners();

		System.out.println("\nall owners are \n");
		ownersIterable.forEach(System.out::println);

		// modelAndView.addObject("owner",);
		modelAndView.setViewName("Welcome");
		return modelAndView;
	}

	// viewOwnerDetails
//	@GetMapping(value = "/viewOwnerDetails")
	@GetMapping(value = "/viewMyDetails")
	public ModelAndView viewMyDetails(ModelAndView modelAndView,HttpSession session) {
		try {
			//Object object = new Owner();
			
//			Object object = session.getAttribute("owner");
//			
//			//Object user = null;
//			if(object != null && object instanceof Owner)
//			{
//				LOGGER.info("\nOC -viewMyDetails - object is instanceof Owner \n");
//				//user = object ;
//				modelAndView.addObject("user", object);
//				//modelAndView.setViewName("ViewOwnerDetails");
//			}
//			else
//			{
//				LOGGER.info("\nOC -viewMyDetails - object is not instanceof Owner \n");
//				Object object2 = session.getAttribute("inMate");
//				
//				if(object2 != null && object2 instanceof InMate)
//				{
//					LOGGER.info("\nOC -viewMyDetails - object2 is instanceof inMate \n");
//					//user = object2;
//					modelAndView.addObject("user", object2);
//				}
//			}
		       Object loggedInUser = whoIsLoggedInUser(session);
		     
				if(loggedInUser instanceof Owner)
				{
					LOGGER.info("OC-viewMyDetails- yes loggedInUser instanceof Owner ");
					LOGGER.info("OC-viewMyDetails- so moving to ViewOwnerDetails \n\n");
					modelAndView.setViewName("ViewOwnerDetails");
				}
				else if(loggedInUser instanceof InMate)
				{
					LOGGER.info("OC-viewMyDetails- yes loggedInUser instanceof InMate");
					LOGGER.info("OC-viewMyDetails- so moving to ViewMyDetails\n\n ");
					modelAndView.setViewName("ViewInMateDetails");
				}
				else
				{
					throw new NullPointerException(Constants.SESSION_ENDED);
				}
		
			modelAndView.addObject("user", loggedInUser);
			
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("OwnerHome");
			return modelAndView;
		}
	}

	// openEditOwnerDetails view
	//@GetMapping(value = "/openEditOwnerDetails")
	@GetMapping(value = "/editMyDetails")
	public ModelAndView editMyDetails(ModelAndView modelAndView, HttpSession session) 
	{
		try {
			//Owner owner = (Owner) session.getAttribute("owner");
			LOGGER.info("OC- editMyDetails - try");
			Object loggedInUser = whoIsLoggedInUser(session);
			
			if(loggedInUser instanceof Owner)
			{
				LOGGER.info("OC- editMyDetails - loggedInUser instanceof Owner");
				LOGGER.info("so returning to EditOwnerDetails ");
				
				modelAndView.addObject("owner", loggedInUser);
				
				modelAndView.setViewName("EditOwnerDetails");
			}
			else if (loggedInUser instanceof InMate)
			{
				LOGGER.info("OC- updateInMate - loggedInUser instanceof InMate");
				LOGGER.info("so returning to EditMyDetails ");
				
				modelAndView.addObject("inMate", loggedInUser);
				
				modelAndView.setViewName("EditMyDetails");
			}
			else
			{
				LOGGER.info("OC- updateInMate - loggedInUser instanceof InMate");
			}
			
			
			
			return modelAndView;
		} catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.info("OC- editMyDetails - Exception e = \n "+e.getMessage());
			
			System.out.println("\n openEditOwnerDetails catch (Exception e) ()\n");

			modelAndView.setViewName("OwnerHome");
			return modelAndView;
		}
	}

	/*
	 * public ModelAndView registerOwner(@Valid Owner owner, BindingResult
	 * br,HttpSession hs,ModelAndView modelAndView)
	 */
	// updateOwner
	// PUT not supported in html fomr :( @PutMapping(value = "/updateOwner")
	// public ModelAndView updateOwner(@Valid Owner owner,BindingResult
	// br,ModelAndView modelAndView)
	@PostMapping(value = "/updateOwner")
	public ModelAndView updateOwner(@Valid Owner owner, BindingResult br,
			ModelAndView modelAndView, HttpSession session) {
		if (br.hasErrors()) {
			System.out.println("\n updateOwner br.hasErrors() with Invalid owner = \n" + owner);
			// return "EditOwnerDetails";
			modelAndView.setViewName("EditOwnerDetails");
			modelAndView.addObject("owner", owner);
			return modelAndView;

		} else {
			System.out.println("\n updateOwner br.passesd () with valid owner = \n" + owner);

			try {
				// System.out.println(" updateOwner() ownerId = \n"+ownerId);
				// Owner sessionOwner = (Owner) hs.getAttribute("owner");

				// pgOwnerService.update(pgOwnerBean);
				// set back to HS
				// hs.setAttribute("pgOwnerBean", pgOwnerBean);
				// String ownerDetailsUpdatedSuccesMessageStr = "Your details have been updated
				// successfully as follows";
				// m.addAttribute("ownerDetailsUpdatedSuccesMessage",
				// ownerDetailsUpdatedSuccesMessageStr);
				// return "ViewOwnerDetails";

				// System.out.println("\n sessionOwner.getOwnerId() =
				// \n"+sessionOwner.getOwnerId());
				// System.out.println("\nsessionOwner = \n"+sessionOwner);
				// set states which are not supposd to be edited
				// owner.setOwnerId(sessionOwner.getOwnerId());
				// owner.setPhoneNumber(phoneNumber);
				// owner.setFirstName("firstName");
				// owner.setPassword(sessionOwner.getPassword());
				// owner.setRepeatPassword(sessionOwner.getRepeatPassword());

				Owner updatedOwner = ownerService.updateOwner(owner);

				System.out.println("\n updatedOwner =\n" + updatedOwner);

				// set back to HS
				session.setAttribute("owner", updatedOwner);

				String ownerDetailsUpdatedSuccesMessageStr = "Your details have been updated successfully as follows";
				modelAndView.addObject("ownerDetailsUpdatedSuccesMessage", ownerDetailsUpdatedSuccesMessageStr);

				modelAndView.setViewName("ViewOwnerDetails");

				return modelAndView;
			} catch (Exception e) {
				System.out.println("\n updateOwner catch (Exception e)  ()\n");

				e.printStackTrace();
				System.out.println("\n e.getMessage() \n" + e.getMessage());
				System.out.println("\n e.getCause()() \n" + e.getCause());

				System.out.println("\n e.getClass()() \n" + e.getClass());

				System.out.println("\n e.getStackTrace()() \n" + e.getStackTrace());

				// m.addAttribute("errorMessage", e.getLocalizedMessage());
				modelAndView.addObject("errorMessage", e.getMessage());
				// return "Error";
				modelAndView.setViewName("ViewOwnerDetails");
				return modelAndView;
			}
		}
	}

	// viewPgDetails
	@GetMapping(value = "/viewPgDetails")
	public ModelAndView viewPgDetails(ModelAndView modelAndView) {
		try {
			// Owner pgOwnerBean = (Owner)hs.getAttribute("pgOwnerBean");
			// Pg pgBean = pgService.read(pgOwnerBean.getMyPg().getPgId());

			// m.addAttribute("pgBean", pgBean);

			// return "ViewPgDetails";
			modelAndView.setViewName("ViewOwnerPgDetails");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n viewPgDetails() exception e = \n" + e.getMessage());
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());

			// return "OwnerHome";

			modelAndView.setViewName("Error");
			return modelAndView;
		}
	}

	// openViewAndEditPgDetails
	@GetMapping(value = "/openEditPgDetails")
	public ModelAndView openViewAndEditPgDetails(ModelAndView modelAndView, HttpSession session) {
		try {
			// myPg
			Pg pg = (Pg) session.getAttribute("pg");

			System.out.println("\n session pg = \n" + pg);
			modelAndView.setViewName("EditPgDetails");
			modelAndView.addObject("pg", pg);
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("OwnerHome");
			return modelAndView;
		}
	}

	// updatePg
	// @RequestMapping(value="/updatePG",method=RequestMethod.POST)
	@PostMapping(value = "/updatePg")
	public ModelAndView updatePgDetails(@Valid Pg pg, BindingResult br,
			ModelAndView modelAndView,
			HttpSession session) {
		try {
			if (br.hasErrors()) {
				System.out.println("updatePGDetails () yes br had errors & invalid pg  = \n " + pg);
				System.out.println(br);
				/// return "EditPgDetails";

				modelAndView.setViewName("EditPgDetails");
				return modelAndView;
			} else {
				System.out.println("updatePGDetails () no b rdidnt have errors with valid pg = \n" + pg);

				Pg sessionPg = (Pg) session.getAttribute("pg");
				// set back pgstartedDate
				pg.setPgStartedDate(sessionPg.getPgStartedDate());

				// check for blank space in address

				// checking any field is empty for Address
				List<String> emptyAddressFieldsList = Address.addressValidation(pg.getAddress().getHouseNumber(),
						pg.getAddress().getStreet(), pg.getAddress().getDisrtict(), pg.getAddress().getState(),
						pg.getAddress().getCountry(), pg.getAddress().getPin());
				if (emptyAddressFieldsList == null || (emptyAddressFieldsList.isEmpty() == false)) {
					// there are some fields are empty
					System.out.println("\n emptyAddressFieldsList is empty so throwing \n");
					throw new PgAddressCanNotBeEmptyException(emptyAddressFieldsList.toString());
				}

				// compare session pg and baeing upadted for chanegs
				System.out.println("\n sessionPg = \n" + sessionPg);
				// take id of pg being updated
				// load pg fom db
//		    				Pg loadedPg = restTemplate.getForObject(PG_SERVICE_URI+"findPgById/{pgId}",
//		    						Pg.class,
//		    						pg.getPgId());
				// check wether he has changed address
				if (sessionPg.getAddress().equals(pg.getAddress())) {
					// addreess remains same
					System.out.println("\n addreess remains same \n");

					// check for othe fields i.e only name
					if (sessionPg.getName().equals(pg.getName())) {
						// no nothing he has changed
						// so no need to update to db
						// just forward controll to view
						System.out.println("\n  nothing he has changed just forward controll to view \n");

						String pgUpdatedSuccessMessageStr = "You have not changed anything,thanks for redusing our effort";
						modelAndView.setViewName("OwnerHome");
						modelAndView.addObject("successMessage", pgUpdatedSuccessMessageStr);

						modelAndView.setViewName("OwnerHome");

						return modelAndView;
					} else {
						// yes he changed pg name
						System.out.println("\nyes he changed pg name\n" + " \n");

						// so update to db
						restTemplate.put(PG_SERVICE_URI + "/updatePg", pg);

						// and session
						session.setAttribute("pg", pg);

						String pgUpdatedSuccessMessageStr = "Your Pg details have been updated successfully";
						modelAndView.addObject("successMessage", pgUpdatedSuccessMessageStr);
						modelAndView.setViewName("OwnerHome");

						return modelAndView;
					}
				} else {
					// address is changed
					System.out.println("\n  address is changed \n");

					// now check is address colloidng with existing for ducicatePg

					String houseNumber = pg.getAddress().getHouseNumber();
					String street = pg.getAddress().getStreet();
					String disrtict = pg.getAddress().getDisrtict();
					String state = pg.getAddress().getState();
					String country = pg.getAddress().getCountry();
					String pin = pg.getAddress().getPin();

					String loadPgUri = PG_SERVICE_URI + "findPg/houseNumber/street/disrtict/state/country/pin/"
							+ "{houseNumber}/{street}/{disrtict}/{state}/{country}/{pin}";

					 uriVariablesMap = new HashMap<String, String>();
					//= new HashMap<String, String>();
					System.out.println("\n updatePg urivariblemapap b4 adding pg address  varibales = \n"+uriVariablesMap);
					//uriVariablesMap.forEach(System.out::println);
					for(Entry<String, String>  e: uriVariablesMap.entrySet())
					{
						System.out.println("\n Entry e =\n"+e);
						System.out.println("e.getkey = "+e.getKey());
						System.out.println("e.getValue() = "+e.getValue());
					}
					 
					uriVariablesMap.put("houseNumber", houseNumber);
					uriVariablesMap.put("street", street);
					uriVariablesMap.put("disrtict", disrtict);
					uriVariablesMap.put("state", state);
					uriVariablesMap.put("country", country);
					uriVariablesMap.put("pin", pin);

					// restTemplate.getForObject(url, responseType, uriVariables)
					Pg newAddressLoadedPg = restTemplate.getForObject(loadPgUri, Pg.class, uriVariablesMap);

					if (newAddressLoadedPg != null && newAddressLoadedPg instanceof Pg) {
						// dupliactepg
						System.out.println("\n  address is changed but dupliactepg\n");

						Address address = pg.getAddress();
						String addressStr = address.getHouseNumber() + " " + address.getStreet() + " "
								+ address.getDisrtict() + " " + address.getState() + " " + address.getCountry() + " "
								+ address.getPin();
						String duplicatePgErrorMessageStr = "Hello , you'r trying to change your "
								+ "Pg 's address to the place = " + addressStr + ", which belongs to someone else";
						throw new DuplicatePgException(duplicatePgErrorMessageStr);
					} else {
						// unique pg so upadte it
						System.out.println("\n  address is changed & unique so uadting\n");

						// so update to db
						restTemplate.put(PG_SERVICE_URI + "/updatePg", pg);

						// and session
						session.setAttribute("pg", pg);

						String pgUpdatedSuccessMessageStr = "Your Pg details have been updated successfully";
						modelAndView.addObject("successMessage", pgUpdatedSuccessMessageStr);
						modelAndView.setViewName("OwnerHome");

						return modelAndView;
					}

				}

			}

		} catch (PgAddressCanNotBeEmptyException e) {
			e.printStackTrace();
			System.out.println("\n  PgAddressCanNotBeEmptyException \n" + e.getMessage());
			System.out.println(e.toString());
			String subStr = (e.toString()).substring(2, e.toString().length() - 1);

			String[] sa1 = (e.toString()).split(":");

			String subS = sa1[1].substring(2, sa1[1].length() - 1);
			String[] sa = subS.split(" ");
			ArrayList<String> al = new ArrayList<>();
			for (String str : sa) {
				al.add(str);
			}

			if (al.contains("HOUSE-NUMBER")) {
				modelAndView.addObject("houseNumberErrorMessage", "HOUSE-NUMBER can not be empty");
			}
			if (al.contains("STREET")) {
				modelAndView.addObject("streetErrorMessage", "STREET can not be empty");
			}
			if (al.contains("DISTRICT")) {
				modelAndView.addObject("districtErrorMessage", "DISTRICT can not be empty");
			}
			if (al.contains("STATE")) {
				modelAndView.addObject("stateErrorMessage", "STATE can not be empty");
			}
			if (al.contains("COUNTRY")) {
				modelAndView.addObject("countryErrorMessage", "COUNTRY can not be empty");
			}
			if (al.contains("PIN")) {
				modelAndView.addObject("pinErrorMessage", "PIN can not be empty");
			}
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			modelAndView.setViewName("EditPgDetails");
			return modelAndView;

		} catch (DuplicatePgException e) {
			// m.addAttribute("duplicatePgErrorMessage", e.getLocalizedMessage());
			e.printStackTrace();
			System.out.println("\n  updtedPg() DuplicatePgException \n" + e.getMessage());
			modelAndView.addObject("duplicatePgErrorMessage", e.getLocalizedMessage());

			modelAndView.addObject("pg", pg);
			// return "ViewAndEditPGDetails";
			modelAndView.setViewName("EditPgDetails");

			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n updtedPg() Exception \n" + e.getMessage());

			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			// return "Error";
			modelAndView.setViewName("OwnerHome");

			return modelAndView;

		}
	}
	
	///openAddARoom
	//@RequestMapping("/openAddARoom")
	@GetMapping(value = "/openAddARoom")
	public ModelAndView openAddARoom(ModelAndView modelAndView,HttpSession session)
	{
		try
		{
			System.out.println("in openAddARoom try");
			
			//System.out.println(pgOwnerBean.getMyPG());
			//m.addAttribute("roomBean", new Room());
			modelAndView.addObject("room", new Room());
			//return "AddRoom";
			
			modelAndView.setViewName("AddARoom");
			return modelAndView;
		}
	/*	catch(IllegalAccessOfPGDetailsPageException e)
		{
			hs.invalidate();
			m.addAttribute("errorMessage", e.getLocalizedMessage());
			return "Welcome";
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getMessage());
			//modelAndView.setViewName(ERROR_PATH);
			
			modelAndView.setViewName("Error");
			
			return modelAndView;
		}
	}
	
	
	//addARoom
	@PostMapping(value = "/addARoom")
	public ModelAndView addARoom(@Valid Room room,BindingResult br,
			ModelAndView modelAndView,HttpSession session)
	{
		//modelAndView.setViewName("Error");
		
		//return modelAndView; 
		if(br.hasErrors())
		{
			System.out.println("\n addARoom br has errors with Invallidd room = \n"+room);
			modelAndView.addObject("room", room);
			
			modelAndView.setViewName("AddARoom");
			
			return modelAndView ;
		}
		else
		{
			try
			{
				System.out.println("\n addARoom br paasessd with vallidd room = \n"+room);
				
				Pg pg = (Pg)session.getAttribute("pg");
				
				//check for duplicate room
				
//				String findRoomUri =ROOM_SERVICE_URI+"/findRoomByRoomNumberAndMyPg/"
//						+ "roomNumber/{roomNumber}/"
//						+ "myPg/{myPg}";
				
				//System.out.println("\n addARoom urivariblemapap b4 adding room varibales = \n"+uriVariablesMap);
				//uriVariablesMap.forEach(System.out::println);
//				for(Entry<String, String>  e: uriVariablesMap.entrySet())
//				{
//					System.out.println("\n Entry e =\n"+e);
//					System.out.println("e.getkey = "+e.getKey());
//					System.out.println("e.getValue() = "+e.getValue());
//				}
				//uriVariablesMap.put("roomNumber", room.getRoomNumber());
				
				//String.valueOf calls Long.toString() so Long.toString i faster
				//Long.toString(room.getRoomOfThePg());
				//uriVariablesMap.put("myPg",Long.toString(room.getMyPg()));
				
				//uriVariablesMap.put("myPg",Long.toString(pg.getPgId()));

				
				//String roomOfThePg = String.valueOf(room.getRoomOfThePg());
				
				//uriVariablesMap.put("roomOfThePg",));
//				restTemplate.getForObject(findRoomUri,
//						Room.class,
//						room.getRoomNumber(),room.getRoomOfThePg());
				
				
//				Room foundRoom = restTemplate.getForObject(findRoomUri,
//						Room.class,
//						uriVariablesMap);
				Room foundRoom = searchRoom(room.getRoomNumber(), room.getMyPg());
				if(foundRoom != null )
				{
					//dupliacate stop here
			    	throw new DuplicateRoomException();
				}
				else
				{
					//proccced its new room
					//not necessary it is set in addAroom.html ,set myPg to Room
					//room.setMyPg(pg.getPgId());
					//create room
					LOGGER.info("OC-addARoom- room not duplicate proceed");
					LOGGER.info("OC-addARoom- room = \n "+room);
					room = restTemplate.postForObject(ROOM_SERVICE_URI+"/createRoom", room, Room.class);
					
					LOGGER.info("OC-addARoom- posted room to room micro  = \n "+room);
					//add room id to Pg's collection and update pg
					
					
					boolean isRoomAdded = pg.addRoom(room.getRoomId());
					
					if(isRoomAdded)
					{
						LOGGER.info("OC-addARoom- yes isRoomAdded  = \n "+isRoomAdded);
						//update pg
						LOGGER.info("OC-addARoom- updateing pg  = \n "+isRoomAdded);
						restTemplate.put(PG_SERVICE_URI+"/updatePg", pg);
						
						String roomAdditionSuccessMessageStr = "Room with room Number = "+room.getRoomNumber()+" added to ur pg ";
						
						LOGGER.info("OC-addARoom-  pg updated, pgid= \n "+pg);
						//is it requeired to add pg back to session *
						//session.setAttribute("pg", pg);
						LOGGER.info("OC-addARoom- successMessage = \n "+roomAdditionSuccessMessageStr);
						modelAndView.addObject("successMessage", roomAdditionSuccessMessageStr);
						//return "OwnerHome";
						
						LOGGER.info("OC-addARoom- returning ownerhome  = \n ");
						modelAndView.setViewName("OwnerHome");
						
						return modelAndView ;
					}
					else
					{
						LOGGER.info("OC-addARoom- no  isRoomAdded  = \n "+isRoomAdded);
						throw new RuntimeException("Could not add room due to some internet issue..sorry :(");
					}
					
					
				}
				
				//String findRoomUri =
				//findRoomById/{roomId}
//				restTemplate.getForObject(ROOM_SERVICE_URI+"/findRoomById/{roomId}",
//						Room.class,
//						room.getRoomId());

	
			}
			catch(DuplicateRoomException e)
			{
				System.out.println("catch (DuplicateRoomException e)");
				LOGGER.error("\n addaroom - DuplicateRoomException = \n", e);
				String duplicateRoomStr = "U have forgotten that  "
						+"  room with "
						+ " "+room.getRoomNumber()+" already exist in ur Pg, ?"
								+ " so pls give different number for ur new room. ";
				
				modelAndView.addObject("errorMessage", duplicateRoomStr);
                
				modelAndView.setViewName("AddARoom");
				
				return modelAndView ;

			}
			catch(RuntimeException e)
			{
				System.out.println("catch (RuntimeException e)n e = "+e.getLocalizedMessage());
				e.printStackTrace();
				modelAndView.addObject("errorMessage", e.getLocalizedMessage());
               
				modelAndView.setViewName("AddARoom");
				
				return modelAndView ;

			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("\n addARoom Exception  e \n"+e.getLocalizedMessage());
				
				modelAndView.setViewName("AddARoom");
				
				return modelAndView ;

			}
		}
		
	}
	
	//viewRooms
	@GetMapping(value = "/viewRooms")
	public ModelAndView viewRooms(ModelAndView modelAndView,HttpSession session)
	{
		//Pg pg = (Pg)session.getAttribute("pg");
		
		
		//"findAllRoomsByPgId/{pgId}
		
//		List<Room> rooms = restTemplate.getForObject(ROOM_SERVICE_URI+"findAllRoomsByPgId/{pgId}",
//				List.class,
//				pg.getPgId());
		try
		{
			List<Room> rooms = loadAllRoomsOfAPg(session);
			/*
			 * if(rooms == null || rooms.isEmpty()) { String emptyPgMessageStr =
			 * "There are no rooms in your pg,please add ASAP to add InMates"; //
			 * m.addAttribute("emptyPGMessage", emptyPGMessageStr);
			 * modelAndView.addObject("emptyPgMessage", emptyPgMessageStr); }
			 */
			
			modelAndView.addObject("rooms",rooms);
			
			modelAndView.setViewName("ViewOwnerRooms");

			return modelAndView ;
		} 
		catch (EmptyPgException e)
		{
			e.printStackTrace();
			System.out.println("\n openaddInMate () Exception e \n");	
			
			String emptyPgMessageStr = "There are no rooms in your pg,please add ASAP to add InMates";
			//	m.addAttribute("emptyPGMessage", emptyPGMessageStr);
//				modelAndView.addObject("emptyPgMessage", emptyPgMessageStr);
			
			modelAndView.addObject("errorMessage", emptyPgMessageStr);
			
				modelAndView.setViewName("OwnerHome");
				
				//modelAndView.setViewName("ViewRooms");
			
				return modelAndView ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());

			//return "Error";
			
			modelAndView.setViewName("Error");
			return modelAndView ;
		}
		
		
	}
	
	

	//openEditRoomView
	@GetMapping(value="/openEditRoomView")
	public ModelAndView openEditRoomView(ModelAndView modelAndView,
			@RequestParam("roomId") long roomId)
	{
		try
		{
			//load Room
			//findRoomById/{roomId}
			System.out.println("\n openEditRoomView roomId = \n"+roomId);
		
			Room room = searchRoom(roomId);
			
			//send to editRoom view
			modelAndView.addObject("room", room);
			//return "EditRoom";
			modelAndView.setViewName("EditRoom");
			
			return modelAndView ;

			
			/*
			 * PGOwner pgOwnerBean = (PGOwner)hs.getAttribute("pgOwnerBean"); PG pgBean =
			 * pgOwnerBean.getMyPG() ; Set<Room> roomsSet = pgBean.getRooms(); Room roomBean
			 * = null ; for(Room r :roomsSet) { if(r.getRoomId() == roomId) { roomBean = r ;
			 * } } if(roomBean == null) { throw new
			 * RoomDoesNotExistExcepton("The room u r trying to edit no more exist"); } else
			 * { m.addAttribute("roomBean", roomBean);
			 * System.out.println("PGHC editRoom try "); return "EditRoom"; }
			 */
			
		}
//		catch(RoomDoesNotExistExcepton e)
//		{
//			m.addAttribute("errorMessage", e.getLocalizedMessage());
//			return "OwnerHome";
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			//m.addAttribute("errorMessage", e.getLocalizedMessage());
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());

			//return "Error";
			
			modelAndView.setViewName("Error");
			return modelAndView ;

		}
	}
	
	//updateRoom 
	@PostMapping(value="/updateRoom")
	public ModelAndView updateRoom(@Valid Room room,BindingResult result,
			ModelAndView modelAndView,HttpSession session)
	{
		try
		{
			if(result.hasErrors())
			{
				//return "EditRoom";
				System.out.println("-------------------------br falied  with invalid room = "+room);
				modelAndView.setViewName("EditRoom");
				return modelAndView ;
			}
			else
			{
				
				System.out.println("-------------------------br passed with valid room = "+room);
				//checck for duplicate room
				Room searchedRoom = searchRoom(room.getRoomId());
				
				if(searchedRoom.getRoomNumber().equals(room.getRoomNumber()) == false)
				{
					
					Room foundRoom = searchRoom(room.getRoomNumber(), room.getMyPg());
					
					if(foundRoom != null )
					{
						//dupliacate stop here
						
				    	throw new DuplicateRoomException();
					}

				}
				//chek valid numberof bdes
				int totalInMatesOfRoom = room.getRoomMates().size();
				if(room.getNumberOfBeds() < totalInMatesOfRoom)
				{
					String inMatesOverFlowErrorMessageStr = "You'r trying to reduce the number of beds to = "+
				     room.getNumberOfBeds()+" but there are still "+totalInMatesOfRoom+" InMates in that"
				     		+ " room ,would u like to let them stay on teres or corridor ?";
				     
					throw new InMatesOverFlowInARoomException(inMatesOverFlowErrorMessageStr);
				}
				//else
				//{
					//nuber of bedsa re in range
					//go on 
					
	           
					//boolean isRoomUpdateRequired = false;
					//passed room and existing in db room number matching
//					if(searchedRoom.getRoomNumber().equals(room.getRoomNumber()))
//					{
//						//yes room number has not changed
//						//go on
//					
//						isRoomUpdateRequired  = true;
//						
//					}
					//else
					//{
						//he has changed the room number
						//check is it colliding with any other of existing
						//
					
					
//						else
//						{
//							//updated room do not collid go on
//							isRoomUpdateRequired = true;
//							
//						}
						
//						if(isRoomUpdateRequired)
//						{
//							
//						}
//						else
//						{
//							String errorMessageStr =" Due to some issues we couldnt update ur room,sorry";
//							//however control never comes here
//							modelAndView.addObject("errorMessage", errorMessageStr);
//							modelAndView.setViewName("ViewRooms");
//							return modelAndView ;
//						}
					//}
						
				//}
				
				restTemplate.put(ROOM_SERVICE_URI+"updateRoom",
						room);
				
				
				String roomUpdatedSuccessMessageStr = "The room details have been updated to ur wish";
				
				modelAndView.addObject("successMessage", roomUpdatedSuccessMessageStr);
				modelAndView.setViewName("OwnerHome");
				return modelAndView ;
				
			}
		}
		catch(InMatesOverFlowInARoomException e)
		{
//			m.addAttribute("errorMessage", e.getLocalizedMessage());
//			return "EditRoom";
			
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			modelAndView.setViewName("EditRoom");
			return modelAndView ;
		}
//		catch(RoomDoesNotExistExcepton |CanNotRemoveTheRoomException e)
//		{
//			//String errorMessageStr = "The room u r trying to edit no more exist";
//			m.addAttribute("errorMessage", e.getLocalizedMessage());
//			return "OwnerHome";
//		}
		catch (DuplicateRoomException e)
		{
			String duplicateRoomStr = "You'r trying to change room number to ="
					+ " "+room.getRoomNumber()+" ,but unfortunatelly that room number already exist in ur Pg,"
							+ " so pls give different number or let it be same number,TQ";
			//m.addAttribute("errorMessage", duplicateRoomStr);
			//return "EditRoom";
			
			e.printStackTrace();
			modelAndView.addObject("errorMessage", duplicateRoomStr);
			modelAndView.setViewName("EditRoom");
			return modelAndView ;
		}
		catch(RuntimeException e)
		{
			//m.addAttribute("errorMessage", e.getLocalizedMessage());
			//return "EditRoom";
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			modelAndView.setViewName("EditRoom");
			return modelAndView ;
		}
		catch (Exception e)
		{
			//m.addAttribute("errorMessage", e.getLocalizedMessage());
			//return "Error";
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			modelAndView.setViewName("Error");
			return modelAndView ;
		}
	}
	
	//deleteRoom
	@GetMapping(value = "/deleteRoom")
	public ModelAndView openDeleteRoomView(ModelAndView modelAndView,
			@RequestParam("roomId") long roomId)
	{
		try 
		{
			System.out.println("\n  deleteRoom with roomid= \n"+roomId);
			Room room = searchRoom(roomId);
		
			modelAndView.addObject("room", room);
			
			modelAndView.setViewName("DeleteRoom");
			
			return modelAndView ;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			modelAndView.setViewName("Error");
			return modelAndView ;
		}
	}
	
	//deleteRoom
	//@DeleteMapping(value = "/deleteRoom")
	@PostMapping(value = "/deleteRoom")
	public ModelAndView deleteRoom(ModelAndView modelAndView,@RequestParam("roomId") long roomId)
	{
		try 
		{
			System.out.println("\n  deleteRoom with roomid= \n"+roomId);
			
			//check roomates are there 
			Room room = searchRoom(roomId);
			if(room != null)
			{
				if(room.getRoomMates().isEmpty())
				{
					//zero room mates 
					//go on
					restTemplate.delete(ROOM_SERVICE_URI+"deleteRoom/{roomId}", roomId);
					
					String roomRemovalSuccessMessageStr = "The room with room number = "
					+room.getRoomNumber()+" "
							+ " removed from ur Pg  ,Go to view rooms for confirmation";
					
					//m.addAttribute("roomRemovalSuccessMessage", roomRemovalSuccessMessageStr);
					modelAndView.addObject("successMessage", roomRemovalSuccessMessageStr);

					//return "OwnerHome";
					modelAndView.setViewName("OwnerHome");
					return modelAndView ;
				}
				else
					throw new CanNotRemoveTheRoomException();

			}
			else
				throw new RoomDoesNotExistExcepton("Room does not exist sorry to remove");
			
			

		}
		catch(CanNotRemoveTheRoomException e)
		{
			e.printStackTrace();
			String cantRemoveRoomErrorMessageStr = "You'r trying to remove room in which there is/are still few InMates"
					+ ",So we do not entertain you very sorry. "
					+ "If you still want to continue first give "
					+ "sendOff to those InMates of that room from PG";
			//m.addAttribute("errorMessage", cantRemoveRoomErrorMessageStr);
			//return "RemoveRoom";
			
			modelAndView.addObject("errorMessage", cantRemoveRoomErrorMessageStr);
			modelAndView.setViewName("DeleteRoom");
			return modelAndView ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			modelAndView.setViewName("Error");
			return modelAndView ;
		}
	}
	
	
	//viewInMates
	@GetMapping("/viewInMates")
	public ModelAndView viewInMates(ModelAndView modelAndView,HttpSession session)
	{
		try
		{
			System.out.println("\n viewInMates()\n");
			
			
				
			//call Inmate micro service
			List<InMate> inMates = loadAllInMatesOfAPg(session);
					
			//load all inmates 
			//check for empty collection
			if(inMates == null || inMates.isEmpty())
			{
				LOGGER.info("oc- viewInMates - There'r no InMates in Pg yet,please add ");
				String emptyRoomsMessageStr = "There'r no InMates in Pg yet,please add";
				modelAndView.addObject("emptyRoomsMessage",emptyRoomsMessageStr);
			}
			else
			{
				LOGGER.info("\n\noc- viewInMates - There'r  InMates & r = \n "+inMates);
				InMate TempinMate = inMates.get(0);
				LOGGER.info("\n\noc- viewInMates - There'r  InMates.get(0) & r = \n"+ inMates.get(0));
				//LOGGER.info("oc- viewInMates - There'r  InMates & r = \n "+TempinMate);
			}
			//add list to MNV and pass
			modelAndView.addObject("inMates", inMates);
			
			modelAndView.setViewName("ViewInMates");
			return modelAndView ;
		} 
		catch (EmptyPgException e)
		{
			//re-direct to ownerhome since there are no rooms at all in a pg no worth of forwarding to ViewInMates
			e.printStackTrace();
			System.out.println("\n viewInMates () Exception e \n");	
			
			String emptyPgMessageStr = "There are no rooms at all in your pg....so InMates cant hang in corridoor I hope";
			//	m.addAttribute("emptyPGMessage", emptyPGMessageStr);
				
			//modelAndView.addObject("emptyPgMessage", emptyPgMessageStr);
				
				modelAndView.addObject("errorMessage", emptyPgMessageStr);
				
			modelAndView.setViewName("OwnerHome");
				
				//modelAndView.setViewName("ViewInMates");
			
				return modelAndView ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());
			modelAndView.setViewName("Error");
			return modelAndView ;
		}
	}
	
	//addInMate
	//selectRoomToAddInMate
	@GetMapping(value = "/addInMate")
	public ModelAndView openAddInMate(ModelAndView modelAndView,HttpSession session)
	{
		try
		{
			System.out.println("\n openAddInMate()\n");

			//check is there atleast 1 room in pg then only allow 
		
			//Retrieve all rooms of that pg
			List<Room> rooms = loadAllRoomsOfAPg(session);
			
			/*
			 * if(rooms == null || rooms.isEmpty()) { throw new EmptyPgException();
			 * 
			 * }
			 */
			//pass rooms to view so that owner can choose which room should be allocated to this tenant
			//add rooms to view
			modelAndView.addObject("rooms",rooms);

			//no need to add inmate to satisfy model attribute in thymleaf bcs we r doing so in next screen
		//	modelAndView.addObject("inMate", new InMate());
			modelAndView.setViewName("AddInMate");
			return modelAndView ;


		} 
		catch (EmptyPgException e)
		{
			e.printStackTrace();
			System.out.println("\n openaddInMate () Exception e \n");	
			
			String emptyPgMessageStr = "There'r no rooms at all in ur Pg, so first add a room "
					+ " to add InMate";
			modelAndView.addObject("errorMessage",emptyPgMessageStr);
			//modelAndView.addObject("roomsSet", roomsSet);
			//return "OwnerHome";
			//modelAndView.setViewName("AddInMate");
			modelAndView.setViewName("OwnerHome");

			return modelAndView ;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("\n openaddInMate () Exception e \n");
            
			modelAndView.addObject("errorMessage", e.getMessage());
			
			modelAndView.setViewName("Error");
			
			return modelAndView ;

		}
	}
	
	//addInMate post
	
	@PostMapping(value = "/addInMate")
	public ModelAndView addInMate(@RequestParam long roomId, @RequestParam String roomNumber,
			ModelAndView modelAndView,HttpSession session)
	{
			//System.out.println("\n addInMate ()  br passed with valid inmate =  \n"+inMate);
		try 
		{
			System.out.println("\n addInMate ()  e \n");
           
            
			    InMate inMate = new InMate();
			    
			    session.setAttribute("roomId", roomId);
			    //keep roomid in session
			
			  // no need since we'r giving roomid to session 
			   // inMate.setMyRoom(roomId);
			  //password will be set in cretainmate as last step b4 saveinmat.setMyRoom(roomId); //set password
			 // inMate.setPassword(InMate.generatePassword());
			 
			    
            //keep roomNumber in session so that all api can access it & no need to pass to model evrytime
            //modelAndView.addObject("roomNumber", roomNumber);
			    session.setAttribute("roomNumber", roomNumber);

            //no  need to pass roomid since we have  set it  to inmate bean 
           // modelAndView.addObject("roomId", roomId);
           // InMate inMate =
            //add inmate 
            LOGGER.info("going to add inamte to model = "+inMate);
            //System.out.println("going to add inamte to model = "+inMate);
           modelAndView.addObject("inMate", inMate);
            //modelAndView.addObject("inmate",inmate);

			modelAndView.setViewName("AddInMate2");

			return modelAndView ;

		}
		catch (Exception e) 
		{
			   e.printStackTrace();
				modelAndView.addObject("errorMessage", e.getMessage());
				
			   modelAndView.setViewName("Error");
				
				return modelAndView ;
		}
	}
	
//	@PostMapping(value = "/createInMate")
//	public ModelAndView createInMate(@Valid InMate inMate,BindingResult result,
//			ModelAndView modelAndView,@RequestParam String roomNumber,HttpSession session)
	
	@PostMapping(value = "/createInMate")
	public ModelAndView createInMate(@Valid InMate inMate,BindingResult result,
			ModelAndView modelAndView,HttpSession session)
	{
		if(result.hasErrors())
		{
			System.out.println("\n createInMate br failed with inmate = \n "+inMate);
			
			//add back room number 
			// modelAndView.addObject("roomNumber", roomNumber);
			 
			 LOGGER.info("createInMate - br failed with inmate & added back same  = "+inMate);
			modelAndView.addObject("inMate",inMate);
			
			modelAndView.setViewName("AddInMate2");
			
			return modelAndView ;
		}
		else
		{
			try
			{
				System.out.println("\n createInMate  br passed with inmate = \n "+inMate);
				LOGGER.info("\n createInMate br passed with inmate = \n "+inMate);
				
				
				
				//check wether phonenumber clashes with owner number of same pg
				Owner owner = (Owner) session.getAttribute("owner");
				if(owner.getPhoneNumber().equals(inMate.getPhoneNumber()))
				{
					String errorMessage = "R U willing to become Tenant of ur own PG, The InMate u r trying to add is yourself"
							+ " please change his phone number if I am wrong";
					throw new DuplicateOwnerException(errorMessage);
				}
				//check whetether inmate is owner of any other pG
				
				boolean isOwnerOfAnotherPg = isDuplicateOwner(inMate.getPhoneNumber());
				
				if(isOwnerOfAnotherPg)
				{
					throw new DuplicateOwnerException("The InMate u r trying to add to ur PG is Owner of some other Pg ,"
							+ ".. so Cant sorry");
				}
				
				
				//check wether phonenumber clashes with any other inmate's phonenumber of the same PG
				
		
				
				Boolean isInMateExist = isDuplicateInMate(inMate.getPhoneNumber());
				
				if(isInMateExist == false)
				{
					//continue adding 
					//set password as last step b4 saving :) coz we have not set it in addinmate api
					 
					
					
					
					//inMate.setPassword(InMate.generatePassword()); 
					
					
					//change afterwards b4 production
					inMate.setPassword("1");
					
					
					
					
			           
					  //set roomid i.e myRoom to inmate
					  inMate.setMyRoom((Long) session.getAttribute("roomId"));
					  
			           System.out.println("\nb4 createInamte inmate  dosnt exist & after setting all states = \n"+inMate);
			           LOGGER.info("\nb4 createInamte inmate dosnt exist & after setting all states = \n"+inMate);
					//create inmate
					//call inmate micro service n crate inmate
					InMate createdInMate = restTemplate.postForObject(INMATE_SERVICE_URI+"create",
							inMate,
							InMate.class);
					
					System.out.println("\n OC created imate = \n"+createdInMate);
					
					LOGGER.info("\n OC created imate = \n"+createdInMate);
					
					//cant load room by getting roomid from inmate
					//Room room = searchRoom(inMate.getMyRoom())
					
					//load room by passin roomid from session
					Room room = searchRoom((long)session.getAttribute("roomId"));
					
					//add inmate to room's roommate collection
					room.addInMate(createdInMate.getInMateId());
					
					System.out.println("\n after adding inamte to room = \n"+room);
					
					LOGGER.info("\n after adding inamte to room = \n"+room);
					
					//update room
					//micro rvice
				    //restTemplate.put(ROOM_SERVICE_URI+"updateRoom", room)
				    
					//call updateroom 
					updateRoom(room);
				    
				    LOGGER.info("\n after resttemplate.put of ROOm , room = \n"+room);
					String successMessage =" InMate = "+inMate.getFirstName()+" has been placed"
							+ " in room number = ";
					
					modelAndView.addObject("successMessage", successMessage);

					//remove attributes from session
					session.removeAttribute("roomId");
					
					session.removeAttribute("roomNumber");
					
					//return "OwnerHome";
					modelAndView.setViewName("OwnerHome");
					
					return modelAndView ;
				}
				else
				{
					//stop by
					LOGGER.info("duplicate inmate = "+inMate);
					throw new DuplicateInMateException();
				}
				
			}
			catch(DuplicateOwnerException e)
			{
				//m.addAttribute("roomNumber", roomNumber);
				//m.addAttribute("errorMessage",e.getLocalizedMessage());
			//	return "AddInMate2";
				//add back room number 
				LOGGER.info("createInMate DuplicateOwnerException e = "+e.getMessage());
				
				//modelAndView.addObject("roomNumber", roomNumber);
				 
				modelAndView.addObject("errorMessage",e.getLocalizedMessage());
				 
				LOGGER.info("createInMate DuplicateOwnerException sending inamte  to AddInMate2 via model = "+inMate);
				
				modelAndView.addObject("inMate",inMate);
				
				
				System.out.println("\n after adding inmate to model inamte = \n\n\n "+inMate);
				
				modelAndView.setViewName("AddInMate2");

				return modelAndView ;
			}
			catch(DuplicateInMateException e)
			{
				e.printStackTrace();
				
				LOGGER.info("createInMate DuplicateInMateException e = "+e.getMessage());

				
				String errorMessage ="InMate with phonenumber "+inMate.getPhoneNumber()+"   already exists "
						+ "Please add  his right phone number";
				//m.addAttribute("roomNumber", roomNumber);
				
				// modelAndView.addObject("roomNumber", roomNumber);
				 
				//m.addAttribute("errorMessage",duplicateInMateErrorMessageStr);
				
				modelAndView.addObject("errorMessage",errorMessage);
				
				//return "AddInMate2";
				LOGGER.info("createInMate DuplicateInMateException sending inamte  to AddInMate2 via model = "+inMate);
				
				modelAndView.addObject("inMate",inMate);
				
				System.out.println("\n after adding inmate to model inamte = \n\n\n "+inMate);

				modelAndView.setViewName("AddInMate2");
				return modelAndView ;
			}
			catch (Exception e)
			{
				  e.printStackTrace();
					LOGGER.info("createInMate Exception e = "+e.getMessage());

					modelAndView.addObject("errorMessage", e.getMessage());
					
				   modelAndView.setViewName("Error");
					
					return modelAndView ;
			}
			finally
			{
				LOGGER.info("creatInmate finally not removing attributes from session " );
				
				System.out.println("\n creatInmate finally not removing attributes from session \n");
//				//remove attributes from session
//				session.removeAttribute("roomId");
//				
//				session.removeAttribute("roomNumber");
				
			}
		}
	}
	
	//editInMate  openEditInMate 
	@GetMapping(value = "editInMate")
	public ModelAndView openEditInMate(@RequestParam("inMateId") long inMateId,ModelAndView modelAndView,
			HttpSession session)
	{
		try
		{
			
			//load InMate 
			
					InMate inMate = searchInMate(inMateId);
			
			//add inmate to Model
			modelAndView.addObject("inMate", inMate);
			
			System.out.println("\n openEditInMate passing this inmate to edit = \n"+inMate);
			
			//add roomNumber to model
			//read Room from Room Micro-service
			
			Room room = searchRoom(inMate.getMyRoom());
			
			modelAndView.addObject("roomNumber", room.getRoomNumber());
			
			// no need session.setAttribute("roomNumber", room.getRoomNumber());
			
			modelAndView.setViewName("EditInMate");
			
			return modelAndView ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("openEditInMate Exception e = \n"+e.getLocalizedMessage());
			modelAndView.addObject("errorMessage", e.getMessage());
			
			modelAndView.setViewName("Error");
				
			return modelAndView ;
		}
	}
	
//	//updateInMate
//		@PostMapping(value = "/updateInMate")
//		public ModelAndView updateInMate(@Valid InMate inMate,BindingResult result,
//				ModelAndView modelAndView,
//				HttpSession session,
//				@RequestParam("roomNumber") String roomNumber)
//		{
//			System.out.println("oc updateInMate with inamte = \n\n"+inMate);
//			System.out.println("roomnumber = \n "+roomNumber);
//			modelAndView.setViewName("Error");
//			//"/find/phoneNumber/{phoneNumber}/pgId/{pgId}
//			String searchInMateURL = INMATE_SERVICE_URI+"/find/{phoneNumber}/{pgId}";
//			
//			InMate searchedInMate = restTemplate.getForObject(searchInMateURL,
//					InMate.class,
//					inMate.getPhoneNumber(),inMate.getMyPg());
//			
//			System.out.println("\n searchedinmate = \n"+searchedInMate);
//			
//			modelAndView.addObject("errorMessage", "succes from updateiNmate api test with inamte = "+inMate.toString());
//			
//			return modelAndView ;
//		}
	
	//updateInMate @RequestParam("roomNumber") String roomNumber
	@PostMapping(value = "/updateInMate")
	public ModelAndView updateInMate(@Valid InMate inMate,BindingResult result,
			ModelAndView modelAndView,
			HttpSession session,
			@RequestParam String roomNumber)
	{
		Object loggedInUser = whoIsLoggedInUser(session);
		if(result.hasErrors())
		{
			System.out.println("\n updateInMate BindingResult failed br = \n"+result);
			//System.out.println("PGHC -> updateInMate br has errors ");
			modelAndView.addObject("roomNumber",roomNumber);
			//return "EditInMate";
			
			
			
			if(loggedInUser instanceof Owner)
			{
				LOGGER.info("OC- updateInMate - loggedInUser instanceof Owner");
				LOGGER.info("so returning to EditInMate ");
				modelAndView.setViewName("EditInMate");
			}
			else if (loggedInUser instanceof InMate)
			{
				LOGGER.info("OC- updateInMate - loggedInUser instanceof InMate");
				LOGGER.info("so returning to EditMyDetails ");
				modelAndView.setViewName("EditMyDetails");
			}
			
			return modelAndView ;
		}
		else
		{
			System.out.println("\n  BindingResult  pzssed with inamte = \n"+inMate);
			try
			{
				System.out.println("\n updateInMate try \n");
				//no need since we'r not allowing to change phonenumber bcs its login id  ,Check for duplicate
				
				//no need since we'r not allowing to change phonenumber bcs its login id  ,check is it onwer's number
				//Owner owner = (Owner)session.getAttribute("owner");
				
				/*
				 * if(owner.getPhoneNumber().equals(inMate.getPhoneNumber())) { String
				 * errorMessage =inMate.getPhoneNumber()+" is your own phone number " +
				 * " please either change his phone number to other or keep the old one"; throw
				 * new DuplicateOwnerException(errorMessage); } //no need since we'r not
				 * allowing to change phonenumber bcs its login id, check is it any other
				 * Imate's phoneNumber //of same PG
				 * 
				 * InMate searchedInMate =
				 * restTemplate.getForObject(INMATE_SERVICE_URI+"find/{phoneNumber}/{pgId}",
				 * InMate.class, inMate.getPhoneNumber(),inMate.getMyPg());
				 *
				
				if(searchedInMate == null)
				{
					//its unique phone number so proceed
					
				}
				else
				{
					//duplicate Inmate
					throw new DuplicateInMateException();
				}*/
				System.out.println("going to update inmate = "+inMate);
				
				restTemplate.put(INMATE_SERVICE_URI+"update", inMate);
				
				
				if(loggedInUser instanceof Owner)
				{
					LOGGER.info("OC- updateInMate - loggedInUser instanceof Owner n updated inmate ");
					LOGGER.info("so returning to OwnerHome ");
					  modelAndView.addObject("successMessage","ur inMate's details successfully updated pls check");
						
						modelAndView.setViewName("OwnerHome");
				}
				else if (loggedInUser instanceof InMate)
				{
					LOGGER.info("OC- updateInMate - loggedInUser instanceof InMate n updated inmate ");
					LOGGER.info("so returning to inMateHome ");
					  modelAndView.addObject("successMessage","ur details successfully updated pls check");
						//adding loggedInUser to satisfy view
					  modelAndView.addObject("user", inMate);
					  
					  session.setAttribute("inMate", inMate);
						modelAndView.setViewName("ViewInMateDetails");
				}
				else
				{
					throw new NullPointerException(Constants.SESSION_ENDED);
				}
				
              
				
				return modelAndView ;
//				modelAndView.setViewName("OwnerHome");
//				return modelAndView ;
			}
			/*
			 * catch(DuplicateOwnerException e) { //m.addAttribute("roomNumber",
			 * roomNumber); modelAndView.addObject("roomNumber", roomNumber);
			 * //m.addAttribute("errorMessage",e.getLocalizedMessage());
			 * modelAndView.addObject("errorMessage",e.getLocalizedMessage()); //return
			 * "EditInMate"; modelAndView.setViewName("EditInMate");
			 * 
			 * return modelAndView; } catch(DuplicateInMateException e) { String
			 * errorMessage ="U r trying to change InMate's phone number = " +
			 * " "+inMate.getPhoneNumber()+" which belongs to other InMate of your own PG  "
			 * ;
			 * 
			 * //m.addAttribute("errorMessage",errorMessage);
			 * modelAndView.addObject("errorMessage", errorMessage); //return "EditInMate";
			 * modelAndView.setViewName("EditInMate");
			 * 
			 * return modelAndView; }
			 */
			catch (Exception e) 
			{
				
				System.out.println("\n updateInMate catch (Exception e) \n");
				e.printStackTrace();
				
				modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
			}
		}
		
	}
	

	@PostMapping(value = "/confirmDeleteInMate")
	public ModelAndView confirmDeleteInMate(@RequestParam long inMateId, ModelAndView modelAndView,
			@RequestParam String firstName ,
			@RequestParam String phoneNumber,
			HttpSession session,
			@RequestParam long myRoom)
	{
		try
		{
			System.out.println("\n confirmDeleteInMate try with inmate name ,phno ,inmateid =  \n"
		+firstName+" & "+phoneNumber+ " & "+inMateId);

			//adding roomid myRoom to session 
			session.setAttribute("roomId", myRoom);
			
			String confirmDeleteInMateMessage ="R U sure u wanna delete "+firstName+" "+
		     phoneNumber+" from ur PG permenently ?";
			
			modelAndView.addObject("confirmDeleteInMateMessage", confirmDeleteInMateMessage);
			
	           //add also inmateid back to model so that it can delete/cancel
			
			modelAndView.addObject("inMateId", inMateId);
			
			//add all stuffs which u have added to satisfy ViewInmates view
			List<InMate> inMates = loadAllInMatesOfAPg(session);
			
			//load all inmates 
			//check for empty collection * since we r deleting there must be a inate atleast
//			if(inMates.isEmpty())
//			{
//				String emptyRoomsMessageStr = "There'r no InMates in Pg yet,please add";
//				modelAndView.addObject("emptyRoomsMessage",emptyRoomsMessageStr);
//			}
		
			//add list to MNV and pass
			modelAndView.addObject("inMates", inMates);
			
			//modelAndView.setViewName("ViewInMates");
			//return modelAndView ;
						
		
			modelAndView.setViewName("ViewInMates");
			
			return modelAndView ;
			
		}
		catch (Exception e)
		{
			System.out.println("\n confirmDeleteInMate catch (Exception e) \n");
			e.printStackTrace();
			
			modelAndView.addObject("errorMessage", e.getMessage());
			
			modelAndView.setViewName("Error");
				
			return modelAndView ;
		}
	}
	
	//cancellDeleteInMate was not implemented since cancelling leads to show all available inamates
	// and hence redirected to ViewInmates from view itslef
	
	//deleteInMate
	@PostMapping(value = "/deleteInMate")
	public ModelAndView deleteInMate(@RequestParam long inMateId,ModelAndView modelAndView,
			HttpSession session)
	{
		try 
		{
			System.out.println("\n deleteInMate try with inametId = \n"+inMateId);
			
			//restTemplate.delete(ROOM_SERVICE_URI+"deleteRoom/{roomId}", roomId);
			
			//  /delete/{inMateId}
			
			
//			restTemplate.delete(INMATE_SERVICE_URI+"delete/{inMateId}",inMateId);
			
			//remove inmate from room
			LOGGER.info("\n \n remove inmate from room");
			
			long roomId = (long)session.getAttribute("roomId");
			LOGGER.info("\n got roomid from session = "+roomId);
			
			//search room to remove inmate from it
			Room vacatingRoom = searchRoom(roomId);
			
			boolean isInMateRemoved = vacatingRoom.removeInMate(inMateId);
			
			if(isInMateRemoved)
			{
				//yes removed 
				LOGGER.info("\n \n deleteInMate -  yes removed  inmate "+inMateId+" from room = "+roomId);
				
				//update room
				//restTemplate.put(ROOM_SERVICE_URI+"updateRoom", vacatingRoom)
				
				updateRoom(vacatingRoom);
				
				// ? is it possible to directly send request to room micro service to remove inmate
				//from the room by sending roomid & inmate id
				
				restTemplate.delete(INMATE_SERVICE_URI+"delete/"+inMateId);
				
				System.out.println("going to call viMewInMates api locally");
				//viewInMates(modelAndView,session);
				
				LOGGER.info("\nafter delete from inmatemicro servce\n");
				modelAndView.addObject("successMessage", "Ur unwanted inmate has been deleted from ur Pg successfully");
				
				
			}
			else
			{
				LOGGER.info("\nCould not remove  inmate = \n"+inMateId+" \n from room = "+roomId);
				
				String errorMessage = "Could not remove ur inmate as of now, pls try agian after sometime" ;
				modelAndView.addObject("errorMessage",errorMessage);
			}
			modelAndView.setViewName("OwnerHome");
			
			return modelAndView ;
			
			
		} 
		catch (Exception e) 
		{
			System.out.println("\n deleteInMate catch (Exception e) \n");
			e.printStackTrace();
			
			modelAndView.addObject("errorMessage", e.getMessage());
			
			modelAndView.setViewName("Error");
				
			return modelAndView ;
		}
	}
	
	/*
	 * //deleteInMate
	 * 
	 * @PostMapping(value = "/deleteInMate") public void deleteInMate(@RequestParam
	 * long inMateId,ModelAndView modelAndView, HttpSession session) { try {
	 * System.out.println("\n deleteInMate try with inametId = \n"+inMateId);
	 * 
	 * //restTemplate.delete(ROOM_SERVICE_URI+"deleteRoom/{roomId}", roomId);
	 * 
	 * // /delete/{inMateId}
	 * 
	 * 
	 * // restTemplate.delete(INMATE_SERVICE_URI+"delete/{inMateId}",inMateId);
	 * 
	 * restTemplate.delete(INMATE_SERVICE_URI+"delete/"+inMateId);
	 * System.out.println("going to call viMewInMates api locally");
	 * //viewInMates(modelAndView,session);
	 * 
	 * //modelAndView.addObject("successMessage",
	 * "Ur unwanted inmate has been deleted from ur Pg successfully");
	 * 
	 * //modelAndView.setViewName("OwnerHome");
	 * 
	 * //return modelAndView ; viewInMates(modelAndView, session); } catch
	 * (Exception e) { System.out.println("\n deleteInMate catch (Exception e) \n");
	 * e.printStackTrace();
	 * 
	 * //modelAndView.addObject("errorMessage", e.getMessage());
	 * 
	 * //modelAndView.setViewName("Error");
	 * 
	 * //return modelAndView ; } }
	 */
	
	
	//viewDues
	//@RequestMapping("/viewDues")
	@GetMapping(value = "/viewDues")
	public ModelAndView viewDues(ModelAndView modelAndView,HttpSession session)
	{
		//try this
		//u can just load all inmates n check calclualate thier total bill paid amount n compare with total
		// bill supposed to be paid
		//tht diff is due
		
		
		
		try
		{
			//PGOwner pgOwnerBean = (PGOwner)hs.getAttribute("pgOwnerBean");
			LOGGER.info("oc-viewdues-try");
			Owner owner = (Owner) session.getAttribute("owner");
			
			//check who all have due
			//load all inmtes of a pg
			
			//micor service call
			//List<InMate> inMatesList = inMateService.loadAllInMatesOfAPG(pgOwnerBean.getMyPG());
			
			
			
			
			
			
			List<InMate> inMatesList = loadAllInMatesOfAPg(session);
			
			
			/* test example 
			 * InMate tempinMate = new InMate(); tempinMate.setInMateId(420);
			 * tempinMate.setPhoneNumber("2"); tempinMate.setMyRoom(1L);
			 * tempinMate.setMyPg(1L); SimpleDateFormat sdf = new
			 * SimpleDateFormat("dd/MM/yyyy");
			 * 
			 * Date tempdate = sdf.parse("29/03/1998");
			 * 
			 * tempinMate.setDateOfJoining(tempdate); tempinMate.setFirstName("kamala");
			 * List<InMate> inMatesList = new ArrayList<InMate>();
			 * inMatesList.add(tempinMate) ;
			 */
			
			
			LOGGER.info("oc-viewdues-after loading inmatelist =\n "+inMatesList);
			
			//one map to hold all inmates dues with total months count and total dues so far
			LOGGER.info("oc-viewdues-b4 allInMatesDuesMap decalre =\n ");
			
			//Map<InMate, List<String>> allInMatesDuesMap = new TreeMap<InMate,List<String>>();
			
			Map<InMate, List<String>> allInMatesDuesMap = new HashMap<InMate, List<String>>();
			
			LOGGER.info("oc-viewdues-after allInMatesDuesMap decalre allInMatesDuesMap =\n ");
			
			Date today = new Date();
			
			String currentMonthStr = today.toString().substring(0,19);
			
			//System.out.println("curnmtStr = ");
			//System.out.println(currentMonthStr);
			//search for bill in each inmate
			for(InMate inMate:inMatesList)
			{
				System.out.println("\n\n FOR INMATE --------= ");
				LOGGER.info("oc-viewdues-FOR each INMATE --------=\n"+inMate);

				System.out.println(inMate.getFirstName());
				//check his joined date and no of bills available
				Date inMatesJoinedDate = inMate.getDateOfJoining();
				
				//calculate how many months he haas stayed in pg
		
				int totalMonths = Util.getTotalMonths(inMatesJoinedDate);
				System.out.println("toatal months = "+totalMonths);
				
				//chaeck his bills col
				
				//Set<Bill> inMateBillsSet = new LinkedHashSet<Bill>(inMate.getMyBills());

				LOGGER.info("oc-viewdues-b4 inMateBillsSet declare");
				
				
				
				
				
				Set<Long> inMateBillsSet = new LinkedHashSet<Long>(inMate.getMyBills());
				
				
				
				
				//Set<Long> inMateBillsSet = new LinkedHashSet<Long>();
				
				
				
				
				LOGGER.info("oc-viewdues- after inMateBillsSet declare");
				int duesMonthCount = 0;
				//check how many bills he has 
				//declare one list fro one inmate to hold duesmonths list
				List<String> oneInMatesDuesYearNMonthsList = null;
				if(inMateBillsSet.size() != totalMonths)
				{
					LOGGER.info("oc-viewdues- inside if(inMateBillsSet.size() != totalMonths)");
					duesMonthCount = totalMonths - inMateBillsSet.size() ;
					
					
					int thisYear = today.getYear();
					int thisMonth = today.getMonth();
					//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					//create those many dues
								
					 oneInMatesDuesYearNMonthsList = Util.calculateDuesMonthNYear(duesMonthCount);
					
				
				}
				if(duesMonthCount > 0)
				{
					System.out.println("\n yes there are dues since dumnth count > 0\n");
					LOGGER.info("\n yes there are dues since dumnth count > 0\n");
					//calculate his total dues amount
					//double oneMonthCost = inMate.getMyRoom().getCostPerBed();
					
					Room room = searchRoom(inMate.getMyRoom());
					
					double oneMonthCost = room.getCostPerBed() ;
							
					double totalCost = oneMonthCost * duesMonthCount ;
					
					oneInMatesDuesYearNMonthsList.add(totalCost+"");
					//allInMatesDuesMap.put(i, duesMonthCount+"-"+totalCost);
					
					allInMatesDuesMap.put(inMate,oneInMatesDuesYearNMonthsList);
					
					System.out.println("i, duesMonthCount+\"-\"+totalCost");
					LOGGER.info("i, duesMonthCount+\"-\"+totalCost");
					System.out.println(duesMonthCount+"-"+totalCost);
				}
								
			}
			if(allInMatesDuesMap.isEmpty())
			{
				
				String errorMessage = "There are no pending dues ";
				//m.addAttribute("noDuesAtAllMessage", noDuesAtAllMessageStr);
				//return "Dues";
				
				LOGGER.info("\n  no pending dues so returning to ownerhome with errror msg ");
				modelAndView.addObject("errorMessage", errorMessage);
				
				modelAndView.setViewName("OwnerHome");
					
				return modelAndView ;
			}
			else
			{
				//m.addAttribute("allInMatesDuesMap", allInMatesDuesMap);
				//return "Dues";
				LOGGER.info("\n  thr r  dues so shwoing them ");
				modelAndView.addObject("allInMatesDuesMap", allInMatesDuesMap);
				
				LOGGER.info("\n  thr r  dues - after adding  allInMatesDuesMap t model");
				
				modelAndView.setViewName("Dues");
				
				return modelAndView ;
			}
			
		} 
		catch (EmptyPgException e)
		{
			e.printStackTrace();
			System.out.println("\n viewdues () Exception e \n");	
			LOGGER.info("\n viewdues () Exception e \n"+e.getLocalizedMessage());	
			
			//TextUtil.
			String emptyPgMessageStr = "There are no rooms in your pg at all,..so no inmates , no dues...";
			//	m.addAttribute("emptyPGMessage", emptyPGMessageStr);
//				modelAndView.addObject("emptyPgMessage", emptyPgMessageStr);
			
			modelAndView.addObject("errorMessage", emptyPgMessageStr);
			
				modelAndView.setViewName("OwnerHome");
				
				//modelAndView.setViewName("ViewRooms");
			
				return modelAndView ;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			LOGGER.info("viewDues -exception - e = "+e.getMessage()+" - trace = \n "+e.getStackTrace().toString());
			
			//m.addAttribute("errorMessage",e.getLocalizedMessage());
			//return "Error";
			
			System.out.println("viewDues -exception - e = "+e.getMessage());
			
			
			modelAndView.addObject("errorMessage", e.getMessage());
			
			modelAndView.setViewName("Error");
				
			return modelAndView ;
		}
	}
	
	@GetMapping(value = "/UsingBillServiceviewDues")
	public ModelAndView viewDues2(ModelAndView modelAndView,HttpSession session)
	{
		try
		{
			LOGGER.info("viewDues try");
			
			//loadallinmates of this pg
			
			List<InMate> allInMatesOfAPgList = loadAllInMatesOfAPg(session);
			
			Map<InMate,List<Bill>> allInMatesDuesMap = new TreeMap<InMate, List<Bill>>();
			
			LOGGER.info("viewDues after allInMatesDuesMap declare ");
			//for each inmate load his all bills
			for(InMate inMate : allInMatesOfAPgList)
			{
				LOGGER.info("viewDues for each inamte = "+inMate);
				//load his bills via bill micro
				
				Bill [] billArray = restTemplate.getForObject(BILL_SERVICE_URI+"findAll/{myInMate}",
						Bill[].class,
						inMate.getInMateId()) ;
				
				
				LOGGER.info("billaray = \n \n "+Arrays.toString(billArray));
				List<Bill> list = Arrays.asList(billArray);
				
				if(list == null || list.isEmpty())
				{
					LOGGER.info("viewDues- after microcall to Bill ,list is null or list is empty  = "+list);
				}
				LOGGER.info("viewDues- after microcall to Bill list  = "+list);
						//add to map both key & value
				
				allInMatesDuesMap.put(inMate, list);

			}
			if(allInMatesDuesMap == null || allInMatesDuesMap.isEmpty())
				{
					
					String errorMessage = "There are no pending dues ";
					//m.addAttribute("noDuesAtAllMessage", noDuesAtAllMessageStr);
					//return "Dues";
					
					LOGGER.info("\n  no pending dues so returning to ownerhome with errror msg ");
					modelAndView.addObject("errorMessage", errorMessage);
					
					modelAndView.setViewName("OwnerHome");
						
					return modelAndView ;
				}
				else
				{
					//m.addAttribute("allInMatesDuesMap", allInMatesDuesMap);
					//return "Dues";
					LOGGER.info("\n  thr r  dues so shwoing them ");
					modelAndView.addObject("allInMatesDuesMap", allInMatesDuesMap);
					
					LOGGER.info("\n  thr r  dues - after adding  allInMatesDuesMap t model");
					
					modelAndView.setViewName("Dues");
					
					
					//modelAndView.setViewName("DuesTemp");
					//modelAndView.setViewName("Error");
					return modelAndView ;
				}
		
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.info("viewDues -exception - e = "+e.getMessage()+" - trace = \n "+e.getStackTrace().toString());
			
			//m.addAttribute("errorMessage",e.getLocalizedMessage());
			//return "Error";
			
			System.out.println("viewDues -exception - e = "+e.getMessage());
			
			
			modelAndView.addObject("errorMessage", e.getMessage());
			
			modelAndView.setViewName("Error");
				
			return modelAndView ;
		}
	}
	
	//openComplaintBox viewAllComplaints
	//@RequestMapping(value="/openComplaintBox",method=RequestMethod.GET)
//	@GetMapping(value = "/openComplaintBox")
	@GetMapping(value = "/viewAllComplaints")
	public ModelAndView viewAllComplaints(ModelAndView modelAndView,HttpSession session)
	{
		Object loggedInUser = whoIsLoggedInUser(session);
		
		try
		{
			if(loggedInUser == null) 
			{
				throw new SessionTimedOutException(Constants.SESSION_ENDED);
			}
			
			//Pg pg = (Pg)session.getAttribute("pg");
			
			LOGGER.info("oc -viewAllComplaints-try");
			System.out.println("PGHC openComplaintBox(Model m)");
			
			//load all complaints from db
			//List<Complaint> complaintsList = complaintService.loadAllComplaintsOfAPG(pgOwnerBean.getMyPG());
			
						
			//List<Complaint> pgAllComplaintsList = loadAllComplaintsOfPg(pg.getPgId());
			
			//List<Complaint> pgAllComplaintsList = loadAllComplaintsOfPg(session);
			
			//if no complaints 
		/*	if(pgAllComplaintsList == null || pgAllComplaintsList.isEmpty() )
			{
				
					System.out.println("\nPGHC opencomlBOx -> comlist size = 0");
					LOGGER.info("oc -viewAllComplaints-THERE R NO CMPLAINTS YET-comlist size = 0, ");
					String errorMessage = "There are no complaints in your Pg";
					
					//m.addAttribute("emptyComplaintBoxMessage", emptyComplaintBoxMessageStr);
					//return "ViewAllComplaintsOfPG";
					
					modelAndView.addObject("errorMessage", errorMessage);
					
					if(loggedInUser instanceof Owner)
					{
						LOGGER.info("OC-viewAllComplaints- yes loggedInUser instanceof Owner ");
						LOGGER.info("OC-viewAllComplaints- so moving to OwnerHome \n\n");
						modelAndView.setViewName("OwnerHome");
					}
					else if(loggedInUser instanceof InMate)
					{
						LOGGER.info("OC-viewAllComplaints- yes loggedInUser instanceof InMate");
						LOGGER.info("OC-viewAllComplaints- so moving to InMateHome\n\n ");
						modelAndView.setViewName("InMateHome");
					}
					else
					{
						throw new NullPointerException(Constants.SESSION_ENDED);
					}
					
					return modelAndView ;
			}*/
				//else
				//{
					System.out.println("\nPGHC opencomlBOx -> comlist size > 0");
					LOGGER.info("oc -viewAllComplaints-comlist size > 0");
					
					//call localapi 
					Map<Complaint, InMate> complaintedInMatesMap = makeUpReturnToViewAllComplaints(session);
					
					
					//Set<Complaint> complaintsSet = new LinkedHashSet<Complaint>(complaintsList);
					//int complaintSLNumber = 1;
					//m.addAttribute("complaintSLNumber", complaintSLNumber);
					//m.addAttribute("complaintsSet", complaintsSet);
					//return "ViewAllComplaintsOfPG";
					
					//modelAndView.addObject("complaintSLNumber", complaintSLNumber);
					
					//modelAndView.addObject("complaintsSet", complaintsSet);
					
//					if(loggedInUser == null )
//					{
//						//throw new NullPointerException(Constants.SESSION_ENDED);
//						throw new SessionTimedOutException(Constants.SESSION_ENDED);
//						
//					}
					//else
					//{
						//Map<InMate, Complaint> complaintedInMatesMap = loadComplaintedInMates(pgAllComplaintsList);
						
						//Map<Complaint, InMate> complaintedInMatesMap = loadComplaintedInMates(pgAllComplaintsList);
						 
						
						modelAndView.addObject("complaintedInMatesMap", complaintedInMatesMap);
						
						LOGGER.info("OC-viewALlComplaintBox- complaintedInMatesMap = \n "+complaintedInMatesMap);
						
						if(loggedInUser instanceof Owner)
						{
							LOGGER.info("OC-openComplaintBox- yes loggedInUser instanceof Owner ");
							LOGGER.info("OC-openComplaintBox- so moving to ViewOwnerAllComplaints \n\n");
							//modelAndView.setViewName("ViewOwnerAllComplaints");
							
							modelAndView.setViewName("OwnerViewPgAllComplaints");
						}
						else if(loggedInUser instanceof InMate)
						{
							LOGGER.info("OC-openComplaintBox- yes loggedInUser instanceof InMate");
							LOGGER.info("OC-openComplaintBox- so moving to InMateViewPgAllComplaints\n\n ");
							//modelAndView.setViewName("ViewInMateAllComplaints");
							
							modelAndView.setViewName("InMateViewPgAllComplaints");
						}
					//}
				
					return modelAndView ;
				//}
				
		}
		catch (SessionTimedOutException e)
		{
			e.printStackTrace();
			LOGGER.info("OC-viewAllComplaints- SessionTimedOutException\n so returning to login page ");
			
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("Welcome");
			
			return modelAndView ;
			
		}
		catch (ZeroComplaintsException e)
		{
			LOGGER.info("oc -viewAllComplaints-ZeroComplaintsInPgException -"
					+ "THERE R NO CMPLAINTS YET-comlist size = 0, ");
			String errorMessage = "There are no complaints in your Pg as of now";
					
			modelAndView.addObject("errorMessage", errorMessage);
			
			
			if(loggedInUser instanceof Owner)
			{
				LOGGER.info("OC-viewAllComplaints- yes loggedInUser instanceof Owner ");
				LOGGER.info("OC-viewAllComplaints- so moving to OwnerHome \n\n");
				
				modelAndView.setViewName("OwnerHome");
			}
			else if(loggedInUser instanceof InMate)
			{
				LOGGER.info("OC-viewAllComplaints- yes loggedInUser instanceof InMate");
				LOGGER.info("OC-viewAllComplaints- so moving to InMateHome\n\n ");
				modelAndView.setViewName("InMateHome");
			}
			
			return modelAndView;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			LOGGER.info("OC-viewallcomplaints- Exception - e = \n "+e.getLocalizedMessage());
			
	        modelAndView.addObject("errorMessage", e.getMessage());
			
			modelAndView.setViewName("Error");
				
			return modelAndView ;
		}
	}
	
	//INMATE FASILITIES
	
	//viewInMatePgDetails
	
	@GetMapping(value = "/viewInMatePgDetails")
	public ModelAndView viewInMatePgDetails(ModelAndView modelAndView)
	{
		try
		{
			LOGGER.info("OC-viewInMatePgDetails try");
			
            modelAndView.setViewName("viewInMatePgDetails");
			
			return modelAndView ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.info("OC-viewInMatePgDetails Exception e = \n  "+e.getLocalizedMessage());
			
			modelAndView.setViewName("Error");
			
			return modelAndView ;
			
		}
	}

	//viewRooms by inmate
	@GetMapping(value = "/viewPgRooms")
	public ModelAndView viewPgRooms(ModelAndView modelAndView,HttpSession session)
	{
	
		try
		{
			LOGGER.info("OC- viewPgRooms - try");
			
			List<Room> rooms = loadAllRoomsOfAPg(session);
		
			modelAndView.addObject("rooms",rooms);
			
			modelAndView.setViewName("ViewInMateAllRooms");

			return modelAndView ;
		} 
		catch (EmptyPgException e)
		{
			e.printStackTrace();
			
			LOGGER.info("OC- viewPgRooms - EmptyPgException e = \n "+e.getLocalizedMessage());
			
			System.out.println("\n openaddInMate () Exception e \n");	
			
			String errorMessage = "There are no rooms in your pg,please add ASAP to add InMates";

			
			modelAndView.addObject("errorMessage", errorMessage);
			
			modelAndView.setViewName("InMateHome");
						
			return modelAndView ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			LOGGER.info("OC- viewPgRooms - Exception e = \n "+e.getLocalizedMessage());
			
			modelAndView.addObject("errorMessage", e.getLocalizedMessage());

			//return "Error";
			
			modelAndView.setViewName("Error");
			return modelAndView ;
		}
	}
	
	    //viewInMateDues
		@GetMapping(value = "/viewInMateDues")
		public ModelAndView viewInMateDues(ModelAndView modelAndView,HttpSession session)
		{
			try
			{
				//PGOwner pgOwnerBean = (PGOwner)hs.getAttribute("pgOwnerBean");
				LOGGER.info("oc-viewInMateDues -try");
				Owner owner = (Owner) session.getAttribute("owner");
				
	
				//List<InMate> inMatesList = loadAllInMatesOfAPg(session);
				
				InMate inMate = (InMate) session.getAttribute("inMate");
				
				LOGGER.info("oc-viewInMateDues -after loading inmate from session =\n "+inMate);
				
				//one map to hold all inmates dues with total months count and total dues so far
				LOGGER.info("oc-viewInMateDues-b4 allInMatesDuesMap decalre =\n ");
				
				//Map<InMate, List<String>> allInMatesDuesMap = new TreeMap<InMate,List<String>>();
				
				Map<InMate, List<String>> allInMatesDuesMap = new HashMap<InMate, List<String>>();
				
				LOGGER.info("oc-viewInMateDues-after allInMatesDuesMap decalre allInMatesDuesMap =\n ");
				
				Date today = new Date();
				
				String currentMonthStr = today.toString().substring(0,19);
			
				//for(InMate inMate:inMatesList)
				//{
					System.out.println("\n\n FOR single INMATE --------= ");
					LOGGER.info("oc-viewInMateDues -FOR each INMATE --------=\n"+inMate);

					System.out.println(inMate.getFirstName());
					//check his joined date and no of bills available
					Date inMatesJoinedDate = inMate.getDateOfJoining();
					
					//calculate how many months he haas stayed in pg
			
					int totalMonths = Util.getTotalMonths(inMatesJoinedDate);
					System.out.println("toatal months = "+totalMonths);
					
					//chaeck his bills col
			

					LOGGER.info("oc-viewInMateDues -b4 inMateBillsSet declare");
					
					
					Set<Long> inMateBillsSet = new LinkedHashSet<Long>(inMate.getMyBills());
					
					LOGGER.info("oc-viewInMateDues- after inMateBillsSet declare");
					int duesMonthCount = 0;
					//check how many bills he has 
					//declare one list fro one inmate to hold duesmonths list
					List<String> oneInMatesDuesYearNMonthsList = null;
					if(inMateBillsSet.size() != totalMonths)
					{
						LOGGER.info("oc-viewInMateDues- inside if(inMateBillsSet.size() != totalMonths)");
						duesMonthCount = totalMonths - inMateBillsSet.size() ;
						
						
						int thisYear = today.getYear();
						int thisMonth = today.getMonth();
						//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						//create those many dues
									
						 oneInMatesDuesYearNMonthsList = Util.calculateDuesMonthNYear(duesMonthCount);
						
					
					}
					if(duesMonthCount > 0)
					{
						System.out.println("\n yes there are dues since dumnth count > 0\n");
						LOGGER.info("\noc -viewInMateDues - yes there are dues since dumnth count > 0\n");
						//calculate his total dues amount
						//double oneMonthCost = inMate.getMyRoom().getCostPerBed();
						
						Room room = searchRoom(inMate.getMyRoom());
						
						double oneMonthCost = room.getCostPerBed() ;
								
						double totalCost = oneMonthCost * duesMonthCount ;
						
						oneInMatesDuesYearNMonthsList.add(totalCost+"");
						//allInMatesDuesMap.put(i, duesMonthCount+"-"+totalCost);
						
						allInMatesDuesMap.put(inMate,oneInMatesDuesYearNMonthsList);
						
						System.out.println("i, duesMonthCount+\"-\"+totalCost");
						LOGGER.info(" oc -viewInMateDues - i, duesMonthCount+\"-\"+totalCost");
						System.out.println(duesMonthCount+"-"+totalCost);
					}
									
				//}
				if(allInMatesDuesMap.isEmpty())
				{
					
					String errorMessage = "There are no pending dues ";
									
					LOGGER.info("\n viewInMateDues no pending dues so returning to ownerhome with errror msg ");
					modelAndView.addObject("errorMessage", errorMessage);
					
					modelAndView.setViewName("InMateHome");
						
					return modelAndView ;
				}
				else
				{
				
					LOGGER.info("\n oc- viewInMateDues - thr r  dues so shwoing them ");
					
					modelAndView.addObject("allInMatesDuesMap", allInMatesDuesMap);
					
					LOGGER.info("\n oc- viewInMateDues thr r  dues - after adding  allInMatesDuesMap t model");
					
					modelAndView.addObject("today", new Date());
					modelAndView.setViewName("InMateDues");
					
					return modelAndView ;
				}
				
			} 
			catch (EmptyPgException e)
			{
				e.printStackTrace();
				System.out.println("\n viewdues () Exception e \n");	
				LOGGER.info("\n viewInMateDues () Exception e \n"+e.getLocalizedMessage());	
		
				String errorMessage = "There are no rooms in your pg at all,..so no inmates , no dues...";

				
				modelAndView.addObject("errorMessage", errorMessage);
				
					modelAndView.setViewName("InMateHome");
		
				
					return modelAndView ;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				LOGGER.info("viewInMateDues -exception - e = "+e.getMessage()+" - trace = \n "+e.getStackTrace().toString());
							
				System.out.println("viewDues -exception - e = "+e.getMessage());
								
				modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
			}
		}
		
		// /viewInMateRoom
		@GetMapping(value = "/viewInMateRoom")
		public ModelAndView viewInMateRoom(ModelAndView modelAndView,HttpSession session)
		{
			try
			{
				//load inmate's room
				InMate inMate = (InMate) session.getAttribute("inMate");
				
				LOGGER.info("oc- viewInMateRoom - try \n\n");
				
				Room room = searchRoom(inMate.getMyRoom());
				
				modelAndView.addObject("room", room);
				
				//modelAndView.addObject(attributeName, attributeValue)
				modelAndView.setViewName("ViewInMateRoom");
				
				return modelAndView ;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				LOGGER.info("oc- viewInMateRoom -exception - e = "+e.getMessage()+" - trace = \n "+e.getStackTrace().toString());
							
				System.out.println("oc -viewInMateRoom -exception - e = "+e.getMessage());
								
				modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
			}
		}
		
		
		//openInMateComplaintBox viewInMateComplaints
		@GetMapping(value = "/inMateViewMyComplaints")
		public ModelAndView inMateviewMyComplaints(ModelAndView modelAndView,HttpSession session)
		{
			try
			{
				
				InMate inMate = (InMate) session.getAttribute("inMate");
				
				LOGGER.info("oc -openInMateComplaintBox -try");
				
				System.out.println("oc openComplaintBox(Model m)");
				
				//local api to load all complaints of inmate  from db
			
				List<Complaint> complaintsList = loadMyAllComplaints(session);
				
//				String loadComplaintsURL = COMPLAINT_SERVICE_URI+"findAll/inMateId/{inMateId}"
//				
//				Complaint[] complaintsArray = restTemplate.getForObject(loadComplaintsURL,
//						Complaint[].class, 
//						inMate.getInMateId())
//				
//				
//				List<Complaint> complaintsList = Arrays.asList(complaintsArray)
				
				//if no complaints 
//				if(complaintsList == null || complaintsList.isEmpty() )
//				{
//					
//						System.out.println("\nPGHC opencomlBOx -> comlist size = 0");
//						LOGGER.info("oc -openInMateComplaintBox -comlist size = 0");
//						String errorMessage = "U haven't raised any complaints yet ";
//												
//						modelAndView.addObject("errorMessage", errorMessage);
//						
//						modelAndView.setViewName("InMateHome");
//						
//						return modelAndView ;
//				}
				//	else
					//{
						System.out.println("\nPGHC opencomlBOx -> comlist size > 0");
						LOGGER.info("oc -openInMateComplaintBox - 0 i.e .size() = \n"+complaintsList.size());
						LOGGER.info("\n\n oc -openInMateComplaintBox -comlist = "+complaintsList);
						
						
						modelAndView.addObject("complaintsList", complaintsList);
						
						//modelAndView.setViewName("ViewInMateComplaints");
						
						modelAndView.setViewName("InMateViewMyComplaints");
						
						//modelAndView.setViewName("ViewInMateAllComplaints");
						//LOGGER.info("oc -openInMateComplaintBox -complaintSLNumber= \n "+complaintSLNumber);
						
						return modelAndView ;
					//}
					
			}
			catch (ZeroComplaintsException e) 
			{
				LOGGER.info("oc -inmatevomycppmliants -ZeroComplaintsException = "+e.getLocalizedMessage());
				
				LOGGER.info("oc -inmatevomycppmliants -U haven't raised any complaints yet "
						+ "so rturing to InMateHome");
				
				String errorMessage = "U haven't raised any complaints yet ";
										
				modelAndView.addObject("errorMessage", errorMessage);
				
				modelAndView.setViewName("InMateHome");
				
				return modelAndView ;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				LOGGER.info("oc -openInMateComplaintBox -Exception e = \n "+e.getLocalizedMessage());
				
		        modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
			}
		}
		
		// /raiseAComplaint
		@GetMapping(value = "openRaiseAComplaintView")
		public ModelAndView openRaiseAComplaintView(ModelAndView modelAndView)
		{
			try
			{
				 LOGGER.info("oc -openraiseAComplaint- try");
				 
				 modelAndView.setViewName("RaiseAComplaint");
				 
				 Complaint complaint = new Complaint();
				
				 LOGGER.info("oc -openraiseAComplaint- added empty compaint to satisfy view , complant = \n "+complaint);
				
				 modelAndView.addObject("complaint", complaint);
				 
				 return modelAndView ;
				 
			} 
			catch (Exception e)
			{
				e.printStackTrace();
                
				LOGGER.info("oc -openraiseAComplaint -Exception e = \n "+e.getLocalizedMessage());
				
		        modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
			}
		}
		
		// raiseAComplaint
		@PostMapping(value ="/raiseAComplaint" )
		public ModelAndView raiseAComplaint(@Valid Complaint complaint,ModelAndView modelAndView,
				BindingResult result)
		{
			if(result.hasErrors())
			{
				LOGGER.info("oc -raiseAComplaint- result.failed with complaint =\n "+complaint);
				
				 modelAndView.addObject("complaint",complaint);
						 
				 modelAndView.setViewName("RaiseAComplaint");
				
				 return modelAndView ;
			}
			else
			{
				LOGGER.info("oc -raiseAComplaint- result passed with complaint =\n "+complaint);
				try
				{
					 LOGGER.info("oc -raiseAComplaint- try");
					 
					 //set all has a
					 complaint.setDate(new Date());
					 
					 LOGGER.info("oc -raiseAComplaint- b4 posting to micro Complaint = \n "+complaint);
					 
					 Complaint createdComplaint = restTemplate.postForObject(COMPLAINT_SERVICE_URI+"create",
							 complaint,
							 Complaint.class);
					 
					 LOGGER.info("oc -raiseAComplaint- after posting to micro createdComplaint = \n "+createdComplaint);
					 
					 if(createdComplaint != null && createdComplaint instanceof Complaint)
					 {
						 LOGGER.info("oc -raiseAComplaint- succesfull  = \n "+createdComplaint);
						 
						 modelAndView.addObject("successMessage", "ur complaint has been raised successfully pls verify");
						 
						 modelAndView.setViewName("InMateHome");
							
						 return modelAndView ;
					 }
					 else
					 {
						 throw new CanNotRaiseAComplaintException();
					 }
				}
				catch (CanNotRaiseAComplaintException e)
				{
                    e.printStackTrace();
	                
					LOGGER.info("oc -CanNotRaiseAComplaintException -Exception e = \n "+e.getLocalizedMessage());
					String errorMessage = "Sorry due to some reasons we colud not add ur complaint to Pg's complaint box"
							+ "try again after sometime ";
					  modelAndView.addObject("errorMessage", errorMessage);
						
						modelAndView.setViewName("InMateHome");
							
						return modelAndView ;
				}
				catch (Exception e)
				{
	                e.printStackTrace();
	                
					LOGGER.info("oc -raiseAComplaint -Exception e = \n "+e.getLocalizedMessage());
					
			        modelAndView.addObject("errorMessage", e.getMessage());
					
					modelAndView.setViewName("Error");
						
					return modelAndView ;
				}
			}
		
		}
		
	
		//openRespondToComplaintView
		@PostMapping(value = "/openRespondToComplaintView")
		public ModelAndView openRespondToComplaintView(ModelAndView modelAndView,
				@RequestParam long complaintId, HttpSession session)
		{
			try 
			{
				LOGGER.info("\n oc -openRespondToComplaintView recvd in oc -openRespondToComplaintView complaintId = \n "+complaintId);
				
				modelAndView.addObject("errorMessage", " recvd in oc -openRespondToComplaintView complaintId = \n "+complaintId);
				
				//String ownerResponse
				
				boolean isOwnerLikeToRespond = true;
				
				LOGGER.info("\n oc -openRespondToComplaintView  isOwnerLikeToRespond = \n "+isOwnerLikeToRespond);
				
				modelAndView.addObject("isOwnerLikeToRespond", isOwnerLikeToRespond) ;
				
				//add back complaintId to view so that they can fowrd to control to submit response
				
				modelAndView.addObject("complaintId", complaintId);
				
				//load everything what viewAllComplaints url loads actually
				
				
				
				
				//Pg pg = (Pg)session.getAttribute("pg");
				
				LOGGER.info("oc -openRespondToComplaintView load all complaints cal local api\n");
				
			
								
				//List<Complaint> pgAllComplaintsList = loadAllComplaintsOfPg(session)
				
				//Map<Complaint, InMate> complaintedInMatesMap = loadComplaintedInMates(pgAllComplaintsList)
				
				
				//call local api to make up to returnto viewallcomplaints
				Map<Complaint, InMate> complaintedInMatesMap = makeUpReturnToViewAllComplaints(session);
											
				
							 
				modelAndView.addObject("complaintedInMatesMap", complaintedInMatesMap);
				
				modelAndView.setViewName("OwnerViewPgAllComplaints");
				
				return modelAndView ;
			} 
			catch (Exception e) 
			{
				   e.printStackTrace();
	                
					LOGGER.info("oc -openRespondToComplaintView -Exception e = \n "+e.getLocalizedMessage());
					
			        modelAndView.addObject("errorMessage", e.getMessage());
					
					modelAndView.setViewName("Error");
						
					return modelAndView ;
			}
		}
		
		// respondToComplaint
		@PostMapping(value = "/respondToComplaint")
		public ModelAndView respondToComplaint(@RequestParam long complaintId, ModelAndView modelAndView,
				@RequestParam String responseMessage,HttpSession session)
		{
			try 
			{
				Pg pg = (Pg) session.getAttribute("pg");
				
				LOGGER.info("\n  oc -respondToComplaint complaintId = \n "+complaintId);
				
				LOGGER.info("\n  &  responseMessage = \n "+responseMessage);
				
				//load cmplaint & set responseMessage & setResponded & update
				Complaint complaint = searchComplaint(complaintId);
				
				complaint.setOwnersResponse(responseMessage);
				
				complaint.setResponded(true);
				
				//update complaint via micro complaint 
				
//				restTemplate.put(COMPLAINT_SERVICE_URI+"update",
//						complaint)
				
				//call local api
				updateComplaint(complaint);
				
				LOGGER.info("\n  oc -respondToComplaint complaintId");
				
				modelAndView.addObject("successMessage", "Ur response is sent to respective InMate,pls verify");
					
//				modelAndView.setViewName("OwnerHome");
					
				
				//load all complaints from db
				
				//List<Complaint> pgAllComplaintsList = loadAllComplaintsOfPg(session);	
											
				//Map<Complaint, InMate> complaintedInMatesMap = loadComplaintedInMates(pgAllComplaintsList);
				
				//loaclapi call
				Map<Complaint, InMate> complaintedInMatesMap = makeUpReturnToViewAllComplaints(session);
				
				modelAndView.addObject("complaintedInMatesMap", complaintedInMatesMap);
				
				modelAndView.setViewName("OwnerViewPgAllComplaints");
				
				
				return modelAndView ;
			} 
			catch (Exception e)
			{
				e.printStackTrace();
                
				LOGGER.info("oc -respondToComplaint -Exception e = \n "+e.getLocalizedMessage());
				
		        modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
				
			}
		}

		//setComplaintResolved
		//@PostMapping(value = "/setComplaintResolved")
		@PostMapping(value = "/toggleComplaintState")
		public ModelAndView toggleComplaintState(ModelAndView modelAndView,@RequestParam long complaintId,
				HttpSession session)
		{
			try
			{
				LOGGER.info("\n oc -toggleComplaintState complaintId - "+complaintId);
				
				Complaint complaint = searchComplaint(complaintId);
				
				//set it resolved
				if(complaint.isResolved())
				{
					LOGGER.info("\n oc -toggleComplaintState -state=\n"+complaint.isResolved());
					complaint.setResolved(false);
				}
				else
				{
					LOGGER.info("\n oc -toggleComplaintState -state=\n"+complaint.isResolved());
					complaint.setResolved(true);
				}
				
				LOGGER.info("\n oc -toggleComplaintState -state set to =\n"+complaint.isResolved());
				
				LOGGER.info("\n oc -toggleComplaintState updating complaint =- "+complaint);
				
				updateComplaint(complaint);
			
				LOGGER.info("\n oc -toggleComplaintState updated complaint");
				
                
				modelAndView.addObject("successMessage", Constants.COMPLAINT_STATUS_TOGGLED_MESSAGE);
				
                //load all complaints from db
				
				//List<Complaint> pgAllComplaintsList = loadAllComplaintsOfPg(session);	
											
				//Map<Complaint, InMate> complaintedInMatesMap = loadComplaintedInMates(pgAllComplaintsList);
				
				
				//call local api
				
				Map<Complaint, InMate> complaintedInMatesMap = makeUpReturnToViewAllComplaints(session);
				
				modelAndView.addObject("complaintedInMatesMap", complaintedInMatesMap);
				
				
				modelAndView.setViewName("InMateViewPgAllComplaints");
					
				LOGGER.info("\n oc -toggleComplaintState moving to InMateViewPgAllComplaints");
				return modelAndView ;
				
			} 
			catch (Exception e)
			{
                e.printStackTrace();
                
				LOGGER.info("oc -toggleComplaintState -Exception e = \n "+e.getLocalizedMessage());
				
		        modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
			}
		}
		
		//try to identify is it posible to discover for controll that from whoch view it recvd request
		//toggleComplaintState2
		
		@PostMapping(value = "/toggleComplaintState2")
		public ModelAndView toggleComplaintState2(ModelAndView modelAndView,@RequestParam long complaintId,
				HttpSession session)
		{
			try
			{
				LOGGER.info("\n oc -toggleComplaintState2 complaintId - "+complaintId);
				
				Complaint complaint = searchComplaint(complaintId);
				
				//set it resolved
				if(complaint.isResolved())
				{
					LOGGER.info("\n oc -toggleComplaintState2 -state=\n"+complaint.isResolved());
					complaint.setResolved(false);
				}
				else
				{
					LOGGER.info("\n oc -toggleComplaintState2 -state=\n"+complaint.isResolved());
					complaint.setResolved(true);
				}
				
				LOGGER.info("\n oc -toggleComplaintState2 -state set to =\n"+complaint.isResolved());
				
				LOGGER.info("\n oc -toggleComplaintState2 updating complaint =- "+complaint);
				
				updateComplaint(complaint);
			
				LOGGER.info("\n oc -toggleComplaintStat2e updated complaint  \n ");
				
                
				modelAndView.addObject("successMessage", Constants.COMPLAINT_STATUS_TOGGLED_MESSAGE);
				
                //load all complaints from db
							
				
				//call local api
							
				List<Complaint> complaintsList = loadMyAllComplaints(session);
				
				modelAndView.addObject("complaintsList", complaintsList);
				
				modelAndView.setViewName("InMateViewMyComplaints");
					
				LOGGER.info("\n oc -toggleComplaintState2 moving to InMateViewPgAllComplaints");
				return modelAndView ;
				
			} 
			catch (Exception e)
			{
                e.printStackTrace();
                
				LOGGER.info("oc -toggleComplaintState2 -Exception e = \n "+e.getLocalizedMessage());
				
		        modelAndView.addObject("errorMessage", e.getMessage());
				
				modelAndView.setViewName("Error");
					
				return modelAndView ;
			}
		}
		
		//closeComplaint
		//showCloseComplaintWindow
		//post pr get ? since complaintId is passed
		@GetMapping(value = "/showCloseComplaintWindow")
		public ModelAndView showCloseComplaintWindow(ModelAndView modelAndView,@RequestParam long complaintId,
				HttpSession session)
		{
			try
			{
				LOGGER.info("oc -showCloseComplaintWindow -try");
				
				
				
				
				//reset complaints view of inmate to help inmate to delete
	            List<Complaint> complaintsList = loadMyAllComplaints(session);
				
				
				modelAndView.addObject("complaintsList", complaintsList);
				
				//set isInMateLikeToDeleteComplaint true to help inmat to delete
				modelAndView.addObject("isInMateLikeToDeleteComplaint", true);
				
				//return back complaintid to help inmate to delete
				modelAndView.addObject("complaintId", complaintId);
				LOGGER.info("\n oc -showCloseComplaintWindow reset complaints view of inmate"
						+ " done to help inmate to delete\n");
				
				
				modelAndView.setViewName("InMateViewMyComplaints");
						
				return modelAndView ;
				
			}
			catch (Exception e) 
			{
		        e.printStackTrace();
	                
				LOGGER.info("oc  -showCloseComplaintWindow -Exception e = \n "+e.getLocalizedMessage());
					
			     modelAndView.addObject("errorMessage", e.getMessage());
					
				modelAndView.setViewName("Error");
						
				return modelAndView ;
			}
		}
		
		//closeComplaint
		@PostMapping(value = "/closeComplaint")
		public ModelAndView closeComplaint(ModelAndView modelAndView,@RequestParam long complaintId, 
				HttpSession session)
		{
			try
			{
				LOGGER.info("oc -closeComplaint -try");
				
				//delete complaint
			
				deleteComplaint(complaintId);
				
				List<Complaint> complaintsList = loadMyAllComplaints(session);
				
				//reset complaints view of inmate
				modelAndView.addObject("complaintsList", complaintsList);
				
				//add success messgae
				modelAndView.addObject("successMessage", Constants.COMPLAINT_DELETED_SUCCESS_MESSAGE);
				
				modelAndView.setViewName("InMateViewMyComplaints");
				
				LOGGER.info("\n reset complaints view of inmate moving to InMateViewMyComplaints\n");
				return modelAndView ;
				
			}
			catch (ZeroComplaintsException e) 
			{
				String errorMessage = "There are no more complaints available in your complaint BOX";
				
				modelAndView.addObject("errorMessage", errorMessage);
				
				modelAndView.setViewName("InMateHome");
				
				return modelAndView;
				
			}
			catch (Exception e) 
			{
			      e.printStackTrace();
	                
					//LOGGER.info("oc -closeComplaint -Exception e = \n "+e.getLocalizedMessage());
					
					LOGGER.error("\noc -closeComplaint -Exception = \n ", e);
				    
					modelAndView.addObject("errorMessage", e.getMessage());
						
					modelAndView.setViewName("Error");
							
					return modelAndView ;
			}
		}
		//showCloseComplaintWindow2
		@PostMapping(value = "/showCloseComplaintWindow2")
		public ModelAndView showCloseComplaintWindow2(ModelAndView modelAndView,@RequestParam long complaintId,
				HttpSession session)
		{
			try
			{
				LOGGER.info("oc -showCloseComplaintWindow2 -try");
				
				
				
				
				//reset complaints view of inmate to help inmate to delete
	         				
				Map<Complaint, InMate> complaintedInMatesMap = makeUpReturnToViewAllComplaints(session);
				
				modelAndView.addObject("complaintedInMatesMap", complaintedInMatesMap);
				
				
				//set isInMateLikeToDeleteComplaint true to help inmat to delete
				modelAndView.addObject("isInMateLikeToDeleteComplaint", true);
				
				//return back complaintid to help inmate to delete
				modelAndView.addObject("complaintId", complaintId);
				LOGGER.info("\n oc -showCloseComplaintWindow2 reset complaints view of inmate"
						+ " done to help inmate to delete\n");
				
				
				modelAndView.setViewName("InMateViewPgAllComplaints");
						
				return modelAndView ;
				
			}
			catch (Exception e) 
			{
		        e.printStackTrace();
	                
				//LOGGER.info("oc  -showCloseComplaintWindow2 -Exception e = \n "+e.getLocalizedMessage());
				
		        LOGGER.error("oc  -showCloseComplaintWindow2 -Exception e = \n ", e);
			     modelAndView.addObject("errorMessage", e.getMessage());
					
				modelAndView.setViewName("Error");
						
				return modelAndView ;
			}
		}
		
		         //closeComplaint
				@PostMapping(value = "/closeComplaint2")
				public ModelAndView closeComplaint2(ModelAndView modelAndView,@RequestParam long complaintId, 
						HttpSession session)
				{
					try
					{
						LOGGER.info("oc -closeComplaint -try");
						
						//delete complaint
					
						deleteComplaint(complaintId);
						
						
						//reset complaints view of inmate
						Map<Complaint, InMate> complaintedInMatesMap = makeUpReturnToViewAllComplaints(session);
						
						modelAndView.addObject("complaintedInMatesMap", complaintedInMatesMap);
						//add success messgae
						modelAndView.addObject("successMessage", Constants.COMPLAINT_DELETED_SUCCESS_MESSAGE);
						
						modelAndView.setViewName("InMateViewPgAllComplaints");
						
						LOGGER.info("\n reset complaints view of inmate moving to InMateViewMyComplaints\n");
						return modelAndView ;
						
					}
					catch (ZeroComplaintsException e) 
					{
						String errorMessage = "There are no more complaints available in your Pg";
						
						modelAndView.addObject("errorMessage", errorMessage);
						
						modelAndView.setViewName("InMateHome");
						
						return modelAndView;
						
					}
					catch (Exception e) 
					{
					      e.printStackTrace();
			                
							//LOGGER.info("oc -closeComplaint -Exception e = \n "+e.getLocalizedMessage());
							
							LOGGER.error("\noc -closeComplaint -Exception = \n ", e);
						    
							modelAndView.addObject("errorMessage", e.getMessage());
								
							modelAndView.setViewName("Error");
									
							return modelAndView ;
					}
				}
}//end of main

