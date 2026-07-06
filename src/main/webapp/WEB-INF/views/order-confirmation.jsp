<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.controller.CartServlet.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Order Confirmation</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/order-confirmation.css">
</head>
<body>

    <header class="global-header">
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion Store</a>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/product">Products</a>
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
        </nav>
    </header>

    <main class="confirmation-workspace">
        <div class="confirmation-card">
            
            <div class="success-header">
                <div class="success-icon-circle">✓</div>
                <h2>Order Placed Successfully!</h2>
                <p class="sub-message">Your order has been processed using your account details.</p>
            </div>

            <div class="summary-section-block">
                <table class="metadata-table">
                    <tr>
                        <td>Order ID</td>
                        <td class="align-right font-bold">#${orderId}</td>
                    </tr>
                    <tr>
                        <td>Order Date</td>
                        <td class="align-right">${orderDate}</td>
                    </tr>
                    <tr>
                        <td>Payment Method</td>
                        <td class="align-right font-bold">${paymentMethod}</td>
                    </tr>
                    <tr class="total-row">
                        <td>Total Amount Charged</td>
                        <td class="align-right price-text">₹ ${totalCharges}</td>
                    </tr>
                </table>
            </div>

            <div class="summary-section-block">
                <h3>Delivery Details</h3>
                <div class="address-details-content">
                    <p class="font-bold">${name}</p>
                    <p>Phone: ${phone}</p>
                    <p>${address}</p>
                    <p>${city}, ${state} - ${pincode}</p>
                    <p>${country}</p>
                </div>
            </div>

            <div class="summary-section-block">
                <h3>Ordered Items</h3>
                <div class="items-container-list">
                    <% 
                        List<CartItem> confirmedItems = (List<CartItem>) request.getAttribute("confirmedItemsList");
                        if (confirmedItems != null && !confirmedItems.isEmpty()) {
                            for (CartItem item : confirmedItems) {
                    %>
                                <div class="item-variant-row">
                                    <div class="item-left-meta">
                                        <span class="variant-label-id"><%= item.getName() %> (<%= item.getSize() %>)</span>
                                        <span class="item-qty-sub">Quantity: <%= item.getQuantity() %></span>
                                    </div>
                                    <div class="item-right-price">
                                        ₹ <%= String.format("%.2f", item.getPrice() * item.getQuantity()) %>
                                    </div>
                                </div>
                    <% 
                            }
                        } 
                    %>
                </div>
            </div>

            <div class="action-footer-links">
                <a href="${pageContext.request.contextPath}/product" class="btn-continue">Continue Shopping</a>
                <a href="${pageContext.request.contextPath}/home" class="link-home">Go to Home</a>
            </div>

        </div>
    </main>

    <footer class="global-footer">
        <span>© 2026 Fashion Store. All Rights Reserved.</span>
    </footer>

</body>
</html>