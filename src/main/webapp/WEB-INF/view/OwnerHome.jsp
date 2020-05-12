<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="OwnerHeader.html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OwnerHome</title>


</head>
<body>
    <h1>Hi ${pgOwnerBean.firstName} Welcome</h1>
	<h2 >${pgOwnerBean.myPG.name } is your PG  all the best</h2>
	<h3>${pgUpdatedSuccessMessage}</h3>
	<h4>${roomAdditionSuccessMessage}</h4>
	<h4>${inMateAddedSuccessMessage }</h4>
	<h4>${inMateUpdatedSuccessMessage }</h4>
	<h4>${inMateRemovalSuccessMessage }</h4>
	<h4>${paymentSuccessMessage }</h4>
</body>
</html>