<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>
<style type="text/css">
<!--
.style2 {color: #3366FF; font-size: 36px;}
.style6 {color: #FFFFFF}
-->
</style></head>

<body>
<table width="1050" height="30" border="0">
<tr><td width="581" height="26" bgcolor="#3366FF"></td>
<td width="266" bgcolor="#3366FF"><span class="style6">Welcome Nabil </span></td>
  <td width="76" bgcolor="#3366FF"><a href="profile.html">profile</a></td>
  <td width="109" bordercolor="#3366FF" bgcolor="#3366FF"><div align="left"><a href="logout.html">logout</a></div></td>
  </table>
<table width="1050" height="995" border="0">
  <tr>
    <td width="200" height="166" bgcolor="#FFFFFF"><div align="center"><img src="images/wallet-es.jpg" width="185" height="147"></div></td>
    <td width="611" bgcolor="#3399CC"><div align="center">
      <table width="581" height="140" border="0" bgcolor="#3366FF">
          <tr>
            <td width="575" height="46" bgcolor="#FFFFFF"><div align="center" class="style2">Personal Money Management </div></td>
          </tr>
          <tr>
            <td height="58" bgcolor="#3366FF"><table width="571" height="68" border="0" bordercolor="#3366FF">
              <tr>
                <td width="561" height="23">&nbsp;</td>
              </tr>
              <tr>
                <td><table width="561" border="0">
                  <tr>
                    <td width="134"><div align="center"><a href="android.html" class="style6">Android Client</a></div></td>
                    <td width="138"><div align="center"><a href="getfullaccess.html" class="style6">Get Full Access</a></div></td>
                    <td width="142"><div align="center"><a href="contactus.html" class="style6">Contact Us</a></div></td>
                    <td width="119">&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
        </table>
    </div></td>
    <td width="225" bgcolor="#FFFFFF"><div align="center"><img src="images/checkbook-es.jpg" width="161" height="164"></div></td>
  </tr>
  <tr>
    <td height="703" valign="top"><tiles:insert attribute="menu" /></td>
    <td align="center" valign="top"><tiles:insert attribute="body" /></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
