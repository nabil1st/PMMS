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
            <title>Expenses</title>
        </head>
        <body>

          <script  language="javascript">
            function confirmDelete(link)
                 {
                     var del = confirm('Are you sure you want to delete this expense?');
                     if (del == true) {
                         link.onclick();
                     }
                 }
          </script>

            <h:form id="Expenses">
                <link href="style.css" rel="stylesheet" type="text/css"/>

				<%@include file="MenuBar.jsp"%>
				<br>                
                <h:panelGrid columns="1">
                    <h:outputText value="Expenses Summary" styleClass="title"/>
                </h:panelGrid>

                <br><br>

                <h:panelGrid columns="6">
                    <h:commandButton value="New Expense" immediate="true" action="#{expenseListBean.newAction}"/>
                    <h:commandButton value="Itemize Expense" immediate="true" action="#{expenseListBean.itemizeAction}"/>
                    
                </h:panelGrid>

                <br>

                <h:panelGrid columns="5">
                    <h:outputLabel id="from_date_label" value="From"/>
                    <rich:calendar id="from_date" immediate="true" value="#{expenseListBean.fromDate}"/>
                    <h:outputLabel id="to_date_label" value="To"/>
                    <rich:calendar id="to_date" immediate="true" value="#{expenseListBean.toDate}"/>
                    <h:commandButton value="Search" action="show_expenses"/>
                </h:panelGrid>

                <rich:spacer height="30"/>


                <!--
                <rich:datascroller align="center" for="expensesList" maxPages="20" page="#{expenseListBean.scrollerPage}" reRender="sc2" id="sc1"/>
                -->

                <rich:spacer height="30"/>

                <rich:dataTable width="483" id="expensesList" rows="10" columnClasses="col"
                                value="#{expenseListBean.expensesList}" var="account">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Delete"/>
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Date" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Expense Type" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Project" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Payee" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Payment Method" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Itemized" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:commandLink value="Delete" onmousedown="return confirmDelete(this);"
                        actionListener="#{expenseListBean.deleteAction}"
                        immediate="true" action="show_expenses">
                            <f:param name="modelRow" value="#{account.id}"/>
                        </h:commandLink>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.dateStr}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.amount}" />
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.expenseType}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.group}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.payee}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{account.paymentMethod}"/>
                    </h:column>
                    <h:column>
                    	<h:commandLink value="Itemized" onmousedown="return confirmDelete(this);"
                        actionListener="#{expenseListBean.deleteAction}"
                        immediate="true" action="show_expense_items" rendered="#{account.itemized}">
                            <f:param name="modelRow" value="#{account.id}"/>
                        </h:commandLink>
                        
                    </h:column>
                </rich:dataTable>

                <rich:datascroller align="left" for="expensesList" maxPages="20" 
                	page="#{expenseListBean.scrollerPage}" reRender="sc1" id="sc2" renderIfSinglePage="false" />
                
                <rich:spacer height="30"/>                

                <h:messages styleClass="errorMessage" globalOnly="true"/>                
            </h:form>
        </body>
    </html>

</f:view>
