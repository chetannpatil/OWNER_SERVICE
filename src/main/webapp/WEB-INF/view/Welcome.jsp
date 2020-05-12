<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ include file="WelcomeHeader.html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MyPGStyle.css"/>
<title>WelcomePage</title>
</head>
<body bgcolor="white">
<p style=" font-family:monospace;  font-size: 70pt; " align="center"   >....</p>
<div class="division">
</div>
   <h1 ><marquee>${signOutMessage }</marquee> </h1>              

<h4 align="center">${loginErrorMessage }</h4>

<h4 align="center">${errorMessage }</h4>
<table align="center" border="2">
<tr>
<td>
<s:form action="logIn" method="POST" >
<p  >PHONE NUMBER :<input  type="text" name="phoneNumber" value="${phoneNumber }"/>

</p>
                        
<p  >PASSWORD &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :<input type="password" name="password" />

</p>

<p align="center"><input type="submit" class="button" value='LOG IN' />
</p>
</s:form>
</td>
</tr>
</table>


<h6 ><marquee>${SuccessMessage }</marquee> </h6>
</body>
</html>
<%@ include file="WelcomeFooter.html" %>