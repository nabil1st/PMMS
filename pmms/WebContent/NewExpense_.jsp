<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@page import="trackit.backingbeans.ExpenseBean"%>

<f:loadBundle basename="messages" var="msg" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<f:view>


	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>PMMS</title>
	</head>
	<body>
	<h:form id="NewExpense">
		<link href="style.css" rel="stylesheet" type="text/css"/>
		<%@include file="MenuBar.jsp"%>
		
		<%ExpenseBean eb = (ExpenseBean) request.getSession().getAttribute("expenseBean");%>
		<br>
		<h:messages styleClass="errorMessage" globalOnly="true" />
		<h:panelGrid columns="1">
			<h:outputText value="#{expenseBean.title}" styleClass="subtitle"/>        	        	
        </h:panelGrid>
        
        
		<br>
			<table>
				
				<tr>
					<td><div align="left"><h:outputText styleClass="requiredfieldlabel" value="Amount" 
						rendered="true" /></div></td>
					<td width="10"></td>
					<td><div align="left"><h:inputText styleClass="fieldinput" value="#{expenseBean.amount}" 
						rendered="true" disabled="#{!expenseBean.renderExpenseAmountPanel}"/></div></td>
				</tr>
				
				
				<tr>
					<td><div align="left"><h:outputText styleClass="requiredfieldlabel" value="Date"/></div></td>
					<td width="10"></td>
					<td><div align="left">
					<rich:calendar value="#{expenseBean.date}" inputClass="fieldinput"/>
					</div></td>
				</tr>
				
				<tr>
					<td><div align="left"><h:outputText styleClass="requiredfieldlabel" value="Purpose"/></div></td>
					<td width="10"></td>
					<td><div align="left"><h:selectOneMenu id="expense_purpose" styleClass="fieldinput"
							value="#{expenseBean.expensePurposeId}">
							<f:selectItems value="#{expenseBean.expensePurposeList}"/>
					</h:selectOneMenu></div></td>
				</tr>				
				
				<tr>
					<td><div align="left"><h:outputText styleClass="requiredfieldlabel" value="Payment Method"/></div></td>
					<td width="10"></td>
					<td>
					<div align="left">
					<h:selectOneMenu id="payment_method" styleClass="fieldinput"
						value="#{expenseBean.paymentMethodId}"
						valueChangeListener="#{expenseBean.updateByPaymentMethod}"
						onchange="submit()" immediate="true">
						<f:selectItems value="#{expenseBean.paymentMethodList}" />
					</h:selectOneMenu>
					</div>
					</td>
				</tr>
				
				<%if(eb != null && eb.isRenderMoneyOrderPanel()) {%>
				<tr>
					<td><div align="left">
					<h:outputText value="Money Order" styleClass="requiredfieldlabel"
						rendered="true"/>
					</div></td>
					<td width="10"></td>
					<td><div align="left">
					<h:panelGrid columns="2" rendered="true">
					<h:selectOneMenu id="money_order_menu" styleClass="fieldinput"
						value="#{expenseBean.moneyOrderId}" rendered="true"
						valueChangeListener="#{expenseBean.updateByMoneyOrder}" immediate="true" onchange="submit()">
						<f:selectItems value="#{expenseBean.moneyOrderList}" />
					</h:selectOneMenu>
					<h:commandButton  image="plus_green.gif" title="Add Money Order" rendered="true"
						onclick="submit()" action="#{expenseBean.addMoneyOrderAction}" />
					</h:panelGrid>
					</div></td>
				</tr>
				<%} %>
				
				<%if(eb != null && eb.isRenderBankAccountPanel()) {%>
				<tr>
					<td><div align="left"><h:outputText styleClass="requiredfieldlabel" value="Bank Account" 
					rendered="#{expenseBean.renderBankAccountPanel}"/></div></td>
					<td width="10"></td>
					<td><div align="left">					
					<h:panelGrid columns="2" rendered="#{expenseBean.renderBankAccountPanel}">
					<h:selectOneMenu id="bank_account_menu" styleClass="fieldinput"
						value="#{expenseBean.bankAccountId}" rendered="true">
						<f:selectItems value="#{expenseBean.bankAccountList}" />
					</h:selectOneMenu>
					<h:commandButton title="Add Bank Account" rendered="true" image="plus_green.gif"
						onclick="submit()" action="#{expenseBean.addBankAccountAction}" />
					</h:panelGrid>
					</div></td>
				</tr>
				<%} %>
				
				<%if(eb != null && eb.isRenderCheckNumberPanel()) {%>
				<tr>
					<td><div align="left"><h:outputText styleClass="requiredfieldlabel" value="Check Number" 
						rendered="#{expenseBean.renderCheckNumberPanel}"/></div></td>
					<td width="10"></td>
					<td><div align="left">
					<h:inputText styleClass="fieldinput" value="#{expenseBean.checkNumber}" 
					rendered="#{expenseBean.renderCheckNumberPanel}" />
					</div></td>
				</tr>
				<%}%>
				
				<%if(eb != null && eb.isRenderCreditCardPanel()) {%>
				<tr>				
					<td><div align="left">
					<h:outputText value="Credit Card" styleClass="requiredfieldlabel"
						rendered="#{expenseBean.renderCreditCardPanel}"/>
					</div></td>
					<td width="10"></td>
					<td><div align="left">
					<h:panelGrid columns="2" rendered="#{expenseBean.renderCreditCardPanel}">
						<h:selectOneMenu id="credit_card_menu" styleClass="fieldinput"
							value="#{expenseBean.creditCardId}" rendered="true">
							<f:selectItems value="#{expenseBean.creditCardList}" />
						</h:selectOneMenu>
						<h:commandButton title="Add Credit Card" rendered="true" image="plus_green.gif"
							onclick="submit()" action="#{expenseBean.addCreditCardAction}" />
					</h:panelGrid>
					</div></td>
				</tr>
				<%} %>
				
				<tr>
					<td><div align="left">
					<h:outputText value="Project" styleClass="fieldlabel"/>
					</div></td>
					<td width="10"></td>
					<td><div align="left">
					<h:panelGrid columns="2">
						<h:selectOneMenu id="expense_group" styleClass="fieldinput"
						value="#{expenseBean.expenseGroupId}">
						<f:selectItems value="#{expenseBean.expenseGroupList}" />
					</h:selectOneMenu>
					<h:commandButton value="Add Project" rendered="true"
						image="plus_green.gif" onclick="submit()"
						action="#{expenseBean.addExpenseGroupAction}" />
					</h:panelGrid>
					</div></td>
				</tr>
				<tr>
					<td><div align="left">
					<h:outputText value="Expense Type" styleClass="requiredfieldlabel"/>
					</div></td>
					<td width="10"></td>
					<td><div align="left">
					<h:panelGrid columns="2">
						<h:selectOneMenu id="expense_category_type" styleClass="fieldinput"
						value="#{expenseBean.expenseTypeId}">
						<f:selectItems value="#{expenseBean.expenseCategoryTypeList}" />
					</h:selectOneMenu>
					<h:commandButton value="Add Expense Type" rendered="true"
						image="plus_green.gif" onclick="submit()"
						action="#{expenseBean.addExpenseTypeAction}" />
					</h:panelGrid>
					</div></td>
				</tr>
				<tr>
					<td><div align="left">
					<h:outputText value="Payee" styleClass="requiredfieldlabel"/>
					</div></td>
					<td width="10"></td>
					<td><div align="left">
					<h:panelGrid columns="2">
						<h:selectOneMenu id="payee" value="#{expenseBean.payeeId}" styleClass="fieldinput">
						<f:selectItems value="#{expenseBean.payeeList}" />
					</h:selectOneMenu>
					<h:commandButton value="Add Payee" rendered="true"
						onclick="submit()" action="#{expenseBean.addPayeeAction}"
						image="plus_green.gif" />
					</h:panelGrid>
					</div></td>
				</tr>
				<tr>
					<td><div align="left">
					<h:outputText value="Notes" styleClass="fieldlabel"/>
					</div></td>
					<td width="10"></td>
					<td><div align="left">
					<h:inputText value="#{expenseBean.description}" rendered="true" styleClass="fieldinput"/>
					</div></td>
				</tr>
			</table>
			<br>
			<rich:separator/>
			<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{expenseBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{expenseBean.createAction}" />					
				</h:panelGrid>				
			</div>
			<rich:separator/>
			
			<h:inputHidden id="oldMoneyOrderId" value="#{expenseBean.oldMoneyOrderId}"/>
			<h:inputHidden id="editMode" value="#{expenseBean.editMode}"/>
		
	</h:form>
	</body>
	</html>

</f:view>
