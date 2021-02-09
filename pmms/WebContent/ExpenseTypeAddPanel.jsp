<%--
    Document   : ExpenseTypeEditPanel
    Created on : Jul 24, 2009, 3:41:48 PM
    Author     : ndaoud
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<rich:modalPanel id="expenseTypeAdd">
    <f:facet name="header">
        Add Expense Type
    </f:facet>
    <h:form>
        <h:panelGrid id="expenseTypeInfo">
            <h:outputLabel for="nameInput" value="Type:"/>
            <h:inputText id="nameInput" value="#{expenseTypeBean.description}"/>
            <h:panelGrid columns="2">
                <a4j:commandLink onclick="#{rich:component('expenseTypeAdd')}.hide();return false">
                    Close
                </a4j:commandLink>
                <a4j:commandLink actionListener="#{expenseTypeBean.createAction}"
                                 oncomplete="#{rich:component('expenseTypeAdd')}.hide();return false"
                                 reRender="description">
                    Save
                </a4j:commandLink>
            </h:panelGrid>
        </h:panelGrid>
    </h:form>
</rich:modalPanel>
