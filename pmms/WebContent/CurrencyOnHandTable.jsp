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
                <rich:dataTable width="483" id="currencyList" rows="10" columnClasses="col"
                value="#{currencyOnHandHistoryBean.historyItems}" var="account">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Source" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Source Type" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Currency Type" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Group" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="On Hand" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:outputLabel value="#{account.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.source}" />
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.sourceType}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.currencyType}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.amount}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.group}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.onHand}"/>
                    </h:column>
                </rich:dataTable>

                <rich:datascroller align="center" for="currencyList" maxPages="20" 
                	page="#{currencyOnHandHistoryBean.scrollerPage}" reRender="coh_sc1" id="coh_sc2" renderIfSinglePage="false" />
				</h:panelGrid>