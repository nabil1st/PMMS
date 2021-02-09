<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:loadBundle basename="messages" var="msg"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">




<rich:dataTable width="483" id="bankAccountList" rows="10" columnClasses="col"
                value="#{bankAccountListBean.accounts}" var="account" rowKeyVar="index">
    <f:facet name="header">
        <rich:columnGroup>
            <h:column>
                <h:outputText styleClass="headerText" value="Description" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Type" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Balance" />
            </h:column>
        </rich:columnGroup>
    </f:facet>

    <h:column>
        <h:commandLink value="#{account.description}"
                       actionListener="#{bankAccountListBean.accountSummaryAction}"
                       immediate="true" action="show_bank_statement">
            <f:param name="modelRow" value="#{account.id}"/>
        </h:commandLink>
    </h:column>
    <h:column>
        <rich:inplaceSelect value="#{account.accountTypeId}" defaultLabel="Click here to edit">
            <f:selectItems value="#{bankAccountBean.accountTypeList}" />
        </rich:inplaceSelect>
    </h:column>
    <h:column>
        <h:outputText value="#{account.balance}"/>
    </h:column>
</rich:dataTable>

<a4j:commandButton value="New"
                   oncomplete="#{rich:component('bankAccountAdd')}.show()"
                   reRender="newBankAccountInfo">
    <f:setPropertyActionListener value="#{account}"
                                 target="#{expenseTypeListBean.selectedExpenseType}" />
</a4j:commandButton>

<h:messages styleClass="errorMessage" globalOnly="true"/>

