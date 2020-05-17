package io.chetan.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Owner 
{

	@Id
	@GeneratedValue
	private long ownerId;

	@NotEmpty(message="FirstName is mandatory")
	@Pattern(regexp="(^[a-zA-Z]{1,16})",message="First Name should contain only letters & can have maximum of 16 letters")
	private String firstName;
	
	@Pattern(regexp="(^[a-zA-Z]{0,16})",message="Name should contain only letters")
	private String lastName ;
	
	@Column(name = "phone")
	@NotEmpty(message="Phone number is mandatory")
//	@Pattern(regexp="(^[0-9]{10})",message="Invalid Phone number")
	private String phoneNumber ;
	
	private String aadhaarNumber ;
	
	private Address address;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Past(message="You can not become a owner since you are not yet born")
	private Date dob ;
		 
	@NotEmpty(message="If you do not wish to set password then how you will login in future?")
	private String password;
    
//	@NotEmpty(message="Cant you remember the password you just entered above ? Please enter same password.")
//	private transient String repeatPassword ;
	
	@NotEmpty(message="Cant you remember the password you just entered above ? Please enter same password.")
	private String repeatPassword ;
	
	@Email(message="Not at all a email id")
	private String emailId ;
   
	// @OneToOne(mappedBy="myOwner",fetch=FetchType.EAGER)
	//should not map from one microservc to another's entity
	//private Pg myPG ;
	
	private Long myPg ;
	
	//private long myPG ;
    
	
	
    //GETTERS & SETTERS
	
	
	public String getFirstName() {
		return firstName;
	}


	public long getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
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
		Owner other = (Owner) obj;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}


	public Owner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static boolean isPasswordMatchingRepeatPassword(String psw, String repPsw)
	{
		
		if(psw.trim().length() != repPsw.trim().length())
			return false ;
		if(psw.trim().equals(repPsw.trim()) == false)
			return false ;
		else
			return true ;
	}

	


	public Long getMyPg() {
		return myPg;
	}


	public void setMyPg(Long myPg) {
		this.myPg = myPg;
	}


	public String getRepeatPassword() {
		return repeatPassword;
	}


	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}


	@Override
	public String toString() {
		return "Owner [ownerId=" + ownerId + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", aadhaarNumber=" + aadhaarNumber + ", address=" + address + ", dob=" + dob
				+ ", password=" + password + ", repeatPassword=" + repeatPassword + ", emailId=" + emailId + ", myPg="
				+ myPg + "]";
	}


	
}
