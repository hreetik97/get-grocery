# get-grocery

**Grocery Booking App:**  
A Spring Boot REST API for managing grocery items and orders with JWT-based authentication and role-based authorization.

---

## Features

- **Admin Functions:**
  - Add, update, delete, and list grocery items.
- **User Functions:**
  - Order groceries and view available items.
- **Authentication:**
  - JWT-based login.
- **Database:**
  - PostgreSQL (Dockerized) with a defined schema.

---

## Technology Stack

- **Backend:** Spring Boot, Spring Security, Spring Data JPA, Hibernate  
- **Database:** PostgreSQL  
- **Containerization:** Docker & Docker Compose  
- **Build Tool:** Gradle  
- **Utilities:** JWT, Lombok, AOP, Bean Validation

---

## Database Schema

### Tables

1. **GroceryItem**  
   - **Columns:**  
     - `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)  
     - `name` (VARCHAR(100), NOT NULL)  
     - `price` (DECIMAL(10,2), NOT NULL)  
     - `quantity` (INT, NOT NULL)  
     - `created_at` (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)  
     - `updated_at` (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)

2. **User**  
   - **Columns:**  
     - `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)  
     - `username` (VARCHAR(50), UNIQUE, NOT NULL)  
     - `email` (VARCHAR(100), UNIQUE, NOT NULL)  
     - `password` (VARCHAR(255), NOT NULL)  
     - `role` (ENUM('ADMIN','USER'), DEFAULT 'USER', NOT NULL)  
     - `created_at` (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)

3. **Order**  
   - **Columns:**  
     - `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)  
     - `user_id` (BIGINT, FOREIGN KEY referencing User(id))  
     - `total_amount` (DECIMAL(10,2), NOT NULL)  
     - `created_at` (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)

4. **OrderItem**  
   - **Columns:**  
     - `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)  
     - `order_id` (BIGINT, FOREIGN KEY referencing Order(id))  
     - `grocery_item_id` (BIGINT, FOREIGN KEY referencing GroceryItem(id))  
     - `quantity` (INT, NOT NULL)  
     - `price` (DECIMAL(10,2), NOT NULL)

### Relationships

- **User ↔ Order:** One user can place multiple orders.
- **Order ↔ OrderItem:** One order can have multiple items.
- **GroceryItem ↔ OrderItem:** A grocery item can appear in multiple orders.

### Sample SQL Scripts

```sql
-- Create GroceryItem table
CREATE TABLE GroceryItem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create User table
CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Order table
CREATE TABLE Order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id)
);

-- Create OrderItem table
CREATE TABLE OrderItem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    grocery_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Order(id),
    FOREIGN KEY (grocery_item_id) REFERENCES GroceryItem(id)
);

-- Sample Data
INSERT INTO GroceryItem (quantity, name, price)
VALUES (10, 'Milk', 29), (5, 'Bread', 60), (20, 'Eggs', 100), (30, 'Apple', 99), (4, 'Cheese', 49);

INSERT INTO User (email, password, role, username)
VALUES ('max@mail.com', 'password123', 'ADMIN', 'max_wane'),
       ('jill@mail.com', 'adminpass', 'USER', 'jill_do');
```

---

## API Endpoints

### Authentication
- **POST** `/api/auth/login`  
  **Body:**
  ```json
  { "username": "max_wane", "password": "password123" }
  ```  
  **Response:** Returns a JWT token.

### Admin Endpoints (Require ROLE_ADMIN)
- **POST** `/api/admin/grocery-items`  
  **Body:**
  ```json
  {
    "name": "Milk",
    "price": 100,
    "inventoryCount": 5
  }
  ```
- **PUT** `/api/admin/grocery-items/{id}`  
  **Body:**
  ```json
  {
    "name": "Milk",
    "price": 32,
    "inventoryCount": 15
  }
  ```
- **DELETE** `/api/admin/grocery-items/{id}`
- **GET** `/api/admin/grocery-items`

### User Endpoints
- **POST** `/api/grocery/order-grocery`  
  **Body:**
  ```json
  {
    "userId": 3,
    "items": [
      { "name": "Apple", "quantity": 1 },
      { "name": "Toothpaste", "quantity": 1 },
      { "name": "Cheese", "quantity": 2 }
    ]
  }
  ```
- **GET** `/api/grocery/grocery-items`

### Health Check
- **GET** `/actuator/health`

> **Note:** For all secured endpoints, include the header:  
> `Authorization: Bearer <your-token>`

---

## Running the Application

### Local Development
1. **Build:**  
   ```bash
   ./gradlew clean build
   ```
2. **Run:**  
   ```bash
   ./gradlew bootRun
   ```
   The application will run at [http://localhost:8080](http://localhost:8080).

### Docker Setup
1. **Start Containers:**  
   ```bash
   docker-compose up --build
   ```
2. **Access:**  
   - App: [http://localhost:8080](http://localhost:8080)  
   - Database: PostgreSQL on port 5432  
   (Adjust `spring.datasource.url` accordingly if running locally vs. in Docker.)

---

## Example cURL Commands

**Generate Token:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "max_wane", "password": "password123"}'
```

**Add Grocery Item (Admin):**
```bash
curl -X POST http://localhost:8080/api/admin/grocery-items \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"name": "Milk", "price": 100, "inventoryCount": 5}'
```

**Update Grocery Item (Admin):**
```bash
curl -X PUT http://localhost:8080/api/admin/grocery-items/6 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"name": "Milk", "price": 32, "inventoryCount": 15}'
```

**Delete Grocery Item (Admin):**
```bash
curl -X DELETE http://localhost:8080/api/admin/grocery-items/10 \
  -H "Authorization: Bearer <token>"
```

**Order Grocery (User):**
```bash
curl -X POST http://localhost:8080/api/grocery/order-grocery \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"userId": 3, "items": [{"name": "Apple", "quantity": 1}, {"name": "Toothpaste", "quantity": 1}, {"name": "Cheese", "quantity": 2}]}'
```

**Get Available Groceries (User):**
```bash
curl -X GET http://localhost:8080/api/grocery/grocery-items \
  -H "Authorization: Bearer <token>"
```

---

## Security Considerations

- **Passwords:** In production, use a secure password encoder (e.g., BCrypt) instead of storing plain-text passwords.
- **JWT Secret:** Secure the JWT secret key (preferably via environment variables).
- **Endpoint Protection:** Role-based access is enforced via Spring Security.

---

Feel free to customize this README further as needed.
