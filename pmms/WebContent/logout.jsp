<html>
<body>
<h1>Logged out</h1>
<% session.invalidate(); %>
<jsp:forward page="/HomePage.jsf"/>
</body>
</html>