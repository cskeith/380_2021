<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Create a Ticket</h2>
    <form:form method="POST" enctype="multipart/form-data"
                             modelAttribute="ticketForm">
        <form:label path="subject">Subject</form:label><br />
        <form:input type="text" path="subject" /><br /><br />
        <form:label path="body">Body</form:label><br />
        <form:textarea path="body" rows="5" cols="30" /><br /><br />
        <b>Attachments</b><br />
        <input type="file" name="attachments" multiple="multiple" /><br /><br />
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
