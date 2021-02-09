<%--
    Document   : HomePage
    Created on : Feb 15, 2010, 8:33:32 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="page.template">
	<tiles:put name="title" value="Cash Transfers"/>
        <tiles:put name="header" value="/Header.jsp"/>
        <tiles:put name="menu" value="/Menu.jsp"/>
        <tiles:put name="body" value="/CashTransfers_.jsp"/>
        <tiles:put name="footer" value="/Header.jsp"/>
</tiles:insert>