<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:loadBundle basename="messages" var="msg"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Summary of Loans</title>
        </head>
        <body>

            <h:form id="LoanSummary">                
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="1">
                    <h:outputText value="Loans Summary" styleClass="title"/>
                </h:panelGrid>
                

                <br>

                <h:panelGrid columns="5">
                    <h:outputLabel styleClass="fieldlabel" id="from_date_label" value="From"/>
                    <rich:calendar inputClass="fieldinput" id="from_date" immediate="true" value="#{loanSummaryBean.fromDate}"/>
                    <h:outputLabel styleClass="fieldlabel" id="to_date_label" value="To"/>
                    <rich:calendar inputClass="fieldinput" id="to_date" immediate="true" value="#{loanSummaryBean.toDate}"/>
                    <h:commandButton image="img/Refresh.gif" title="Refresh" action="SHOW_LOAN_SUMMARY"/>
                </h:panelGrid>

                <h:panelGrid columns="2">
                    <h:outputLabel styleClass="fieldlabel" value="Show All Loans"/>
                    <h:selectBooleanCheckbox styleClass="fieldinput" value="#{loanSummaryBean.showAll}"/>
                </h:panelGrid>
                
                <br>
                
                <h:panelGrid columns="1">
                    <h:commandButton title="Add a New Loan" 
                    	action="#{loanSummaryBean.newLoanAction}" image="img/AddNew.gif"/>                    
                </h:panelGrid>
                <%@include file="LoansTable.jsp"%>	
                                
            </h:form>
        </body>
    </html>

</f:view>
