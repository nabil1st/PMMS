<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--
    This file is an entry point for JavaServer Faces application.
--%>

<f:view>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Login to PMMS</title>
<style type="text/css">
<!--
.style2 {color: #3366FF; font-size: 36px;}
.style6 {color: #FFFFFF}
.style8 {
	font-size: xx-large;
	color: #000000;
}
.style9 {
	font-size: larger;
	font-style: italic;
	font-weight: bold;
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
                <td width="353" height="26"><span class="style8">Sign in now! </span></td>
              </tr>
              <tr>
                <td><table width="358" height="217" border="0">
                  <tr>
                    <td width="348" height="171" bordercolor="#F0F0F0"><table width="356" height="128" border="0">
                      <tr>
                        <td width="164" height="37"><div align="right" class="style11">Email</div></td>
                        <td width="164"><h:inputText id="j_username" value="#{userBean.email}"/></td>
                      </tr>
                      <tr>
                        <td height="29"><div align="right" class="style11">Password</div></td>
                        <td><h:inputSecret id="j_password" value="#{userBean.password}"/></td>
                      </tr>
                      <tr>
                        <td height="54">&nbsp;</td>
                        <td><DIV align="right"><h:commandButton value="Sign In" action="#{userBean.loginAction}"/></DIV> </td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td bordercolor="#F0F0F0"><span class="style10">Don't have and account?</span>&nbsp;&nbsp;<h><a href='NewAccount.jsf'>Create One</a></h></td>
                    
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