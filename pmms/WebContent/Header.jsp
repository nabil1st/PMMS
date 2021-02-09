<%-- 
    Document   : Header
    Created on : Feb 16, 2010, 10:19:15 PM
    Author     : HP
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   
<html>        
        <body>
        	<%trackit.backingbeans.SessionBean sessionBean = (trackit.backingbeans.SessionBean) request.getSession().getAttribute("sessionBean");%>
			<table>
			<tr align="left">
			<td colspan="5" align="center"><h1>Personal Money Management System</h1> </td>			
			</tr>
			<tr>
			<td colspan="2" align="right">
			<h5>Welcome <%=sessionBean.getUserName()%></h5>
			</td>
			</tr>
			</table>			
        </body>
    </html>

