<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мои заказы</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/order.css">
</head>
<body>
<jsp:include page="menu.jsp" />
<div class="container">
    <h1>Мои заказы</h1>

    <c:if test="${not empty ordersByClient}">
        <ul>
            <c:forEach var="order" items="${ordersByClient}">

                    <strong>Номер заказа:</strong> ${order.order_id}<br>
                    <strong>Заказ оформлен:</strong> ${order.created_at}<br>
                    <strong>Стадия заказа:</strong> ${stageMap[order.stage_id].name}<br>
                    ${stageMap[order.stage_id].description}<br>
                    <strong>Товары в заказе:</strong>

                        <c:if test="${not empty order.productOrders}">
                            <table border="1">
                                <thead>
                                <tr>
                                    <th>Название</th>
                                    <th>Количество</th>
                                    <th>Сумма</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="productOrder" items="${order.productOrders}">
                                    <c:set var="product" value="${productMap[productOrder.product_id]}" />
                                        <c:set var="amount" value="${productOrder.amount}" />
                                        <c:set var="price" value="${productOrder.total_price}" />
                                        <c:set var="price" value="${productOrder.total_price}" />

                                        <tr>
                                            <td>${product}</td>
                                            <td>${amount}</td>
                                            <td>${price}</td>
                                        </tr>


                                    </c:forEach>
                                    <tr>
                                        <td colspan="2" style="text-align:right;"><strong>Итого:</strong></td>
                                        <td><strong>${order.total_price}</strong></td>

                                    </tr>
                                <br>
                                </tbody>
                            </table>

                        </c:if>
                        <c:if test="${empty order.productOrders}">
                            <li>Нет товаров в заказе.</li>
                        </c:if>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${empty ordersByClient}">
        <p>Заказы не найдены.</p>
    </c:if>

    <a class="menu-button" href="${pageContext.request.contextPath}/mainpage">Меню</a>
</div>
</body>
</html>
