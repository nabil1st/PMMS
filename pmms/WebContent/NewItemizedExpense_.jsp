<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:loadBundle basename="messages" var="msg" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<f:view>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>PMMS</title>
	</head>
	
	<STYLE>
		itemization{background-color:red}
	</STYLE>
	 
	<body>
	<script  language="javascript">
            function confirmDelete(link)
                 {
                     var del = confirm('Are you sure you want to delete this expense?');
                     if (del == true) {
                         link.click();
                     }
                 }

            function makeCheckBoxSelections(checkbox)
            {        
                var id = checkbox.id;
                var index = id.substr(id.length - 1, 1);

				if (index == 0) {
					var element = document.getElementById('NewItemizedExpense:taxes_list:1');
					element.checked = 0;					
				} else if (index == 1) {
					var element = document.getElementById('NewItemizedExpense:taxes_list:0');
					element.checked = 0;					
				} 
            }
    </script>
          
    <%trackit.backingbeans.ItemizedExpenseBean theBean = (trackit.backingbeans.ItemizedExpenseBean) request.getSession().getAttribute("itemizedExpenseBean");%>
	<h:form id="NewItemizedExpense">
		<link href="style.css" rel="stylesheet" type="text/css"/>
		<%@include file="MenuBar.jsp"%>
		
		<br>
		<div align="left">
		<h:panelGrid columns="1">
		<h:messages styleClass="errorMessage" globalOnly="true" />
		</h:panelGrid>
		</div>
		
		<div align="left">
			<h:panelGrid id="newExpenseTitlePanel" columns="1">
				<h:outputText value="#{itemizedExpenseBean.title}" styleClass="subtitle"/>
			</h:panelGrid>
		</div>
		
		<br>
		
		<div align="left">
		<table>
			<tr>
			<td valign="top">
			<table>
				
				<tr>
					<td><div align="left">
						<h:outputText styleClass="requiredfieldlabel" value="Expense Amount" rendered="true"/>
					</div></td>
					<td><div align="left">
						<h:inputText styleClass="fieldinput" value="#{itemizedExpenseBean.amount}" 
							disabled="#{!itemizedExpenseBean.renderExpenseAmountPanel}" />
					</div></td>
				</tr>			
				
				<tr>
					<td><div align="left">				
						<h:outputText styleClass="requiredfieldlabel" value="Expense Date" />
					</div></td>
					<td><div align="left">
						<rich:calendar inputClass="fieldinput" value="#{itemizedExpenseBean.date}" />
					</div></td>										
				</tr>
				<tr>
					<td><div align="left">
						<h:outputText styleClass="requiredfieldlabel" value="Payment Method" />
					</div></td>
					<td><div align="left">
						<h:selectOneMenu id="payment_method" styleClass="fieldinput"
							value="#{itemizedExpenseBean.paymentMethodId}"
							valueChangeListener="#{itemizedExpenseBean.updateByPaymentMethod}"
							onchange="submit()" immediate="true">
							<f:selectItems value="#{itemizedExpenseBean.paymentMethodList}" />
						</h:selectOneMenu>
					</div></td>
				</tr>
				
				<%if (theBean == null || theBean.isRenderBankAccountPanel()) {%>	
				<tr>
					<td><div align="left">
						<h:outputText id="bank_account_label" value="Bank Account" styleClass="requiredfieldlabel"
							rendered="#{itemizedExpenseBean.renderBankAccountPanel}" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderBankAccountPanel}">
							<h:selectOneMenu id="bank_account_menu" styleClass="fieldinput"
								value="#{itemizedExpenseBean.bankAccountId}" rendered="true">
								<f:selectItems value="#{itemizedExpenseBean.bankAccountList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Bank Account" rendered="true"
								onclick="submit()" action="#{itemizedExpenseBean.addBankAccountAction}" image="plus_green.gif"/>
						</h:panelGrid>
					</div></td>
				</tr>
				<%}%>
				
				<%if (theBean == null || theBean.isRenderCheckNumberPanel()) {%>
				<tr>
					<td><div align="left">				
						<h:outputText id="check_number_label" value="Check Number" styleClass="requiredfieldlabel"
							rendered="#{itemizedExpenseBean.renderCheckNumberPanel}" />
					</div></td>
					<td><div align="left">
						<h:inputText styleClass="fieldinput" value="#{itemizedExpenseBean.checkNumber}" rendered="#{itemizedExpenseBean.renderCheckNumberPanel}" />
					</div></td>
				</tr>
				<%}%>
				
				<%if (theBean == null || theBean.isRenderMoneyOrderPanel()) {%>
				<tr>
					<td><div align="left">				
						<h:outputText id="money_order_label" value="Money Order" styleClass="requiredfieldlabel"
							rendered="#{itemizedExpenseBean.renderMoneyOrderPanel}" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderMoneyOrderPanel}">
							<h:selectOneMenu id="money_order_menu" styleClass="fieldinput"
								value="#{itemizedExpenseBean.moneyOrderId}" rendered="true"
								valueChangeListener="#{itemizedExpenseBean.updateByMoneyOrder}" immediate="true" onchange="submit()">
								<f:selectItems value="#{itemizedExpenseBean.moneyOrderList}" />
							</h:selectOneMenu>
							<h:commandButton title="Add Money Order" image="plus_green.gif" rendered="true"
								onclick="submit()" action="#{itemizedExpenseBean.addMoneyOrderAction}" />
						</h:panelGrid>
					</div></td>
				</tr>
				<%}%>
				
				<%if (theBean == null || theBean.isRenderCreditCardPanel()) {%>
				<tr>
					<td><div align="left">				
						<h:outputText id="credit_card_label" value="Credit Card" styleClass="requiredfieldlabel"
							rendered="#{itemizedExpenseBean.renderCreditCardPanel}" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderCreditCardPanel}">
							<h:selectOneMenu id="credit_card_menu" styleClass="fieldinput"
								value="#{itemizedExpenseBean.creditCardId}" rendered="true">
								<f:selectItems value="#{itemizedExpenseBean.creditCardList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Credit Card" rendered="true"
								onclick="submit()" action="#{itemizedExpenseBean.addCreditCardAction}" image="plus_green.gif"/>
						</h:panelGrid>
					</div></td>
				</tr>
				<%}%>				
				<tr>
					<td><div align="left">				
						<h:outputText value="Payee" styleClass="requiredfieldlabel" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2">
							<h:selectOneMenu id="payee" value="#{itemizedExpenseBean.payeeId}" styleClass="fieldinput">
								<f:selectItems value="#{itemizedExpenseBean.payeeList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Payee" rendered="true"
								onclick="submit()" action="#{itemizedExpenseBean.addPayeeAction}"
								image="plus_green.gif" />
						</h:panelGrid>
					</div></td>
				</tr>
				<tr>
						<td style="white-space: nowrap;"><div align="left">
						<h:outputText styleClass="fieldlabel" value="Tax 1($)"/>
						</div></td>
						<td><div align="left">
							<h:inputText styleClass="fieldinput" value="#{itemizedExpenseBean.tax1}" size="5"/>
						</div></td>
					</tr>
					<tr>
						<td style="white-space: nowrap;"><div align="left">
						<h:outputText styleClass="fieldlabel" value="Tax 2($)"/>
					</div></td>
					<td><div align="left">
						<h:inputText styleClass="fieldinput" value="#{itemizedExpenseBean.tax2}" size="5"/>
					</div></td>
					</tr>
					<tr>
					<td style="white-space: nowrap;"><div align="left">
						<h:outputText styleClass="fieldlabel" value="Tax 3($)"/>
					</div></td>
					<td><div align="left">
						<h:inputText styleClass="fieldinput" value="#{itemizedExpenseBean.tax3}"  size="5"/>
					</div></td>
					</tr>
			</table>
			
			</td>
			<td valign="top">
			<div>
			<table>
			<tr>
				<td>
				<rich:spacer height="5" />
				<rich:separator height="1" />
				<rich:spacer height="5" />
				<h:panelGrid columns="1">
				<h:outputText styleClass="fieldlabel" value="Enter the details for each expense item then click the arrow at the bottom to add it"/>
				<h:outputText styleClass="fieldlabel" value="to the Expense Items list below."/>
				</h:panelGrid>
				<rich:separator height="1" />
				<rich:spacer height="5" />
				
				
				<div align="left">
			<table bgcolor="ADD8E6" cellspacing="5" width="100" title="Expense item details">	
				<tr>
					<td colspan="4">
					<h:panelGrid columns="3">											
						<h:outputText styleClass="requiredfieldlabel" value="Amount"/>
						<h:inputText styleClass="fieldinput"value="#{itemizedExpenseBean.itemAmount}" size="5"/>																	
						<h:selectManyCheckbox id="taxes_list" style="white-space: nowrap; color:red" onclick="makeCheckBoxSelections(this);"
							  value="#{itemizedExpenseBean.selectedTaxes}" disabled="false"
							  title="Select the taxes that apply to this item"> 
							<f:selectItem id="notax" itemLabel="Tax Free" itemValue="0" />
							<f:selectItem id="tax1" itemLabel="Taxable" itemValue="1" />														
						</h:selectManyCheckbox><br>
					</h:panelGrid>
					</td>
				</tr>
				<tr>
					<td><div align="left">
						<h:outputText styleClass="requiredfieldlabel" value="Expense Type"/>
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2">						
							<h:selectOneMenu id="expense_category_type" styleClass="fieldinput" 
								value="#{itemizedExpenseBean.expenseTypeId}">
								<f:selectItems value="#{itemizedExpenseBean.expenseCategoryTypeList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Expense Type" rendered="true"
								image="plus_green.gif" onclick="submit()"
								action="#{itemizedExpenseBean.addExpenseTypeAction}" />
						</h:panelGrid>
					</div></td>
					<td><div align="left">					
						<h:outputText styleClass="fieldlabel" value="Project"/>
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2">
							<h:selectOneMenu id="expense_group" styleClass="fieldinput"
								value="#{itemizedExpenseBean.expenseGroupId}">
								<f:selectItems value="#{itemizedExpenseBean.expenseGroupList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Project" rendered="true"
								image="plus_green.gif" onclick="submit()"
								action="#{itemizedExpenseBean.addExpenseGroupAction}" />
						</h:panelGrid>
					</div></td>
				</tr>
				<tr>
					<td><div align="left">
						<h:outputText styleClass="requiredfieldlabel" value="Purpose"/>
					</div></td>
					<td><div align="left">
						<h:selectOneMenu id="expense_purpose" styleClass="fieldinput"
							value="#{itemizedExpenseBean.expensePurposeId}"
							valueChangeListener="#{itemizedExpenseBean.updateByExpensePurpose}"
							onchange="submit()" immediate="true">
							<f:selectItems value="#{itemizedExpenseBean.expensePurposeList}" />
						</h:selectOneMenu>
					</div></td>
					<td><div align="left">
						<h:outputText styleClass="requiredfieldlabel" value="Borrower" rendered="#{itemizedExpenseBean.renderBorrower}"/>
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderBorrower}">
							<h:selectOneMenu id="borrower" styleClass="fieldinput"
								value="#{itemizedExpenseBean.borrowerId}">
								<f:selectItems value="#{itemizedExpenseBean.borrowerList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Borrower" rendered="true"
								image="plus_green.gif" onclick="submit()"
								action="#{itemizedExpenseBean.addBorrowerAction}" />
						</h:panelGrid>
					</div></td>
				</tr>
				<tr>
					<td colspan="1"><div align="left">
						<h:outputText styleClass="fieldlabel" value="Notes"/>
					</div></td>
					<td colspan="3"><div align="left">
						<h:inputText value="#{itemizedExpenseBean.itemDescription}" size="50" styleClass="fieldinput"/>
					</div></td>
				</tr>		
				<tr>
					<td colspan="1"><div align="left">
						<h:commandButton image="img/Down.gif" title="Add Item" 
							action="#{itemizedExpenseBean.addExpenseItemAction}" onclick="submit()"/>
					</div></td>
				</tr>
			</table>									
		</div>	
				
				
				
				</td>			
			</tr>
			</table>
			</div>
			</td>
			</tr>
		</table>			
		</div>
		
		<rich:separator height="2" />
		
		<rich:spacer height="5" />
		<h:outputText styleClass="fieldlabel" value="Expense Items"/>
		<br>
		<div align="left">				
			<h:panelGrid columns="1">
					
				<rich:dataTable width="483" id="expensesList" rows="#{itemizedExpenseBean.pageSize}" columnClasses="col"
                                value="#{itemizedExpenseBean.expenseItems}" var="item">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Delete"/>
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Amount" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Tax" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Category" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Project" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Purpose" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Borrower" />
                            </h:column>
                            <h:column>
                                <h:outputText styleClass="headerText" value="Notes" />
                            </h:column>
                        </rich:columnGroup>
                    </f:facet>

                    <h:column>
                    	<h:commandButton image="img/Delete_s.gif" title="Delete" 
                        	onmousedown="return confirmDelete(this);"
                        	action="#{itemizedExpenseBean.deleteIt}">
                            <f:setPropertyActionListener value="#{item}" 
  	     						target="#{itemizedExpenseBean.itemToDelete}" />
                        </h:commandButton>                        
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.amount}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.tax}" />
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.expenseType}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.expenseGroup}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.expensePurposeName}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.borrowerName}"/>
                    </h:column>
                    <h:column>
                        <h:outputLabel value="#{item.description}"/>
                    </h:column>
                </rich:dataTable>
                
                <rich:datascroller align="center" for="expensesList" 
                	maxPages="20" renderIfSinglePage="false" 
						id="expenses_list_sc3" page="#{itemizedExpenseBean.scrollerPage}"/>
                
        	</h:panelGrid>
        
        	
        </div>
        <div align="left">        	
			<h:panelGrid columns="2">
				<h:outputText value="Total: " styleClass="fieldlabel"/>
				<h:outputText value="#{itemizedExpenseBean.total}" styleClass="fieldinput"/>
			</h:panelGrid>
			
		</div>		
		<br>
		<rich:separator/>
		<div align="left">
				<h:panelGrid columns="3">
					<h:commandButton image="img/Stop.gif" title="Cancel" immediate="true"
						action="#{itemizedExpenseBean.cancelAction}" />
					<rich:spacer width="30"/>
					<h:commandButton image="img/Save.gif" title="Save"
						action="#{itemizedExpenseBean.createAction}" />					
				</h:panelGrid>				
			</div>
		<rich:separator/>
		
		
	</h:form>
	</body>
	</html>

</f:view>
