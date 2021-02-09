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
            <title>Statement Column Selection</title>
        </head>
        <body>            
            <h:form id="StatementColumnSelection">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Field identification" styleClass="subtitle"/>                    
                </h:panelGrid>
                
                <br>
                
                <h:panelGrid columns="2">
                <h:outputText value="Date Column"/>
                <h:selectOneMenu id="dateSelection" styleClass="fieldinput"
						value="#{statementColumnSelectionBean.dateColumn}" rendered="true">
						<f:selectItems value="#{statementColumnSelectionBean.selectionColumns}" />
					</h:selectOneMenu>
					
				<h:outputText value="Payee Column"/>
                <h:selectOneMenu id="payeeSelection" styleClass="fieldinput"
						value="#{statementColumnSelectionBean.payeeColumn}" rendered="true">
						<f:selectItems value="#{statementColumnSelectionBean.selectionColumns}" />
					</h:selectOneMenu>
					
				<h:outputText value="Amount Column"/>
                <h:selectOneMenu id="amountSelection" styleClass="fieldinput"
						value="#{statementColumnSelectionBean.amountColumn}" rendered="true">
						<f:selectItems value="#{statementColumnSelectionBean.selectionColumns}" />
					</h:selectOneMenu>
				</h:panelGrid>
					
				<h:commandButton  value="Confirm Selection" title="Confirm Selection" rendered="true"
						onclick="submit()" action="#{statementColumnSelectionBean.confirmSelection}" />
                
				<rich:dataTable value="#{statementColumnSelectionBean.model}" var="model" width="750">

		            <f:facet name="header">
		                <h:outputText value="Fields in Statement"></h:outputText>
		            </f:facet>
		            
		            <rich:columns value="#{statementColumnSelectionBean.columns}" var="acolumn"
		                index="ind" id="columns">
		                 
		                <f:facet name="header">
		                    <h:outputText value="#{acolumn}" />
		                </f:facet>
		            
		                <h:outputText value="#{model[ind]}" />
		            </rich:columns>
            
        		</rich:dataTable>                                
            </h:form>
        </body>
    </html>

</f:view>
