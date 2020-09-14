package io.chetan.owner.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;


//@Entity
@Embeddable
public class Address 
{

	//@Id
	//@GeneratedValue
	private long addresId;
	
	//@NotEmpty
	@Pattern(regexp="(^[0-9]{0,20})",message="should contain only numerics")
	private String houseNumber;
	
	//@NotEmpty
	private String street ;
	
	//@NotEmpty
	private String disrtict ;
	
	//@NotEmpty
	private String state;
	
	//@Column(in)
	//@NotEmpty
	private String country ;
	
	//@NotEmpty
	private String pin;


	
	public long getAddresId() {
		return addresId;
	}

	public Address(long addresId, String houseNumber, String street, String disrtict, String state, String country,
			String pin)
	{
		super();
		this.addresId = addresId;
		this.houseNumber = houseNumber;
		this.street = street;
		this.disrtict = disrtict;
		this.state = state;
		this.country = country;
		this.pin = pin;
	}

	public Address() 
	{
		super();
	}

	public Address(String houseNumber, String street, String disrtict, String state, String country, String pin)
	{
		super();
		this.houseNumber = houseNumber;
		this.street = street;
		this.disrtict = disrtict;
		this.state = state;
		this.country = country;
		this.pin = pin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((disrtict == null) ? 0 : disrtict.hashCode());
		result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
		result = prime * result + ((pin == null) ? 0 : pin.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
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
		Address other = (Address) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.trim().equalsIgnoreCase(other.country.trim()))
			return false;
		if (disrtict == null) {
			if (other.disrtict != null)
				return false;
		} else if (!disrtict.trim().equalsIgnoreCase(other.disrtict.trim()))
			return false;
		if (houseNumber == null) {
			if (other.houseNumber != null)
				return false;
		} else if (!houseNumber.trim().equalsIgnoreCase(other.houseNumber.trim()))
			return false;
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.trim().equalsIgnoreCase(other.pin.trim()))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.trim().equalsIgnoreCase(other.state.trim()))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.trim().equalsIgnoreCase(other.street.trim()))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Address [addresId=" + addresId + ", houseNumber=" + houseNumber + ", street=" + street + ", disrtict="
				+ disrtict + ", state=" + state + ", country=" + country + ", pin=" + pin + "]";
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDisrtict() {
		return disrtict;
	}

	public void setDisrtict(String disrtict) {
		this.disrtict = disrtict;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	//non behavoural
	public static List<String> addressValidation(String houseNumber,String street,String disrtict,String state,String country,String pin)
	{
		List<String> addressList = new ArrayList<String>(); 
		if(houseNumber == null || houseNumber.trim().length() == 0)
		{
			addressList.add("HOUSE-NUMBER ");
			//addressList.add(" ");
		}
		if(street == null || street.trim().length() == 0)
		{
			addressList.add("STREET ");
		}
		if(disrtict == null || disrtict.trim().length() == 0)
		{
			addressList.add("DISTRICT ");
		}
		if(state == null || state.trim().length() == 0)
		{
			addressList.add("STATE ");
		}
		if(country == null || country.trim().length() == 0)
		{
			addressList.add("COUNTRY ");
		}
		if(pin == null || pin.trim().length() == 0)
		{
			addressList.add("PIN");
		}
		//for()
			//throw new PGAddressCanNotBeEmptyException("HouseNumber")
		return addressList ;
	}
	
	//removeExtraSpace
	/*public static Address removeExtraSpace(Address address)
	{
	// if( address == null || remvExSpcStr.trim().length() == 0 )
		if( address == null )
		 return address ;
		// throw new IllegalArgumentException("CAN NOT REMOVE EXTRA SPACE FROM NULL/BLANK/EMPTY STRING");
	 StringBuilder sb = new StringBuilder();
	 String [] saToBeEdoted = remvExSpcStr.trim().split(" ");
      for(int i=0 ; i<saToBeEdoted.length ;i++)
	 {
    	  if(saToBeEdoted[i].trim().length() != 0 )
    	  {
    		  sb.append(saToBeEdoted[i].trim());
				 sb.append(" ");
    	  }	
	
	 }
	 
	 return sb.toString().trim() ;
	}*/
	
	
}
