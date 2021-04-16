<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<h2>Tickets</h2>
<a href="<c:url value="/ticket/create" />">Create a Ticket</a><br /><br />
<c:choose>
    <c:when test="${fn:length(ticketDatabase) == 0}">
        <i>There are no tickets in the system.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${ticketDatabase}" var="entry">
            Ticket ${entry.key}:
            <a href="<c:url value="/ticket/view/${entry.key}" />">
               <c:out value="${entry.value.subject}" /></a>
            (customer: <c:out value="${entry.value.customerName}" />)
            [<a href="<c:url value="/ticket/edit/${entry.key}" />">Edit</a>]
            [<a href="<c:url value="/ticket/delete/${entry.key}" />">Delete</a>]<br />
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
