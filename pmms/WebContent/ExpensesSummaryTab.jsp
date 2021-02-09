<%-- 
    Document   : ExpensesSummaryTab
    Created on : Jul 23, 2009, 4:17:37 PM
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

<h:panelGrid columns="5">
    <h:outputLabel id="from_date_label" value="From"/>
    <rich:calendar id="from_date" immediate="true" value="#{expenseListBean.fromDate}"/>
    <h:outputLabel id="to_date_label" value="To"/>
    <rich:calendar id="to_date" immediate="true" value="#{expenseListBean.toDate}"/>
    <h:commandButton value="Search" action="show_expenses"/>
</h:panelGrid>

<rich:spacer height="30"/>


<!--
                            <rich:datascroller align="center" for="expensesList" maxPages="20" page="#{expenseListBean.scrollerPage}" reRender="sc2" id="sc1"/>
                            -->

<rich:spacer height="30"/>

<rich:dataTable width="483" id="expensesList" rows="10" columnClasses="col"
                value="#{expenseListBean.expensesList}" var="account">
    <f:facet name="header">
        <rich:columnGroup>
            <h:column>
                <h:outputText styleClass="headerText" value="Date" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Amount" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Expense Group" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Payee" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Payment Method" />
            </h:column>
        </rich:columnGroup>
    </f:facet>

    <h:column>
        <h:outputLabel value="#{account.date}"/>
    </h:column>
    <h:column>
        <h:outputLabel value="#{account.amount}" />
    </h:column>
    <h:column>
        <h:outputLabel value="#{account.expenseGroupName}"/>
    </h:column>
    <h:column>
        <h:outputLabel value="#{account.payeeName}"/>
    </h:column>
    <h:column>
        <h:outputLabel value="#{account.paymentMethodName}"/>
    </h:column>
</rich:dataTable>

<rich:datascroller align="center" for="expensesList" maxPages="20" page="#{expenseListBean.scrollerPage}" 
	reRender="sc1" id="sc2" renderIfSinglePage="false" />

<rich:spacer height="30"/>

<h:panelGrid title="Add a New Expense" border="2">
    <h:outputText value="Add a New Expense"/>
    <%@include file="NewExpenseTab.jsp"%>
</h:panelGrid>

<h:messages styleClass="errorMessage" globalOnly="true"/>
