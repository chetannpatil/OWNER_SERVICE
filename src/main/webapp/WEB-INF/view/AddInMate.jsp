<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>    
<%@ include file="OwnerHeader.html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AddInMate</title>
</head>
<body>



<c:if test="${empty roomsSet }">
<h3 align="center">${emptyPGMessage }</h3>
</c:if>


<c:if test="${!empty roomsSet }">
<h2 align="center">Select a room from below to add a new InMate</h2>
<table align="center" border="1">

<tr style="color: maroon;">
<th >
Room Number
</th>

<th>
Sharing(Max persons in this room)
</th>

<th>
Room Mates(Who'r staying in this room)
</th>

<th>
Total vacancies
</th>

</tr>

<c:forEach var="roomBean" items="${roomsSet }">

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

<c:if test="${roomBean.numberOfBeds > roomBean.roomMates.size() }">

<td>
<form action="addInMate.owner" method="post">
<input type="hidden" name="roomId" value='${roomBean.roomId }'/>
<input type="submit" value="Add here">
</form>
</td>

</c:if>

</tr>
</c:forEach>

</table>
</c:if>
</body>
</html>