<jsp:useBean id="timeValues" class="java.util.Date" />
<fmt:setLocale value="en" />
<!DOCTYPE html>
<html>
    <head>
        <title>Session Activity</title>
    </head>
    <body>
        <h1>Session Activity</h1>
        
        <h2>Session properties</h2>
        Session ID: ${pageContext.session.id}<br />
        Session is new: ${pageContext.session["new"]}<br />
        <c:set target="${timeValues}" property="time"
            value="${pageContext.session.creationTime}" />
        Session created: <fmt:formatDate value="${timeValues}"
                          pattern="EEE, d MMM yyyy HH:mm:ss Z" /><br />

        <h2>Page activity in this session</h2>
        <c:forEach var="visit" items="${activity}">
            ${visit.request}
            <c:if test="${!empty visit.ipAddress}">
                from IP ${visit.ipAddress.hostAddress}
            </c:if>
            <c:set target="${timeValues}" property="time"
                value="${visit.enteredTimestamp}" />    
            (<fmt:formatDate value="${timeValues}"
                          pattern="EEE, d MMM yyyy HH:mm:ss Z" />
            <c:if test="${!empty visit.timeString}">,
                stayed for ${visit.timeString}
            </c:if>)<br />
        </c:forEach>
    </body>
</html>
