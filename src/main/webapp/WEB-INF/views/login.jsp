<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fashion Store - Sign In</title>
    
    <link rel="icon" type="image/jpeg" href="${pageContext.request.contextPath}/assets/images/img/favicon.jpg">
    
    <style>
        html, body {
            margin: 0;
            padding: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            /* 🎨 Elegant Dark/Green Combination Canvas */
            background-color: #121614; 
            color: #ffffff;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .global-header {
            background-color: #0b3c2c; /* Signature Forest Green */
            padding: 16px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
        }

        .global-header .logo {
            color: white;
            font-size: 22px;
            font-weight: bold;
            text-decoration: none;
            letter-spacing: 0.5px;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            margin-left: 24px;
            font-size: 14px;
            opacity: 0.9;
        }

        .nav-links a:hover {
            opacity: 1;
            text-decoration: underline;
        }

        .auth-workspace-frame {
            flex-grow: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 60px 20px;
        }

        /* 🌟 ATTRACTIVE DEEP GREEN/BLACK PREMIUM CARD CONTAINMENT */
        .auth-card-container {
            background-color: #0b110e; /* Deep green-tinted charcoal black */
            border: 1px solid rgba(16, 185, 129, 0.2); /* Soft green border outline */
            width: 100%;
            max-width: 440px;
            border-radius: 20px;
            padding: 45px 40px;
            box-sizing: border-box;
            text-align: center;
            
            /* Layered drop shadows with a dark green under-glow effect */
            box-shadow: 
                0 10px 30px rgba(0, 0, 0, 0.5),
                0 20px 50px rgba(11, 60, 44, 0.3);
        }

        .auth-card-container h2 {
            margin: 0 0 8px 0;
            font-size: 32px;
            font-weight: 700;
            color: #ffffff;
            letter-spacing: -0.5px;
        }

        .auth-subtitle {
            font-size: 14px;
            color: #94a3b8;
            margin-bottom: 32px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 22px;
            text-align: left;
        }

        .form-input-block {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        .form-input-block label {
            font-size: 13px;
            font-weight: 600;
            color: #cbd5e0;
        }

        .form-input-block input, .form-input-block select {
            background-color: #161d1a; /* Dark green input fields */
            border: 1px solid #2e3d37;
            color: #ffffff;
            padding: 14px 16px;
            border-radius: 12px;
            font-size: 14px;
            box-sizing: border-box;
            width: 100%;
            outline: none;
            transition: all 0.2s ease-in-out;
        }

        /* Input field selection reactive glow shadow */
        .form-input-block input:focus, .form-input-block select:focus {
            border-color: #10b981;
            box-shadow: 0 0 0 4px rgba(16, 185, 129, 0.15);
        }

        /* Solid Brand Green Action Button with Shadow */
        .btn-auth-action {
            background-color: #0b3c2c;
            color: #ffffff;
            border: 1px solid #10b981;
            padding: 15px;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 10px;
            width: 100%;
            box-shadow: 0 4px 12px rgba(11, 60, 44, 0.4);
            transition: all 0.2s ease;
        }

        .btn-auth-action:hover {
            background-color: #0f523d;
            transform: translateY(-1px);
            box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
        }

        .btn-auth-action:active {
            transform: translateY(0);
        }

        .auth-redirect-subtext {
            text-align: center;
            font-size: 13px;
            color: #a0aec0;
            margin-top: 16px;
        }

        .auth-redirect-subtext a {
            color: #10b981;
            text-decoration: none;
            font-weight: 600;
        }

        .auth-redirect-subtext a:hover {
            text-decoration: underline;
        }

        .alert-status-msg {
            padding: 12px 16px;
            border-radius: 12px;
            font-size: 13px;
            margin-bottom: 20px;
            text-align: center;
        }

        .error-banner {
            background-color: rgba(239, 68, 68, 0.2);
            color: #fca5a5;
            border: 1px solid #ef4444;
        }

        .global-footer {
            background-color: #0b3c2c;
            padding: 24px;
            color: #e2e8f0;
            text-align: center;
            font-size: 13px;
            margin-top: auto;
            box-shadow: 0 -4px 15px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>

    <header class="global-header">
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion Store</a>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </nav>
    </header>

    <% if (session.getAttribute("successMessage") != null) { %>
        <div id="logoutToast" style="
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #ef4444;
            color: #ffffff;
            padding: 16px 24px;
            border-radius: 8px;
            font-family: sans-serif;
            font-weight: bold;
            font-size: 14px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            z-index: 10000;
            opacity: 0;
            transform: translateY(-20px);
            transition: opacity 0.3s ease, transform 0.3s ease;
        ">
            <%= session.getAttribute("successMessage") %>
        </div>

        <script>
            window.addEventListener('DOMContentLoaded', function() {
                var toast = document.getElementById('logoutToast');
                setTimeout(function() {
                    toast.style.opacity = '1';
                    toast.style.transform = 'translateY(0)';
                }, 100);
                setTimeout(function() {
                    toast.style.opacity = '0';
                    toast.style.transform = 'translateY(-20px)';
                }, 3000);
            });
        </script>
        <% session.removeAttribute("successMessage"); %>
    <% } %>

    <main class="auth-workspace-frame">
        <div class="auth-card-container">
            <h2>Welcome Back</h2>
            <div class="auth-subtitle">Sign in to your account to manage your fashion experience</div>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert-status-msg error-banner">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login" method="POST">
                <div class="form-input-block">
                    <label>Email Address</label>
                    <input type="email" name="email" placeholder="name@example.com" value="radhikathelladala02@gmail.com" required />
                </div>

                <div class="form-input-block">
                    <label>Password</label>
                    <input type="password" name="password" placeholder="••••••••" required />
                </div>

                <div class="form-input-block">
                    <label for="role">Select Account Type</label>
                    <select name="role" id="role" required>
                        <option value="Customer">Customer</option>
                        <option value="Admin">Admin</option>
                        <option value="Delivery Boy">Delivery Boy</option>
                    </select>
                </div>

                <button type="submit" class="btn-auth-action">Sign In</button>
                
                <div class="auth-redirect-subtext">
                    New User? <a href="${pageContext.request.contextPath}/register">Register Now</a>
                </div>
            </form>
        </div>
    </main>

    <footer class="global-footer">
        <span>© 2026 Fashion Store. All Rights Reserved.</span>
    </footer>

</body>
</html>