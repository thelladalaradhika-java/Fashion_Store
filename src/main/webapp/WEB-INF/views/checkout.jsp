<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.controller.CartServlet.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Checkout</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/checkout.css">
    <link rel="icon" type="image/jpeg" href="${pageContext.request.contextPath}/assets/images/img/favicon.jpg">
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

    <div class="checkout-main-frame">
        <div class="checkout-form-column">
            <form action="${pageContext.request.contextPath}/checkout" method="POST">
                
                <div class="input-row">
                    <label>Full Name</label>
                    <input type="text" name="fullName" value="<%= request.getAttribute("dbName") != null ? request.getAttribute("dbName") : "" %>" required />
                </div>

                <div class="input-row">
                    <label>Phone Number</label>
                    <input type="tel" name="phone" value="<%= request.getAttribute("dbPhone") != null ? request.getAttribute("dbPhone") : "" %>" required />
                </div>

                <div class="input-row">
                    <label>Delivery Address</label>
                    <input type="text" name="address" value="<%= request.getAttribute("dbAddress") != null ? request.getAttribute("dbAddress") : "" %>" required />
                </div>

                <div class="input-split-grid">
                    <div class="input-row">
                        <label>City</label>
                        <input type="text" name="city" value="<%= request.getAttribute("dbCity") != null ? request.getAttribute("dbCity") : "" %>" required />
                    </div>
                    <div class="input-row">
                        <label>State</label>
                        <input type="text" name="state" value="<%= request.getAttribute("dbState") != null ? request.getAttribute("dbState") : "" %>" required />
                    </div>
                </div>

                <div class="input-split-grid">
                    <div class="input-row">
                        <label>Pincode</label>
                        <input type="text" name="pincode" value="<%= request.getAttribute("dbPincode") != null ? request.getAttribute("dbPincode") : "" %>" required />
                    </div>
                    <div class="input-row">
                        <label>Country</label>
                        <input type="text" name="country" value="<%= request.getAttribute("dbCountry") != null ? request.getAttribute("dbCountry") : "" %>" required />
                    </div>
                </div>

                <div class="payment-selection-header">Payment Method</div>
                <div class="payment-option-card">
                    <label><input type="radio" name="paymentMethod" value="COD" checked /> Cash on Delivery</label>
                </div>
                <div class="payment-option-card">
                    <label><input type="radio" name="paymentMethod" value="UPI" /> UPI</label>
                </div>
                <div class="payment-option-card">
                    <label><input type="radio" name="paymentMethod" value="CARD" /> Credit / Debit Card</label>
                </div>

                <button type="submit" class="btn-place-order">Place Order</button>
            </form>
        </div>

        <div class="checkout-summary-column">
            <div class="summary-sticky-box">
                <h3>Order Summary</h3>
                <hr>
                <% 
                    List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartList");
                    double subtotal = 0.0;
                    if (cartItems != null && !cartItems.isEmpty()) {
                        for (CartItem item : cartItems) {
                            subtotal += (item.getPrice() * item.getQuantity());
                %>
                    <div style="display:flex; justify-content:space-between; margin-bottom:10px;">
                        <div>
                            <strong><%= item.getName() %></strong><br>
                            <span style="font-size:12px; color:#94a3b8;">Qty: <%= item.getQuantity() %></span>
                        </div>
                        <div>₹ <%= String.format("%.2f", item.getPrice() * item.getQuantity()) %></div>
                    </div>
                <% 
                        }
                    } 
                    // 🌟 FIXED: Delivery fee set to ₹200.00 matching CartServlet calculation exactly
                    double delivery = subtotal > 0 ? 200.0 : 0.0;
                    double total = subtotal + delivery;
                %>
                <hr>
                
                <!-- 🌟 ADDED: Transparent itemized summary lines -->
                <div style="display:flex; justify-content:space-between; margin-bottom:8px; font-size:14px; color:#cbd5e1;">
                    <div>Items Subtotal</div>
                    <div>₹ <%= String.format("%.2f", subtotal) %></div>
                </div>
                <div style="display:flex; justify-content:space-between; margin-bottom:8px; font-size:14px; color:#cbd5e1;">
                    <div>Delivery & Handling Fee</div>
                    <div>₹ <%= String.format("%.2f", delivery) %></div>
                </div>
                
                <hr>
                <div style="display:flex; justify-content:space-between; font-weight:bold; font-size:18px; margin-top:12px; color:#10b981;">
                    <div>Total Amount</div>
                    <div>₹ <%= String.format("%.2f", total) %></div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
