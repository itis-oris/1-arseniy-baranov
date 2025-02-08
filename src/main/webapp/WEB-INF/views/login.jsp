<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Вход</h2>
    <form action="${pageContext.request.contextPath}/login" method="post" class="mt-4">
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>

            <input type="email" id="email" name="email" class="form-control"
                   value="${email != null ? email : ''}" required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Пароль:</label>
            <input type="password" id="password" name="password" class="form-control"
                   value="${password != null ? password : ''}" required>
        </div>

        <c:if test="${not empty errorEmpty}">
            <div class="alert alert-danger" role="alert">
                    ${errorEmpty}
            </div>
        </c:if>

        <c:if test="${not empty errorInvalid}">
            <div class="alert alert-danger" role="alert">
                    ${errorInvalid}
            </div>
        </c:if>

        <div class="text-center">
            <button type="submit" class="btn btn-primary">Войти</button>
        </div>
    </form>

    <div class="text-center">
        <p>У вас нет аккаунта? <a href="${pageContext.request.contextPath}/registration">Зарегистрироваться</a></p>
    </div>
</div>

</body>
</html>
