package io.chetan.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class Complaint implements Comparable<Complaint>
{

	//@Id
	//@GeneratedValue
	private long complaintId ;
	
	@NotEmpty(message="If you do not know what you want to complaint then why are raising ?")
	@Size(min=10,max=300,message="The complaint can not be 300 letters or more & less than 10 letters")
	private String complaintMessage;
	
	private Date complaintRaisedDate;
	
	//@NotNull
	//@ManyToOne
	//private InMate complaintRaisedBy;
	private Long complaintRaisedBy;
	
	private boolean isComplaintResolved ;
	
	private boolean isOwnerResponded ;
	
	//@ManyToOne
	//private Pg complaintBelongsTo ;
	private Long complaintBelongsTo ;

	
	public long getComplaintId() 
	{
		return complaintId;
	}





	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((complaintMessage == null) ? 0 : complaintMessage.hashCode());
		result = prime * result + ((complaintRaisedBy == null) ? 0 : complaintRaisedBy.hashCode());
		result = prime * result + ((complaintRaisedDate == null) ? 0 : complaintRaisedDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complaint other = (Complaint) obj;
		if (complaintMessage == null) {
			if (other.complaintMessage != null)
				return false;
		} else if (!complaintMessage.equals(other.complaintMessage))
			return false;
		if (complaintRaisedBy == null) {
			if (other.complaintRaisedBy != null)
				return false;
		} else if (!complaintRaisedBy.equals(other.complaintRaisedBy))
			return false;
		if (complaintRaisedDate == null) {
			if (other.complaintRaisedDate != null)
				return false;
		} else if (!complaintRaisedDate.equals(other.complaintRaisedDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Complaint [complaintId=" + complaintId + ", complaintMessage=" + complaintMessage
				+ ", complaintRaisedDate=" + complaintRaisedDate + ", complaintRaisedBy=" + complaintRaisedBy
				+ ", isComplaintResolved=" + isComplaintResolved + ", isOwnerResponded=" + isOwnerResponded + "]";
	}

	public String getComplaintMessage() {
		return complaintMessage;
	}

	public void setComplaintMessage(String complaintMessage) {
		this.complaintMessage = complaintMessage;
	}

	public Date getComplaintRaisedDate() {
		return complaintRaisedDate;
	}

	public void setComplaintRaisedDate(Date complaintRaisedDate) {
		this.complaintRaisedDate = complaintRaisedDate;
	}

	public boolean isComplaintResolved() {
		return isComplaintResolved;
	}

	public void setComplaintResolved(boolean isComplaintResolved) {
		this.isComplaintResolved = isComplaintResolved;
	}


	public boolean isOwnerResponded() {
		return isOwnerResponded;
	}

	public void setOwnerResponded(boolean isOwnerResponded) {
		this.isOwnerResponded = isOwnerResponded;
	}

	@Override
	public int compareTo(Complaint complaint)
	{
		String c1 = this.complaintMessage+this.complaintRaisedDate+this.complaintRaisedBy;
		String c2 = complaint.complaintMessage+complaint.complaintRaisedDate+complaint.complaintRaisedBy;
		return( c1.compareToIgnoreCase(c2));
	}





	public Long getComplaintRaisedBy() {
		return complaintRaisedBy;
	}





	public void setComplaintRaisedBy(Long complaintRaisedBy) {
		this.complaintRaisedBy = complaintRaisedBy;
	}





	public Long getComplaintBelongsTo() {
		return complaintBelongsTo;
	}





	public void setComplaintBelongsTo(Long complaintBelongsTo) {
		this.complaintBelongsTo = complaintBelongsTo;
	}





	public void setComplaintId(long complaintId) {
		this.complaintId = complaintId;
	}


	
	
}
