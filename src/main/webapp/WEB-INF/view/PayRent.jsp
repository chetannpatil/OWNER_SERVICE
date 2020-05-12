<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="OwnerHeader.html" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PayRent</title>
</head>
<body>

<h4 align="center">${payMentErrorMessage }</h4>

<c:if test="${inMateBean ne null}">

<h3 style="color: maroon;" align="center">Pay the rent for ${inMateBean.firstName } ${inMateBean.lastName } </h3>

<form action="payRent.owner" method="POST">

<input type="hidden"  name="inMateId" value='${inMateBean.inMateId }'>

<table border='1' bordercolor="green" style="width:80%" align="center">

<tr>
<td>
DATE OF JOINING
</td>
<td>
${inMateBean.dateOfJoining.toString().substring(0,10)}
</td>
</tr>

<tr>
<td>
HE IS YET TO PAY FOR THESE MANY MONTHS
</td>
<td>
<input name="duesMonthCount" value='${duesMonthCount}' readonly="readonly">
</td>
</tr>

<tr>
<td>
TOTAL AMOUNT HE HAS TO PAY
</td>
<td>
<input  name="totalDue" value='${totalDue}' readonly="readonly">
</td>
</tr>

<tr>
<td>
FOR HOW MANY MONTHS HE WANTS TO PAY NOW
</td>

<td>
<input type="text" name="numberOfMonthsInMateWantsToPay"/>
</td>
</tr>

</table>

<h2 align="center"><input type="submit" value="pay rent now"></h2>
</form>

</c:if>
</body>
</html>