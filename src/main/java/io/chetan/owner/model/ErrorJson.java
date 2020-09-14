package io.chetan.owner.model;

import java.util.Map;

public class ErrorJson 
{

	private int status ;
	
	private String error;
	
	private String message ;
	
	private String timeStamp;
	
	private String trace ;
	
	public ErrorJson (int status, Map<String, Object> errorAttributes)
	{
		System.out.println("\n ErrorJson() with status = \n"+status);
		this.status = status;
		
		this.error = (String)errorAttributes.get("error");
		

		if(status == 404)
		{
			//this.message = (String)errorAttributes.get("message");
			this.message = "The URL u r requesting is not available now. "+
			            "Our engineers are looking into it";
		}
		else
		{
			this.message = (String)errorAttributes.get("message");
		}
		this.timeStamp = (String)errorAttributes.get("timeStamp");
		this.trace = (String)errorAttributes.get("trace");
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	@Override
	public String toString() {
		return "ErrorJson [status=" + status + ", error=" + error + ", message=" + message + ", timeStamp=" + timeStamp
				+ ", trace=" + trace + "]";
	}
	
	
	
}
