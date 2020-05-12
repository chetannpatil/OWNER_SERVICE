<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %> 
<%@ include file="OwnerHeader.html" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Room</title>
</head>
<body>

<h1 align="center">Please enter your PG details to add a room to ur PG</h1>
<h4 align="center">${errorMessage }</h4>
<h1 align="center">ALL ARE MANDATORY FIELDS</h1>

<s:form action="addARoom.owner"  modelAttribute="roomBean" method="POST">

<table align="center" border="0" bordercolor="maroon">

<tr>
<td>
NUMBER OF BEDS
</td>
<td>
<s:input path="numberOfBeds"/> 
<s:errors path="numberOfBeds" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
ROOM NUMBER
</td>
<td>
<s:input path="roomNumber"/>
<s:errors path="roomNumber" style="color:red"></s:errors>
</td>

<td><b style="color: red;">${dupUserErrorMessage }</b></td>
</tr>


<tr>
<td>
COST PER BED
</td>
<td>
<s:input path="costPerBed"/>
<s:errors path="costPerBed" style="color:red"></s:errors>
</td>

<tr>
<td colspan="2" align="center">
<input type="submit" value="AddARoom"/> 
</td>
</tr>

</table>

</s:form>

</body>
</html>