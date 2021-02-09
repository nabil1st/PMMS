<%-- 
    Document   : BankAccountAddPanel
    Created on : Jul 24, 2009, 3:43:29 PM
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

<rich:modalPanel id="expenseFilterModelPanel">
    <f:facet name="header">
        Select Filter Criteria
    </f:facet>
    <h:form>
        <h:panelGrid id="expenseFilterCriteria">

            <h:panelGrid columns="2">
                <h:outputText value="User Name"/>                
            </h:panelGrid>
            <h:panelGrid columns="2">
                <h:commandLink id="closeLink">
                    Close
                </h:commandLink>   
                <rich:componentControl for="expenseFilterModelPanel" attachTo="closeLink" operation="hide" event="onclick"/>
            </h:panelGrid>
        </h:panelGrid>
    </h:form>
</rich:modalPanel>