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
        <title>New Expense Type</title>
    </head>
    <body>
        <h:form id="NewExpenseType">
        	<link href="style.css" rel="stylesheet" type="text/css"/>
            <%@include file="MenuBar.jsp"%>
            
            <br>
            <h:panelGrid columns="1">
            	<h:messages styleClass="errorMessage" globalOnly="true"/>
            </h:panelGrid>
        	<h:panelGrid columns="1">        		
        		<h:outputText value="Expense Categories and Types" styleClass="subtitle"/>
        	</h:panelGrid>
        	<br>        	
            <h:panelGrid columns="2" >
                <h:outputText value="Expense Categories" styleClass="fieldlabel"/>
                <h:outputText value="Expense Types" styleClass="fieldlabel"/>
                <h:selectOneListbox styleClass="fieldinput" id="expense_category_list" value="#{expenseTypeBean.selectedCategoryId}"
                	valueChangeListener="#{expenseTypeBean.categoryChanged}" onchange="submit()" immediate="true" size="10">
                	<f:selectItems value="#{expenseTypeBean.expenseCategoryList}" />
                </h:selectOneListbox>
                <h:selectOneListbox styleClass="fieldinput" id="expense_type_list" value="#{expenseTypeBean.selectedTypeId}" size="10">
                	<f:selectItems value="#{expenseTypeBean.expenseTypeList}"/>
                </h:selectOneListbox>
                
                <rich:spacer height="10" />
				<rich:spacer height="10" />
                
                <h:inputText id="new_category" value="#{expenseTypeBean.categoryDescription}"/>
                <h:inputText id="new_type" value="#{expenseTypeBean.description}"/>

				<h:commandButton image="img/Export.gif" title="Add Category" action="#{expenseTypeBean.addCategoryAction}"/>
                <h:commandButton image="img/Export.gif" title="Add Type" action="#{expenseTypeBean.addTypeAction}"/>
                <h:panelGrid columns="1">
                	<rich:spacer width="10"/>
                	<h:commandButton image="img/Return.gif" title="Done" action="#{expenseTypeBean.doneAction}"/>
                	
                </h:panelGrid>
                
            </h:panelGrid>
        </h:form>
    </body>
</html>

</f:view>
