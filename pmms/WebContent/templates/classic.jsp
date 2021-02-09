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
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<%
	if (isMSIE) {
%>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<%
	}
%>

<link rel="icon" 
      type="image/ico" 
      href="img/favicon.ico">

<title>PMMS</title>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" marginheight="0" marginwidth="0" bgcolor="#FFFFFF">

<%
	trackit.backingbeans.SessionBean sessionBean = (trackit.backingbeans.SessionBean) request
			.getSession().getAttribute("sessionBean");
%>

<table border="0" width="100%" cellspacing="0" cellpadding="0" background="img/blackline.gif">
  <tr>
    <td width="100%" height="20"><font color="#B8C0F0" face="Arial" size="2"><b>&nbsp;&nbsp;
      Welcome <%=sessionBean == null ? "guest" : sessionBean.getUserName()%> &nbsp;&nbsp; |&nbsp;&nbsp; <a href="NewAccountUser.jsf">profile</a>&nbsp;&nbsp; |&nbsp;&nbsp;
      <a href="j_spring_security_logout">logout</a>&nbsp;&nbsp;</b></font></td>
  </tr>
</table>
<table border="0" width="100%" cellspacing="0" cellpadding="0" background="img/topbkg.gif">
  <tr>
    <td width="50%"><img border="0" src="img/toplogo.gif" width="142" height="66"></td>
    <td width="50%">
      <p align="right"><img border="0" src="img/topright.gif" width="327" height="66"></td>
  </tr>
</table>
<table>
	<tr>
		<td width="30"/>
		<td width="800"><tiles:insert attribute="body" /></td>
	</tr>
</table> 

<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp; </font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20" align="center"><font face="Arial" color="#000000" size="1">©
COPYRIGHT 2010 ALL RIGHTS RESERVED PMMS.COM</font></p>
<table border="0" width="100%" cellspacing="0" cellpadding="0" background="img/botline.gif">
  <tr>
    <td width="100%"><img border="0" src="img/botline.gif" width="41" height="12"></td>
  </tr>
</table>


</body>

</html>
