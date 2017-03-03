<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Ticket #${ticketId}: <c:out value="${ticket.subject}" /></h2>
        <i>Customer Name - <c:out value="${ticket.customerName}" /></i><br /><br />
        <c:out value="${ticket.body}" /><br /><br />
        <a href="<c:url value="/tickets" />">Return to list tickets</a>
    </body>
</html>
