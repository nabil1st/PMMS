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
        <title>New Income Source</title>
    </head>
    <body>
        <h:form id="NewIncomeSource">
        	<link href="style.css" rel="stylesheet" type="text/css"/>        	
        	<%@include file="MenuBar.jsp"%>
        	
        	<br>
        	<h:panelGrid columns="1">
        	<h:messages styleClass="errorMessage" globalOnly="true"/>
            <h:outputText value="New Income Source" styleClass="subtitle"/>
            </h:panelGrid>
            <br>
            <h:panelGrid columns="2">
                <h:outputText styleClass="fieldlabel" value="Income Source"/>
                <h:inputText styleClass="fieldinput" id="description_text" value="#{incomeSourceBean.name}" required="true"/>
            </h:panelGrid>
            
            <br>
			
			<rich:separator/>
				<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{incomeSourceBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{incomeSourceBean.createAction}" />					
				</h:panelGrid>				
				</div>
				<rich:separator/>
            
        </h:form>
    </body>
</html>

</f:view>
