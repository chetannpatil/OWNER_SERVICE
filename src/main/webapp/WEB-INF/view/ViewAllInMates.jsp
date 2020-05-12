<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="OwnerHeader.html" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<c:if test="${empty inMatesSet }">
<h3 align="center">${emptyRoomsMessage }</h3>
</c:if>

<c:if test="${!empty inMatesSet }">

<table align="center" border="1">


<thead>

<tr>

<th rowspan="2">
FIRST NAME
</th>

<th rowspan="2">
LAST NAME
</th>

<th rowspan="2">
PHONE NUMBER
</th>

<th rowspan="2">
AADHAAR NUMBER
</th>

<th rowspan="2">
D.O.B
</th>

<th rowspan="2">
OCCUPATION
</th>

<th rowspan="2">
EMAIL
</th>

<th rowspan="2">
DATE OF JOINING
</th>

<th rowspan="2">
TOTAL COMPLAINTS RAISED BY HIM
</th>

<th rowspan="2">
HIS ROOM NUMBER
</th>

<th colspan="6">
ADDRESS
</th>

</tr>

 <tr>
  
 <th>HOUSE NUMBER</th>
<th>STREET </th>
<th>DISTRICT</th>
<th>STATE</th>
<th>COUNTRY</th>
<th>PIN</th>

</tr>

</thead>

<c:forEach var="inMateBean" items="${inMatesSet }">

<tr>

<td>
<label>${inMateBean.firstName}</label>
</td>

<td>
<label>${inMateBean.lastName}</label>
</td>

<td>
<label>${inMateBean.phoneNumber}</label>
</td>

<td>
<label>${inMateBean.aadhaarNumber}</label>
</td>

<td>
<label>${inMateBean.dob}</label>
</td>

<td>
<label>${inMateBean.occupation}</label>
</td>

<td>
<label>${inMateBean.emailId}</label>
</td>

<td>
<label>${inMateBean.dateOfJoining}</label>
</td>


<td>
<label>${inMateBean.myComplaints.size()}</label>
</td>

<td>
<label>${inMateBean.myRoom.getRoomNumber()}</label>
</td>

<td>
<label>${inMateBean.address.houseNumber}</label>

</td>

<td>
<label>${inMateBean.address.street}</label>

</td>


<td>
<label>${inMateBean.address.disrtict}</label>

</td>

<td>
<label>${inMateBean.address.state}</label>

</td>

<td>
<label>${inMateBean.address.country}</label>

</td>

<td>
<label>${inMateBean.address.pin}</label>
</td>

<td>
<form action="openEditInMateView.owner" method="post">
<input type="hidden" name="inMateId" value='${inMateBean.inMateId }'/>
<input type="submit" value="Edit InMate">
</form>
</td>

<td>
<form action="openRemoveInMateView.owner" method="post">
<input type="hidden" name="inMateId" value='${inMateBean.inMateId }'/>
<input type="submit" value="Remove InMate from PG">
</form>
</td>

</tr>

</c:forEach>

</table>

</c:if>

</body>
</html>