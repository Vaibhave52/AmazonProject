# Amazon-Style E-Commerce Web App

A full-stack e-commerce application with a React storefront and a Spring Boot REST API. Customers can register, browse and filter products, manage a cart, place orders, and pay through Razorpay (test mode). An admin panel manages products and orders.

---

## Features

- User sign-up and login with **JWT authentication**
- Product listing with **filters, sorting, search, and pagination**
- Product details, **ratings and reviews**
- **Shopping cart** (add, update quantity, remove)
- **Checkout** with shipping address and order history
- **Razorpay** payment link integration (test mode)
- **Admin panel** to add products, view orders, and confirm / ship / deliver / cancel orders
- Responsive UI built with Tailwind CSS and Material UI

## Tech Stack

| Layer       | Technologies                                                                  |
| ----------- | ----------------------------------------------------------------------------- |
| Frontend    | React 18, Redux (Thunk), React Router 6, Axios, Tailwind CSS, Material UI     |
| Backend     | Java 17, Spring Boot 3.0.6, Spring Security, Spring Data JPA (Hibernate), JWT |
| Database    | MySQL                                                                         |
| Payments    | Razorpay (test mode)                                                          |
| API testing | Postman                                                                       |

## Project Structure

```
ecommerce-app/
├── backend/                  # Spring Boot REST API (port 5454)
│   ├── src/main/java/com/zosh/
│   │   ├── config/           # Security, JWT
│   │   ├── controller/       # REST controllers
│   │   ├── service/          # Business logic
│   │   ├── repository/       # JPA repositories
│   │   ├── modal/            # Entities
│   │   ├── request/          # Request DTOs
│   │   ├── response/         # Response DTOs
│   │   └── exception/        # Global exception handling
│   ├── src/main/resources/application.properties
│   └── .env.example
├── frontend/                 # React app (port 3000)
│   ├── src/
│   │   ├── Admin/            # Admin panel
│   │   ├── customer/         # Storefront pages and components
│   │   ├── Redux/            # Store, actions, reducers
│   │   └── config/api.js     # Axios setup
│   └── .env.example
└── postman_collection.json   # Ready-to-use API requests
```

---

## Getting Started

### Prerequisites

- **JDK 17**
- **Node.js 18+** and npm (or yarn)
- **MySQL 8+** running locally

Maven does not need to be installed. The project includes the Maven wrapper (`mvnw`).

### 1. Clone

```bash
git clone https://github.com/Vaibhave52/AmazonProject
cd ecommerce-app
```

### 2. Run the backend

```bash
cd backend
```

Create your config file by copying the template:

```bash
# macOS / Linux
cp .env.example .env

# Windows (PowerShell)
copy .env.example .env
```

Open `.env` and set at least `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET` (32+ characters). The `ecommerce` database is created automatically.

Start the server:

```bash
# macOS / Linux
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The API runs at `http://localhost:5454`. Run this command from the `backend` folder so the `.env` file is found.

### 3. Run the frontend

Open a second terminal:

```bash
cd frontend
npm install
```

Copy `.env.example` to `.env` (the default points to `http://localhost:5454`), then:

```bash
npm start
```

The app opens at `http://localhost:3000`. The backend allows this origin by default.

Using yarn instead? Run `yarn install` and `yarn start`.

---

## Using the App

1. Open `http://localhost:3000` and **register** a new account.
2. **Add products** through the admin panel at `http://localhost:3000/admin` (after logging in), or send them from Postman with the **Create product** request.
3. Browse products, add them to the cart, and place an order.

### Payments (optional)

Checkout needs Razorpay **test** keys. Create a free Razorpay account, copy the test Key ID and Secret from the dashboard, and add them to `backend/.env`:

```env
RAZORPAY_KEY_ID=rzp_test_xxxxxxxx
RAZORPAY_KEY_SECRET=xxxxxxxx
```

Without these keys, everything except the payment step still works.

---

## Configuration

| Variable                                 | Where           | Purpose                       | Default                          |
| ---------------------------------------- | --------------- | ----------------------------- | -------------------------------- |
| `DB_HOST`, `DB_PORT`, `DB_NAME`          | backend `.env`  | MySQL location                | `localhost`, `3306`, `ecommerce` |
| `DB_USERNAME`, `DB_PASSWORD`             | backend `.env`  | MySQL login                   | `root`, empty                    |
| `JWT_SECRET`                             | backend `.env`  | Token signing key (32+ chars) | built-in dev key, change it      |
| `RAZORPAY_KEY_ID`, `RAZORPAY_KEY_SECRET` | backend `.env`  | Payment keys                  | empty                            |
| `REACT_APP_API_URL`                      | frontend `.env` | Backend URL                   | `http://localhost:5454`          |

Real `.env` files are listed in `.gitignore` and are never committed.

---

## API Overview

Import `postman_collection.json` into Postman. Run **Sign Up** or **Sign In** first, because the JWT is saved to a variable automatically and sent with every other request.

| Area                | Endpoints                                                                       |
| ------------------- | ------------------------------------------------------------------------------- |
| Auth                | `POST /auth/signup`, `POST /auth/signin`                                        |
| User                | `GET /api/users/profile`                                                        |
| Products            | `GET /api/products`, `GET /api/products/id/{id}`, `GET /api/products/search?q=` |
| Cart                | `GET /api/cart/`, `PUT /api/cart/add`, `PUT/DELETE /api/cart_items/{id}`        |
| Orders              | `POST /api/orders/`, `GET /api/orders/user`, `GET /api/orders/{id}`             |
| Payments            | `POST /api/payments/{orderId}`, `GET /api/payments`                             |
| Reviews and ratings | `/api/reviews`, `/api/ratings`                                                  |
| Admin               | `/api/admin/products/**`, `/api/admin/orders/**`                                |

Protected endpoints expect the header `Authorization: Bearer <token>`.

---

## Troubleshooting

| Problem                                     | Fix                                                                                                           |
| ------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| `Access denied for user 'root'`             | Check `DB_USERNAME` and `DB_PASSWORD` in `backend/.env`, and run the command from the `backend` folder        |
| `Communications link failure`               | MySQL is not running, or `DB_HOST` and `DB_PORT` are wrong                                                    |
| `JWT_SECRET must be at least 32 characters` | Use a longer secret                                                                                           |
| Port 5454 or 3000 already in use            | Stop the other app, or change `server.port` in `application.properties`                                       |
| CORS error in the browser                   | Run the frontend on `http://localhost:3000`, or add your origin in `AppConfig.java`                           |
| Frontend cannot reach the API               | Make sure the backend is running and `REACT_APP_API_URL` is correct. Restart `npm start` after editing `.env` |
| Payment fails                               | Add valid Razorpay test keys to `backend/.env`                                                                |

---

## Known Limitations

- Admin endpoints require login but do **not** check for an admin role, so any logged-in user can reach them. Add role-based rules (for example `hasRole('ADMIN')`) before any real deployment.
- No automated tests beyond the Spring Boot context test.
- Product images are stored as URLs; there is no image upload.

## Future Improvements

- Role-based authorization for admin routes
- Image upload, wishlist, and email notifications
- Docker and docker-compose setup
- Unit and integration tests, plus CI with GitHub Actions

## Acknowledgements

Based on the open-source "Shop With Zosh" e-commerce tutorial project. This version adds configuration through environment variables, cleanup of hardcoded secrets, and updated setup documentation.

## Author

**Vaibhav**
GitHub:https://github.com/Vaibhave52/AmazonProject
