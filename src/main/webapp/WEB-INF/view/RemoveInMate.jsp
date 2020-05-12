<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="OwnerHeader.html" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RemoveInMate</title>
</head>
<body>


<h4 align="center">${errorMessage }</h4>


<form action="removeInMate.owner"  method="POST">

<input name="inMateId" type="hidden" value='${inMateBean.inMateId }'/>

<input type="hidden" name="roomNumber" value='${inMateBean.myRoom.getRoomNumber() }'/>

<h1 align="center"><input type="submit" value="Remove InMate from PG" align="middle"/> </h1>

</form>

<c:if test="${ inMateBean != null}">

<table align="center" border="0" bordercolor="maroon">

<tr>

<td>
Room Number
</td>

<td>
<label>${inMateBean.myRoom.getRoomNumber()}</label>
</td>

</tr>

<tr>
<td>
FIRST NAME
</td>

<td>
<label>${inMateBean.firstName}</label>
</td>
</tr>

<tr>
<td>
LAST NAME
</td>

<td>
<label>${inMateBean.lastName}</label>
</td>
</tr>

<tr>
<td>
PHONE NUMBER
</td>
<td>
<label>${inMateBean.phoneNumber}</label>
</td>
</tr>

<tr>
<td>
AADHAAR NUMBER
</td>
<td>
<label>${inMateBean.aadhaarNumber}</label>
</td>
</tr>

<tr>
<td>
DOB
</td>
<td>
<label>${inMateBean.dob}</label>
</td>
</tr>


<tr>
<td>
occupation
</td>
<td>
<label>${inMateBean.occupation}</label>
</td>
</tr>

<tr>
<td>
emailId
</td>
<td>
<label>${inMateBean.emailId}</label>
</td>
</tr>

<tr>
<td>
dateOfJoining
</td>
<td>
<label>${inMateBean.dateOfJoining}</label>
</td>
</tr>

<tr>
<td colspan="2" align="center" >ADDRESS  
</td>
</tr>

<tr>
<td>
HOUSE-NUMBER  
</td>
<td>
<label>${inMateBean.address.houseNumber}</label>
</td>
</tr>

<tr>
<td>
STREET  
</td>
<td>
<label>${inMateBean.address.street}</label>
</td>
</tr>

<tr>
<td>
DISTRICT
</td>
<td>
<label>${inMateBean.address.disrtict}</label>
</td>
</tr>

<tr>
<td>
STATE  
</td>
<td>
<label>${inMateBean.address.state}</label>
</td>
</tr>

<tr>
<td>
COUNTRY 
</td>
<td>
<label>${inMateBean.address.country}</label>
</td>
</tr>

<tr>
<td>
PIN
</td>
<td>
<label>${inMateBean.address.pin}</label>
</td>
</tr>



</table>

</c:if>


</body>
</html>