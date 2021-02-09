<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:loadBundle basename="messages" var="msg"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<f:view>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Account User</title>
    </head>
    <body>
    	<a4j:keepAlive beanName="accountUserBean" />
        <h:form id="NewAccountUser">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
        	<%@include file="MenuBar.jsp"%>
        	<br>
        	<h:messages styleClass="errorMessage" globalOnly="true"/>
        	<h:panelGrid columns="1">
        	<h:panelGrid id="newAccountUserTitlePanel" columns="1" rendered="#{accountUserBean.newUser}">
        	<h:outputText value="New Account User" styleClass="subtitle"/>
        	</h:panelGrid>
        	<h:panelGrid id="editAccountUserTitlePanel" columns="1" rendered="#{!accountUserBean.newUser}">
        	<h:outputText value="Edit Account User" styleClass="subtitle"/>
        	</h:panelGrid>
        	<br>
            <h:panelGrid columns="2">                
                <h:outputText styleClass="fieldlabel" value="First Name"/>
                <h:inputText styleClass="fieldinput" value="#{accountUserBean.firstName}" required="true"/>
                <h:outputText styleClass="fieldlabel" value="Last Name"/>
                <h:inputText styleClass="fieldinput" value="#{accountUserBean.lastName}" required="true"/>
                <h:outputText styleClass="fieldlabel" value="Email Address"/>
                <h:inputText styleClass="fieldinput" value="#{accountUserBean.email}" required="true"/>                
                <h:outputText styleClass="fieldlabel" value="Password" />
                <h:inputSecret styleClass="fieldinput" redisplay="true" value="#{accountUserBean.password}" required="true"/>
                <h:outputText styleClass="fieldlabel" value="Confirm Password"/>
                <h:inputSecret styleClass="fieldinput" redisplay="true" value="#{accountUserBean.confirmPassword}" required="true"/>
                <h:outputText styleClass="fieldlabel" value="Cash Balance"/>
                <h:inputText styleClass="fieldinput" value="#{accountUserBean.cashBalance}" required="true" disabled="#{!accountUserBean.newUser}"/>
			</h:panelGrid>
			<br>
			<h:panelGrid columns="1" rendered="#{accountUserBean.allowedToViewUserPermissions}">
                <h:outputText styleClass="fieldlabel" value="Set User Permissions"/>
                <rich:pickList styleClass="fieldinput" id="permissions"  
                		value="#{accountUserBean.selectedPermissions}" listsHeight="200" 
                		sourceListWidth="200" targetListWidth="200">
                    <f:selectItems value="#{accountUserBean.allPermissions}" />
                </rich:pickList>
                <br>                
            </h:panelGrid>
                        
		<rich:separator/>
		<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{accountUserBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{accountUserBean.createAction}"/>					
				</h:panelGrid>				
			</div>
		<rich:separator/>
            
            </h:panelGrid>
            <h:inputHidden id="userId" value="#{accountUserBean.user.id}" required="false"/>
        </h:form>
    </body>
</html>

</f:view>
