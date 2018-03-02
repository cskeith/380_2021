<%@ page import="java.util.Vector, ouhk.comps380f.PageVisit" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat" %>
<%!
    private static String toString(long timeInterval) {
        if (timeInterval < 1_000) {
            return "less than one second";
        }
        if (timeInterval < 60_000) {
            return (timeInterval / 1_000) + " seconds";
        }
        return "about" + (timeInterval / 60_000) + " minutes";
    }
%>
<%
    SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Session Activity</title>
    </head>
    <body>
        <h1>Session Activity</h1>

        <h2>Session properties</h2>
        Session ID: <%= session.getId()%><br />
        Session is new: <%= session.isNew()%><br />
        Session created: <%= f.format(new Date(session.getCreationTime()))%><br />

        <h2>Page activity in this session</h2>
        <%
            @SuppressWarnings("unchecked")
            Vector<PageVisit> visits
                    = (Vector<PageVisit>) session.getAttribute("activity");

            for (PageVisit visit : visits) {
                out.print(visit.getRequest());
                if (visit.getIpAddress() != null) {
                    out.print(" from IP "
                            + visit.getIpAddress().getHostAddress());
                }
                out.print(" (" + f.format(new Date(visit.getEnteredTimestamp())));
                if (visit.getLeftTimestamp() != null) {
                    out.println(", stayed for " + toString(
                            visit.getLeftTimestamp() - visit.getEnteredTimestamp()
                    ));
                }
                out.println(")<br />");
            }
        %>
    </body>
</html>
