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
        <title>New Bank Account Transaction</title>
    </head>
    <body>
        <h:form id="NewBankAccountTransaction">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	<br>
        	<h:messages styleClass="errorMessage" globalOnly="true"/>
        	<h:panelGrid columns="1">            
            	<h:outputText value="New Bank Account Transaction" styleClass="subtitle"/>
            </h:panelGrid>
            
            <br>
            <table>
            	<tr>
            		<td>
            		<div align="left">    
                    <h:outputText styleClass="fieldlabel" value="Transaction Date"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <rich:calendar inputClass="fieldinput" value="#{bankAccountTransactionBean.date}"/>
                    </div>
                    </td>
                </tr>   
                
                <tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="fieldlabel" id="bank_account_label" value="Bank Account" rendered="true"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:panelGrid columns="2"> 
                    <h:selectOneMenu id="bank_account_menu" styleClass="fieldinput"
                        value="#{bankAccountTransactionBean.bankAccountId}" rendered="true">
                        <f:selectItems value="#{bankAccountTransactionBean.bankAccountList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Bank Account" rendered="true"
                        onclick="submit()"
                        action="#{bankAccountTransactionBean.addBankAccountAction}" image="plus_green.gif"/>
                    </h:panelGrid>
                    </div>
                    </td>
                </tr>

                <tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="fieldlabel" value="Transaction Type"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:selectOneMenu id="transaction_type" styleClass="fieldinput"
                                 value="#{bankAccountTransactionBean.transactionTypeId}">
                        <f:selectItems value="#{bankAccountTransactionBean.transactionTypeList}" />
                    </h:selectOneMenu>
                    </div>
                    </td>
                </tr>
                <tr>
                	<td>
                	<div align="left">
                    <h:outputText styleClass="fieldlabel" value="Transaction Amount" rendered ="true"/>
                    </div>
                    </td>
                    <td>
                    <div align="left">
                    <h:inputText styleClass="fieldinput" value="#{bankAccountTransactionBean.amount}" rendered="true"/>
                    </div>
                    </td>
                </tr>
                </table>

				<br>

				<rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{bankAccountTransactionBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{bankAccountTransactionBean.createAction}" />					
				</h:panelGrid>				
				</div>
				<rich:separator/>
			            
        </h:form>
    </body>
</html>

</f:view>
