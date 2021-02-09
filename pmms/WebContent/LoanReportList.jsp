<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<h:panelGrid columns="1">
<rich:dataTable width="483" id="loanList" rows="10" columnClasses="col"
value="#{loanReportListBean.loans}" var="account" rowKeyVar="index">
    <f:facet name="header">
        <rich:columnGroup>                            
            <h:column>
                <h:outputText styleClass="headerText" value="Loan Date" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Description" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Borrower" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Amount" />
            </h:column>
            <h:column>
                <h:outputText styleClass="headerText" value="Balance" />
            </h:column>
        </rich:columnGroup>
    </f:facet>

    <h:column>
        <h:commandLink value="#{account.dateStr}"
        actionListener="#{loanReportListBean.loanHistoryAction}"
        immediate="true" action="SHOW_LOAN_HISTORY">
            <f:param name="modelRow" value="#{account.id}"/>
        </h:commandLink>
    </h:column>
    <h:column>
        <h:outputLabel value="#{account.description}"/>
    </h:column>
    <h:column>
        <h:outputLabel value="#{account.borrowerName}"/>
    </h:column>
    <h:column>
        <h:outputLabel value="#{account.amount}" />
    </h:column>
    <h:column>
        <h:outputText value="#{account.balance}"/>
    </h:column>
</rich:dataTable>
<rich:datascroller align="center" for="loanList" maxPages="20" page="#{loanReportListBean.scrollerPage}" 
	reRender="loan_rp_sc1" id="loan_rp_sc2" renderIfSinglePage="false" />
</h:panelGrid>  