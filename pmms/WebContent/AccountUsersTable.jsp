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
       <rich:dataTable width="483" id="accountUserList" rows="20" columnClasses="col"
       value="#{accountUserListBean.users}" var="user" rowKeyVar="index">
           <f:facet name="header">
               <rich:columnGroup>
                   <h:column>
                       <h:outputText styleClass="headerText" value="Name" />
                   </h:column>
                   <h:column>
                       <h:outputText styleClass="headerText" value="Email" />
                   </h:column>
                   <h:column>
                       <h:outputText styleClass="headerText" value="Cash Balance" />
                   </h:column>
               </rich:columnGroup>
           </f:facet>

           <h:column>
               <h:commandLink value="#{user.name}" 
                  actionListener="#{accountUserListBean.accountSummaryAction}"
                  immediate="true" action="edit_account_user">
                   <f:param name="modelRow" value="#{user.id}"/>
               </h:commandLink>
           </h:column>
           <h:column>
               <h:outputText value="#{user.email}"/>
           </h:column>
           <h:column>
               <h:outputText value="#{user.cashBalance}"/>
           </h:column>
       </rich:dataTable>      
       <rich:datascroller align="center" for="accountUserList" maxPages="20" renderIfSinglePage="false" 
       		page="#{accountUserListBean.scrollerPage}" reRender="sc1" id="sc2"/>
       </h:panelGrid>