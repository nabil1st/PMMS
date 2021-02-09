<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:loadBundle basename="messages" var="msg"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">



            <h:panelGrid columns="1">
                <h><a href='faces/AccountUsers.jsp'>Account Users</a></h><br>
                <h><a href='faces/BankAccounts.jsp'>Bank Accounts</a></h><br>
                <h><a href='faces/CreditCards.jsp'>Credit Cards</a></h><br>
                <h><a href='faces/Expenses.jsp'>Expenses Summary</a></h><br>
                <h><a href='faces/LoanSummary.jsp'>Loans Summary</a></h><br>
                <h><a href='faces/CurrencyOnHandSummary.jsp'>Currency On Hand Summary</a></h><br>
                <h><a href='faces/ExpenseGroupReport.jsp'>Expense Group Report</a></h><br>
                <h><a href='faces/CapitalReport.jsp'>Capital Report</a></h><br>
                <h:commandLink value="Logout" action="#{userBean.logoutAction}"/>
            </h:panelGrid>
