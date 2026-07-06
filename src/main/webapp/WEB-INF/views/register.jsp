<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Create Your Account</title>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            background-color: #1a1919;
            color: #ffffff;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .global-header {
            background-color: #0b3c2c;
            padding: 16px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .global-header .logo {
            color: white;
            font-size: 22px;
            font-weight: bold;
            text-decoration: none;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            margin-left: 24px;
            font-size: 14px;
        }

        .auth-workspace-frame {
            flex-grow: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px 20px;
        }

        .auth-card-container {
            background-color: #111111; /* Cleaned to dark charcoal black matching login page */
            border: 1px solid #2d2d35;
            width: 100%;
            max-width: 600px;
            border-radius: 16px;
            padding: 40px;
            box-sizing: border-box;
            box-shadow: 0 10px 25px rgba(0,0,0,0.4);
        }

        .auth-card-container h2 {
            margin: 0 0 8px 0;
            font-size: 26px;
            text-align: center;
            font-weight: bold;
        }

        .subtitle-text {
            text-align: center;
            color: #a0aec0;
            font-size: 14px;
            margin: 0 0 25px 0;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 16px;
        }

        .split-input-row {
            display: flex;
            gap: 15px;
        }

        .split-input-row .form-input-block {
            flex: 1;
        }

        .form-input-block {
            display: flex;
            flex-direction: column;
            gap: 6px;
            position: relative;
        }

        .form-input-block label {
            font-size: 13px;
            font-weight: bold;
            color: #cbd5e0;
        }

        .form-input-block input, .form-input-block select {
            background-color: #1a1919;
            border: 1px solid #4a5568;
            color: #ffffff;
            padding: 12px 14px;
            border-radius: 8px;
            font-size: 14px;
            box-sizing: border-box;
            width: 100%;
            outline: none;
        }

        .form-input-block input:focus, .form-input-block select:focus {
            border-color: #10b981;
        }

        .password-toggle-eye {
            position: absolute;
            right: 15px;
            top: 36px;
            cursor: pointer;
            font-size: 16px;
            opacity: 0.6;
            user-select: none;
        }

        .btn-auth-action {
            background-color: #0b3c2c;
            color: #ffffff;
            border: 1px solid #10b981;
            padding: 14px;
            border-radius: 25px;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 10px;
            width: 100%;
            transition: background-color 0.2s ease;
        }

        .btn-auth-action:hover {
            background-color: #0f523d;
        }

        .auth-redirect-subtext {
            text-align: center;
            font-size: 13px;
            color: #a0aec0;
            margin-top: 5px;
        }

        .auth-redirect-subtext a {
            color: #10b981;
            text-decoration: none;
            font-weight: bold;
        }

        .alert-status-msg {
            padding: 12px;
            border-radius: 8px;
            font-size: 13px;
            margin-bottom: 15px;
            text-align: center;
        }

        .error-banner {
            background-color: rgba(239, 68, 68, 0.2);
            color: #ef4444;
            border: 1px solid #ef4444;
        }

        .global-footer {
            background-color: #0b3c2c;
            padding: 20px;
            color: #cbd5e0;
            text-align: center;
            font-size: 13px;
            margin-top: auto;
        }
    </style>
</head>
<body>

    <header class="global-header">
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion Store</a>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/product">Products</a>
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </nav>
    </header>

    <main class="auth-workspace-frame">
        <div class="auth-card-container">
            <h2>Create Your Account</h2>
            <p class="subtitle-text">Join Fashion Store and start shopping your favorite styles.</p>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert-status-msg error-banner">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/register" method="POST">
                <div class="form-input-block">
                    <label>Full Name</label>
                    <input type="text" name="fullName" placeholder="Enter full name" required />
                </div>

                <div class="split-input-row">
                    <div class="form-input-block">
                        <label>Email</label>
                        <input type="email" name="email" value="radhikathelladala02@gmail.com" required />
                    </div>
                    <div class="form-input-block">
                        <label>Phone</label>
                        <input type="tel" name="phone" placeholder="Enter phone number" required />
                    </div>
                </div>

                <div class="form-input-block">
                    <label>Password</label>
                    <input type="password" id="regPasswordInput" name="password" placeholder="••••••••" required />
                    <span class="password-toggle-eye" onclick="togglePasswordVisibility('regPasswordInput', this)">👁️</span>
                </div>

                <div class="form-input-block">
                    <label>Address Street Name</label>
                    <input type="text" name="addressLine1" placeholder="House No., Street Name" required />
                </div>

                <div class="split-input-row">
                    <div class="form-input-block">
                        <label>City</label>
                        <input type="text" name="city" placeholder="City" required />
                    </div>
                    <div class="form-input-block">
                        <label>State</label>
                        <input type="text" name="state" placeholder="State" required />
                    </div>
                </div>

                <div class="split-input-row">
                    <div class="form-input-block">
                        <label>Pincode</label>
                        <input type="text" name="pincode" placeholder="Pincode" required />
                    </div>
                    <div class="form-input-block">
                        <label>Country</label>
                        <input type="text" name="country" placeholder="Country" required />
                    </div>
                </div>

                <div class="form-input-block">
                    <label for="role">Account Type</label>
                    <select name="role" id="role" required>
                        <option value="Customer">Customer</option>
                        <option value="Admin">Admin</option>
                        <option value="Delivery Boy">Delivery Boy</option>
                    </select>
                </div>

                <button type="submit" class="btn-auth-action">Register</button>
                
                <div class="auth-redirect-subtext">
                    Already have an account? <a href="${pageContext.request.contextPath}/login">Sign In Instead</a>
                </div>
            </form>
        </div>
    </main>

    <footer class="global-footer">
        <span>© 2026 Fashion Store. All Rights Reserved.</span>
    </footer>

    <script>
        function togglePasswordVisibility(inputId, eyeIcon) {
            const field = document.getElementById(inputId);
            if (field.type === "password") {
                field.type = "text";
                eyeIcon.textContent = "🙈";
                eyeIcon.style.opacity = "1";
            } else {
                field.type = "password";
                eyeIcon.textContent = "👁️";
                eyeIcon.style.opacity = "0.6";
            }
        }
    </script>
</body>
</html>