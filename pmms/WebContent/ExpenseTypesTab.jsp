<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:loadBundle basename="messages" var="msg"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">






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

<a4j:commandButton value="New"
                   oncomplete="#{rich:component('expenseTypeAdd')}.show()"
                   reRender="expenseTypeInfo">
</a4j:commandButton>

<h:messages styleClass="errorMessage" globalOnly="true"/>



<!-- modalPanel must be declared outside the main form -->

