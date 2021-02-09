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
        <title>New Expense</title>
    </head>
    <body>       

        <h:form id="ManageExpenseCategories">
            <link href="style.css" rel="stylesheet" type="text/css"/>
			<%@include file="MenuBar.jsp"%>
			
            <h:panelGrid columns="1">
                <h:outputText value="Manage Expense Categories" styleClass="title"/>
            </h:panelGrid>

            <br><br>
            
            
            <h:panelGrid columns="1">
                <rich:tree style="width:300px"
                    nodeSelectListener="#{expenseCategoryTreeBean.processSelection}"
                    changeExpandListener="#{expenseCategoryTreeBean.changeExpandListener}"
                    reRender="selectedNode"
                    ajaxSubmitSelection="true"
                    switchType="client" value="#{expenseCategoryTreeBean.treeNode}"
                    var="item" immediate="true" adviseNodeOpened="#{expenseCategoryTreeBean.adviseNodeOpened}"
                    adviseNodeSelected="#{expenseCategoryTreeBean.adviseNodeSelected}">

                </rich:tree>

                <br>


                <h:panelGrid columns="4">
                    <h:outputText value="Description:"/>
                    <h:inputText value="#{expenseCategoryTreeBean.description}" required="true"/>
                    <h:commandButton value="Add Category" onclick="submit()"
                        action="#{expenseCategoryTreeBean.addCategoryAction}"/>
                    <h:commandButton value="Add Type" onclick="submit()"
                        action="#{expenseCategoryTreeBean.addTypeAction}"/>
                </h:panelGrid>

                <br>

                <h:panelGrid columns="1">
                    <h:commandButton value="Done" immediate="true" action="#{expenseCategoryTreeBean.doneAction}"/>
                </h:panelGrid>

                
                <h:messages styleClass="errorMessage" globalOnly="true"/>
            </h:panelGrid>
                
        </h:form>
    </body>
</html>

</f:view>
