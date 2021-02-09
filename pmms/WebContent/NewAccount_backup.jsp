<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:loadBundle basename="messages" var="msg"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<f:view>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>
<style type="text/css">
<!--
.style2 {color: #3366FF; font-size: 36px;}
.style6 {color: #FFFFFF}
.style8 {
	font-size: xx-large;
	color: #000000;
}
.style10 {font-size: larger}
.style11 {font-family: Arial, Helvetica, sans-serif}
-->
</style>
</head>

<body>
<h:form>
<table width="1128" height="623" border="0">
  <tr>
    <td width="327" height="70">&nbsp;</td>
    <td width="478">&nbsp;</td>
    <td width="301">&nbsp;</td>
  </tr>
  <tr>
    <td height="506">&nbsp;</td>
    <td><table width="481" height="479" border="1">
      <tr>
        <td width="471" height="59"><table width="581" height="108" border="0" bgcolor="#3366FF">
          <tr>
            <td width="575" height="43" bgcolor="#FFFFFF"><div align="center" class="style2">Personal Money Management </div></td>
          </tr>
          <tr>
            <td height="58" bgcolor="#3366FF"><table width="576" border="0">
                <tr>
                  <td width="129"><div align="center"><a href="#" class="style6">Android Client</a></div></td>
                  <td width="139"><div align="center"><a href="contactus.html" class="style6">Contact Us</a></div></td>
                  <td width="137">&nbsp;</td>
                  <td width="142">&nbsp;</td>
                </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><table width="584" height="341" border="0" id="inner_table">
          <tr>
            <td width="106" height="21">&nbsp;</td>
            <td width="356">&nbsp;</td>
            <td width="98">&nbsp;</td>
          </tr>
          <tr>
            <td height="314">&nbsp;</td>
            <td><table width="363" height="312" border="0">
              <tr>
                <td width="353" height="26"><span class="style8">New account</span></td>
              </tr>
              <tr>
                <td><table width="358" height="217" border="0">
                  <tr>
                    <td width="348" height="171" bordercolor="#F0F0F0"><table width="354" border="0">
                      <tr>
                        <td width="164"><div align="right"><span class="style11">First Name</span></div></td>
                        <td width="164"><h:inputText value="#{accountBean.firstName}" required="true"/></td>
                      </tr>
                      <tr>
                        <td><div align="right"><span class="style11">Last Name</span></div></td>
                        <td><h:inputText value="#{accountBean.lastName}" required="true"/></td>
                      </tr>
                      <tr>
                        <td><div align="right"><span class="style11">Email</span></div></td>
                        <td><h:inputText value="#{accountBean.email}" required="true"/></td>
                      </tr>
                      <tr>
                        <td><div align="right"><span class="style11">Confirm email </span></div></td>
                        <td><h:inputText value="#{accountBean.confirmEmail}" required="true"/></td>
                      </tr>
                      <tr>
                        <td><div align="right"><span class="style11">Password</span></div></td>
                        <td><h:inputSecret value="#{accountBean.password}" required="true"/></td>
                      </tr>
                      <tr>
                        <td><div align="right"><span class="style11">Confirm password </span></div></td>
                        <td><h:inputSecret value="#{accountBean.confirmPassword}" required="true"/></td>
                      </tr>
                      <tr>
                        <td><div align="right"><span class="style11">Account description </span></div></td>
                        <td><h:inputText value="#{accountBean.accountDescription}" required="true"/></td>
                      </tr>
                      <tr>
                        <td><div align="right"><span class="style11">Total cash on hand </span></div></td>
                        <td><h:inputText value="#{accountBean.cashBalance}" required="true"/></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td bordercolor="#F0F0F0"><div align="right"><span class="style10"><h:commandButton value="Create Account" action="#{accountBean.createAction}"/></span></div></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<h:messages styleClass="errorMessage" globalOnly="true"/>
</h:form>
</body>
</html>
</f:view>


