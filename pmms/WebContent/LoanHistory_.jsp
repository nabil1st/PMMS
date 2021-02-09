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
            <title>Loan History</title>
        </head>
        <body>

            <h:form id="LoanHistory">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Loan History for loan " styleClass="subtitle"/>
                    <h:outputText styleClass="subname" value="#{loanHistoryBean.description}"/>
                </h:panelGrid>

                <br>

                <h:panelGrid columns="1">
                    <h:commandButton image="img/Payment.gif" title="New Payment" 
                    	action="#{loanHistoryBean.paymentAction}"/>
                </h:panelGrid>

				<h:panelGrid columns="1">
                <rich:dataTable width="483" id="loanList" rows="10" columnClasses="col"
                value="#{loanHistoryBean.historyItems}" var="account" rowKeyVar="index">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Description" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>
                    
                    <h:column>
                        <h:outputLabel value="#{account.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.description}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.amount}" />
                    </h:column>                    
                </rich:dataTable>
                <rich:datascroller align="center" for="loanList" maxPages="20" 
                	page="#{loanHistoryBean.scrollerPage}" reRender="loan_sc1" id="loan_sc2" renderIfSinglePage="false" />
				</h:panelGrid>
				
                <h:panelGrid columns="2">
                    <h:outputLabel styleClass="fieldlabel" value="Loan Balance = "/>
                    <h:outputText styleClass="fieldinput" value="#{loanHistoryBean.balance}"/>
                </h:panelGrid>                
            </h:form>
        </body>
    </html>

</f:view>
