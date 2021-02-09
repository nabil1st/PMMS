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
            <title>Summary of Account Users</title>
        </head>
        <body>
            
                
            <h:form id="AccountUsers">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="1">
                    <h:outputText value="Account Users" styleClass="title"/>
                </h:panelGrid>

                <br>

                <h:panelGrid columns="1">
                    <h:commandButton title="Add a New Account User" value="New" action="#{accountUserListBean.newAction}" image="img/AddNew.gif"/>                    
                </h:panelGrid>

				<%@include file="AccountUsersTable.jsp"%>
                
                
            </h:form>
                
        </body>
    </html>

</f:view>
