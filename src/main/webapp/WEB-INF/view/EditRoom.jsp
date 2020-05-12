<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="OwnerHeader.html" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Room</title>
</head>
<body>
<h3>Edit the room details and can add/remove InMate from/to room </h3>
<h4 align="center">${errorMessage }</h4>
<c:if test="${ roomBean != null}">

<s:form action="updateRoom.owner"  modelAttribute="roomBean" method="POST">

<input type="hidden" name="roomId" value='${roomBean.roomId }'>


<table align="center" border="0" bordercolor="maroon">

<tr>

<td>
Room Number
</td>

<td>
<s:input path="roomNumber"/> 
<s:errors path="roomNumber" style="color:red"></s:errors>
</td>

</tr>

<tr>

<td>
Total Beds
</td>

<td>
<s:input path="numberOfBeds"/> 
<s:errors path="numberOfBeds" style="color:red"></s:errors>
</td>

</tr>

<tr>

<td>
Total InMates in room
</td>

<td>
<label>${roomBean.roomMates.size() }</label>
</td>

</tr>

<tr>

<td>
Total vacancies in room
</td>

<td>
<label>${roomBean.numberOfBeds - roomBean.roomMates.size() }</label>
</td>

</tr>

<tr>
<td colspan="2" align="center">
<input type="submit" value="Update ROOM Details"/> 
</td>
</tr>

</table>
</s:form>
</c:if>

</body>
</html>