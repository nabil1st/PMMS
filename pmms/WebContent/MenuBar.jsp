<%-- 
    Document   : ExpensesSummaryTab
    Created on : Jul 23, 2009, 4:17:37 PM
    Author     : ndaoud
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   
<%
	trackit.backingbeans.SessionBean sessionBean = (trackit.backingbeans.SessionBean) request
			.getSession().getAttribute("sessionBean");
%>

<rich:toolBar>
	<%if (sessionBean.canViewAccountUsers()) { %>
	<rich:dropDownMenu>
         <f:facet name="label">
             <h:outputText value="Account Users"/>
         </f:facet>
         <rich:menuItem value="Summary" immediate="true" action="account_user_summary"/>
         <rich:menuSeparator/>
         <rich:menuItem value="Cash Transfers" immediate="true" action="cash_transfers_summary"/>
     </rich:dropDownMenu>
     <%} %>
     
     <%if (sessionBean.canViewExpenseInfo()) { %>
     <rich:dropDownMenu>
         <f:facet name="label">
             <h:outputText value="Expenses"/>
         </f:facet>
         <rich:menuItem value="Summary" immediate="true" action="show_expenses"/>
         <rich:menuItem value="New Expense" immediate="true" action="create_expense_requested"/>
         <rich:menuItem value="Itemize Expense" immediate="true" action="ITEMIZE_EXPENSE_REQUESTED"/>
         <rich:menuItem value="Expense Categories" immediate="true" action="create_expense_type_requested"/>
         <rich:menuSeparator/>                      
         <rich:menuItem value="Projects" immediate="true" action="EXPENSE_GROUPS"/>
         <rich:menuSeparator/>                      
         <rich:menuItem value="Payess" immediate="true" action="PAYEES"/>  
     </rich:dropDownMenu>
     <%} %>
     
     <%if (sessionBean.canViewBankAccounts()) { %>
     <rich:dropDownMenu>
         <f:facet name="label">
             <h:outputText value="Bank Accounts"/>
         </f:facet>
         <rich:menuItem value="Summary" immediate="true" action="BANK_ACCOUNTS_SUMMARY"/>
         
     </rich:dropDownMenu>
     <%} %>
     
     <%if (sessionBean.canViewCreditCards()) { %>
     <rich:dropDownMenu>
         <f:facet name="label">
             <h:outputText value="Credit Cards"/>
         </f:facet>
         <rich:menuItem value="Summary" immediate="true" action="CREDIT_CARD_SUMMARY"/>         
     </rich:dropDownMenu>
     <%} %>
     
     <%if (sessionBean.canViewCurrencyOnHand()) { %>
     <rich:dropDownMenu>
         <f:facet name="label">
             <h:outputText value="Currency on Hand"/>
         </f:facet>
         <rich:menuItem value="Summary" immediate="true" action="SHOW_CURRENCY_ON_HAND_SUMMARY"/>
         <rich:menuItem value="Income Sources" immediate="true" action="SHOW_INCOME_SOURCES"/>
         
     </rich:dropDownMenu>
     <%} %>
     
     <%if (sessionBean.canViewLoanSummary()) { %>
     <rich:dropDownMenu>
         <f:facet name="label">
             <h:outputText value="Loans"/>
         </f:facet>
         <rich:menuItem value="Summary" immediate="true" action="SHOW_LOAN_SUMMARY"/>
         <rich:menuItem value="Borrowers" immediate="true" action="SHOW_BORROWERS"/>
         
     </rich:dropDownMenu>
     <%} %>
     
     <%if (sessionBean.canViewReports()) { %>
     <rich:dropDownMenu>
         <f:facet name="label">
             <h:outputText value="Reports"/>
         </f:facet>
         <rich:menuItem value="Project Report" immediate="true" action="show_expense_group_report"/>
         <rich:menuItem value="Capital Report" immediate="true" action="show_capital_report"/>
         <rich:menuItem value="Expense Report" immediate="true" action="show_expenses_report"/>
                  
     </rich:dropDownMenu>      
     <%} %>              
 </rich:toolBar>