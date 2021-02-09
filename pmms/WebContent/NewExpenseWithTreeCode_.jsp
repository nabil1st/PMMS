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
        <title>New Expense</title>
    </head>
    <body>
        <h2>New Expense:</h2>
        
        <h:form id="NewExpense">
            <h:panelGrid columns="1">
                <h:panelGrid columns="2">
                    <h:outputText value="Expense Date:"/>
                    <rich:calendar value="#{expenseBean.date}"/>
                </h:panelGrid>
                <h:panelGrid columns="2">
                    <h:outputText value="Payment Method:"/>
                    <h:selectOneMenu id="payment_method"
                                 value="#{expenseBean.paymentMethodId}"
                                 valueChangeListener="#{expenseBean.updateByPaymentMethod}"
                                 onchange="submit()" immediate="true">
                        <f:selectItems value="#{expenseBean.paymentMethodList}" />
                    </h:selectOneMenu>
                </h:panelGrid>
                <h:panelGrid columns="2" id="expense_amount_panel" rendered="#{expenseBean.renderExpenseAmountPanel}">
                    <h:outputText value="Expense Amount:" rendered ="true"/>
                    <h:inputText value="#{expenseBean.amount}" rendered="true"/>
                </h:panelGrid>                
                <h:panelGrid columns="3" id="bank_account_panel" rendered="#{expenseBean.renderBankAccountPanel}">
                    <h:outputText id="bank_account_label" value="Bank Account:" rendered="true"/>
                    <h:selectOneMenu id="bank_account_menu" 
                        value="#{expenseBean.bankAccountId}" rendered="true">
                        <f:selectItems value="#{expenseBean.bankAccountList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Bank Account" rendered="true"
                        onclick="submit()"
                        action="#{expenseBean.addBankAccountAction}"/>
                </h:panelGrid>

                <h:panelGrid columns="3" id="check_panel" rendered="#{expenseBean.renderCheckNumberPanel}">
                    <h:outputText id="check_number_label" value="Check Number:" rendered="true"/>
                    <h:inputText value="#{expenseBean.checkNumber}" rendered="true"/>
                </h:panelGrid>

                <h:panelGrid columns="3" id="money_order_panel" rendered="#{expenseBean.renderMoneyOrderPanel}">
                    <h:outputText id="money_order_label" value="Money Order:" rendered="true"/>
                    <h:selectOneMenu id="money_order_menu" 
                        value="#{expenseBean.moneyOrderId}" rendered="true">
                        <f:selectItems value="#{expenseBean.moneyOrderList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Money Order" rendered="true"
                        onclick="submit()"
                        action="#{expenseBean.addMoneyOrderAction}"/>
                </h:panelGrid>
                

                <h:panelGrid columns="3" id="credit_card_panel" rendered="#{expenseBean.renderCreditCardPanel}">
                    <h:outputText id="credit_card_label" value="Credit Card:" rendered="true"/>
                    <h:selectOneMenu id="credit_card_menu" 
                        value="#{expenseBean.creditCardId}" rendered="true">
                        <f:selectItems value="#{expenseBean.creditCardList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Credit Card" rendered="true"
                        onclick="submit()"
                        action="#{expenseBean.addCreditCardAction}"/>
                </h:panelGrid>

                <h:panelGrid columns="3">
                    <h:outputText value="Expense Group:"/>
                    <h:selectOneMenu id="expense_group"
                        value="#{expenseBean.expenseGroupId}">
                        <f:selectItems value="#{expenseBean.expenseGroupList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Expense Group" rendered="true" image="plus_green.gif"
                        onclick="submit()"
                        action="#{expenseBean.addExpenseGroupAction}"/>
                </h:panelGrid>

                <br>
                
                <h:panelGrid columns="2">
                    <h:outputText value="Expense Type:"/>
                    <h:selectOneMenu id="expense_category_type"
                        value="#{expenseBean.expenseTypeId}">
                        <f:selectItems value="#{expenseBean.expenseCategoryTypeList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Expense Type" rendered="true" image="plus_green.gif"
                        onclick="submit()"
                        action="#{expenseBean.addExpenseTypeAction}"/>
                </h:panelGrid>

               

                <br>


                <h:panelGrid columns="3">
                    <h:outputText value="Payee:"/>
                    <h:selectOneMenu id="payee" 
                        value="#{expenseBean.payeeId}">
                        <f:selectItems value="#{expenseBean.payeeList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Payee" rendered="true"
                        onclick="submit()"
                        action="#{expenseBean.addPayeeAction}" image="plus_green.gif"/>
                </h:panelGrid>

                <h:panelGrid columns="2">
                    <h:outputText value="Description:" rendered ="true"/>
                    <h:inputText value="#{expenseBean.description}" rendered="true"/>
                </h:panelGrid>

                <h:panelGrid columns="3">
                    <h:commandButton value="Submit" action="#{expenseBean.createAction}"/>
                    <h:commandButton value="Itemize" action="#{expenseBean.itemizeAction}"/>
                    <h:commandButton value="Cancel" immediate="true" action="#{expenseBean.cancelAction}"/>
                </h:panelGrid>

                <h:messages styleClass="errorMessage" globalOnly="true"/>                
            </h:panelGrid>
        </h:form>
    </body>
</html>

</f:view>
