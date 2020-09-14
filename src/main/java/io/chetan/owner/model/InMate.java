package io.chetan.owner.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;



public class InMate implements Comparable<InMate>
{

	private long inMateId ; 
	
	@NotEmpty(message="FirstName is mandatory")
	//@Pattern(regexp="^[A-Za-z]*$")
	@Pattern(regexp="(^[a-zA-Z]{3,16})",message="First Name should contain only letters & should be in the range 6 to 16")
	private String firstName;
	
	@Pattern(regexp="^[A-Za-z]*$",message="Last Name should contain only letters & should be in the range 6 to 16 and also cant add middle name here")
	private String lastName ;
	
	//@NumberFormat(pattern="##########")
	@NotEmpty(message="Phone number is mandatory")
	@Pattern(regexp="(^$|[0-9]{10})",message="Invalid Phone number")
	private String phoneNumber ;
	
	private String aadhaarNumber ;
	
	@Embedded
	private Address address;
	
	private String occupation ;
	
	@Email(message="M sure it's Not at all a email id")
	private String emailId ;
	
	
	@Past(message="You can not add a person who is not yet born")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dob ;
	
	@NotNull(message="Please add the date when the InMate has joined")
	@Past(message="Cant add InMate who is not yet joined")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dateOfJoining;
	
//	@OneToMany(mappedBy="billOfInMate",fetch=FetchType.EAGER)
//	private Set<Bill> myBills ;
	
	@ElementCollection
	private Set<Long> myBills ; 

	//	
//	@OneToMany(mappedBy="complaintRaisedBy",fetch=FetchType.EAGER)
//	private Set<Complaint> myComplaints ;
	
	@ElementCollection
	private Set<Long> myComplaints ;
	//@NotNull
	
//	@ManyToOne
//	private Room myRoom ;
	
	private Long myRoom ;
	
	//his pg
//	@ManyToOne
//	private PG myPG;
	
	private Long myPg;
	

	//@NotEmpty(message="If you do not wish to set password then how you will login ?")
    private String password;
    
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setInMateId(long inMateId) {
		this.inMateId = inMateId;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}



	//only arg constructor taking mandatory fields
	

	//arg constructir taking all inst var


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
		InMate other = (InMate) obj;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "InMate [inMateId=" + inMateId + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", aadhaarNumber=" + aadhaarNumber + ", address=" + address + ", occupation="
				+ occupation + ", emailId=" + emailId + ", dob=" + dob + ", dateOfJoining=" + dateOfJoining
				+ ", myBills=" + myBills + ", myComplaints=" + myComplaints + ", myRoom=" + myRoom + "]";
	}

	@Override
	public int compareTo(InMate inMate)
	{
	 return (this.phoneNumber.compareToIgnoreCase(inMate.getPhoneNumber()));	
	}
	
	//behaviours
	public boolean addABill(long billId)
	{
			if(this.myBills.add(billId) == false)
			{
				return false;
			}
			else
				return true;
				//throw new CanNotAddABillException("Could not add a Bill "+bill.getBillId()+" to InMate "+this.firstName);
	}
	
	public boolean removeABill(long billId)
	{
			if(this.myBills.remove(billId) == false)
			{
				return false ;
			}
			else
				return true ;
				//throw new CanNotRemoveTheBillException("Could not remove the Bill "+bill.getBillId()+" from InMate "+this.firstName);
	}
	
	public boolean addAComplaint(long complaintId)
	{
			if(this.myComplaints.add(complaintId) == false)
			{
             return false;				
			}
			else
				return true ;
			//	throw new CanNotAddAComplaintException("Could not add a complaint "+complaint.getComplaintId());
	}
	
	public boolean removeTheComlaint(Long complaintId)
	{
			if(this.myComplaints.remove(complaintId) == false)
			{
				return false ;
			}
			else
				return true ;
				//throw new CanNotRemoveTheComplaintException("Could not remove the complaint "+complaint.getComplaintId());
	}
	//non behaviours
	//paswrd genrtor
	public static String generatePassword()
	{
		double d =( Math.random()*1000000);
		System.out.println("Inmate  generatePassword() ");
		System.out.println("\n paswrd = "+d);
		long password = (long)d;
		System.out.println("\n paswrd lomng = "+password);
		return password+"";
	}

	public Set<Long> getMyBills() {
		return myBills;
	}

	public void setMyBills(Set<Long> myBills) {
		this.myBills = myBills;
	}

	public Set<Long> getMyComplaints() {
		return myComplaints;
	}

	public void setMyComplaints(Set<Long> myComplaints) {
		this.myComplaints = myComplaints;
	}

	public Long getMyRoom() {
		return myRoom;
	}

	public void setMyRoom(Long myRoom) {
		this.myRoom = myRoom;
	}

	public Long getMyPg() {
		return myPg;
	}

	public void setMyPg(Long myPg) {
		this.myPg = myPg;
	}
	
	
}
