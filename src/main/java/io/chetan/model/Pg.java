package io.chetan.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import io.chetan.exception.CanNotAddAComplaintException;
import io.chetan.exception.CanNotRemoveTheComplaintException;



public final class Pg implements Comparable<Pg>
{

	//@Id
	//@GeneratedValue
	private long pgId;
	
	@NotEmpty(message="why cant you name your PG?")
	private String name ;
	
	private Address address ;
	
	private int totalRooms;
	
	//@OneToMany(mappedBy="roomBelongsTo",fetch=FetchType.EAGER)
	private Set<Long> rooms ;
	
	//@OneToMany(mappedBy="complaintBelongsTo",fetch=FetchType.EAGER)
	private Set<Long> complaintBox;
	
	private Date pgStartedDate;
	
	//@OneToMany(fetch=FetchType.EAGER)
	private Set<Long> bills;
	
	//@OneToOne
	private Long myOwner ;
	
	public void setPgId(long pgId) {
		this.pgId = pgId;
	}


	public Date getPgStartedDate() {
		return pgStartedDate;
	}

	public void setPgStartedDate(Date pgStartedDate) {
		this.pgStartedDate = pgStartedDate;
	}


	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}

	@Override
	public String toString() {
		return "PG [pgId=" + pgId + ", name=" + name + ", address=" + address + ", totalRooms=" + totalRooms
				+ ", rooms=" + rooms + ", complaintBox=" + complaintBox + ", pgStartedDate=" + pgStartedDate
				+ ", myOwner=" + myOwner + "]";
	}

	//behaviours

	
	public boolean addRoom(Room room)
	{
		if(room != null)
		{
			if(this.rooms != null)
			{
				System.out.println("PG addrom ()rooms set is  not null");
				if(this.rooms.add(room.getRoomId()) == false)
				{
					System.out.println("PG addrom () Could not add the room "+room.getRoomNumber()+ " to PG");
					//throw new DuplicateRoomException("Could not add the room "+room.getRoomNumber()+ " to PG");
					return false ;
				}
				else
				{
					System.out.println("");
					System.out.println("PG addRoom() room = ");
					System.out.println(room);
					this.totalRooms = this.totalRooms+1;
					return true ;
				}
			}
			else
			{
				System.out.println("PG addrom ()rooms set is null");
				return false ;
			}
			
		}
		else
			return false;
	}

	public boolean removeRoom(Room room)
	{
		if(room != null)
		{
			if(this.rooms.remove(room.getRoomId()) == false)
			{
				//throw new CanNotRemoveTheRoomException("Could not remove the room "+room.getRoomNumber()+ " from PG");
				return false ;
			}
			else
			{
				System.out.println("");
				System.out.println("PG removeRoom() room = ");
				System.out.println(room);
				this.totalRooms = this.totalRooms - 1;
				return true;
			}
		}
		else
			return false;
	}
	
	
	public void addAComplaint(Complaint complaint)
	{
		if(complaint != null)
		{
			if(this.complaintBox.add(complaint.getComplaintId()) == false)
				throw new CanNotAddAComplaintException("Could not add a complaint "+complaint.getComplaintId());
		}
	}
	
	public void removeTheComlaint(Complaint complaint)
	{
		if(complaint != null)
		{
			if(this.complaintBox.remove(complaint.getComplaintId()) == false)
				throw new CanNotRemoveTheComplaintException("Could not remove the complaint "+complaint.getComplaintId());
		}
	}

	

	public Pg() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public long getPgId() {
		return pgId;
	}

	public int getTotalRooms() 
	{
		if(this.rooms != null)
		{
			return rooms.size();
		}
		else
			return this.totalRooms ;
	}


	@Override
	public int compareTo(Pg pg)
	{
		return ( (this.myOwner.toString()+this.address).compareToIgnoreCase(pg.myOwner.toString()+pg.address) );
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		Pg other = (Pg) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}


	public Set<Long> getRooms() {
		return rooms;
	}


	public void setRooms(Set<Long> rooms) {
		this.rooms = rooms;
	}


	public Set<Long> getComplaintBox() {
		return complaintBox;
	}


	public void setComplaintBox(Set<Long> complaintBox) {
		this.complaintBox = complaintBox;
	}


	public Set<Long> getBills() {
		return bills;
	}


	public void setBills(Set<Long> bills) {
		this.bills = bills;
	}


	public Long getMyOwner() {
		return myOwner;
	}


	public void setMyOwner(Long myOwner) {
		this.myOwner = myOwner;
	}

	
	
}
