# 🍬 Sweet Shop Kata API

Welcome to the **Sweet Shop Kata** backend repository!
This is a **production-ready RESTful API** built with **Spring Boot** that powers a modern e-commerce platform for a delightful sweet shop.

The application features a **secure, scalable architecture** with role-based access control, product management, a dynamic shopping cart, and an integrated payment gateway with **Razorpay**.

---

## ✨ Key Features

### 👩‍🍳 For Customers
- 🔐 **Secure Authentication** — Registration & login system with **JWT**.
- 🍰 **Browse & Discover** — View all sweets, search by name, and filter by category.
- 🛒 **Shopping Cart** — Add sweets, update quantities, or remove items.
- 💳 **Seamless Checkout** — Secure order placement with the Razorpay payment gateway.
- 🧾 **Order History** — Track all past purchases.

### 👑 For Admins
- 🔑 **Admin Login** — Secure authentication for administrators.
- 🍭 **Sweet Management** — Full **CRUD** operations for sweets.
- 🗂️ **Category Management** — Create, update, and delete categories.
- 📈 **Order Management** *(Future Scope)* — View all orders, update statuses (e.g., *Shipped*, *Delivered*), and manage fulfillment.

---
```mermaid
sequenceDiagram
    participant Client as 👩‍🍳 Customer (Frontend)
    participant API as 🌐 Sweet Shop API (Backend)
    participant DB as 🗄️ MySQL Database

    Client->>API: POST /api/auth/login (email, password)
    API->>DB: Find user by email and verify password
    DB-->>API: User details with role
    API-->>Client: Returns JWT Token 🔑

    Note over Client: Stores JWT Token for future requests

    Client->>API: GET /api/sweets (includes JWT in Header)
    API->>API: Verifies JWT signature and role
    API->>DB: Fetch all sweets
    DB-->>API: Returns list of sweets
    API-->>Client: 200 OK with sweet data


## 🛠️ Tech Stack & Tools

- **Backend:** Spring Boot 3
- **Security:** Spring Security with JWT
- **Database:** MySQL + Spring Data JPA (Hibernate)
- **Payments:** Razorpay Payment Gateway
- **Image Hosting:** Cloudinary
- **Validation:** Spring Validation
- **Testing:** JUnit 5 & Mockito

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java 17+
- Apache Maven
- MySQL database
- Postman (for API testing)
- Accounts for **Cloudinary** & **Razorpay**

### ⚙️ Installation & Setup

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/breakpointChai/sweet-shop-kata.git](https://github.com/your-username/sweet-shop-kata.git)
    cd sweet-shop-kata
    ```

2.  **Configure the Database**
    * Create a new MySQL database: `CREATE DATABASE sweet_shop;`
    * Update `src/main/resources/application.properties` with your MySQL username & password.

3.  **Set Environment Variables**
    * Set the following in your IDE's run configuration or your OS:
    ```bash
    # Cloudinary
    CLOUDINARY_CLOUD_NAME=your_cloud_name
    CLOUDINARY_API_KEY=your_api_key
    CLOUDINARY_API_SECRET=your_api_secret

    # Razorpay
    RAZORPAY_KEY_ID=your_key_id
    RAZORPAY_KEY_SECRET=your_key_secret
    ```

4.  **Run the Application**
    ```bash
    ./mvnw spring-boot:run
    ```
    The API will be available at: 👉 `http://localhost:8080`


---


## 📝 API Endpoints

### 🔑 Authentication (`/api/auth`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/register` | `POST` | Register a new customer account. | Public |
| `/login` | `POST` | Log in & get a JWT token. | Public |

### 🍭 Sweets (`/api/sweets`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/` | `GET` | Get all sweets. | Authenticated |
| `/{sweetId}` | `GET` | Get details of a sweet. | Authenticated |
| `/search` | `GET` | Search sweets by keyword. | Authenticated |
| `/` | `POST` | Add a new sweet. | **Admin Only** |
| `/{sweetId}` | `PUT` | Update an existing sweet. | **Admin Only** |
| `/{sweetId}` | `DELETE` | Delete a sweet. | **Admin Only** |

### 🗂️ Categories (`/api/categories`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/` | `GET` | Get all categories. | Public |
| `/{categoryId}` | `GET` | Get category details. | Public |
| `/` | `POST` | Add a new category. | **Admin Only** |
| `/{categoryId}` | `PUT` | Update an existing category. | **Admin Only** |
| `/{categoryId}` | `DELETE` | Delete a category. | **Admin Only** |

### 🛒 Cart (`/api/cart`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/` | `GET` | Get current user's cart. | **User Role** |
| `/add` | `POST` | Add a sweet to the cart. | **User Role** |
| `/items/{itemId}` | `DELETE` | Remove an item from the cart. | **User Role** |

### 📦 Orders (`/api/orders`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/create` | `POST` | Create an order from cart (start payment). | **User Role** |
| `/capture` | `POST` | Verify & capture Razorpay payment. | **User Role** |
| `/` | `GET` | Get all past orders of a user. | **User Role** |

---

Enjoy your delicious coding journey with Sweet Shop Kata!

