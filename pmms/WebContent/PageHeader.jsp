<%--
    Document   : Header
    Created on : Feb 16, 2010, 10:19:15 PM
    Author     : HP
--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <h:form id="BankAccounts">
        <TABLE><tr><td align="center"><h4>Hello walak ya 7ayawan</h4></td><td><h:commandLink value="Logout" action="#{userBean.logoutAction}"/></td></tr></TABLE>
    </h:form>
</f:view>