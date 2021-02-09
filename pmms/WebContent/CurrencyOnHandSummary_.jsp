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
            <title>Currency On Hand</title>
        </head>
        <body>
        	<script  language="javascript">
	            function confirmDelete(link)
	                 {
	                     var del = confirm('Are you sure you want to delete this currency on hand item?');
	                     if (del == true) {
	                         link.click();
	                     }
	                 }
	
	            function confirmEdit(link)
	            {
	                var del = confirm('Are you sure you want to edit this currency on hand item?');
	                if (del == true) {
	                    link.click();
	                }
	            }
          	</script>

            <h:form id="CurrencyOnHandSummary">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="1">
                    <h:outputText value="Currency On Hand Summary" styleClass="title"/>
                </h:panelGrid>

                <br>
                <h:panelGrid columns="5">
                    <h:outputLabel styleClass="fieldlabel" id="from_date_label" value="From "/>
                    <rich:calendar inputClass="fieldinput" id="from_date" immediate="true" value="#{currencyOnHandHistoryBean.fromDate}"/>
                    <h:outputLabel styleClass="fieldlabel" id="to_date_label" value="To "/>
                    <rich:calendar inputClass="fieldinput" id="to_date" immediate="true" value="#{currencyOnHandHistoryBean.toDate}"/>
                    <h:commandButton image="img/Refresh.gif" title="Refresh" action="#{currencyOnHandHistoryBean.refresh}"/>
                </h:panelGrid>
                
                <br>

                <h:panelGrid columns="1">
                
                <rich:simpleTogglePanel
                        switchType="client"
                        label="More Search Options"
                        width="200" opened="false">

                    <h:panelGrid columns="2">
                        <h:outputText value="Show all dates"/>
                        <h:selectBooleanCheckbox value="#{currencyOnHandHistoryBean.showAllDates}"/>
                        <h:outputText value="Currency Availability"/>
                        <h:selectOneMenu id="currency_on_hand_status"
                                value="#{currencyOnHandHistoryBean.status}">
                                <f:selectItems value="#{currencyOnHandHistoryBean.currencyOnHandStatusList}" />
                        </h:selectOneMenu>

                        <h:outputText value="Currency Type"/>
                        <h:selectOneMenu id="currency_on_hand_type"
                                value="#{currencyOnHandHistoryBean.currencyType}">
                                <f:selectItems value="#{currencyOnHandHistoryBean.currencyOnHandTypeList}" />
                        </h:selectOneMenu>

                        <h:outputText value="Source Type"/>
                        <h:selectOneMenu id="currency_on_hand_source_type"
                                value="#{currencyOnHandHistoryBean.sourceType}"
                                valueChangeListener="#{currencyOnHandHistoryBean.updateSourceType}"
                                onchange="submit()" immediate="true">
                                <f:selectItems value="#{currencyOnHandHistoryBean.currencyOnHandSourceTypeList}" />
                        </h:selectOneMenu>

                        <h:outputText value="Source"/>
                        <h:selectOneMenu id="currency_on_hand_source"
                                disabled="#{!currencyOnHandHistoryBean.currencyOnHandSourceListEnabled}"
                                value="#{currencyOnHandHistoryBean.sourceId}">
                                <f:selectItems value="#{currencyOnHandHistoryBean.currencyOnHandSourceList}" />
                        </h:selectOneMenu>

                        <h:outputText value="Group"/>
                        <h:selectOneMenu id="currency_on_hand_group"
                                value="#{currencyOnHandHistoryBean.groupId}">
                                <f:selectItems value="#{currencyOnHandHistoryBean.groupList}" />
                        </h:selectOneMenu>
                    </h:panelGrid>

                </rich:simpleTogglePanel>

                <br>

				<h:panelGrid columns="2">                    
                    <h:commandButton image="img/Cach.jpg" title="New Income" immediate="true" action="#{currencyOnHandHistoryBean.newIncomeAction}"/>
                    <h:commandButton image="img/Check.jpg" title="New Money Order" immediate="true" action="#{currencyOnHandHistoryBean.newMoneyOrderAction}"/>
                </h:panelGrid>
                
                <h:panelGrid columns="1">
                <rich:dataTable width="483" id="currencyList" rows="10" columnClasses="col"
                value="#{currencyOnHandHistoryBean.historyItems}" var="account">
                    <f:facet name="header">
                        <rich:columnGroup>
                        	<h:column>
                                <h:outputText styleClass="headerText" value="Delete"/>
                            </h:column>
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
                        <h:commandButton image="img/Delete_s.gif" title="Delete" 
                        	onmousedown="return confirmDelete(this);"
                        	action="#{currencyOnHandHistoryBean.deleteIt}"
                        	>
                            <f:setPropertyActionListener value="#{account}" 
  	     						target="#{currencyOnHandHistoryBean.selectedListItem}" />
                        </h:commandButton>
                    </h:column>
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
                

              </h:panelGrid>                
                
            </h:form>
        </body>
    </html>

</f:view>
