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
                <rich:tabPanel switchType="ajax" selectedTab="#{mainPageBean.selectedTabId}" >
                    <rich:tab label="Expenses" id="expenses_tab_item">
                        <f:facet name="label">
                            Manage your expenses here.
                        </f:facet>

                        <rich:tabPanel switchType="ajax" id="expenses_tab" selectedTab="#{mainPageBean.selectedExpensesTabId}" >
                            <rich:tab id="expenses_summary_tab_item" label="Expenses Summary">
                                <f:facet name="label">
                                    Expenses Summary
                                </f:facet>
                                <%@include file="ExpensesSummaryTab.jsp"%>
                            </rich:tab>
                            <rich:tab id="expense_types_tab_item" label="Expense Types">
                                <f:facet name="label">
                                    Expense Types
                                </f:facet>
                                <%@include file="ExpenseTypesTab.jsp"%>
                            </rich:tab>
                            
                        </rich:tabPanel>
                    </rich:tab>
                    <rich:tab label="Bank Transactions" id="bank_transactions_tab">
                        <h:outputText styleClass="headerText" value="Manage your bank transactions here."/>

                        <rich:tabPanel switchType="ajax" id="bank_transactions_tab_item" selectedTab="#{mainPageBean.selectedBankTransactionsTabId}" >
                            <rich:tab id="bank_accounts_summary_tab_item" label="Bank Accounts Summary">
                                <f:facet name="label">
                                    Bank Accounts Summary
                                </f:facet>
                                <%@include file="BankSummaryTab.jsp"%>
                            </rich:tab>
                        </rich:tabPanel>
                    </rich:tab>
                </rich:tabPanel>

                <br>
                <h:messages styleClass="errorMessage" globalOnly="true"/>
            </h:form>

            <%@include file="ExpenseTypeEditPanel.jsp"%>
            <%@include file="ExpenseTypeAddPanel.jsp"%>
            <%@include file="BankAccountAddPanel.jsp"%>
        </body>
    </html>

</f:view>