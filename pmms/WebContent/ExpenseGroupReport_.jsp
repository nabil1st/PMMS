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
            <title>Currency On Hand</title>
        </head>
        <body>

            <h:form id="ExpenseGroupReport">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="1">
                    <h:outputText value="Project Report" styleClass="title"/>
                </h:panelGrid>

                
                <br>

                <h:panelGrid columns="1">

                
                <h:panelGrid columns="3">
                    <h:outputText value="Select Project"/>
                    <h:selectOneMenu id="expense_group"
                            value="#{expenseGroupReportBean.groupId}">
                                <f:selectItems value="#{expenseGroupReportBean.groupList}" />
                    </h:selectOneMenu>

                    <h:commandButton value="Search" action="SHOW_EXPENSE_GROUP_REPORT"/>
                </h:panelGrid>

                

                <rich:spacer height="30"/>
                
                <rich:spacer height="10"/>

                <h:outputLabel value="Expense Summary:"/>
                <rich:dataTable width="483" id="expensesList" rows="10" columnClasses="col"
                value="#{expenseGroupReportBean.expensesList}" var="expense">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Type" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Payee" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Payment Method" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Check Number" />
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
                        <h:outputLabel value="#{expense.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{expense.expenseType}" />
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{expense.payee}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{expense.paymentMethod}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{expense.checkNumber}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{expense.description}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{expense.amount}"/>
                    </h:column>                    
                </rich:dataTable>

                <h:panelGrid columns="2">
                    <h:outputText value="Expenses Total = "/>
                    <h:outputText value="#{expenseGroupReportBean.expensesTotal}"/>
                </h:panelGrid>

                <rich:spacer height="30"/>

                <h:outputLabel value="Income Summary:"/>
                <rich:dataTable width="483" id="incomeList" rows="10" columnClasses="col"
                value="#{expenseGroupReportBean.incomesForGroup}" var="income">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Source" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Source Type" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Currency Type" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:outputLabel value="#{income.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{income.source}" />
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{income.sourceType}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{income.currencyType}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{income.amount}"/>
                    </h:column>
                </rich:dataTable>

                <h:panelGrid columns="2">
                    <h:outputText value="Income Total = "/>
                    <h:outputText value="#{expenseGroupReportBean.incomesTotal}"/>
                </h:panelGrid>

                <rich:spacer height="30"/>


                <h:outputLabel value="Loan Summary:"/>
                <rich:dataTable width="483" id="loanList" rows="10" columnClasses="col"
                value="#{expenseGroupReportBean.loansList}" var="loan">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Borrower" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Description" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Balance" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:outputLabel value="#{loan.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{loan.borrowerName}" />
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{loan.description}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{loan.amount}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{loan.balance}"/>
                    </h:column>
                </rich:dataTable>

                <h:panelGrid columns="2">
                    <h:outputText value="Loan Balance Total = "/>
                    <h:outputText value="#{expenseGroupReportBean.loanBalanceTotal}"/>
                </h:panelGrid>

                <rich:spacer height="30"/>

                <h:panelGrid columns="2">
                    <h:outputText value="Income - Expenses = "/>
                    <h:outputText value="#{expenseGroupReportBean.incomeMinusExpensesTotal}"/>
                    <h:outputText value="Income + Loan Balances - Expenses = "/>
                    <h:outputText value="#{expenseGroupReportBean.incomePlusLoanBalanceMinusExpensesTotal}"/>
                </h:panelGrid>

                </h:panelGrid>                
                
            </h:form>
        </body>
    </html>

</f:view>
