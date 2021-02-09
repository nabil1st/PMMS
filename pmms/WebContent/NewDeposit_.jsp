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
        <title>New Deposit</title>
    </head>
    <body>
        <h:form id="NewDeposit">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
			<%@include file="MenuBar.jsp"%>
        	
        	<br>
        	<h:messages styleClass="errorMessage" globalOnly="true"/>
        	<h:panelGrid>
        		<h:outputText value="New Deposit" styleClass="subtitle"/>
        	</h:panelGrid>
        	<br>
            <table>
            	<tr>
            	<td>
            	<div align="left">
                	<h:outputText styleClass="fieldlabel" value="Deposit Date"/>
                </div>
                </td>
                <td>
                <div align="left">
                	<rich:calendar inputClass="fieldinput" value="#{depositBean.date}"/>
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
                <h:selectOneMenu id="bank_account_menu" required="true" styleClass="fieldinput"
                    value="#{depositBean.bankAccountId}" rendered="true">
                    <f:selectItems value="#{depositBean.bankAccountList}" />
                </h:selectOneMenu>
                </div>
                </td>
                </tr>
                
                <tr>
                <td>
                <div align="left">
                <h:outputText styleClass="fieldlabel" value="Cash Amount" rendered ="true"/>
                </div>
                </td>
                <td>
                <div align="left">
                <h:inputText styleClass="fieldinput" value="#{depositBean.totalCash}" rendered="true"/>
                </div>
                </td>
                </tr>
                </table>
                
           <br>
                <h:outputText styleClass="fieldlabel" value="Select checks to deposit:"/>
                <rich:pickList styleClass="fieldinput" id="checks" 
                	value="#{depositBean.depositCheckIds}" 
                	sourceListWidth="275" targetListWidth="275" listsHeight="100">
                    <f:selectItems value="#{depositBean.undepositedChecksList}" />
                </rich:pickList>
                
            <br>
            
            <h:panelGrid columns="2">    
                <h:outputText styleClass="fieldlabel" id="total" value="Deposit Total" rendered="true"/>
                <h:outputText styleClass="fieldinput" id="total_result" value="#{depositBean.depositTotal}" rendered="true"/>
			</h:panelGrid>
               
            <br>
            
            <rich:separator/>
			<div align="left">
				<h:panelGrid columns="5">
					<h:commandButton image="img/Calculator.gif" value="Calculate Total" action="#{depositBean.calculateTotalAction}"/>
					<rich:spacer width="30"/>
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{depositBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{depositBean.createAction}" />					
				</h:panelGrid>				
			</div>
			<rich:separator/>
            
        </h:form>
    </body>
</html>

</f:view>
