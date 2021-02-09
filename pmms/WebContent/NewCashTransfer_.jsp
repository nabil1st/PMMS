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
        <title>New Loan</title>
    </head>
    <body>
        <h:form id="NewCashTransfer">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	
        	<br>
        	 <h:messages styleClass="errorMessage" globalOnly="true"/>
        	<h:panelGrid>
            	<h:outputText value="New Cash Transfer" styleClass="subtitle"/>
            </h:panelGrid>
            <br>
            
            <div align="left">
            	<table>
            		<tr>
            			<td><div align="left">
            				<h:outputText styleClass="fieldlabel" value="Transfer Date"/>
            			</div></td>
            			<td><div align="left">
            				<rich:calendar inputClass="fieldinput" value="#{cashTransferBean.transferDate}"/>
            			</div></td>
            			
            		</tr>
            		<tr>
            			<td><div align="left">
            				<h:outputText styleClass="fieldlabel" value="From Account"/>
            			</div></td>
            			<td><div align="left">
            				<h:selectOneMenu id="from_account" styleClass="fieldinput"
                                 value="#{cashTransferBean.sourceId}">
                        		<f:selectItems value="#{cashTransferBean.cashAccountList}" />
                    		</h:selectOneMenu>
            			</div></td>
            		</tr>
            		<tr>
            			<td><div align="left">
            				<h:outputText styleClass="fieldlabel" value="To Account"/>
            			</div></td>
            			<td><div align="left">
            				<h:selectOneMenu id="to_account" styleClass="fieldinput"
                                 value="#{cashTransferBean.destinationId}">
                        		<f:selectItems value="#{cashTransferBean.cashAccountList}" />
                    		</h:selectOneMenu>
            			</div></td>
            		</tr>
            		<tr>
            			<td><div align="left">
            				<h:outputText value="Transfer Amount" styleClass="fieldlabel"/>
            			</div></td>
            			<td><div align="left">
            				<h:inputText value="#{cashTransferBean.amount}" styleClass="fieldinput"/>
            			</div></td>
            		</tr>
            		<tr>
            			<td><div align="left">
            				<h:outputText value="Notes" styleClass="fieldlabel"/>
            			</div></td>
            			<td><div align="left">
            				<h:inputText value="#{cashTransferBean.notes}" styleClass="fieldinput"/>
            			</div></td>
            		</tr>
            	</table>
            </div>
            
			<br>
			<rich:separator/>
			<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{cashTransferBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{cashTransferBean.createAction}" />					
				</h:panelGrid>				
			</div>
			<rich:separator/>
        </h:form>
    </body>
</html>

</f:view>
