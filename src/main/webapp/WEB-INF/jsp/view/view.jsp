<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<h2>Ticket #${ticketId}: <c:out value="${ticket.subject}" /></h2>
<i>Customer Name - <c:out value="${ticket.customerName}" /></i><br /><br />
<c:out value="${ticket.body}" /><br /><br />
<c:if test="${ticket.numberOfAttachments > 0}">
    Attachments:
    <c:forEach items="${ticket.attachments}" var="attachment"
               varStatus="status">
        <c:if test="${!status.first}">, </c:if>
        <a href="<c:url value="/ticket/${ticketId}/attachment/${attachment.name}" />">
          <c:out value="${attachment.name}" /></a>
    </c:forEach><br /><br />
</c:if>
<a href="<c:url value="/ticket" />">Return to list tickets</a>
</body>
</html>
