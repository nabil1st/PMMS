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
            <title>Statement Comparison</title>
        </head>
        <body>            
            <h:form id="StatementComparison">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Statement Comparison" styleClass="subtitle"/>
                </h:panelGrid>
                        
                
                <br>

                
                
                <rich:dataTable width="483" id="statementItems" columnClasses="col"
                                value="#{statementComparisonBean.comparisonModel}" var="item">
                    <f:facet name="header">
                        <rich:columnGroup>
                        	<rich:column colspan="3">
                                <h:outputText value="Statement Details" />
                            </rich:column>
                            <rich:column colspan="3">
                                <h:outputText value="Transaction Details" />
                            </rich:column>
                            <rich:column breakBefore="true">
                                <h:outputText styleClass="headerText" value="Date" />
                            </rich:column>
                            <rich:column>
                                <h:outputText styleClass="headerText" value="Payee" />
                            </rich:column>
                            <rich:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </rich:column>
                            <rich:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </rich:column>
                            <rich:column>
                                <h:outputText styleClass="headerText" value="Payee" />
                            </rich:column>
                            <rich:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </rich:column>
                            
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:outputLabel value="#{item.statementDate}" styleClass="#{item.styleClass}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.statementPayee}" styleClass="#{item.styleClass}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.statementAmount}" styleClass="#{item.styleClass}"/>
                    </h:column>
                    
                    <h:column>
                        <h:outputLabel value="#{item.transactionAmount}" styleClass="#{item.styleClass}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.transactionPayee}" styleClass="#{item.styleClass}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.transactionDate}" styleClass="#{item.styleClass}"/>
                    </h:column>
                                        
                </rich:dataTable>
                       
            </h:form>
        </body>
    </html>

</f:view>
