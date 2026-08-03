# 🛍️ FashionStore — Full-Stack E-Commerce Platform

[![Java](https://img.shields.io/badge/Java-17-orange.svg?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-Servlets%20%26%20JSP-blue.svg?style=flat-square&logo=eclipseide)](https://jakarta.ee/)
[![Server](https://img.shields.io/badge/Server-Apache%20Tomcat%2010.1-red.svg?style=flat-square&logo=apachetomcat)](https://tomcat.apache.org/)
[![Build](https://img.shields.io/badge/Build-Apache%20Maven-C71A36.svg?style=flat-square&logo=apachemaven)](https://maven.apache.org/)
[![Database](https://img.shields.io/badge/Database-MySQL%20%2F%20JDBC-4479A1.svg?style=flat-square&logo=mysql)](https://www.mysql.com/)

An enterprise-grade, dynamic full-stack e-commerce web application engineered using **Java 17**, **Servlets & JSP**, **JDBC**, and **Maven**. Built following strict **MVC Architecture** and **Data Access Object (DAO)** design patterns, **FashionStore** handles user authentication, product categorization with sizing variations, dynamic shopping cart operations, and end-to-end checkout processing.

---

## 🎯 Key Engineering Highlights

* **Clean Architecture:** Strict separation of concerns across Model, View, Controller, and DAO abstraction layers.
* **Database Access:** High-performance data operations utilizing raw **JDBC (`DBConnection`)** with custom DAO implementations to prevent overhead.
* **Modular View Engine:** JSP views leveraging decoupled partial components (`navbar.jsp`, `footer.jsp`) for clean UI maintenance.
* **Multi-Variant Product Logic:** Complex database relationships supporting categories, apparel sizes (`SizeDAO`, `ProductSizeDAO`), and inventory management.
* **Secure User Workflows:** Authentication state tracking and session-managed cart persistence across user transactions.

---

## 🏗️ Architecture & Project Structure

```text
FashionStore/
├── src/main/java/
│   ├── com.fashionstore.controller/        # Controller Layer (Java Servlets)
│   │   ├── CartServlet.java                # Shopping cart modifications & updates
│   │   ├── CheckoutServlet.java            # Order processing logic
│   │   ├── HomeServlet.java                # Storefront landing controller
│   │   ├── LoginServlet.java               # Authentication handler
│   │   ├── LogoutServlet.java              # Session invalidation controller
│   │   ├── OrderConfirmationServlet.java   # Order receipt & confirmation page
│   │   ├── ProductDetailsServlet.java      # Single product & variant fetcher
│   │   ├── ProductServlet.java             # Product catalog & category filtering
│   │   └── RegisterServlet.java            # Account creation servlet
│   │
│   ├── com.fashionstore.dao/               # Data Access Object Interfaces
│   │   ├── CartDAO.java
│   │   ├── CartItemDAO.java
│   │   ├── CategoryDAO.java
│   │   ├── OrderDAO.java
│   │   ├── OrderItemDAO.java
│   │   ├── ProductDAO.java
│   │   ├── ProductSizeDAO.java
│   │   ├── SizeDAO.java
│   │   └── UserDAO.java
│   │
│   ├── com.fashionstore.dao.impl/          # JDBC DAO Implementations
│   │   ├── CartDAOImpl.java
│   │   ├── CartItemDAOImpl.java
│   │   ├── CategoryDAOImpl.java
│   │   ├── OrderDAOImpl.java
│   │   ├── OrderItemDAOImpl.java
│   │   ├── ProductDAOImpl.java
│   │   ├── ProductSizeDAOImpl.java
│   │   ├── SizeDAOImpl.java
│   │   └── UserDAOImpl.java
│   │
│   ├── com.fashionstore.model/             # POJO Data Models
│   │   ├── Cart.java | CartItem.java | Category.java
│   │   ├── Order.java | OrderItem.java | Product.java
│   │   └── ProductSize.java | Size.java | User.java
│   │
│   └── com.fashionstore.util/              # Utility & Database Helpers
│       ├── DBConnection.java               # Connection Pool / JDBC Manager
│       └── TestDB.java                     # DB connectivity test suite
│
└── src/main/webapp/                        # Web Presentation Layer
    ├── index.html
    ├── assets/                             # Frontend Static Resources
    │   ├── css/                            # Component-Specific Stylesheets
    │   ├── images/                         # Product media assets
    │   └── js/                             # Client-side scripts
    └── WEB-INF/
        ├── web.xml                         # Deployment Descriptor
        └── views/                          # Dynamic JSP Views
            ├── partials/                   # Reusable UI Layout Components
            │   ├── navbar.jsp
            │   └── footer.jsp
            ├── cart.jsp | checkout.jsp | home.jsp
            ├── login.jsp | order-confirmation.jsp
            ├── product.jsp | productDetails.jsp | register.jsp