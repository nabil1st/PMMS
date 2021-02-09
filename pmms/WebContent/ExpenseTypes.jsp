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
            <title>Summary of Expense Types</title>
        </head>
        <body>
            
            <!-- a4j:keepAlive saves the bean in the view tree, therefore the been
            must be Serializable -->
            <a4j:keepAlive beanName="expenseTypeListBean"></a4j:keepAlive>

            <h:form id="ExpenseTypes">

                <link href="style.css" rel="stylesheet" type="text/css"/>

                <h:panelGrid columns="1">
                    <h:outputText value="Expense Types" styleClass="title"/>
                </h:panelGrid>

                <br><br>

                <h:panelGrid columns="3">
                    <h:commandButton value="Home" action="#{expenseTypeListBean.homeAction}"/>
                    <h:commandButton value="Expense Summary" action="#{expenseTypeListBean.expenseSummaryAction}"/>
                    <h:commandButton value="New" action="#{expenseTypeListBean.newAction}"/>
                </h:panelGrid>

                <br>


                <!-- ajaxkeys is the set of table rows that will be updated -->
                <rich:dataTable width="483" id="expenseTypeList" rows="10" columnClasses="col"
                                value="#{expenseTypeListBean.expenseTypes}" var="account"
                                ajaxKeys="#{expenseTypeListBean.rowsToUpdate}">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Edit" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Description" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <a4j:commandButton value="Edit"
                            oncomplete="#{rich:component('expenseTypeEdit')}.show()"
                            reRender="expenseTypeInfo">
                            <f:setPropertyActionListener value="#{account}"
                            target="#{expenseTypeListBean.selectedExpenseType}" />
                        </a4j:commandButton>
                    </h:column>

                    <h:column>
                        <h:outputText id="description"
                            value="#{account.description}"/>
                    </h:column>



                </rich:dataTable>                

                <h:messages styleClass="errorMessage" globalOnly="true"/>
            </h:form>

            
            <!-- modalPanel must be declared outside the main form -->
            <rich:modalPanel id="expenseTypeEdit">
                <f:facet name="header">
                    Expense Type
                </f:facet>
                <h:form>
                    <h:panelGrid id="expenseTypeInfo">
                        <h:outputLabel for="nameInput" value="Type:"/>
                        <h:inputText id="nameInput" value="#{expenseTypeListBean.selectedExpenseType.description}"/>
                        <h:panelGrid columns="2">
                            <a4j:commandLink onclick="#{rich:component('expenseTypeEdit')}.hide();return false">
                                Close
                            </a4j:commandLink>
                            <a4j:commandLink actionListener="#{expenseTypeListBean.save}" 
                            oncomplete="#{rich:component('expenseTypeEdit')}.hide();return false"
                            reRender="description">
                                Save
                            </a4j:commandLink>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>

</f:view>
