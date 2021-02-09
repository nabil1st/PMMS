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
            <title>Summary of Bank Accounts</title>
        </head>
        <body>
            
            <h:form id="BankAccounts">                
                <link href="style.css" rel="stylesheet" type="text/css"/>

                <h:panelGrid columns="1">
                    <h:outputText value="Bank Accounts Summary" styleClass="title"/>
                </h:panelGrid>

                <br><br>

                <h:panelGrid columns="3">
                    <h:commandButton value="Home" action="#{bankAccountListBean.homeAction}"/>
                    <h:commandButton value="New" action="#{bankAccountListBean.newAction}"/>
                </h:panelGrid>

                <br>

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
                        <h:outputText value="#{account.type}"/>
                    </h:column>
                    <h:column>
                        <h:outputText value="#{account.balance}"/>
                    </h:column>
                </rich:dataTable>                

                <h:messages styleClass="errorMessage" globalOnly="true"/>
                
            </h:form>
        </body>
    </html>

</f:view>
