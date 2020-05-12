<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="OwnerHeader.html" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit inMate</title>
</head>
<body>


<h3 align="center">Edit the InMate details and can shift him to other room as well </h3>

<h4 align="center">${errorMessage }</h4>

<c:if test="${ inMateBean != null}">

<s:form action="updateInMate.owner"  modelAttribute="inMateBean" method="POST">

<s:input path="inMateId" type="hidden" value='${inMateBean.inMateId }'/>

<s:input path="password" type="hidden" readonly="true" value='${inMateBean.password }'/>

<input type="hidden" name="roomNumber" value='${roomNumber}'/>

<table align="center" border="0" bordercolor="maroon">

<tr>

<td>
Room Number
</td>

<td>
<label>${roomNumber}</label>
</td>

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
LAST NAME
</td>
<td>
<s:input path="lastName"/> 
<s:errors path="lastName" style="color:red"></s:errors>
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
</tr>

<tr>
<td>
AADHAAR NUMBER
</td>
<td>
<s:input path="aadhaarNumber"/> 
<s:errors path="aadhaarNumber" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
DOB
</td>
<td>
<s:input path="dob"/> 
<s:errors path="dob" style="color:red"></s:errors>
</td>
</tr>


<tr>
<td>
occupation
</td>
<td>
<s:input path="occupation"/> 
<s:errors path="occupation" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
emailId
</td>
<td>
<s:input path="emailId"/> 
<s:errors path="emailId" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
dateOfJoining
</td>
<td>
<s:input path="dateOfJoining"/> 
<s:errors path="dateOfJoining" style="color:red"></s:errors>
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
</tr>

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
<input type="submit" value="Update InMate Details"/> 
</td>
</tr>

</table>

</s:form>

</c:if>
</body>
</html>