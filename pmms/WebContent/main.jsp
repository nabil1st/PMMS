<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--
    This file is an entry point for JavaServer Faces application.
--%>

<f:view>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Expense Tracking System</title>
    </head>
    <body>


            <h:form>
            <!--<h><a href='faces/Login.jsp'>Login to your account</a></h>-->

                <!--<a4j:commandLink value="Login"
                    oncomplete="Richfaces.showModalPanel('loginPanel');">
                </a4j:commandLink>-->


                <h><a onclick="Richfaces.showModalPanel('loginPanel');" href='#'>Account Login</a></h>
                <h><a href='faces/NewAccount.jsp'>New Account</a></h>

                <h:messages styleClass="errorMessage" globalOnly="true"/>
            </h:form>


            <!-- modalPanel must be declared outside the main form -->
            <rich:modalPanel id="loginPanel">
                <f:facet name="header">
                    Account Login
                </f:facet>
                <h:form>
                    <h:panelGrid id="loginInfo" columns="2">
                        <h:outputLabel for="emailInput" value="Your Email Address:"/>
                        <h:inputText id="emailInput" value="#{userBean.email}"/>
                        <h:outputLabel for="passwordInput" value="Password:"/>
                        <h:inputSecret id="passwordInput" value="#{userBean.password}"/>
                        <h:panelGrid columns="2">
                            <a4j:commandLink onclick="Richfaces.hideModalPanel('loginPanel');">
                                Cancel
                            </a4j:commandLink>
                            <a4j:commandLink action="#{userBean.loginAction}"
                                oncomplete="Richfaces.hideModalPanel('loginPanel');">
                                Login
                            </a4j:commandLink>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>



    </body>
</html>

</f:view>
