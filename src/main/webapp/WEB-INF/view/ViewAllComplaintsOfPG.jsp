<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.lang.*" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="OwnerHeader.html" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ViewAllComplaintsOfPG</title>
</head>
<body>

<c:if test="${empty complaintsSet }">
<h3 align="center">${emptyComplaintBoxMessage }</h3>
</c:if>

<c:if test="${!empty complaintsSet }">
<h2 align="center"> The Complaints raised by InMates of your PG</h2>
<table align="center" border="1">

<thead>
<tr>
<th >
Sl Number
</th>

<th >
Complaint Deescription
</th>

<th>
Complaint Raised Date
</th>

<th>
Complaint Raised By
</th>

<th>
Is Complaint Resolved ?
</th>

<th>
I have Responded ?
</th>

</tr>
</thead>

<c:forEach var="complaintBean" items="${complaintsSet }">

 <tr>
 
  <td>
<label>${Integer.parseInt(complaintSLNumber)+1}</label>
</td>

 <td>
<label>${complaintBean.complaintMessage}</label>
</td>

 <td>
<label>${complaintBean.complaintRaisedDate}</label>
</td>

 <td>
Name  : <label>${complaintBean.complaintRaisedBy.getFirstName()}</label>,
Phone :<label>${complaintBean.complaintRaisedBy.getPhoneNumber()}</label>,
Room  :<label>${complaintBean.complaintRaisedBy.myRoom.getRoomNumber}</label>
</td>

 <td>
${complaintBean.isComplaintResolved == true ? "Yes" : "Not Yet"}
</td>

 <td>
<c:choose>
    <c:when test="${complaintBean.isOwnerResponded}">
      <label>Yes</label>
	  
	  <form action="takeBackResponseOfComplaint.owner" method="POST">
	  
<input type="hidden" name="complaintId" value="${complaintBean.complaintId}">


<input type="submit" value="I have not yet solved it" style="color: white;background-color: green;">

</form>

        <br />
    </c:when>    
    <c:otherwise>
	   <label>I have not yet solved it</label>
	  
	  <form action="respondToComplaint.owner" method="POST">
	  
<input type="hidden" name="complaintId" value="${complaintBean.complaintId}">


<input type="submit" value="I have re-solved it" style="color: white;background-color: green;">

</form>
        
        <br />
    </c:otherwise>
</c:choose>

</td>

 </tr>
 
</c:forEach>
</table>

</c:if>

</body>
</html>