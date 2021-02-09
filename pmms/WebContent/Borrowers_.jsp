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
            <title>Summary of Borrowers</title>
        </head>
        <body>

            <h:form id="Borrowers">
                <link href="style.css" rel="stylesheet" type="text/css"/>
				<%@include file="MenuBar.jsp"%>
				
				<br>
				<h:messages styleClass="errorMessage" globalOnly="true"/>
                <h:panelGrid columns="1">
                    <h:outputText value="Borrowers" styleClass="title"/>
                </h:panelGrid>

                <br>

                <h:panelGrid columns="1">
                    <h:commandButton title="Add a New Borrower" 
                    	action="#{borrowerListBean.newBorrowerAction}" image="img/AddNew.gif"/>
                </h:panelGrid>

				<h:panelGrid columns="1">
                <rich:dataTable width="483" id="borrowerList" rows="10" columnClasses="col"
                                value="#{borrowerListBean.borrowers}" var="account">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Description" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                        <h:outputText value="#{account.description}"/>
                    </h:column>
                </rich:dataTable>
                <rich:datascroller align="center" for="borrowerList" maxPages="20" 
                	page="#{borrowerListBean.scrollerPage}" reRender="sc1" id="sc2" renderIfSinglePage="false" />
				</h:panelGrid>
                
                
            </h:form>
        </body>
    </html>

</f:view>
