<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>REGISTER PG OWNER</title>
<script>
var dateControl = document.querySelector('input[type="date"]');
dateControl.value = '2017-06-01';
</script>
</head>
<body>
<h1 align="center">Please enter your details to create your account as a owner</h1>
<pre>
Note :
Followed by your account creation you have to
end-up by entering your PG details to complete 
registration,else your registration will be auto-cancelled
</pre>
<s:form action="registerPGOwner"  modelAttribute="pgOwnerBean" method="POST">
<h1 align="center">MANDATORY FIELDS</h1>
<table align="center" border="0" bordercolor="maroon">
<tr>
<td>
FIRST NAME
</td>
<td>
<s:input path="firstName"/> 
<s:errors path="firstName" style="color:red"></s:errors>
</td>
</tr>
<tr>
<td>
PHONE NUMBER
</td>
<td>
<s:input path="phoneNumber"/>
<s:errors path="phoneNumber" style="color:red"></s:errors>
</td>

<td><b style="color: red;">${dupUserErrorMessage }</b></td>
</tr>


<tr>
<td>
PASSWORD  
</td>
<td>
 <s:input path="password" type="password"/>
 <s:errors path="password" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
REPEAT-PASSWORD  
</td>
<td>
 <s:input path="repeatPassword" type="password"/>
 <s:errors path="repeatPassword" style="color:red"></s:errors>
</td>

<td> <b style="color: red;">${pswErrorMessage }</b></td>
</tr>


</table>



<h1 align="center">OPTIONAL FIELDS</h1>
<table align="center" border="0" bordercolor="maroon" >
<tr >
<td>
LAST NAME
</td>
<td >
<s:input path="lastName" ></s:input> <s:errors path="lastName" style="color:red"></s:errors>
</td>
</tr>

<tr>
<td>
D.O.B  
</td>

<td>

 <s:input path="dob"  />
 <s:errors path="dob" style="color:red"></s:errors>
</td>

<td> <b style="color: red;">${dobErrorMessage }</b></td>
</tr>

<tr>
<td>
AADHAAR NUMBER
</td>
<td>
<s:input path="aadhaarNumber" rows="5" cols="33"></s:input>
<s:errors path="aadhaarNumber" style="color:red"></s:errors>
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
<input type="submit" value="submit details "/> 
</td>
</tr>


</table>
</s:form>

</body>
</html>