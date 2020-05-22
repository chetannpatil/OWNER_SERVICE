package io.chetan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import io.chetan.model.ErrorJson;


/* 
 * To achieve the custom error logic for our spring boot application, developers must create 
 * a custom error controller that will implement the "ErrorController" interface. 
 * This interface provides a "getErrorPath()" method that developers will override to 
 * return a custom path that will be called when an error occurs.
 * 
 */
//@Controller
@RestController
public class CustomErrorcontroller implements ErrorController {

	@Value("${debug}")
	private boolean debug ;
	
	static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorcontroller.class);
	static final String PATH = "/error";

	@Autowired
	private ErrorAttributes errorAttributes;
	
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
	
	@GetMapping(value = PATH)
	public ErrorJson error(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("\n error() \n");
		
		ErrorJson errorJson = new ErrorJson(response.getStatus(), getAttributes(request,debug));
		
		System.out.println("errorjon recd = \n "+errorJson);
		return errorJson ;
	}
	
	private Map<String, Object> getAttributes(HttpServletRequest request, boolean includeStackTrace)
	{
		try 
		{
			System.out.println("\n getAttributes() with includestacktarce = \n"+includeStackTrace);
			
		//	ServletWebRequest extends -> webrequest exteds -> RequestAttributes
		//	RequestAttributes requestAttributes = new ServletRequestAttributes(request);
			
			RequestAttributes requestAttributes = new ServletWebRequest(request);
			
			WebRequest webRequest = null;
			if(requestAttributes instanceof WebRequest)
			{
				webRequest  = (WebRequest) requestAttributes ;
			}
			else
			{
				System.out.println("\n requestAttributes is not instanceof WebRequest\n");
			}
			
			//WebRequest webRequest2 = new ServletRequestAttributes(request);
			
			Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
			
			return errorAttributesMap ;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new HashMap<String, Object>();
		}

	}
	
//	
//	@GetMapping(value= PATH)
//	public String defaultErrorHandler() {
//		LOGGER.info("Showing the custom error page.");
//		return "404";		// In case of an error, this custom error page (404.html) will be rendered.
//	}

	/*
	 * This method returns different error pages based on the error type.
	 */

}
