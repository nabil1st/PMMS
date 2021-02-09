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

<rich:modalPanel id="bankAccountAdd">
    <f:facet name="header">
        Add new bank account
    </f:facet>
    <h:form>
        <h:panelGrid id="newBankAccountInfo">
	
            <h:panelGrid columns="2">
                <h:outputText value="Account Description:"/>
                <h:inputText id="description_text" value="#{bankAccountBean.description}" required="true"/>
                <h:outputText value="Account Type:"/>
                <h:selectOneMenu id="account_type" required="true"
                                 value="#{bankAccountBean.accountTypeId}">
                    <f:selectItems value="#{bankAccountBean.accountTypeList}" />
                </h:selectOneMenu>
                <h:outputText id="current_check_number_label" value="Current Check Number:"/>
                <h:inputText id="current_check_number_text" value="#{bankAccountBean.currentCheckNumber}" required="true"/>

                <h:outputText id="current_account_balance_label" value="Current Account Balance:"/>
                <h:inputText id="current_account_balance_text" value="#{bankAccountBean.currentBalance}" required="true"/>

            </h:panelGrid>

            <h:panelGrid columns="2">
                <a4j:commandLink onclick="#{rich:component('bankAccountAdd')}.hide();return false">
                    Close
                </a4j:commandLink>
                <a4j:commandLink actionListener="#{bankAccountBean.createAction}"
                                 oncomplete="#{rich:component('bankAccountAdd')}.hide();return false"
                                 reRender="description">
                    Save
                </a4j:commandLink>
            </h:panelGrid>
        </h:panelGrid>
    </h:form>
</rich:modalPanel>