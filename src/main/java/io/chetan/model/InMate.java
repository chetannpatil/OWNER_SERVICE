package io.chetan.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import io.chetan.exception.CanNotAddABillException;
import io.chetan.exception.CanNotAddAComplaintException;
import io.chetan.exception.CanNotRemoveTheBillException;
import io.chetan.exception.CanNotRemoveTheComplaintException;


public class InMate  implements Comparable<InMate> 
{

	
	
	//@Id
	//@GeneratedValue
	private long inMateId ; 
	
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
    
	@NotEmpty(message="Cant you remember the password you just entered above ? Please enter same password.")
	private transient String repeatPassword ;
	
	@Email(message="Not at all a email id")
	private String emailId ;
	
	private String occupation ;
	
	@NotNull(message="Please add the date when the InMate has joined")
	@Past(message="Cant add InMate who is not yet joined")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dateOfJoining;
	
	//@OneToMany(mappedBy="billOfInMate",fetch=FetchType.EAGER)
	//private Set<Bill> myBills ;
	private Set<Long> myBills ;
	
	//@OneToMany(mappedBy="complaintRaisedBy",fetch=FetchType.EAGER)
	private Set<Long> myComplaints ;
	
	//@NotNull
	//@ManyToOne
	//private Room myRoom ;
	private Long myRoom ;
	
	//his pg
	//@ManyToOne
	private Long myPG;
	



	public void setInMateId(long inMateId) {
		this.inMateId = inMateId;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	



	public String getFirstName() {
		return firstName;
	}

	

	

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public InMate() 
	{
		System.out.println("InMate()");
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



	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getInMateId() {
		return inMateId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
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


	@Override
	public int compareTo(InMate inMate)
	{
		return (this.phoneNumber.compareToIgnoreCase(inMate.getPhoneNumber()));	
	}
	
	//behaviours
	public void addABill(Bill bill)
	{
		if(bill != null)
		{
			if(this.myBills.add(bill.getBillId()) == false)
				throw new CanNotAddABillException("Could not add a Bill "+bill.getBillId()+" to InMate "+this.firstName);
		}
	}
	
	public void removeABill(Bill bill)
	{
		if(bill != null)
		{
			if(this.myBills.remove(bill.getBillId()) == false)
				throw new CanNotRemoveTheBillException("Could not remove the Bill "+bill.getBillId()+" from InMate "+this.firstName);
		}
	}
	
	public void addAComplaint(Complaint complaint)
	{
		if(complaint != null)
		{
			if(this.myComplaints.add(complaint.getComplaintId()) == false)
				throw new CanNotAddAComplaintException("Could not add a complaint "+complaint.getComplaintId());
		}
	}
	
	public void removeTheComlaint(Complaint complaint)
	{
		if(complaint != null)
		{
			if(this.myComplaints.remove(complaint.getComplaintId()) == false)
				throw new CanNotRemoveTheComplaintException("Could not remove the complaint "+complaint.getComplaintId());
		}
	}
	//non behaviours
	//paswrd genrtor
	public static String generatePassword()
	{
		//System.out.println(firs);
		double d =( Math.random()*1000000);
		System.out.println("Inmate  generatePassword() ");
		System.out.println("\n paswrd = "+d);
		long password = (long)d;
		System.out.println("\n paswrd lomng = "+password);
		return password+"";
	}
	
}
