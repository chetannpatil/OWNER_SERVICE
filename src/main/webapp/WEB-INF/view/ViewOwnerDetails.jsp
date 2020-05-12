<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="OwnerHeader.html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ViewOwnerDetails</title>
</head>
<body>
<h4 align="center">${ownerDetailsUpdatedSuccesMessage }</h4>
<c:if test="${ pgOwnerBean != null}">

<table align="center" border="0" bordercolor="maroon">

<tr>

<td>
FIRST NAME
</td>

<td>
<label>${pgOwnerBean.firstName}</label>
</td>

</tr>

<tr>
<td>
LAST NAME
</td>
<td>
<label>${pgOwnerBean.lastName}</label>

</td>
</tr>

<tr>
<td>
PHONE NUMBER
</td>
<td>
<label>${pgOwnerBean.phoneNumber}</label>
</td>
</tr>

<tr>
<td>
AADHAAR NUMBER
</td>
<td>
<label>${pgOwnerBean.aadhaarNumber}</label>

</td>
</tr>

<tr>
<td>
DOB
</td>
<td>
<label>${pgOwnerBean.dob}</label>
</td>
</tr>

<tr>
<td>
MY PG NAME
</td>
<td>
<label>${pgOwnerBean.myPG.name}</label>
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
<label>${pgOwnerBean.address.houseNumber}</label>

</td>
</tr>

<tr>
<td>
STREET  
</td>
<td>
<label>${pgOwnerBean.address.street}</label>

</td>
</tr>

<tr>
<td>
DISTRICT
</td>
<td>
<label>${pgOwnerBean.address.disrtict}</label>

</td>
</tr>

<tr>
<td>
STATE  
</td>
<td>
<label>${pgOwnerBean.address.state}</label>

</td>
</tr>

<tr>
<td>
COUNTRY 
</td>
<td>
<label>${pgOwnerBean.address.country}</label>

</td>
</tr>

<tr>
<td>
PIN
</td>
<td>
<label>${pgOwnerBean.address.pin}</label>

</td>
</tr>

</table>

</c:if>
</body>
</html>