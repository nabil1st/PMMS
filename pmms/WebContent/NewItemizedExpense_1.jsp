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
	<title>New Itemized Expense</title>
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
                         link.onclick();
                     }
                 }

            function makeCheckBoxSelections(checkbox)
            {        
                var id = checkbox.id;
                var index = id.substr(id.length - 1, 1);

				if (index == 0) {
					var element = document.getElementById('NewItemizedExpense:taxes_list:1');
					element.checked = 0;
					element = document.getElementById('NewItemizedExpense:taxes_list:2');
					element.checked = 0;
					element = document.getElementById('NewItemizedExpense:taxes_list:3');
					element.checked = 0;
					element = document.getElementById('NewItemizedExpense:taxes_list:4');
					element.checked = 0;
				} else if (index == 1 || index == 2 || index == 3) {
					var element = document.getElementById('NewItemizedExpense:taxes_list:0');
					element.checked = 0;
					element = document.getElementById('NewItemizedExpense:taxes_list:4');
					element.checked = 0;
				} else {
					var element = document.getElementById('NewItemizedExpense:taxes_list:0');
					element.checked = 0;
					element = document.getElementById('NewItemizedExpense:taxes_list:1');
					element.checked = 0;
					element = document.getElementById('NewItemizedExpense:taxes_list:2');
					element.checked = 0;
					element = document.getElementById('NewItemizedExpense:taxes_list:3');
					element.checked = 0;
				}
            }
    </script>
          
    <%trackit.backingbeans.ItemizedExpenseBean theBean = (trackit.backingbeans.ItemizedExpenseBean) request.getSession().getAttribute("itemizedExpenseBean");%>
	<h:form id="NewItemizedExpense">
		<div align="left">
		<h:panelGrid columns="1">
		<h:messages styleClass="errorMessage" globalOnly="true" />
		</h:panelGrid>
		</div>
		
		<div align="left">
			<h:panelGrid id="newExpenseTitlePanel" columns="1">
				<h2>Itemize Expense:</h2>
			</h:panelGrid>
		</div>
		
		<div align="left">
		<table>
			<tr>
			<td valign="top">
			<table>
				<tr>
					<td><div align="left">				
						<h:outputText value="Expense Date:" />
					</div></td>
					<td><div align="left">
						<rich:calendar value="#{itemizedExpenseBean.date}" />
					</div></td>										
				</tr>
				<tr>
					<td><div align="left">
						<h:outputText value="Payment Method:" />
					</div></td>
					<td><div align="left">
						<h:selectOneMenu id="payment_method"
							value="#{itemizedExpenseBean.paymentMethodId}"
							valueChangeListener="#{itemizedExpenseBean.updateByPaymentMethod}"
							onchange="submit()" immediate="true">
							<f:selectItems value="#{itemizedExpenseBean.paymentMethodList}" />
						</h:selectOneMenu>
					</div></td>
				</tr>
				
				<%if (theBean == null || theBean.isRenderExpenseAmountPanel()) {%>
				<tr>
					<td><div align="left">
						<h:outputText value="Expense Amount:" rendered="#{itemizedExpenseBean.renderExpenseAmountPanel}"/>
					</div></td>
					<td><div align="left">
						<h:inputText value="#{itemizedExpenseBean.amount}" rendered="#{itemizedExpenseBean.renderExpenseAmountPanel}" />
					</div></td>
				</tr>			
				<%}%>
				
				<%if (theBean == null || theBean.isRenderBankAccountPanel()) {%>	
				<tr>
					<td><div align="left">
						<h:outputText id="bank_account_label" value="Bank Account:"
							rendered="#{itemizedExpenseBean.renderBankAccountPanel}" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderBankAccountPanel}">
							<h:selectOneMenu id="bank_account_menu"
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
						<h:outputText id="check_number_label" value="Check Number:"
							rendered="#{itemizedExpenseBean.renderCheckNumberPanel}" />
					</div></td>
					<td><div align="left">
						<h:inputText value="#{itemizedExpenseBean.checkNumber}" rendered="#{itemizedExpenseBean.renderCheckNumberPanel}" />
					</div></td>
				</tr>
				<%}%>
				
				<%if (theBean == null || theBean.isRenderMoneyOrderPanel()) {%>
				<tr>
					<td><div align="left">				
						<h:outputText id="money_order_label" value="Money Order:"
							rendered="#{itemizedExpenseBean.renderMoneyOrderPanel}" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderMoneyOrderPanel}">
							<h:selectOneMenu id="money_order_menu"
								value="#{itemizedExpenseBean.moneyOrderId}" rendered="true">
								<f:selectItems value="#{itemizedExpenseBean.moneyOrderList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Money Order" rendered="true"
								onclick="submit()" action="#{itemizedExpenseBean.addMoneyOrderAction}" />
						</h:panelGrid>
					</div></td>
				</tr>
				<%}%>
				
				<%if (theBean == null || theBean.isRenderCreditCardPanel()) {%>
				<tr>
					<td><div align="left">				
						<h:outputText id="credit_card_label" value="Credit Card:"
							rendered="#{itemizedExpenseBean.renderCreditCardPanel}" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderCreditCardPanel}">
							<h:selectOneMenu id="credit_card_menu"
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
						<h:outputText value="Payee:" />
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2">
							<h:selectOneMenu id="payee" value="#{itemizedExpenseBean.payeeId}">
								<f:selectItems value="#{itemizedExpenseBean.payeeList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Payee" rendered="true"
								onclick="submit()" action="#{itemizedExpenseBean.addPayeeAction}"
								image="plus_green.gif" />
						</h:panelGrid>
					</div></td>
				</tr>
			</table>
			</td>
			<td valign="top">
			<div>
			<table bgcolor="#8FD9E9">
			<tr>
				<td>
				<table>				
				<tr>
					<td style="white-space: nowrap;"><div align="left">
						<h:outputText>Tax 1(%)</h:outputText>
					</div></td>
					<td><div align="left">
						<h:inputText value="#{itemizedExpenseBean.tax1}" size="5"/>
					</div></td>
				</tr>
				<tr>
					<td style="white-space: nowrap;"><div align="left">
						<h:outputText>Tax 2(%)</h:outputText>
					</div></td>
					<td><div align="left">
						<h:inputText value="#{itemizedExpenseBean.tax2}" size="5"/>
					</div></td>
				</tr>
				<tr>
					<td style="white-space: nowrap;"><div align="left">
						<h:outputText>Tax 3(%)</h:outputText>
					</div></td>
					<td><div align="left">
						<h:inputText value="#{itemizedExpenseBean.tax3}"  size="5"/>
					</div></td>
				</tr>
				</table>
				</td>
				<td>
					<div align="center">-- Or --</div>
			<br>
			<table>
				<tr>
					<td style="white-space: nowrap;"><div align="left">
						<h:outputText>Totaled Tax($)</h:outputText>
					</div></td>
					<td><div align="left">
						<h:inputText value="#{itemizedExpenseBean.totalTax}"  size="5"/>
					</div></td>					
				</tr>
			</table>
				</td>
			</tr>
			</table>
			</div>
			</td>
			</tr>
		</table>			
		</div>
		
		<br>
				
		<div align="center">			
			<table bgcolor="#8FD9E9" cellspacing="5" width="100">				
				<tr>
					<td colspan="4">
					<h:panelGrid columns="4">											
						<h:outputText>Amount</h:outputText>
						<h:inputText value="#{itemizedExpenseBean.itemAmount}" size="5"/>																	
						<h:outputText style="white-space: nowrap;">Applied Taxes</h:outputText>
						<h:selectManyCheckbox id="taxes_list" style="white-space: nowrap; color:red" onclick="makeCheckBoxSelections(this);"
							  value="#{itemizedExpenseBean.selectedTaxes}" disabled="false"
							  title="Select the taxes that apply to this item"> 
							<f:selectItem id="notax" itemLabel="No Tax" itemValue="0" />
							<f:selectItem id="tax1" itemLabel="Tax 1" itemValue="1" />
							<f:selectItem id="tax2" itemLabel="Tax 2" itemValue="2" />
							<f:selectItem id="tax3" itemLabel="Tax 3" itemValue="3" />
							<f:selectItem id="totalTax" itemLabel="Totaled Tax" itemValue="4" />							
						</h:selectManyCheckbox><br>
					</h:panelGrid>
					</td>
				</tr>
				<tr>
					<td><div align="left">
						<h:outputText>Category</h:outputText>
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2">						
							<h:selectOneMenu id="expense_category_type" 
								value="#{itemizedExpenseBean.expenseTypeId}">
								<f:selectItems value="#{itemizedExpenseBean.expenseCategoryTypeList}" />
							</h:selectOneMenu>
							<h:commandButton value="Add Expense Type" rendered="true"
								image="plus_green.gif" onclick="submit()"
								action="#{itemizedExpenseBean.addExpenseTypeAction}" />
						</h:panelGrid>
					</div></td>
					<td><div align="left">					
						<h:outputText>Project</h:outputText>
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2">
							<h:selectOneMenu id="expense_group"
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
						<h:outputText>Purpose</h:outputText>
					</div></td>
					<td><div align="left">
						<h:selectOneMenu id="expense_purpose"
							value="#{itemizedExpenseBean.expensePurposeId}"
							valueChangeListener="#{itemizedExpenseBean.updateByExpensePurpose}"
							onchange="submit()" immediate="true">
							<f:selectItems value="#{itemizedExpenseBean.expensePurposeList}" />
						</h:selectOneMenu>
					</div></td>
					<td><div align="left">
						<h:outputText rendered="#{itemizedExpenseBean.renderBorrower}">Borrower</h:outputText>
					</div></td>
					<td><div align="left">
						<h:panelGrid columns="2" rendered="#{itemizedExpenseBean.renderBorrower}">
							<h:selectOneMenu id="borrower"
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
						<h:outputText>Notes</h:outputText>
					</div></td>
					<td colspan="3"><div align="left">
						<h:inputText value="#{itemizedExpenseBean.itemDescription}" size="50"/>
					</div></td>
				</tr>		
				<tr>
					<td colspan="1"><div align="left">
						<h:commandButton value="Add Item" action="#{itemizedExpenseBean.addExpenseItemAction}" onclick="submit()"/>
					</div></td>
				</tr>
			</table>									
		</div>	
		
		<br>
		
		<div align="center">	
				<rich:dataTable width="483" id="expensesList" rows="10" columnClasses="col"
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
                        <h:commandLink value="Delete" onmousedown="return confirmDelete(this);"
                        actionListener="#{itemizedExpenseBean.deleteAction}"
                        immediate="true" action="ITEMIZE_EXPENSE_REQUESTED">
                            <f:param name="modelRow" value="#{item.id}"/>
                        </h:commandLink>
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
                
        </div>
        <div align="center">        	
			<h:panelGrid columns="2">
				<h:outputText value="Total: "/>
				<h:outputText value="#{itemizedExpenseBean.total}"/>
			</h:panelGrid>
			
		</div>		
		<br>
		<h:panelGrid columns="2">
			<h:commandButton value="Save"
				action="#{itemizedExpenseBean.createAction}" />
			<h:commandButton value="Cancel" immediate="true"
				action="#{itemizedExpenseBean.cancelAction}" />
		</h:panelGrid>
	</h:form>
	</body>
	</html>

</f:view>
