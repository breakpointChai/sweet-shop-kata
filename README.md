
````markdown
<div align="center">

# 🍬 Sweet Shop Kata API 🍭

**A production-ready RESTful API for a modern e-commerce sweet shop, built with Spring Boot & Spring Security.**

</div>

<p align="center">
  <a href="https://github.com/breakpointChai/sweet-shop-kata/actions">
    <img src="https://img.shields.io/github/actions/workflow/status/breakpointChai/sweet-shop-kata/maven.yml?branch=main&style=flat-square" alt="Build Status"/>
  </a>
  <a href="https://github.com/breakpointChai/sweet-shop-kata/blob/main/LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square" alt="License"/>
  </a>
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 17+"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Spring Boot 3.x"/>
</p>

---

This project provides a secure, scalable backend architecture featuring JWT-based authentication, role-based access control, product management, a dynamic shopping cart, and a seamless payment gateway integration with Razorpay.

## ✨ Key Features

| Feature                 | For Customers (👩‍🍳)                                     | For Admins (👑)                                         |
| ----------------------- | -------------------------------------------------------- | ------------------------------------------------------- |
| 🔐 **Authentication** | Secure Registration & JWT Login System                   | Secure Admin-only Login                                 |
| 🍰 **Product Catalog** | Browse, Search & Filter Sweets                           | Full CRUD Operations for Sweets                         |
| 🗂️ **Categories** | View Sweets by Category                                  | Full CRUD Operations for Categories                     |
| 🛒 **Shopping Cart** | Add, Update, and Remove Items from Cart                  | -                                                       |
| 💳 **Payments** | Seamless Checkout with Razorpay                          | -                                                       |
| 🧾 **Order History** | View and Track Past Purchases                            | View all user orders & manage fulfillment *(Future)* |


## 🛠️ Tech Stack & Tools

This project is built with a modern and robust stack:

<p align="center">
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring"/>
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" alt="Spring Security"/>
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate"/>
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5"/>
  <img src="https://img.shields.io/badge/Mockito-FFFFFF?style=for-the-badge&logo=mockito&logoColor=blue" alt="Mockito"/>
  <img src="https://img.shields.io/badge/Razorpay-02042B?style=for-the-badge&logo=razorpay&logoColor=3395FF" alt="Razorpay"/>
  <img src="https://img.shields.io/badge/Cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white" alt="Cloudinary"/>
</p>

<details>
<summary><b>🌀 View System Sequence Flow</b></summary>

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
````

\</details\>

-----

## 🚀 Getting Started

Follow these steps to get the project up and running on your local machine.

### ✅ Prerequisites

- **Java 17+**
- **Apache Maven**
- **MySQL Database**
- **Postman** (or any API client)
- **Cloudinary & Razorpay Accounts** (for API keys)

### ⚙️ Installation & Setup

1️⃣ **Clone the Repository**

```bash
git clone [https://github.com/breakpointChai/sweet-shop-kata.git](https://github.com/breakpointChai/sweet-shop-kata.git)
cd sweet-shop-kata
```

2️⃣ **Configure the Database**

- Create a new MySQL database:
  ```sql
  CREATE DATABASE sweet_shop;
  ```
- Update your database credentials in `src/main/resources/application.properties`.

3️⃣ **Set Environment Variables**

- Configure your API keys in `application.properties`:
  ```properties
  # Cloudinary
  CLOUDINARY_CLOUD_NAME=your_cloud_name
  CLOUDINARY_API_KEY=your_api_key
  CLOUDINARY_API_SECRET=your_api_secret

  # Razorpay
  RAZORPAY_KEY_ID=your_key_id
  RAZORPAY_KEY_SECRET=your_key_secret
  ```

4️⃣ **Run the Application**

```bash
./mvnw spring-boot:run
```

> The API will be available at 👉 `http://localhost:8080`

-----

## 🛰️ API Endpoints

\<details\>
\<summary\>\<b\>🔑 Authentication Endpoints (/api/auth)\</b\>\</summary\>

| Endpoint | Method | Description | Access |
| :--- | :--- | :--- | :--- |
| `/register` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-POST-blue%3Fstyle%3Dflat-square" alt="POST"/\> | Register a new customer account | **Public** |
| `/login` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-POST-blue%3Fstyle%3Dflat-square" alt="POST"/\> | Log in & get a JWT token | **Public** |

\</details\>

\<details\>
\<summary\>\<b\>🍭 Sweets Endpoints (/api/sweets)\</b\>\</summary\>

| Endpoint | Method | Description | Access |
| :--- | :--- | :--- | :--- |
| `/` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-GET-green%3Fstyle%3Dflat-square" alt="GET"/\> | Get all sweets | **Authenticated** |
| `/{sweetId}` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-GET-green%3Fstyle%3Dflat-square" alt="GET"/\> | Get details of a single sweet | **Authenticated** |
| `/search` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-GET-green%3Fstyle%3Dflat-square" alt="GET"/\> | Search sweets by keyword | **Authenticated** |
| `/` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-POST-blue%3Fstyle%3Dflat-square" alt="POST"/\> | Add a new sweet | **Admin Only** |
| `/{sweetId}` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-PUT-orange%3Fstyle%3Dflat-square" alt="PUT"/\> | Update an existing sweet | **Admin Only** |
| `/{sweetId}` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-DELETE-red%3Fstyle%3Dflat-square" alt="DELETE"/\> | Delete a sweet | **Admin Only** |

\</details\>

\<details\>
\<summary\>\<b\>🗂️ Categories Endpoints (/api/categories)\</b\>\</summary\>

| Endpoint | Method | Description | Access |
| :--- | :--- | :--- | :--- |
| `/` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-GET-green%3Fstyle%3Dflat-square" alt="GET"/\> | Get all categories | **Public** |
| `/{categoryId}` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-GET-green%3Fstyle%3Dflat-square" alt="GET"/\> | Get details of a single category | **Public** |
| `/` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-POST-blue%3Fstyle%3Dflat-square" alt="POST"/\> | Add a new category | **Admin Only** |
| `/{categoryId}` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-PUT-orange%3Fstyle%3Dflat-square" alt="PUT"/\> | Update an existing category | **Admin Only** |
| `/{categoryId}` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-DELETE-red%3Fstyle%3Dflat-square" alt="DELETE"/\> | Delete a category | **Admin Only** |

\</details\>

\<details\>
\<summary\>\<b\>🛒 Cart Endpoints (/api/cart)\</b\>\</summary\>

| Endpoint | Method | Description | Access |
| :--- | :--- | :--- | :--- |
| `/` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-GET-green%3Fstyle%3Dflat-square" alt="GET"/\> | Get the current user's cart | **User Role** |
| `/add` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-POST-blue%3Fstyle%3Dflat-square" alt="POST"/\> | Add a sweet to the cart | **User Role** |
| `/items/{itemId}` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-DELETE-red%3Fstyle%3Dflat-square" alt="DELETE"/\> | Remove an item from the cart | **User Role** |

\</details\>

\<details\>
\<summary\>\<b\>📦 Orders Endpoints (/api/orders)\</b\>\</summary\>

| Endpoint | Method | Description | Access |
| :--- | :--- | :--- | :--- |
| `/create` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-POST-blue%3Fstyle%3Dflat-square" alt="POST"/\> | Create an order from the cart | **User Role** |
| `/capture`| \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-POST-blue%3Fstyle%3Dflat-square" alt="POST"/\> | Verify & capture Razorpay payment | **User Role** |
| `/` | \<img src="https://www.google.com/search?q=https://img.shields.io/badge/-GET-green%3Fstyle%3Dflat-square" alt="GET"/\> | Get all past orders for a user | **User Role** |

\</details\>

-----

\<div align="center"\>
\<h3\>🎉 Enjoy your delicious coding journey\! 🎉\</h3\>
\<p\>If you found this project useful, please consider giving it a ⭐\!\</p\>
\</div\>

```
```