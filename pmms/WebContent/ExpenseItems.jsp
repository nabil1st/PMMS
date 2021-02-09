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
            <title>Expense Items</title>
        </head>
        <body>
            <h2>Expense Items:</h2>

            <h:form id="ExpenseItems">

                <rich:dataTable width="483" id="expenseItemsList" rows="30" columnClasses="col"
                                value="#{expenseItemListBean.expenseItems}" var="expenseItem">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Description" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Expense Type" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Projects" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Tax" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:inputText value="#{expenseItem.description}"/>
                    </h:column>

                    <h:column>
                        <h:inputText value="#{expenseItem.amount}"/>
                    </h:column>

                    <h:column>
                        <h:selectOneMenu value="#{expenseItem.expenseTypeId}">
                            <f:selectItems value="#{expenseBean.expenseTypeList}" />
                        </h:selectOneMenu>
                    </h:column>

                    <h:column>
                        <h:selectOneMenu value="#{expenseItem.expenseGroupId}">
                            <f:selectItems value="#{expenseBean.expenseGroupList}" />
                        </h:selectOneMenu>
                    </h:column>

                    <h:column>
                        <h:inputText value="#{expenseItem.tax}"/>
                    </h:column>

                </rich:dataTable>

                <h:panelGrid columns="3">
                    <!--<h:inputText id="rowsToAdd" value="#{expenseItemListBean.rowsToAdd}"/>-->
                    <h:commandButton value="Add Row" action="#{expenseItemListBean.addRowsAction}"/>
                    <h:commandButton value="Save" action="#{expenseItemListBean.updateAction}"/>
                    <h:commandButton value="Cancel" action="#{expenseItemListBean.cancelAction}"/>
                </h:panelGrid>

                <h:messages id="globalMessages" styleClass="errorMessage" globalOnly="true"/>
            </h:form>
        </body>
    </html>

</f:view>
