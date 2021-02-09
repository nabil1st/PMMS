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

<title>PMMS</title>

</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" marginheight="0" marginwidth="0" bgcolor="#FFFFFF">
<h:form>
<link href="style.css" rel="stylesheet" type="text/css"/>
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
		<td>
			<br>
			<h:panelGrid columns="1">
				<h:messages styleClass="errorMessage" globalOnly="true"/>
				<h:outputText value="New Account" styleClass="subtitle"/>
			</h:panelGrid>
		<br>            
		<table>	
			<tr>
		          <td width="164"><div align="left"><h:outputText styleClass="fieldlabel" value="First Name"/></div></td>
		          <td width="20"/>
		          <td width="164"><h:inputText styleClass="fieldinput" value="#{accountBean.firstName}" required="true"/></td>
		        </tr>
		        <tr>
		          <td><div align="left"><h:outputText styleClass="fieldlabel" value="Last Name"/></div></td>
		          <td width="20"/>
		          <td><h:inputText styleClass="fieldinput" value="#{accountBean.lastName}" required="true"/></td>
		        </tr>
		        <tr>
		          <td><div align="left"><h:outputText styleClass="fieldlabel" value="Email"/></div></td>
		          <td width="20"/>
		          <td><h:inputText styleClass="fieldinput" value="#{accountBean.email}" required="true"/></td>
		        </tr>
		        <tr>
		          <td><div align="left"><h:outputText styleClass="fieldlabel" value="Confirm Email"/></div></td>
		          <td width="20"/>
		          <td><h:inputText styleClass="fieldinput" value="#{accountBean.confirmEmail}" required="true"/></td>
		        </tr>
		        <tr>
		          <td><div align="left"><h:outputText styleClass="fieldlabel" value="Password"/></div></td>
		          <td width="20"/>
		          <td><h:inputSecret styleClass="fieldinput" value="#{accountBean.password}" required="true"/></td>
		        </tr>
		        <tr>
		          <td><div align="left"><h:outputText styleClass="fieldlabel" value="Confirm Password"/></div></td>
		          <td width="20"/>
		          <td><h:inputSecret styleClass="fieldinput" value="#{accountBean.confirmPassword}" required="true"/></td>
		        </tr>
		        <tr>
		          <td><div align="left"><h:outputText styleClass="fieldlabel" value="Account Description"/></div></td>
		          <td width="20"/>
		          <td><h:inputText styleClass="fieldinput" value="#{accountBean.accountDescription}" required="true"/></td>
		        </tr>
		        <tr>
		          <td><div align="left"><h:outputText styleClass="fieldlabel" value="Total Cash On Hand"/></div></td>
		          <td width="20"/>
		          <td><h:inputText styleClass="fieldinput" value="#{accountBean.cashBalance}" required="true"/></td>
		        </tr>
		        <tr height="30"/>        
		      
		    <tr>
		      <td/>
		      <td/>
		      <td bordercolor="#F0F0F0"><div align="center"><span class="style10"><h:commandButton value="Create Account" action="#{accountBean.createAction}"/></span></div></td>
		    </tr>
		</table>
		</td>
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

</h:form>
</body>

</html>
</f:view>
