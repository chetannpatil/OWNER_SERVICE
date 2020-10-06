package io.chetan.owner.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

//@Entity
public class Bill implements Comparable<Bill> {

	//@Id
	//@GeneratedValue
	private long billId;

	// @NotNull
	//@ManyToOne
	//private InMate billOfInMate;
	
	private Long myInMate;

	// @NotNull
	private Date month;

	//due to should be added?
	
	//rent paid date
	private Date paidDate;

	// @NotNull
	@Min(1)
	private Double amount;

	// @NotNull
	@Min(1)
	private Integer roomNumber;

	//@OneToOne
	//private Pg billOfPG;
	//private Long billOfPG;
	
	private Long myPg;
	
	
	

	


	public Long getMyInMate() {
		return myInMate;
	}

	public void setMyInMate(Long myInMate) {
		this.myInMate = myInMate;
	}

	public Long getMyPg() {
		return myPg;
	}

	public void setMyPg(Long myPg) {
		this.myPg = myPg;
	}



	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public long getBillId() {
		return billId;
	}

	@Override
	public int compareTo(Bill bill) {
		return (this.toString().compareToIgnoreCase(bill.toString()));
	}



	public void setBillId(long billId) {
		this.billId = billId;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + (int) (billId ^ (billId >>> 32));
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((myInMate == null) ? 0 : myInMate.hashCode());
		result = prime * result + ((myPg == null) ? 0 : myPg.hashCode());
		result = prime * result + ((paidDate == null) ? 0 : paidDate.hashCode());
		result = prime * result + ((roomNumber == null) ? 0 : roomNumber.hashCode());
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
		Bill other = (Bill) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (billId != other.billId)
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (myInMate == null) {
			if (other.myInMate != null)
				return false;
		} else if (!myInMate.equals(other.myInMate))
			return false;
		if (myPg == null) {
			if (other.myPg != null)
				return false;
		} else if (!myPg.equals(other.myPg))
			return false;
		if (paidDate == null) {
			if (other.paidDate != null)
				return false;
		} else if (!paidDate.equals(other.paidDate))
			return false;
		if (roomNumber == null) {
			if (other.roomNumber != null)
				return false;
		} else if (!roomNumber.equals(other.roomNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", myInMate=" + myInMate + ", month=" + month + ", paidDate=" + paidDate
				+ ", amount=" + amount + ", roomNumber=" + roomNumber + ", myPg=" + myPg + "]";
	}

	

}
