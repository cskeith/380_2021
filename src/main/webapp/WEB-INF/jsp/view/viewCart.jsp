<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
        <title>View Cart</title>
    </head>
    <body>
        <a href="<c:url value="/shop?action=emptyCart" />">Empty Cart</a>
        <h1>View Cart</h1>
        <a href="<c:url value="/shop" />">Product List</a><br /><br />
        <%
            @SuppressWarnings("unchecked")
            Map<Integer, String> products =
                   (Map<Integer, String>) request.getAttribute("products");

            @SuppressWarnings("unchecked")
            Map<Integer, Integer> cart =
                   (Map<Integer, Integer>) session.getAttribute("cart");

            if (cart == null || cart.size() == 0) { %>
                Your cart is empty
        <%  } else { %>
            <ul>
            <% for (int id: cart.keySet()) { %>
                <li><%=products.get(id)%> (qty: <%=cart.get(id) %>) </li>
            <% } %>
            </ul>
        <% } %>
    </body>
</html>
