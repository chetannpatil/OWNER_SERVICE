package io.chetan.owner.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

//@Entity
public class Complaint implements Comparable<Complaint>
{

	//@Id
	//@GeneratedValue
	private long complaintId ;
	
	@NotEmpty(message="If you do not know what you want to complaint then why are raising ?")
	@Size(min=10,max=300,message="The complaint description can not be 300 letters or more & less than 10 letters")
	private String description;
	
	//private Date complaintRaisedDate;
	private Date date;
	
	//@NotNull
	//@ManyToOne
	//private InMate complaintRaisedBy;
	
	private Long inMate;
	
	//@ManyToOne
	//private PG complaintBelongsTo ;
	
	private Long myPg;
	
	
	private boolean isResolved ;
	
	private boolean isResponded ;
	
	private String ownersResponse ;

	

	public long getComplaintId() 
	{
		return complaintId;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public Long getInMate() {
		return inMate;
	}



	public void setInMate(Long inMate) {
		this.inMate = inMate;
	}



	public String getOwnersResponse() {
		return ownersResponse;
	}



	public void setOwnersResponse(String ownersResponse) {
		this.ownersResponse = ownersResponse;
	}



	public Long getMyPg() {
		return myPg;
	}



	public void setMyPg(Long myPg) {
		this.myPg = myPg;
	}



	public boolean isResolved() {
		return isResolved;
	}



	public void setResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}



	public boolean isResponded() {
		return isResponded;
	}



	public void setResponded(boolean isResponded) {
		this.isResponded = isResponded;
	}



	public void setComplaintId(long complaintId) {
		this.complaintId = complaintId;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (complaintId ^ (complaintId >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((inMate == null) ? 0 : inMate.hashCode());
		result = prime * result + (isResolved ? 1231 : 1237);
		result = prime * result + (isResponded ? 1231 : 1237);
		result = prime * result + ((myPg == null) ? 0 : myPg.hashCode());
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
		Complaint other = (Complaint) obj;
		if (complaintId != other.complaintId)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (inMate == null) {
			if (other.inMate != null)
				return false;
		} else if (!inMate.equals(other.inMate))
			return false;
		if (isResolved != other.isResolved)
			return false;
		if (isResponded != other.isResponded)
			return false;
		if (myPg == null) {
			if (other.myPg != null)
				return false;
		} else if (!myPg.equals(other.myPg))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Complaint [complaintId=" + complaintId + ", description=" + description + ", date=" + date + ", inMate="
				+ inMate + ", myPg=" + myPg + ", isResolved=" + isResolved + ", isResponded=" + isResponded
				+ ", ownersResponse=" + ownersResponse + "]";
	}



	@Override
	public int compareTo(Complaint complaint)
	{
		String c1 = this.description+this.date+this.inMate;
		String c2 = complaint.description+complaint.date+complaint.inMate;
		return( c1.compareToIgnoreCase(c2));
	}


	
}
