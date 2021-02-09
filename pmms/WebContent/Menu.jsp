<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Summary of Bank Accounts</title>
        </head>
        <body>
        	<%trackit.backingbeans.SessionBean sessionBean = (trackit.backingbeans.SessionBean) request.getSession().getAttribute("sessionBean");%>
			
            <table bgcolor="#3366FF">            
            <%if (sessionBean.canViewAccountUsers()){%>
            <tr><td><input type="button" value="Account Users" onclick="window.location.href='AccountUsers.jsf'" style="width:200px;"></td></tr>
            <%}%>
            <%if (sessionBean.canViewCashTransfers()){%>
            <tr><td><input type="button" value="Cash Transfers" onclick="window.location.href='CashTransfers.jsf'" style="width:200px;"></td></tr>
            <%}%>
            <%if (sessionBean.canViewBankAccounts()){%>
            <tr><td><input type="button" value="Bank Accounts" onclick="window.location.href='BankAccounts.jsf'" style="width:200px;"></td></tr>
            <%}%>
            <%if (sessionBean.canViewCreditCards()){%>
            <tr><td><input type="button" value="Credit Cards" onclick="window.location.href='CreditCards.jsf'" style="width:200px;"></td></tr>
            <%}%>
            
            <tr><td><input type="button" value="Expense Summary" onclick="window.location.href='Expenses.jsf'" style="width:200px;"></td></tr>
            
            <%if (sessionBean.canViewLoanSummary()){%>
            <tr><td><input type="button" value="Loan Summary" onclick="window.location.href='LoanSummary.jsf'" style="width:200px;"></td></tr>
            <%}%>
            <%if (sessionBean.canViewCurrencyOnHand()){%>
            <tr><td><input type="button" value="Currency On Hand" onclick="window.location.href='CurrencyOnHandSummary.jsf'" style="width:200px;"></td></tr>
            <%}%>
            <%if (sessionBean.canViewExpenseGroupReport()){%>
            <tr><td><input type="button" value="Expense Group Report" onclick="window.location.href='ExpenseGroupReport.jsf'" style="width:200px;"></td></tr>
            <%}%>
            <%if (sessionBean.canViewCapitalReport()){%>
            <tr><td><input type="button" value="Capital Report" onclick="window.location.href='CapitalReport.jsf'" style="width:200px;"></td></tr>
            <%}%>
            <%if (sessionBean.canViewCharts()){%>            
            <tr><td><input type="button" value="Charts" onclick="window.location.href='Charts.jsf'" style="width:200px;"></td></tr>
            <%}%>            
        </table>

        </body>
    </html>
