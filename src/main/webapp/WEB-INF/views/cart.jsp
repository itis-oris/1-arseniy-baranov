<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ваша корзина</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/cart.css">
</head>
<body>
<jsp:include page="menu.jsp" />
<div class="table-container">
    <h1>Ваша корзина</h1>

    <c:choose>

        <c:when test="${empty cartItems}">
            <p>Ваша корзина пуста.</p>
        </c:when>


        <c:otherwise>
            <table border="1">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Количество</th>
                    <th>Цена</th>
                    <th>Сумма</th>
                    <th>Действие</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="totalSum" value="0.00" scope="page" />

                <c:forEach var="item" items="${cartItems}">

                    <c:set var="product" value="${products[item.product_id - 1]}" />
                    <c:set var="total" value="${item.amount * product.price}" />
                    <c:set var="totalSum" value="${totalSum + total}" scope="page" />

                    <tr>
                        <td>${product.name}</td>
                        <td>${item.amount}</td>
                        <td>${product.price}</td>
                        <td>${total}</td>
                        <td>

                            <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="quantity" value="${item.amount}">
                                <input type="hidden" name="productId" value="${item.product_id}">
                                <button type="submit">Убрать из корзины</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>


                <tr>
                    <td colspan="4" style="text-align:right;"><strong>Итого:</strong></td>
                    <td><strong>${totalSum}</strong></td>
                </tr>
                </tbody>
            </table>
            <div  class="create-order" >
                <form action="${pageContext.request.contextPath}/order" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="makeOrder">
                    <input type="hidden" name="totalSum" value="${totalSum}">
                    <button type="submit">Оформить заказ</button>
                </form>
            </div>

        </c:otherwise>
    </c:choose>
</div>

</body>
</html>