<!DOCTYPE html>
<html>
    <head>
        <title>Product List</title>
    </head>
    <body>
        <h1>Product List</h1>
        <a href="<c:url value="/shop?action=viewCart" />">View Cart</a><br /><br />
        <c:forEach var="product" items="${products}">
            <a href="<c:url value="shop">
               <c:param name="action" value="addToCart" />
               <c:param name="productId" value="${product.key}" />
           </c:url>">${product.value}</a><br />
        </c:forEach>
    </body>
</html>
