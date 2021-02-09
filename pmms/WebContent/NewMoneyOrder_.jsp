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
        <title>New Money Order</title>
    </head>
    <body>
        <h:form id="NewMoneyOrder">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	
            <br>
            <h:messages styleClass="errorMessage" globalOnly="true"/>
        	<h:panelGrid columns="1">
        		<h:outputText value="New Money Order" styleClass="subtitle"/>
        	</h:panelGrid>
        	<br>
            <h:panelGrid columns="2">

				<h:outputText styleClass="requiredfieldlabel" value="Purchase Amount" rendered ="true"/>
                <h:inputText styleClass="fieldinput" value="#{moBean.amount}" rendered="true"/>
                
                <h:outputText styleClass="fieldlabel" value="Fee" rendered ="true"/>
                <h:inputText styleClass="fieldinput" value="#{moBean.fee}" rendered="true"/>
                
                <h:outputText styleClass="requiredfieldlabel" value="Purchase Date"/>
                <rich:calendar inputClass="fieldinput" value="#{moBean.date}"/>
                
                <h:outputText styleClass="requiredfieldlabel" value="Money Order Number" rendered ="true"/>
                <h:inputText styleClass="fieldinput" value="#{moBean.number}" rendered="true"/>
                
                <h:outputText styleClass="requiredfieldlabel" value="Payment Method"/>
                <h:selectOneMenu id="payment_method" required="true" styleClass="fieldinput"
                             value="#{moBean.paymentMethodId}"
                             valueChangeListener="#{moBean.updateByPaymentMethod}"
                             onchange="submit()" immediate="true">
                    <f:selectItems value="#{moBean.paymentMethodList}" />
                </h:selectOneMenu>
                
                <h:outputText styleClass="requiredfieldlabel" id="bank_account_label" value="Bank Account" rendered="#{moBean.renderBankAccountPanel}"/>
                <h:panelGrid columns="2" rendered="#{moBean.renderBankAccountPanel}">    
                    <h:selectOneMenu styleClass="fieldinput" id="bank_account_menu" required="true"
                        value="#{moBean.bankAccountId}" rendered="true">
                        <f:selectItems value="#{moBean.bankAccountList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Bank Account" rendered="true"
                    immediate="true" onclick="submit()"
                        action="#{moBean.addBankAccountAction}" image="plus_green.gif"/>
                </h:panelGrid>

                <h:outputText styleClass="requiredfieldlabel" id="check_number_label" value="Check Number" 
                	rendered="#{moBean.renderCheckNumberPanel}"/>
                <h:inputText styleClass="fieldinput" id="check_number_text" value="#{moBean.checkNumber}" 
                	rendered="#{moBean.renderCheckNumberPanel}"/>
                
                <h:outputText styleClass="requiredfieldlabel" id="credit_card_label" value="Credit Card" 
                	rendered="#{moBean.renderCreditCardPanel}"/>
                <h:selectOneMenu styleClass="fieldinput" id="credit_card_menu" required="true"
                    value="#{moBean.creditCardId}" rendered="#{moBean.renderCreditCardPanel}">
                    <f:selectItems value="#{moBean.creditCardList}" />
                </h:selectOneMenu>
                
                <h:outputText styleClass="requiredfieldlabel" value="Issuer"/>
                <h:selectOneMenu id="issuer" required="true" styleClass="fieldinput"
                             value="#{moBean.issuerId}"
                             valueChangeListener="#{moBean.updateByIssuer}"
                             onchange="submit()" immediate="true">
                    <f:selectItems value="#{moBean.issuerList}" />
                </h:selectOneMenu>

                
                

            </h:panelGrid>
            <br>
            
            <rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{moBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{moBean.createAction}" />					
				</h:panelGrid>				
				</div>
				<rich:separator/>
                        
        </h:form>
    </body>
</html>

</f:view>
