<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%--
    This file is an entry point for JavaServer Faces application.
--%>

<f:view>

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Expense Tracking System</title>
        </head>

        <body>
            <a4j:keepAlive beanName="expenseTypeListBean"></a4j:keepAlive>
            <a4j:keepAlive beanName="bankAccountBean"></a4j:keepAlive>
            
            <h:form>
                <link href="style.css" rel="stylesheet" type="text/css"/>

                
                <rich:tabPanel width="400" switchType="ajax" selectedTab="#{mainPageBean.selectedTabId}" >
                    <rich:tab label="Expenses" id="expenses_tab_item">

                        <a4j:commandLink immediate="true" value="New expense"
                            oncomplete="#{rich:component('addExpensePanel')}.show()"
                            >
                        </a4j:commandLink>

                        <rich:spacer height="30"/>

                        <a4j:region id="expenses_summary">
                            <h:panelGrid columns="5">
                            <h:outputLabel id="from_expense_date_label" value="From"/>
                            <rich:calendar id="from_expense_date" immediate="true" value="#{expenseListBean.fromDate}"/>
                            <h:outputLabel id="to_expense_date_label" value="To"/>
                            <rich:calendar id="to_expense_date" immediate="true" value="#{expenseListBean.toDate}"/>
                            <a4j:commandButton value="Search" reRender="expensesList"/>
                        </h:panelGrid>

                        <rich:spacer height="30"/>


                        <!--
                        <rich:datascroller align="center" for="expensesList" maxPages="20" page="#{expenseListBean.scrollerPage}" reRender="sc2" id="sc1"/>
                        -->

                        <rich:spacer height="30"/>

                        <rich:dataTable width="483" id="expensesList" rows="10" columnClasses="col"
                                        value="#{expenseListBean.expensesList}" var="account">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <h:column>
                                        <h:outputText styleClass="headerText" value="Date" />
                                    </h:column>
                                    <h:column>
                                        <h:outputText styleClass="headerText" value="Amount" />
                                    </h:column>
                                    <h:column>
                                        <h:outputText styleClass="headerText" value="Expense Type" />
                                    </h:column>
                                    <h:column>
                                        <h:outputText styleClass="headerText" value="Expense Group" />
                                    </h:column>
                                    <h:column>
                                        <h:outputText styleClass="headerText" value="Payee" />
                                    </h:column>
                                    <h:column>
                                        <h:outputText styleClass="headerText" value="Payment Method" />
                                    </h:column>
                                </rich:columnGroup>
                            </f:facet>

                            <h:column>
                                <h:outputLabel value="#{account.dateStr}"/>
                            </h:column>
                            <h:column>
                                <h:outputLabel value="#{account.amount}" />
                            </h:column>
                            <h:column>
                                <h:outputLabel value="#{account.expenseType}"/>
                            </h:column>
                            <h:column>
                                <h:outputLabel value="#{account.group}"/>
                            </h:column>
                            <h:column>
                                <h:outputLabel value="#{account.payee}"/>
                            </h:column>
                            <h:column>
                                <h:outputLabel value="#{account.paymentMethod}"/>
                            </h:column>
                        </rich:dataTable>

                        <rich:datascroller align="center" for="expensesList" maxPages="20" page="#{expenseListBean.scrollerPage}" reRender="sc1" id="sc2"/>

                        </a4j:region>
                    </rich:tab>                                    

                    <rich:tab label="Bank Accounts" id="bank_accounts_tab_item">

                        <a4j:region id="bank_account_summary">
                        <rich:dataTable width="483" id="bankAccountList" rows="10" columnClasses="col"
                            value="#{bankAccountListBean.accounts}" var="account" rowKeyVar="index">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <h:column>
                                            <h:outputText styleClass="headerText" value="Description" />
                                        </h:column>
                                        <h:column>
                                            <h:outputText styleClass="headerText" value="Type" />
                                        </h:column>
                                        <h:column>
                                            <h:outputText styleClass="headerText" value="Balance" />
                                        </h:column>
                                    </rich:columnGroup>
                                </f:facet>

                                <h:column>
                                    <a4j:commandLink value="#{account.description}"
                                    actionListener="#{bankAccountListBean.accountSummaryAction}"
                                    immediate="true" reRender="statementItems, account_balance, account_name">
                                        <f:param name="modelRow" value="#{account.id}"/>
                                    </a4j:commandLink>
                                </h:column>
                                <h:column>
                                    <h:outputText value="#{account.type}"/>
                                </h:column>
                                <h:column>
                                    <h:outputText value="#{account.balance}"/>
                                </h:column>
                            </rich:dataTable>
                        </a4j:region>
                        

                        <br><br><br>

                        <a4j:region id="bank_account_details" rendered="#{statementBean.rendered}">

                        <h:panelGrid columns="2">
                            <h:outputText value="Bank Account Transactions for Account " styleClass="title"/>
                            <h:outputText id="account_name" value="#{statementBean.bankName}" styleClass="name"/>
                        </h:panelGrid>

                        <br>
                            
                        <h:panelGrid columns="5">


                            <h:outputLabel id="from_date_label" value="From"/>
                            <rich:calendar id="from_date" immediate="true" value="#{statementBean.fromDate}"/>
                            <h:outputLabel id="to_date_label" value="To"/>
                            <rich:calendar id="to_date" immediate="true"  value="#{statementBean.toDate}"/>
                            <a4j:commandButton value="Search" reRender="statementItems, account_balance"/>
                        </h:panelGrid>

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

                        <h:panelGrid columns="2">
                            <h:outputLabel value="Balance"/>
                            <h:outputText id="account_balance" value="#{statementBean.bankAccountBalance}"/>
                        </h:panelGrid>
                        </a4j:region>




                    </rich:tab>
                </rich:tabPanel>

                <br>
                <h:messages styleClass="errorMessage" globalOnly="true"/>
            </h:form>

            <rich:modalPanel id="addExpensePanel" autosized="true" >
                <f:facet name="header">
                    Add new expense
                </f:facet>
                <h:form>
                    <h:panelGrid id="newExpenseInfo" columns="1">
                        <h:panelGrid columns="2">
                            <h:outputText value="Expense Date:"/>
                            <rich:calendar value="#{expenseBean.date}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" id="expense_amount_panel" rendered="#{expenseBean.renderExpenseAmountPanel}">
                            <h:outputText value="Expense Amount:" rendered ="true"/>
                            <h:inputText value="#{expenseBean.amount}" rendered="true"/>
                        </h:panelGrid>


                        <h:panelGrid columns="2">
                            <h:outputText value="Payment Method:"/>
                            <h:selectOneMenu id="payment_method"
                                         value="#{expenseBean.paymentMethodId}"
                                         valueChangeListener="#{expenseBean.updateByPaymentMethod}"
                                         immediate="true">
                                <f:selectItems value="#{expenseBean.paymentMethodList}" />
                                <a4j:support event="onchange" reRender="paymentMethodPanels, bank_account_panel,
                                check_panel, money_order_panel, credit_card_panel" />
                            </h:selectOneMenu>
                        </h:panelGrid>
                        <h:panelGrid id="paymentMethodPanels">
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

                        <h:panelGrid columns="1">
                            <h:outputText value="Select an Expense Type:"/>
                            <rich:tree id="expenseCategoryTree" style="width:300px"
                            nodeSelectListener="#{expenseBean.processSelection}"
                            changeExpandListener="#{expenseBean.changeExpandListener}"
                            reRender="selectedNode"
                            ajaxSubmitSelection="true"
                            switchType="client" value="#{expenseBean.treeNode}"
                            var="item" immediate="true" adviseNodeOpened="#{expenseBean.adviseNodeOpened}"
                            adviseNodeSelected="#{expenseBean.adviseNodeSelected}">

                            </rich:tree>
                            <h:commandButton value="Manage Expense Categories" rendered="true"
                                onclick="submit()"
                                action="#{expenseBean.manageExpenseCategoriesAction}"/>
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
                            <a4j:commandButton value="Submit"
                            action="#{expenseBean.createAction}"
                            reRender="expensesList, sc2"
                            oncomplete="#{rich:component('addExpensePanel')}.hide()"/>
                            
                            <a4j:commandButton value="Cancel" immediate="true"
                            action="#{expenseBean.cancelAction}"
                            oncomplete="#{rich:component('addExpensePanel')}.hide()"
                            />
                        </h:panelGrid>

                        <h:messages styleClass="errorMessage" globalOnly="true"/>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>

</f:view>