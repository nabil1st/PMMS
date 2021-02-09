<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<f:view>

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
<link href="style.css" rel="stylesheet" type="text/css"/>

<title>PMMS</title>

</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" marginheight="0" marginwidth="0" bgcolor="#FFFFFF">
<h:form>

<table border="0" width="100%" cellspacing="0" cellpadding="0" background="img/topbkg.gif">
  <tr>
    <td width="40%"><img border="0" src="img/toplogo.gif" width="142" height="66"></td>
    				<td width="20%" nowrap="nowrap">
    				<table>
    				<tr>
    				<td>
                    <div><font face="Arial" size="2" color="#ffffff">Email</font></div>
                    </td>
                    <td>
                    <div><font face="Arial" size="2" color="#ffffff">Password</font></div>
                    </td>               
                    </tr>
                    <tr>
                    <td>                     
                    <h:inputText id="j_username" value="#{userBean.email}"/>                                        	
                    </td>
                    <td>
                    	<h:inputSecret id="j_password" value="#{userBean.password}"/>
                    </td>
                    <td>
                    	<h:commandButton value="Login" action="#{userBean.loginAction}"/>
                    </td>                    
                    </tr>
                    <tr>
                    <td>
                    <a href='NewAccount.jsf'>New Account</a>
                    </td>
                    </tr>
                    </table>                         
                    </td>
                  
    <td width="20%">
      <p align="right"><img border="0" src="img/topright.gif" width="327" height="66"></td>
  </tr>
</table>

<div align="center">
<table>
<tr height="50">
</tr>
<tr>
<td><div><font face="Arial" size="3" color="red">Your credentials could not be verified. Please try again!</font></div></td>
</tr>
</table>
</div>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20"><font face="Arial" size="2" color="#000000">&nbsp;</font></p>
<p style="margin-left: 20" align="center"><font face="Arial" color="#000000" size="1">©
COPYRIGHT 2010 ALL RIGHTS RESERVED DomainName.COM</font></p>
<table border="0" width="100%" cellspacing="0" cellpadding="0" background="img/botline.gif">
  <tr>
    <td width="100%"><img border="0" src="img/botline.gif" width="41" height="12"></td>
  </tr>
</table>

</h:form>
</body>

</html>
</f:view>
