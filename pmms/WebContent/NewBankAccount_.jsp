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
        <title>PMMS</title>
    </head>
    <body>
        <h:form id="NewBankAccount">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	<br>
        	<h:messages styleClass="errorMessage" globalOnly="true"/>
            <h:panelGrid columns="1">
				<h:outputText value="#{bankAccountBean.title}" styleClass="subtitle"/>        	        	
        	</h:panelGrid>
        	<br>
        	<h:panelGrid columns="2">
                <h:outputText styleClass="fieldlabel" value="Account Name"/>
                <h:inputText styleClass="fieldinput" id="description_text" value="#{bankAccountBean.description}" required="true"/>
                <h:outputText styleClass="fieldlabel" value="Account Type"/>
                <h:selectOneMenu styleClass="fieldinput" id="account_type" required="true"
                                 value="#{bankAccountBean.accountTypeId}">
                    <f:selectItems value="#{bankAccountBean.accountTypeList}" />
                </h:selectOneMenu>
                                
                <h:outputText styleClass="fieldlabel" id="current_account_balance_label" value="#{bankAccountBean.openingBalanceTitle}"/>
                <h:inputText styleClass="fieldinput" id="current_account_balance_text" value="#{bankAccountBean.currentBalance}" required="true"/>

            </h:panelGrid>
            <br>
            <rich:separator/>
			<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{bankAccountBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{bankAccountBean.createAction}" />					
				</h:panelGrid>				
			</div>
			<rich:separator/>
			
			<h:inputHidden id="editMode" value="#{bankAccountBean.editMode}"/>
			<h:inputHidden id="bankAccountId" value="#{bankAccountBean.id}"/>
                        
        </h:form>
    </body>
</html>

</f:view>
