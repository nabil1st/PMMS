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
        <title>New Credit Card Payment</title>
    </head>
    <body>
        <h:form id="NewCreditCardPayment">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	<br>
        	<h:panelGrid columns="1">
        		<h:messages styleClass="errorMessage" globalOnly="true"/>
        		<h:outputText value="New Credit Card Payment" styleClass="subtitle"/>
        	</h:panelGrid>
        	<br>
        	
            <h:panelGrid columns="2">
            
            		<h:outputText styleClass="requiredfieldlabel" value="Payment Amount" 
            			rendered="true"/>
                    <h:inputText styleClass="fieldinput" value="#{creditCardPaymentBean.amount}" 
                    	disabled="#{!creditCardPaymentBean.renderExpenseAmountPanel}"/>
                
                    <h:outputText styleClass="requiredfieldlabel" value="Payment Date"/>
                    <rich:calendar inputClass="fieldinput" value="#{creditCardPaymentBean.date}"/>
                
                    <h:outputText styleClass="requiredfieldlabel" id="credit_card_to_pay_label" 
                    	value="Credit Card To Pay" rendered="true"/>
                    <h:panelGrid columns="2">
                    	<h:selectOneMenu styleClass="fieldinput" id="credit_card_to_pay_menu"
                        	value="#{creditCardPaymentBean.creditCardToPayId}" rendered="true">
                        	<f:selectItems value="#{creditCardPaymentBean.creditCardList}" />
                    	</h:selectOneMenu>
                    	<h:commandButton value="Add Credit Card" rendered="true"
                        	onclick="submit()"
                        	action="#{creditCardPaymentBean.addCreditCardAction}" 
                        	image="plus_green.gif"/>
                	</h:panelGrid>
                	
                	<h:outputText styleClass="requiredfieldlabel" value="Payment Method"/>
                    <h:selectOneMenu styleClass="fieldinput" id="payment_method"
                                 value="#{creditCardPaymentBean.paymentMethodId}"
                                 valueChangeListener="#{creditCardPaymentBean.updateByPaymentMethod}"
                                 onchange="submit()" immediate="true">
                        <f:selectItems value="#{creditCardPaymentBean.paymentMethodList}" />
                    </h:selectOneMenu>
                
                    <h:outputText styleClass="requiredfieldlabel" id="bank_account_label" 
                    	value="Bank Account" rendered="#{creditCardPaymentBean.renderBankAccountPanel}"/>
                    <h:panelGrid columns="2" rendered="#{creditCardPaymentBean.renderBankAccountPanel}">
                    	<h:selectOneMenu styleClass="fieldinput" id="bank_account_menu"
                        	value="#{creditCardPaymentBean.bankAccountId}" rendered="true">
                        	<f:selectItems value="#{creditCardPaymentBean.bankAccountList}" />
                    	</h:selectOneMenu>
                    	<h:commandButton image="plus_green.gif" title="Add Bank Account" rendered="true"
                        	onclick="submit()"
                        	action="#{creditCardPaymentBean.addBankAccountAction}"/>
                    </h:panelGrid>
                

                
                    <h:outputText styleClass="requiredfieldlabel" 
                    	id="check_number_label" value="Check Number" rendered="#{creditCardPaymentBean.renderCheckNumberPanel}"/>
                    <h:inputText styleClass="fieldinput" value="#{creditCardPaymentBean.checkNumber}"
                    	rendered="#{creditCardPaymentBean.renderCheckNumberPanel}"/>
                
                
                    <h:outputText styleClass="requiredfieldlabel" id="money_order_label" 
                    	value="Money Order" rendered="#{creditCardPaymentBean.renderMoneyOrderPanel}"/>
                    <h:panelGrid columns="2" rendered="#{creditCardPaymentBean.renderMoneyOrderPanel}">
                    	<h:selectOneMenu styleClass="fieldinput" id="money_order_menu"
                        	value="#{creditCardPaymentBean.moneyOrderId}" rendered="true"
                        	valueChangeListener="#{creditCardPaymentBean.updateByMoneyOrder}" immediate="true" onchange="submit()">
                        	<f:selectItems value="#{creditCardPaymentBean.moneyOrderList}" />
                    	</h:selectOneMenu>
                    	<h:commandButton image="plus_green.gif" title="Add Money Order" rendered="true"
                        	onclick="submit()"
                        	action="#{creditCardPaymentBean.addMoneyOrderAction}"/>
                	</h:panelGrid>


                
                    <h:outputText styleClass="requiredfieldlabel" id="credit_card_label" 
                    	value="Credit Card" rendered="#{creditCardPaymentBean.renderCreditCardPanel}"/>
                    	
                    <h:panelGrid columns="2" id="credit_card_panel" 
                    	rendered="#{creditCardPaymentBean.renderCreditCardPanel}">
                    	<h:selectOneMenu styleClass="fieldinput" id="credit_card_menu"
                        	value="#{creditCardPaymentBean.creditCardId}" rendered="true">
                        	<f:selectItems value="#{creditCardPaymentBean.creditCardList}" />
                    	</h:selectOneMenu>
                    	<h:commandButton image="plus_green.gif" title="Add Credit Card" rendered="true"
                        	onclick="submit()"
                        	action="#{creditCardPaymentBean.addCreditCardAction}"/>
                	</h:panelGrid>
                </h:panelGrid>	
				<br>
				<rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{creditCardPaymentBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{creditCardPaymentBean.createAction}" />					
				</h:panelGrid>				
				</div>
				<rich:separator/>
                
            
        </h:form>
    </body>
</html>

</f:view>
