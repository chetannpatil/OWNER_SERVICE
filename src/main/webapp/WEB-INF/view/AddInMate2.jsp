<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>    
<%@ include file="OwnerHeader.html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>addInMate2</title>
</head>
<body>
<h4 align="center"><b>${errorMessage}</b></h4>
<s:form action="addInMate2.owner"  modelAttribute="inMateBean" method="POST">

<h1 align="center">MANDATORY FIELDS</h1>

<table align="center" border="0" bordercolor="maroon">

<tr>
<td>
ROOM NUMBER
</td>
<td>
<input type="text" name="roomNumber" value="${roomNumber }" readonly="readonly"/>
</td>
<td><b style="color: red;">${roomNumberErrorMessage }</b></td>
</tr>

<tr>
<td>
FIRST NAME
</td>
<td>
<s:input path="firstName"/> 
<s:errors path="firstName" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
PHONE NUMBER
</td>
<td>
<s:input path="phoneNumber"/>
<s:errors path="phoneNumber" style="color:red"></s:errors>
</td>

<td><b style="color: red;">${dupUserErrorMessage }</b></td>
</tr>

<tr>
<td>
DATE OF JOINING  
</td>

<td>
 <s:input path="dateOfJoining"  />
 <s:errors path="dateOfJoining" style="color:red"></s:errors>
</td>

<td> <b style="color: red;">${dateOfJoiningErrorMessage }</b></td>
</tr>

</table>

<h1 align="center">OPTIONAL FIELDS</h1>
<table align="center" border="0" bordercolor="maroon" >
<tr >
<td>
LAST NAME
</td>
<td >
<s:input path="lastName" ></s:input> <s:errors path="lastName" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
D.O.B  
</td>

<td>
 <s:input path="dob"  />
 <s:errors path="dob" style="color:red"></s:errors>
</td>

<td> <b style="color: red;">${dobErrorMessage }</b></td>
</tr>

<tr>
<td>
AADHAAR NUMBER
</td>
<td>
<s:input path="aadhaarNumber" rows="5" cols="33"></s:input>
<s:errors path="aadhaarNumber" style="color:red"></s:errors>
</td>
</tr>


<tr>
<td>
OCCUPATION
</td>
<td>
<s:input path="occupation" rows="5" cols="33"></s:input>
<s:errors path="occupation" style="color:red"></s:errors>
</td>
</tr>


<tr>
<td>
E-MAIL
</td>
<td>
<s:input path="emailId" rows="5" cols="33"></s:input>
<s:errors path="emailId" style="color:red"></s:errors>
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
<s:input path="address.houseNumber"></s:input>
<s:errors path="address.houseNumber" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
STREET  
</td>
<td>
<s:input path="address.street"></s:input>
<s:errors path="address.street" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
DISTRICT
</td>
<td>
<s:input path="address.disrtict"></s:input>
<s:errors path="address.disrtict" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
STATE  
</td>
<td>
<s:input path="address.state"></s:input>
<s:errors path="address.state" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
COUNTRY 
</td>
<td>
<s:input path="address.country"></s:input>
<s:errors path="address.country" style="color:red"></s:errors>

</td>

<tr>
<td>
PIN
</td>
<td>
<s:input path="address.pin"></s:input>
<s:errors path="address.pin" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td colspan="2" align="center">
<input type="submit" value="submit details "/> 
</td>
</tr>


</table>
</s:form>






</body>
</html>