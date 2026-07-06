<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
    <a href="${pageContext.request.contextPath}/home" class="logo">Fashion Store</a>
    <div class="search-bar-container">
        <input type="text" placeholder="Black Casual Shirt">
        <button type="button">Search</button>
    </div>
    <nav class="nav-menu">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/product">Products</a>
        <a href="#">Cart</a>
        <% if (session.getAttribute("userName") != null) { %>
            <span style="color: #00cc96; margin-left: 15px; font-weight: bold;">👋 Hi, <%= session.getAttribute("userName") %></span>
            <a href="${pageContext.request.contextPath}/logout" style="color: #ff4d4d;">Logout</a>
        <% } else { %>
            <a href="${pageContext.request.contextPath}/login">Login</a>
        <% } %>
    </nav>
</header>