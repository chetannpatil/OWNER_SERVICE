<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ include file="OwnerHeader.html" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove Room</title>
</head>
<body>
<h3>The followings are the details of your Room</h3>
<pre>
Warning :
If there is/are  InMate/s in your room
definitelly you cant remove room,still if you 
wish to continue please first remove those/that InMate/s from room
</pre>

<h4 align="center">${errorMessage }</h4>

<c:if test="${ roomBean != null}">

<form action="removeRoom.owner"   method="POST">

<input type="hidden" name="roomId" value='${roomBean.roomId }'>

<table align="center" border="1" bordercolor="maroon">

<tr>
<th >
Room Number
</th>

<th>
Total Beds
</th>

<th>
Room Mates
</th>

<th>
Total vacancies
</th>

</tr>

<tr>

<td style="color: red;">
<b>${roomBean.roomNumber }</b>
</td>

<td style="color: red;">
<b>${roomBean.numberOfBeds }</b>
</td>

<td style="color: red;">
<c:forEach var="inMateBean" items="${roomBean.roomMates}">

<c:if test="${inMateBean ne null}">

<b>${inMateBean.firstName}</b>
<b>${inMateBean.phoneNumber}</b>
</c:if>
</c:forEach>
</td>

<td>
<label>${roomBean.numberOfBeds - roomBean.roomMates.size() }</label>
</td>

</tr>
</table>
<h3 align="center"><input  type="submit" value="Remove Room permenetly"/></h3>

</form>
</c:if>
</body>
</html>