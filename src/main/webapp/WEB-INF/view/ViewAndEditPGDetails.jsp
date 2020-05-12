<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ include file="OwnerHeader.html" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ViewAndEditPGDetails</title>
</head>
<body>
<h5>${duplicatePGErrorMessage}</h5>
<c:if test="${ pgBean != null}">
<s:form action="updatePG.owner"  modelAttribute="pgBean" method="POST">

<table align="center" border="0" bordercolor="maroon">
<tr>
<td>
PG NAME
</td>
<td>
<s:input path="name"/> 
<s:errors path="name" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
PG STARTED DATE
</td>
<td>
<label >${pgBean.pgStartedDate}</label>
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
<input type="submit" value="Update PG Details"/> 
</td>
</tr>


</table>
</s:form>
</c:if>

</body>
</html>