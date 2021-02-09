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
        <title>New Credit Card Transaction</title>
    </head>
    <body>
        <h:form id="NewCreditCardTransaction">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	<br>
        	<h:messages styleClass="errorMessage" globalOnly="true"/>
        	<h:panelGrid columns="1">
        		<h:outputText value="New Credit Card Transaction" styleClass="subtitle"/>
        	</h:panelGrid>
        	<br>
            <h:panelGrid columns="2">
                
                    <h:outputText styleClass="fieldlabel" value="Transaction Date"/>
                    <rich:calendar inputClass="fieldinput" value="#{creditCardTransactionBean.date}"/>
                

                
                    <h:outputText styleClass="fieldlabel" id="credit_card_label" value="Credit Card:" rendered="true"/>
                <h:panelGrid columns="2">
                    <h:selectOneMenu styleClass="fieldinput" id="credit_card_menu"
                        value="#{creditCardTransactionBean.creditCardId}" rendered="true">
                        <f:selectItems value="#{creditCardTransactionBean.creditCardList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Credit Card" rendered="true"
                        onclick="submit()"
                        action="#{creditCardTransactionBean.addCreditCardAction}" image="plus_green.gif"/>
                </h:panelGrid>

                
                    <h:outputText styleClass="fieldlabel" value="Transaction Type"/>
                    <h:selectOneMenu id="transaction_type" styleClass="fieldinput"
                                 value="#{creditCardTransactionBean.transactionTypeId}">
                        <f:selectItems value="#{creditCardTransactionBean.transactionTypeList}" />
                    </h:selectOneMenu>
                
                
                    <h:outputText styleClass="fieldlabel" value="Transaction Amount" rendered ="true"/>
                    <h:inputText styleClass="fieldinput" value="#{creditCardTransactionBean.amount}" rendered="true"/>
            	</h:panelGrid>    

                <br>
                <rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{creditCardTransactionBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{creditCardTransactionBean.createAction}" />					
				</h:panelGrid>				
				</div>
				<rich:separator/>
                
            
        </h:form>
    </body>
</html>

</f:view>
