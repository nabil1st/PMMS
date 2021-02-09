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
            <title>Credit Card Statement</title>
        </head>
        <body>
            <h:form id="CreditCardStatement">                
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Credit Card Transactions for card " styleClass="subtitle"/>
                    <h:outputText value="#{creditCardStatementBean.creditCardName}" styleClass="subname"/>
                </h:panelGrid>

                <br>

                                
                <h:panelGrid columns="5">
                    <h:outputLabel styleClass="fieldlabel" id="from_date_label" value="From"/>
                    <rich:calendar inputClass="fieldinput" id="from_date" immediate="true" value="#{creditCardStatementBean.fromDate}"/>
                    <h:outputLabel styleClass="fieldlabel" id="to_date_label" value="To"/>
                    <rich:calendar inputClass="fieldinput" id="to_date" immediate="true"  value="#{creditCardStatementBean.toDate}"/>
                    <h:commandButton image="img/Refresh.gif" title="Refresh" action="show_credit_card_statement"/>
                </h:panelGrid>
				
				<br>
				<h:panelGrid columns="3">
                    <h:commandButton image="img/Payment.gif" title="New Payment" action="#{creditCardStatementBean.paymentAction}"/>
                    <h:commandButton image="img/NewChargeCredit.gif" title="New (Charge / Credit)" action="#{creditCardStatementBean.chargesCreditsAction}"/>
                    <h:commandButton value="Verify Statement" title="Compare with Credit Card Statement" action="#{creditCardStatementBean.compareAction}"/>
                </h:panelGrid>
                <h:panelGrid columns="1">
                <rich:dataTable width="483" id="statementItems" rows="10" columnClasses="col"
                                value="#{creditCardStatementBean.statementItems}" var="item">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Description" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="To" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:outputLabel value="#{item.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.description}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.to}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.amount}"/>
                    </h:column>
                </rich:dataTable>
                <rich:datascroller align="center" for="statementItems" maxPages="20" 
                	page="#{creditCardStatementBean.scrollerPage}" 
                	reRender="credit_card_statement_sc1" id="credit_card_statement_sc2" renderIfSinglePage="false" />
				</h:panelGrid>
                <h:panelGrid columns="2">
                    <h:outputLabel styleClass="fieldlabel" value="Balance"/>
                    <h:outputText styleClass="fieldinput" 
                    	value="#{creditCardStatementBean.creditCardBalance}"/>
                </h:panelGrid>                
				
                
            </h:form>
        </body>
    </html>

</f:view>
