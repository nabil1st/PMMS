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
            <title>Bank Statement</title>
        </head>
        <body>            
            <h:form id="BankStatement">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Bank Account Transactions for Account " styleClass="subtitle"/>
                    <h:outputText value="#{statementBean.bankName}" styleClass="subname"/>
                </h:panelGrid>
                        
                
                <br>

                <h:panelGrid columns="5">
                    <h:outputLabel styleClass="fieldlabel" id="from_date_label" value="From"/>
                    <rich:calendar inputStyle="fieldinput" id="from_date" immediate="true" value="#{statementBean.fromDate}"/>
                    <h:outputLabel styleClass="fieldlabel" id="to_date_label" value="To"/>
                    <rich:calendar inputStyle="fieldinput" id="to_date" immediate="true"  value="#{statementBean.toDate}"/>
                    <h:commandButton image="img/Refresh.gif" title="Refresh" action="show_bank_statement"/>
                </h:panelGrid>

				<br>
				<h:panelGrid columns="5">
                    <h:commandButton image="img/Import.gif" title="New Deposit" action="#{statementBean.depositAction}"/>
                    <h:commandButton image="img/NewChargeCredit.gif" title="New (Charge / Credit)" action="#{statementBean.chargesAction}"/>
                    <h:commandButton image="img/Export.gif" title="New Withdrawl" action="#{statementBean.withdrawlsAction}"/>
                </h:panelGrid>
                <h:panelGrid columns="1">
                <rich:dataTable width="483" id="statementItems" rows="10" columnClasses="col"
                                value="#{statementBean.statementItems}" var="item">
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
                	page="#{statementBean.scrollerPage}" reRender="bank_statement_sc1" id="bank_statement_sc2" renderIfSinglePage="false" />
                </h:panelGrid>

                <h:panelGrid columns="2">
                    <h:outputLabel styleClass="fieldlabel" value="Balance"/>
                    <h:outputText value="#{statementBean.bankAccountBalance}" styleClass="fieldinput"/>
                </h:panelGrid>                
            </h:form>
        </body>
    </html>

</f:view>
