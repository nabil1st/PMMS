<%-- 
    Document   : ExpensesSummaryTab
    Created on : Jul 23, 2009, 4:17:37 PM
    Author     : ndaoud
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

	<h:panelGrid columns="1">
            <rich:dataTable width="483" id="creditCardList" rows="10" columnClasses="col"
                            value="#{creditCardListBean.accounts}" var="account" rowKeyVar="index">
                <f:facet name="header">
                    <rich:columnGroup>
                        <h:column>
                            <h:outputText styleClass="headerText" value="Card Name" />
                        </h:column>
                        <h:column>
                            <h:outputText styleClass="headerText" value="Balance" />
                        </h:column>
                    </rich:columnGroup>
                </f:facet>

                <h:column>
                    <h:commandLink value="#{account.description}" 
                    actionListener="#{creditCardListBean.accountSummaryAction}"
                    immediate="true" action="show_credit_card_statement">
                        <f:param name="modelRow" value="#{account.id}"/>
                    </h:commandLink>
                </h:column>
                <h:column>
                    <h:outputText value="#{account.balance}"/>
                </h:column>
                
            </rich:dataTable>  
			<rich:datascroller align="center" for="creditCardList" maxPages="20" renderIfSinglePage="false"  
				page="#{creditCardListBean.scrollerPage}" reRender="credit_card_sc1" id="credit_card_sc2"/>             
	</h:panelGrid>