package io.chetan.owner.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public abstract class User
{

	//^[a-zA-Z0-9]+$
	//@Pattern(regexp="(^[a-zA-Z]+$)",message="Name should contain only letters")
	//@NotEmpty(message="FirstName is mandatory")
	@NotNull(message="FirstName is mandatory")
	@Pattern(regexp="(^[a-zA-Z]{2,16})",message="First Name should contain only letters & should be in the range 2 to 16")
	protected String firstName;
	
	//@Pattern(regexp="(^[a-zA-Z]{6,16})",message="Name should contain only letters")
	protected String lastName ;
	
	//@NotEmpty(message="Phone number is mandatory")
	@NotNull(message="Phone number is mandatory")
	@Pattern(regexp="(^[0-9]{10})",message="Invalid Phone number")
	protected String phoneNumber ;
	
	protected String aadhaarNumber ;
	
	protected Address address;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Past(message="You can not become a owner since you are not yet born")
	protected Date dob ;
		 
	@NotNull(message="If you do not wish to set password then how you will login in future?")
	protected String password;
    
	@NotNull(message="Cant you remember the password you just entered above ? Please enter same password.")
	protected transient String repeatPassword ;
	
	@Email(message="Not at all a email id")
	protected String emailId ;

	/*
	 * public User(String lastName) { this.lastName = lastName; }
	 */

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}
	//non behavioral

	public static boolean isPasswordMatchingRepeatPassword(String psw, String repPsw)
	{
		
		if(psw.trim().length() != repPsw.trim().length())
			return false ;
		if(psw.trim().equals(repPsw.trim()) == false)
			return false ;
		else
			return true ;
	}
	
}
