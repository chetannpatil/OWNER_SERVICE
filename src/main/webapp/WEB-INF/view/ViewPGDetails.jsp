<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ include file="OwnerHeader.html" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ViewPGDetails</title>
</head>
<body>


<h5>${duplicatePGErrorMessage}</h5>

<h5>${duplicatePGErrorMessage}</h5>

<c:if test="${ pgBean != null}">

<table align="center" border="0" bordercolor="maroon">
<tr>
<td>
PG NAME
</td>
<td>
<label>${pgBean.name}</label>

</td>
</tr>

<tr>
<td>
PG STARTED DATE
</td>
<td>
<label>${pgBean.pgStartedDate}</label>

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
<label>${pgBean.address.houseNumber}</label>

</td>
</tr>

<tr>
<td>
STREET  
</td>
<td>
<label>${pgBean.address.street}</label>

</td>
</tr>

<tr>
<td>
DISTRICT
</td>
<td>
<label>${pgBean.address.disrtict}</label>

</td>
</tr>

<tr>
<td>
STATE  
</td>
<td>
<label>${pgBean.address.state}</label>

</td>
</tr>

<tr>
<td>
COUNTRY 
</td>
<td>
<label>${pgBean.address.country}</label>

</td>
</tr>

<tr>
<td>
PIN
</td>
<td>
<label>${pgBean.address.pin}</label>

</td>
</tr>

<tr>
<td colspan="2" align="center">
<a href="back"><input type="submit" value="back"/> </a>
</td>
</tr>


</table>

</c:if>


</body>
</html>