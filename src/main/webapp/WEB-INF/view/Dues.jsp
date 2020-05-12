<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="OwnerHeader.html" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dues</title>
</head>
<body>


<h3>${noDuesAtAllMessage}</h3>

<c:if test="${!empty allInMatesDuesMap }">

<h3 style="color: maroon;" align="center">Followings are dues till this month <%=new java.util.Date()%> </h3>

<table border='1' bordercolor="green" style="width:80%" align="center">

<thead>

<tr>

<th >
INMATE'S FULL NAME
</th>


<th >
PHONE NUMBER
</th>

<th>
DATE OF JOINING
</th>


<th >
HIS ROOM NUMBER
</th>

<th >
RENT PER MONTH
</th>

<th >
SHARING(TOTAL PERSONS IN ROOM)
</th>

<th >
HE IS YET TO PAY FOR THESE MANY MONTHS
</th>

<th >
DUES MONTHS
</th>

<th >
TOTAL AMOUNT HE HAS TO PAY
</th>

</tr>

</thead>


<c:forEach var="entryBean" items="${allInMatesDuesMap.entrySet()}">

<tr>

<td>
<label>${entryBean.getKey().firstName}</label>
<label>${entryBean.getKey().lastName}</label>
</td>

<td>
<label>${entryBean.getKey().phoneNumber}</label>
</td>

<td>
<label>${entryBean.getKey().dateOfJoining.toString().substring(0,10)}</label>
</td>

<td>
<label>${entryBean.getKey().myRoom.getRoomNumber()}</label>
</td>

<td>
<label>${entryBean.getKey().myRoom.getCostPerBed() }</label>
</td>

<td>
<label>${entryBean.getKey().myRoom.getNumberOfBeds() }</label>
</td>

<td>
<label>${entryBean.getValue().size()-1 }</label>
</td>

<td>

${entryBean.getValue().get(0)}
<br/>
to
<br/>
${entryBean.getValue().get(entryBean.getValue().size()-2)}

</td>

<td>
<label>${entryBean.getValue().get(entryBean.getValue().size()-1) }</label>
</td>

<td>
<form action="openPayRentView.owner" method="POST">
<input type="hidden" name="inMateId" value='${entryBean.getKey().inMateId }'>

<input type="hidden" name="duesMonthCount" value='${entryBean.getValue().size()-1 }'>

<input type="hidden" name="totalDue" value='${entryBean.getValue().get(entryBean.getValue().size()-1) }'>

<input type="submit" value="pay rent now">
</form>
</td>
</tr>

</c:forEach>

</table>

</c:if>

</body>
</html>