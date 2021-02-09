<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.*"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>

<%
	String ua = request.getHeader("User-Agent");
	boolean isFirefox = (ua != null && ua.indexOf("Firefox/") != -1);
	boolean isMSIE = (ua != null && ua.indexOf("MSIE") != -1);
	response.setHeader("Vary", "User-Agent");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<%
	if (isMSIE) {
%>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<%
	}
%>

<title>PMMS</title>
<style type="text/css">
<!--
.style2 {
	color: #3366FF;
	font-size: 36px;
}

.style6 {
	color: #FFFFFF
}
-->
</style>
</head>

<body>
<%
	trackit.backingbeans.SessionBean sessionBean = (trackit.backingbeans.SessionBean) request
			.getSession().getAttribute("sessionBean");
%>
<table width="1050" height="30" border="0">
	<tr>
		<td width="581" height="26" bgcolor="#33CCFF"></td>
		<td width="266" bgcolor="#33CCFF"><span class="style6">Welcome
		<%=sessionBean == null ? "guest" : sessionBean
					.getUserName()%> </span></td>
		<td width="76" bgcolor="#33CCFF"><a href="NewAccountUser.jsf">profile</a></td>
		<td width="109" bordercolor="#3366FF" bgcolor="#33CCFF">
		<div align="left"><a href="j_spring_security_logout">logout</a></div>
		</td>
	</tr>
</table>
<table width="1050" height="953" border="0">
	<tr>
		<td width="200" height="124" bgcolor="#FFFFFF">
		<div align="center"><img src="images/wallet-es.jpg" width="119"
			height="85"></div>
		</td>
		<td width="611" bgcolor="#3399CC">
		<div align="center">
		<table width="581" height="108" border="0" bgcolor="#3366FF">
			<tr>
				<td width="575" height="43" bgcolor="#FFFFFF">
				<div align="center" class="style2">Personal Money Management</div>
				</td>
			</tr>
			<tr>
				<td height="58" bgcolor="#3366FF">
				<table width="576" border="0">
					<tr>
						<td width="129">
						<div align="center"><a href="#" class="style6">Android
						Client</a></div>
						</td>
						<td width="139">
						<div align="center"><a href="contactus.html" class="style6">Contact
						Us</a></div>
						</td>
						<td width="137">&nbsp;</td>
						<td width="142">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</div>
		</td>
		<td width="225" bgcolor="#FFFFFF">
		<div align="center"><img src="images/checkbook-es.jpg"
			width="106" height="91"></div>
		</td>
	</tr>
	<tr>
		<td height="703" valign="top">
		<table>
			<tr>
				<td height="50" />
			</tr>
			<tr>				
				<td><tiles:insert attribute="menu" /></td>				
			</tr>
		</table>
		</td>
		<td align="center" valign="top">
		<table>
			<tr>
				<td height="20"></td>
			</tr>
			<tr>
				<td></td>
				<tiles:insert attribute="body" />				
			</tr>
		</table>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
</body>
</html>
