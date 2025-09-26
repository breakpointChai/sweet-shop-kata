# 🍬 Sweet Shop Kata API

Welcome to the **Sweet Shop Kata** backend repository! This project is a **production-ready RESTful API** built with **Spring Boot** that powers a modern e-commerce platform for a delightful sweet shop.

What sets this sweet shop apart is its cutting-edge integration with **Google's Gemini AI**. We've introduced **ChefAI**, an intelligent assistant that can generate recipes for any sweet on demand. Additionally, our AI can create captivating descriptions, SEO-friendly tags, and relevant keywords for products, making catalogue management a breeze.

The application features a **secure, scalable architecture** with role-based access control, comprehensive product management, a dynamic shopping cart, and a seamless payment gateway integrated with **Razorpay**.

---

## ✨ Key Features

### 🤖 AI-Powered Innovations
- 👨‍🍳 **ChefAI Recipe Generation**: Instantly generate detailed recipes for any sweet. Each recipe includes a list of ingredients, step-by-step instructions, cooking time, calorie information, and diet type. You can even download the recipes as a PDF!
- ✍️ **AI-Powered Content Automation**: Streamline your workflow with AI-generated content. By simply entering the name and category of a new sweet, admins can receive three unique, mouth-watering menu descriptions for any sweet. The AI also provides five SEO-friendly tags and five keywords to enhance product listings ,improve searchability and attract more customers.

### 👩‍🍳 For Customers
- 🔐 **Secure Authentication**: Robust registration and login system using **JWT** to ensure user data is always protected.
- 🍰 **Browse & Discover**: Customers can effortlessly view all sweets, search for them by name, and filter by category.
- 🛒 **Dynamic Shopping Cart**: A fully functional shopping cart that allows users to add sweets, update quantities, or remove items with ease.
- 💳 **Seamless Checkout**: Secure order placement with the integrated **Razorpay** payment gateway for a smooth and reliable transaction process.
- 🧾 **Order History**: Customers can track all their past purchases and view their order history at any time.

### 👑 For Admins
- 🔑 **Admin Login**: Secure authentication for administrators to manage the application.
- 🍭 **Sweet Management**: Full **CRUD** (Create, Read, Update, Delete) operations for sweets, allowing admins to manage the product catalog effectively.
- 🗂️ **Category Management**: Admins can create, update, and delete categories to organize the sweets.
- 📈 **Order Management** *(Future Scope)*: The system is designed for future expansion, including an order management dashboard for admins to view all orders and update their statuses (e.g., *Shipped*, *Delivered*).

---

## 🛠️ Tech Stack & Tools

- **Backend:** Spring Boot 3
- **AI Integration:** Google Gemini
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
- Accounts for **Cloudinary**, **Razorpay**, and a **Google Gemini API Key**.

### ⚙️ Installation & Setup

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/your-username/sweet-shop-kata.git](https://github.com/your-username/sweet-shop-kata.git)
    cd sweet-shop-kata
    ```

2.  **Configure the Database**
    * Create a new MySQL database: `CREATE DATABASE sweet_shop;`
    * Update `src/main/resources/application.properties` with your MySQL username & password.

3.  **Set Environment Variables**
    * Set the following in your IDE's run configuration or your OS. These are configured in `src/main/resources/application.properties`.
    ```bash
    # Cloudinary
    CLOUDINARY_CLOUD_NAME=your_cloud_name
    CLOUDINARY_API_KEY=your_api_key
    CLOUDINARY_API_SECRET=your_api_secret

    # Razorpay
    RAZORPAY_KEY=your_key_id
    RAZORPAY_SECRET=your_key_secret

    # JWT Secret
    JWT_SECRET=your_jwt_secret_key

    # Google Gemini
    GEMINI_API_KEY=your_gemini_api_key
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

### 🤖 AI (`/api/ai`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/generate-suggestions` | `POST` | Generate sweet descriptions, tags, and keywords. | **Admin Only** |
| `/recipe` | `GET` | Get a recipe for a sweet. | **Admin Only** |
| `/recipe/pdf` | `POST` | Download a recipe as a PDF. | **Admin Only** |

### 🍭 Sweets (`/api/sweets`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/` | `GET` | Get all sweets. | Authenticated |
| `/{id}` | `GET` | Get details of a sweet. | Authenticated |
| `/search` | `GET` | Search sweets by keyword. | Authenticated |
| `/` | `POST` | Add a new sweet. | **Admin Only** |
| `/{id}` | `PUT` | Update an existing sweet. | **Admin Only** |
| `/{id}` | `DELETE` | Delete a sweet. | **Admin Only** |

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
| `/items/{cartItemId}` | `DELETE` | Remove an item from the cart. | **User Role** |

### 📦 Orders (`/api/orders`)
| Endpoint | Method | Description | Access |
|---|---|---|---|
| `/create` | `POST` | Create an order from cart (start payment). | **User Role** |
| `/capture` | `POST` | Verify & capture Razorpay payment. | **User Role** |

---
## 📊 Application Flow
Here's a sequence diagram illustrating a basic user interaction flow:
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