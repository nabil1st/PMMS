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
        <title>New Loan</title>
    </head>
    <body>
        <h:form id="NewLoan">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	
        	<br>
        	<h:panelGrid columns="1">
        		<h:messages styleClass="errorMessage" globalOnly="true"/>
            	<h:outputText value="New Loan" styleClass="subtitle"/>
            </h:panelGrid>
            
            <br>            
                <table>
                	<tr>               
                	<td>
                	<div align="left">
                    <h:outputText styleClass="requiredfieldlabel" value="Loan Amount" rendered ="true"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:inputText styleClass="fieldinput" value="#{loanBean.amount}" disabled="#{!loanBean.renderExpenseAmountPanel}"/>
                    </div>
                	</td>
                	</tr>
                	
                	<tr>
                	<td><div align="left">
                    <h:outputText styleClass="requiredfieldlabel" value="Loan Date"/>
                    </div></td>
                    <td>
                    <div align="left">
                    <rich:calendar inputClass="fieldinput" value="#{loanBean.date}"/>
                    </div>
                    </td>
                	</tr>
                	
                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="requiredfieldlabel" value="Payment Method"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:selectOneMenu id="payment_method" styleClass="fieldinput"
                                 value="#{loanBean.paymentMethodId}"
                                 valueChangeListener="#{loanBean.updateByPaymentMethod}"
                                 onchange="submit()" immediate="true">
                        <f:selectItems value="#{loanBean.paymentMethodList}" />
                    </h:selectOneMenu>
                    </div>
                    </td>
                    </tr>
                	
                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="requiredfieldlabel" id="bank_account_label" 
                    	value="Bank Account:" rendered="#{loanBean.renderBankAccountPanel}"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:panelGrid columns="2" id="bank_account_panel" rendered="#{loanBean.renderBankAccountPanel}">
	                    <h:selectOneMenu id="bank_account_menu" styleClass="fieldinput"
	                        value="#{loanBean.bankAccountId}" rendered="true">
	                        <f:selectItems value="#{loanBean.bankAccountList}" />
	                    </h:selectOneMenu>
	                    <h:commandButton value="Add Bank Account" rendered="true"
	                        onclick="submit()"
	                        action="#{loanBean.addBankAccountAction}" image="plus_green.gif"/>
                	</h:panelGrid>
                	</div>
					</td>
					</tr>
                
                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="requiredfieldlabel" id="check_number_label" 
                    	value="Check Number:" rendered="#{loanBean.renderCheckNumberPanel}"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:inputText styleClass="fieldinput" value="#{loanBean.checkNumber}" 
                    	rendered="#{loanBean.renderCheckNumberPanel}"/>
                    </div>
                	</td>
                	</tr>
                
                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="requiredfieldlabel" id="money_order_label" 
                    	value="Money Order" rendered="#{loanBean.renderMoneyOrderPanel}"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:panelGrid columns="2" id="money_order_panel" rendered="#{loanBean.renderMoneyOrderPanel}">
	                    <h:selectOneMenu id="money_order_menu" styleClass="fieldinput"
	                        value="#{loanBean.moneyOrderId}" rendered="true"
	                        valueChangeListener="#{loanBean.updateByMoneyOrder}" immediate="true" onchange="submit()">
	                        <f:selectItems value="#{loanBean.moneyOrderList}" />
	                    </h:selectOneMenu>
	                    <h:commandButton value="Add Money Order" rendered="true"
	                        onclick="submit()"
	                        action="#{loanBean.addMoneyOrderAction}" image="plus_green.gif"/>
                	</h:panelGrid>
                	</div>
                	</td>
                	</tr>


					<tr>
					<td> 
					<div align="left">               
                    <h:outputText styleClass="requiredfieldlabel" id="credit_card_label" value="Credit Card" rendered="#{loanBean.renderCreditCardPanel}"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:panelGrid columns="2" id="credit_card_panel" rendered="#{loanBean.renderCreditCardPanel}">
	                    <h:selectOneMenu id="credit_card_menu" styleClass="fieldinput"
	                        value="#{loanBean.creditCardId}" rendered="true">
	                        <f:selectItems value="#{loanBean.creditCardList}" />
	                    </h:selectOneMenu>
	                    <h:commandButton value="Add Credit Card" rendered="true"
	                        onclick="submit()"
	                        action="#{loanBean.addCreditCardAction}" image="plus_green.gif"/>
                	</h:panelGrid>
                	</div>
                	</td>
                	</tr>


                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="requiredfieldlabel" value="Borrower"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:panelGrid columns="2">
	                    <h:selectOneMenu id="borrower" styleClass="fieldinput"
	                        value="#{loanBean.borrowerId}">
	                        <f:selectItems value="#{loanBean.borrowerList}" />
	                    </h:selectOneMenu>
	                    <h:commandButton value="Add Borrower" rendered="true"
	                        onclick="submit()"
	                        action="#{loanBean.addBorrowerAction}" image="plus_green.gif"/>
                	</h:panelGrid>
                	</div>
					</td>
					</tr>
                
                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="fieldlabel" value="Payee (If not same as Borrower)"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:panelGrid columns="2">
	                    <h:selectOneMenu id="payee" styleClass="fieldinput"
	                        value="#{loanBean.payeeId}">
	                        <f:selectItems value="#{loanBean.payeeList}" />
	                    </h:selectOneMenu>
	                    <h:commandButton value="Add Payee" rendered="true"
	                        onclick="submit()"
	                        action="#{loanBean.addPayeeAction}" image="plus_green.gif"/>
                	</h:panelGrid>
                	</div>
					</td>
					</tr>
                
                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="fieldlabel" value="Project"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:panelGrid columns="2">
	                    <h:selectOneMenu id="expense_group" styleClass="fieldinput"
	                        value="#{loanBean.groupId}">
	                        <f:selectItems value="#{loanBean.expenseGroupList}" />
	                    </h:selectOneMenu>
	                    <h:commandButton value="Add Group" rendered="true"
	                        onclick="submit()"
	                        action="#{loanBean.addExpenseGroupAction}" image="plus_green.gif"/>
                	</h:panelGrid>
                	</div>
					</td>
					</tr>
                	
                	<tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="fieldlabel" value="Description" rendered ="true"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:inputText styleClass="fieldinput" value="#{loanBean.description}" rendered="true"/>
                    </div>
                    </td>
                    </tr>                    
            	</table>  
            	
            	<br>  
            	
            	<rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{loanBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{loanBean.createAction}" />					
				</h:panelGrid>				
				</div>
				<rich:separator/>
            
        </h:form>
    </body>
</html>

</f:view>
