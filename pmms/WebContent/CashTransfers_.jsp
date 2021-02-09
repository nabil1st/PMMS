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
            <title>Cash Transfers</title>
        </head>
        <body>

          <script  language="javascript">
            function confirmDelete(link)
                 {
                     var del = confirm('Are you sure you want to delete this cash transfer?');
                     if (del == true) {
                         link.onclick();
                     }
                 }
          </script>

            <h:form id="CashTransfers">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="1">
                    <h:outputText value="Cash Transfers Summary" styleClass="title"/>
                </h:panelGrid>
                
                <br>

                <h:panelGrid columns="5">
                    <h:outputLabel id="from_date_label" value="From" styleClass="fieldlabel"/>
                    <rich:calendar id="from_date" immediate="true" value="#{cashTransferListBean.fromDate}"
                    	inputClass="fieldinput"/>
                    <h:outputLabel id="to_date_label" value="To" styleClass="fieldlabel"/>
                    <rich:calendar id="to_date" immediate="true" value="#{cashTransferListBean.toDate}"
                    	inputClass="fieldinput"/>
                    <h:commandButton title="Refresh" action="show_transfers" image="img/Refresh.gif"/>
                </h:panelGrid>

                <br>                

				<h:panelGrid columns="1">
                    <h:commandButton title="Add a New Cash Transfer" immediate="true" action="#{cashTransferListBean.newAction}" image="img/AddNew.gif"/>                    
                </h:panelGrid>
                <h:panelGrid columns="1">
                <rich:dataTable width="483" id="transfersList" rows="10" columnClasses="col"
                                value="#{cashTransferListBean.cashTransferList}" var="transfer">
                    <f:facet name="header">
                        <rich:columnGroup>                            
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="From" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="To" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Notes" />
                            </h:column>
                            
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:outputLabel value="#{transfer.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{transfer.amount}" />
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{transfer.sourceAccountName}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{transfer.destinationAccountName}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{transfer.notes}"/>
                    </h:column>
                    
                </rich:dataTable>

                <rich:datascroller align="center" for="transfersList" renderIfSinglePage="false" 
                	maxPages="20" page="#{cashTransferListBean.scrollerPage}" reRender="sc1" id="sc2"/>
                </h:panelGrid>
                
                <rich:spacer height="30"/>
            </h:form>
        </body>
    </html>

</f:view>
