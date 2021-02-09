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
        <title>New Loan Payment</title>
    </head>
    <body>
        

        <h:form id="NewLoanPayment">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	
        	<br>
        	<h:messages styleClass="errorMessage" globalOnly="true"/>
            <h:panelGrid columns="1">
            	<h:outputText value="New Loan Payment" styleClass="subtitle"/>
            </h:panelGrid>
            
            <br>
            <h:panelGrid columns="2">

                <h:outputText styleClass="fieldlabel" value="Payment Date"/>
                <rich:calendar inputClass="fieldinput" value="#{loanPaymentBean.date}"/>
                
                <h:outputText styleClass="fieldlabel" value="Payment Amount" rendered ="true"/>
                <h:inputText styleClass="fieldinput" value="#{loanPaymentBean.amount}" required="true" rendered="true"/>
                
                <h:outputText styleClass="fieldlabel" id="loan_label" value="Loan" rendered="true"/>
                <h:selectOneMenu id="loan_menu" styleClass="fieldinput"
                    value="#{loanPaymentBean.loanId}" rendered="true">
                    <f:selectItems value="#{loanPaymentBean.loanList}" />
                </h:selectOneMenu>
                
                <h:outputText styleClass="fieldlabel" value="Paid By"/>
                <h:selectOneMenu id="payer" required="true" styleClass="fieldinput"
                             value="#{loanPaymentBean.payerId}">
                    <f:selectItems value="#{loanPaymentBean.payerList}" />
                </h:selectOneMenu>
                
                <h:outputText styleClass="fieldlabel" id="payment_type_label" 
                	value="Payment Type" rendered="true"/>
                <h:selectOneMenu id="payment_type_menu" styleClass="fieldinput"
                    value="#{loanPaymentBean.currencyTypeId}" rendered="true"
                    valueChangeListener="#{loanPaymentBean.updateByCurrencyType}"
                    onchange="submit()" immediate="true">
                    <f:selectItems value="#{loanPaymentBean.currencyTypeList}" />
                </h:selectOneMenu>
                
                <h:outputText styleClass="fieldlabel" id="check_number_label" value="Check Number" 
                	rendered="#{loanPaymentBean.renderCheckNumberField}"/>
                <h:inputText styleClass="fieldinput" id="check_number_text" value="#{loanPaymentBean.checkNumber}" 
                	rendered="#{loanPaymentBean.renderCheckNumberField}"/>
                
                
				<h:outputText styleClass="fieldlabel" id="bank_account_label" value="Bank Account"
						rendered="#{loanPaymentBean.renderBankAccountPanel}" />
				<h:panelGrid columns="2" rendered="#{loanPaymentBean.renderBankAccountPanel}">
					<h:selectOneMenu id="bank_account_menu" styleClass="fieldinput"
						value="#{loanPaymentBean.bankAccountId}" rendered="true">
						<f:selectItems value="#{loanPaymentBean.bankAccountList}" />
					</h:selectOneMenu>
					<h:commandButton value="Add Bank Account" rendered="true" image="plus_green.gif"
						onclick="submit()" action="#{loanPaymentBean.addBankAccountAction}" />
				</h:panelGrid>
                

            </h:panelGrid>
            <br>
            <rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{loanPaymentBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{loanPaymentBean.createAction}" />					
				</h:panelGrid>				
				</div>
			<rich:separator/>
            
        </h:form>
    </body>
</html>

</f:view>
