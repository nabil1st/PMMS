<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


            <h:panelGrid columns="1">
            <rich:dataTable width="483" id="currencyList" rows="10" columnClasses="col"
            value="#{currencyOnHandReportListBean.historyItems}" var="account">
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
            	page="#{currencyOnHandReportListBean.scrollerPage}" reRender="coh_rl_sc1" id="coh_rl_sc2" renderIfSinglePage="false" />
</h:panelGrid>              
