package io.chetan.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//@Entity
public class Bill implements Comparable<Bill> {

	//@Id
	//@GeneratedValue
	private long billId;

	// @NotNull
	//@ManyToOne
	private InMate billOfInMate;

	// @NotNull
	private Date billOfMonth;

	private Date dateOfRentPaid;

	// @NotNull
	@Min(1)
	private Double amount;

	// @NotNull
	@Min(1)
	private Integer roomNumber;

	//@OneToOne
	//private Pg billOfPG;
	private Long billOfPG;
	
	
	
	public InMate getBillOfInMate() {
		return billOfInMate;
	}

	public void setBillOfInMate(InMate billOfInMate) {
		this.billOfInMate = billOfInMate;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + (int) (billId ^ (billId >>> 32));
		result = prime * result + ((billOfInMate == null) ? 0 : billOfInMate.hashCode());
		result = prime * result + ((billOfMonth == null) ? 0 : billOfMonth.hashCode());
		result = prime * result + ((dateOfRentPaid == null) ? 0 : dateOfRentPaid.hashCode());
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
		if (billOfInMate == null) {
			if (other.billOfInMate != null)
				return false;
		} else if (!billOfInMate.equals(other.billOfInMate))
			return false;
		if (billOfMonth == null) {
			if (other.billOfMonth != null)
				return false;
		} else if (!billOfMonth.equals(other.billOfMonth))
			return false;
		if (dateOfRentPaid == null) {
			if (other.dateOfRentPaid != null)
				return false;
		} else if (!dateOfRentPaid.equals(other.dateOfRentPaid))
			return false;
		if (roomNumber == null) {
			if (other.roomNumber != null)
				return false;
		} else if (!roomNumber.equals(other.roomNumber))
			return false;
		return true;
	}


	public InMate getBillOf() {
		return billOfInMate;
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", billOfInMate=" + billOfInMate + ", billOfMonth=" + billOfMonth
				+ ", dateOfRentPaid=" + dateOfRentPaid + ", amount=" + amount + ", roomNumber=" + roomNumber
				+ ", billOfPG=" + billOfPG + "]";
	}

	public void setBillOf(InMate billOf) {
		this.billOfInMate = billOf;
	}

	public Date getBillOfMonth() {
		return billOfMonth;
	}

	public void setBillOfMonth(Date billOfMonth) {
		this.billOfMonth = billOfMonth;
	}

	public Date getDateOfRentPaid() {
		return dateOfRentPaid;
	}

	public void setDateOfRentPaid(Date dateOfRentPaid) {
		this.dateOfRentPaid = dateOfRentPaid;
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

	public Long getBillOfPG() {
		return billOfPG;
	}

	public void setBillOfPG(Long billOfPG) {
		this.billOfPG = billOfPG;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	

}
