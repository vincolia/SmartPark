# 🚗 SmartPark
SmartPark is a RESTful web application built using Spring Boot 3 and Java 21. It provides JWT-secured APIs to manage parking lots, and vehicles

---

## ✅ Features

- 🚪 Login using static credentials with **JWT authentication**
- 🅿️ Register & manage parking lots
- 🚗 Register & manage vehicles
- 🕒 Check-in & check-out vehicles with **cost calculation**
- 📊 View occupancy & vehicle details per parking lot
- 🧼 Auto-remove vehicles parked longer than 15 minutes (scheduler)
- 🔐 Secure all endpoints with JWT token in 'Authorization' header

---

## ⚙️ Tech Stack

- Java 21
- Spring Boot 3.5.4
- Spring Security (JWT-based)
- Spring Data JPA + H2 (in-memory DB)
- Maven + Lombok
- Postman / curl for API testing

---

## 🔧 Requirements

- Java 21+
- Maven 3.8+

---

## 🚀 Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/vincolia/SmartPark.git
cd smartpark
