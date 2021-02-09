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
            <title>Capital Report</title>
        </head>
        <body>

            <h:form id="CapitalReport">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="1">
                    <h:outputText value="Capital Report" styleClass="title"/>
                </h:panelGrid>
                
                <br>                			   
			    <rich:tabPanel switchType="client" width="675" >
			        <rich:tab label="Summary" >
			        	<table>
			        		<tr>
			        		<td><div align="left">			            
		                    	<h:outputText value="Bank Accounts Balance" styleClass="summary"/>
		                    </div></td>
		                    <td width="10"/>
		                    <td><div align="left">
		                    	<h:outputText value="#{capitalReportBean.bankAccountBalance}" styleClass="value"/>
		                    </div></td>
		                    </tr>
		                    <tr>
			        		<td><div align="left">			            
		                    	<h:outputText value="Total Non-Cash Currency On Hand" styleClass="summary"/>
		                    </div></td>
		                    <td width="10"/>
		                    <td><div align="left">
		                    	<h:outputText value="#{currencyOnHandReportListBean.total}" styleClass="value"/>
		                    </div></td>
		                    </tr>
		                    <tr>
			        		<td><div align="left">			            
		                    	<h:outputText value="Total Cash On Hand" styleClass="summary"/>
		                    </div></td>
		                    <td width="10"/>
		                    <td><div align="left">
		                    	<h:outputText value="#{accountUserListBean.totalUsersCash + capitalReportBean.cashBalance}" styleClass="value"/>
		                    </div></td>
		                    </tr>
		                    <tr>
			        		<td><div align="left">			            
		                    	<h:outputText value="Total Cash Due From Loans" styleClass="summary"/>
		                    </div></td>
		                    <td width="10"/>
		                    <td><div align="left">
		                    	<h:outputText value="#{loanReportListBean.total}" styleClass="value"/>
		                    </div></td>
		                    </tr>
		                    <tr height="10"><td></td></tr>		                    
		                    <tr>
			        		<td><div align="left">			            
		                    	<h:outputText value="Subtotal" styleClass="total"/>
		                    </div></td>
		                    <td width="10"/>
		                    <td><div align="left">
		                    	<h:outputText value="#{accountUserListBean.totalUsersCash + capitalReportBean.cashBalance + currencyOnHandReportListBean.total + capitalReportBean.bankAccountBalance + loanReportListBean.total}" styleClass="value"/>
		                    </div></td>
		                    </tr>
		                    <tr height="10"><td></td></tr>
		                    <tr>
			        		<td><div align="left">			            
		                    	<h:outputText value="Credit Cards Balance" styleClass="summary"/>
		                    </div></td>
		                    <td width="10"/>
		                    <td><div align="left">
		                    	<h:outputText value="#{capitalReportBean.creditCardBalance}" styleClass="value"/>
		                    </div></td>
		                    </tr>
		                    <tr height="10"><td></td></tr>
		                    <tr>
			        		<td><div align="left">			            
		                    	<h:outputText value="Net Total" styleClass="total"/>
		                    </div></td>
		                    <td width="10"/>
		                    <td><div align="left">
		                    	<h:outputText value="#{accountUserListBean.totalUsersCash + capitalReportBean.cashBalance + currencyOnHandReportListBean.total + capitalReportBean.bankAccountBalance + loanReportListBean.total - capitalReportBean.creditCardBalance}" styleClass="value"/>
		                    </div></td>
		                    </tr>
		                </table>
			        </rich:tab>
			        <rich:tab label="Bank Accounts Summary">
			        	<%@include file="BankAccountsTable.jsp"%>
		                <h:panelGrid columns="3">
		                    <h:outputText value="Total" styleClass="summary"/>
		                    &nbsp;
		                    <h:outputText value="#{capitalReportBean.bankAccountBalance}" styleClass="value"/>
		                </h:panelGrid>
			        </rich:tab>
			        <rich:tab label="Credit Cards Summary">
			            <%@include file="CreditCardsTable.jsp"%>
		                <h:panelGrid columns="3">
		                    <h:outputText value="Total" styleClass="summary"/>
		                    &nbsp;
		                    <h:outputText value="#{capitalReportBean.creditCardBalance}" styleClass="value"/>
		                </h:panelGrid>
			        </rich:tab>
			        <rich:tab label="Cash Summary">
			        	<%@include file="AccountUsersTable.jsp"%>
			            <h:panelGrid columns="3">
			            	<h:outputText value="Total Users Cash" styleClass="summary"/>
			            	&nbsp;
			            	<h:outputText value="#{accountUserListBean.totalUsersCash}" styleClass="value"/>
		                    <h:outputText value="Main Account Cash" styleClass="summary"/>
		                    &nbsp;
		                    <h:outputText value="#{capitalReportBean.cashBalance}" styleClass="value"/>
		                    <h:outputText value="Total Cash" styleClass="summary"/>
		                    &nbsp;
		                    <h:outputText value="#{accountUserListBean.totalUsersCash + capitalReportBean.cashBalance}" styleClass="value"/>
		                </h:panelGrid>
			        </rich:tab>
			        <rich:tab label="Currency On Hand Summary">
			            <%@include file="CurrencyOnHandReportList.jsp" %>
			                <h:panelGrid columns="3">
			                    <h:outputText value="Total" styleClass="summary"/>
			                    &nbsp;
			                    <h:outputText value="#{currencyOnHandReportListBean.total}" styleClass="value"/>
			                </h:panelGrid>
			        </rich:tab>
			        <rich:tab label="Loan Summary">
			            <%@include file="LoanReportList.jsp"%>
		                <h:panelGrid columns="3">
		                    <h:outputText value="Total" styleClass="summary"/>
		                    &nbsp;
		                    <h:outputText value="#{loanReportListBean.total}" styleClass="value"/>
		                </h:panelGrid>
			        </rich:tab>
			    </rich:tabPanel>
            </h:form>
        </body>
    </html>

</f:view>
