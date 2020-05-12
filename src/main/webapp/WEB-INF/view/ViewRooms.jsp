<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="OwnerHeader.html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Rooms</title>
</head>
<body>
<h4>${roomUpdatedSuccessMessage}</h4>

<c:if test="${empty roomsSet }">
<h3 align="center">${emptyPGMessage }</h3>
</c:if>

<c:if test="${!empty roomsSet }">

<table align="center" border="1">

<tr style="color: maroon;">
<th >
Sl Number
</th>

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


<c:set var="roomCount" value="0" scope="page" />

<c:forEach var="roomBean" items="${roomsSet }">

<tr>

<td style="color: red;">
<c:set var="roomCount" value="${roomCount + 1}" scope="page"/>
</td>

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

<td>
<form action="openEditRoomView.owner" method="post">
<input type="hidden" name="roomId" value='${roomBean.roomId }'/>
<input type="submit" value="Edit Room">
</form>
</td>

<td>
<form action="openRemoveRoomView.owner" method="post">
<input type="hidden" name="roomId" value='${roomBean.roomId }'/>
<input type="submit" value="Remove Room from PG">
</form>
</td>

</tr>
</c:forEach>

</table>


</c:if>
</html>