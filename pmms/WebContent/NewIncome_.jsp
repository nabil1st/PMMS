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
        <title>New Income</title>
    </head>
    <body>
        

        <h:form id="NewIncome">           
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
            
            <br>
            <h:messages styleClass="errorMessage" globalOnly="true"/>
        	<h:panelGrid columns="1">
        		<h:outputText value="New Income" styleClass="subtitle"/>
        	</h:panelGrid>
        	<br>
        	
            <h:panelGrid columns="2">            	
                <h:outputText styleClass="fieldlabel" value="Income Date"/>
                <rich:calendar inputClass="fieldinput" value="#{incomeBean.date}"/>
                
                <h:outputText styleClass="fieldlabel" value="Income Amount" rendered ="true"/>
                <h:inputText styleClass="fieldinput" value="#{incomeBean.amount}" required="true" rendered="true"/>
                
                
                <h:outputText styleClass="fieldlabel" value="Income Source"/>
                <h:panelGrid columns="2">
                    <h:selectOneMenu styleClass="fieldinput" id="income_source" required="true"
                                 value="#{incomeBean.incomeSourceId}">
                        <f:selectItems value="#{incomeBean.incomeSourceList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Income Source" rendered="true"
                    onclick="submit()" immediate="true"
                        action="#{incomeBean.addIncomeSourceAction}" image="plus_green.gif"/>
                </h:panelGrid>

                
                <h:outputText styleClass="fieldlabel" id="expense_group_label" value="Project" rendered="true"/>
                <h:panelGrid columns="2">
                    <h:selectOneMenu id="expense_group_menu" styleClass="fieldinput"
                        value="#{incomeBean.expenseGroupId}" rendered="true">
                        <f:selectItems value="#{incomeBean.expenseGroupList}" />
                    </h:selectOneMenu>
                    <h:commandButton value="Add Project" rendered="true"
                    onclick="submit()" immediate="true"
                        action="#{incomeBean.addExpenseGroupAction}" image="plus_green.gif"/>
                </h:panelGrid>

                
                <h:outputText styleClass="fieldlabel" id="currency_type_label" value="Currency Type" rendered="true"/>
                <h:panelGrid columns="2">
                    <h:selectOneMenu id="currency_type_menu" styleClass="fieldinput"
                        value="#{incomeBean.currencyTypeId}" rendered="true"
                        valueChangeListener="#{incomeBean.updateByCurrencyType}"
                        onchange="submit()" immediate="true">
                        <f:selectItems value="#{incomeBean.currencyTypeList}" />
                    </h:selectOneMenu>
                </h:panelGrid>


                <h:outputText styleClass="fieldlabel" id="check_number_label" 
                    	value="Check Number" rendered="#{incomeBean.renderCheckNumberField}"/>
                <h:inputText styleClass="fieldinput" id="check_number_text" 
                    	value="#{incomeBean.checkNumber}" rendered="#{incomeBean.renderCheckNumberField}"/>
                
                
                <h:outputText styleClass="fieldlabel" id="bank_account_label" 
                	value="Bank Account" rendered="#{incomeBean.renderBankAccountPanel}"/>
                <h:panelGrid columns="2" rendered="#{incomeBean.renderBankAccountPanel}">
                    <h:selectOneMenu id="bank_account_menu" styleClass="fieldinput"
                        value="#{incomeBean.bankAccountId}" rendered="true">
                        <f:selectItems value="#{incomeBean.bankAccountList}" />
                    </h:selectOneMenu>
                </h:panelGrid>
                

            </h:panelGrid>
            <br>
            <rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{incomeBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{incomeBean.createAction}" />					
				</h:panelGrid>				
				</div>
				<rich:separator/>
        </h:form>
    </body>
</html>

</f:view>
