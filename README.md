# 🍽️ Restaurant Table Booking System

### Student: IRERA Mukawera Jockebed
### Student ID: 27453
### Course: Web Technology
### Institution: Adventist University of Central Africa (AUCA)
### Academic Year: 2025 - 2026

---

## Project Overview

The **Restaurant Table Booking System** is a RESTful API built with
Spring Boot that manages restaurant table reservations. The system allows
customers to book tables, staff to manage sittings, and admins to oversee
all operations. It also integrates Rwanda's administrative location hierarchy
(Province → District → Sector → Cell → Village) to track where users come from.

---

## Technologies Used

| Technology        | Version   | Purpose                        |
|-------------------|-----------|--------------------------------|
| Java              | 17        | Programming Language           |
| Spring Boot       | 4.0.3     | Backend Framework              |
| Spring Data JPA   | 4.0.3     | Database ORM                   |
| Hibernate         | 7.2.4     | JPA Implementation             |
| PostgreSQL        | 17        | Relational Database            |
| Maven             | 3.x       | Dependency Management          |
| Postman           | Latest    | API Testing                    |

---

##  Database Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/restaurant_table_booking_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

##  Entity Relationship Diagram (ERD)
```
location (self-referencing)
    ↓ One-to-Many
users
    ↓ One-to-Many              ↓ Many-to-Many
bookings ←── restaurant_tables ←──── table_service
    ↓ One-to-One
current_sittings
```

---

## Database Tables

### 1. location
Stores Rwanda administrative hierarchy using self-referencing relationship.

| Column    | Type    | Description                        |
|-----------|---------|------------------------------------|
| id        | UUID    | Primary Key                        |
| code      | VARCHAR | Unique location code (P1, D1, S1)  |
| name      | VARCHAR | Location name                      |
| type      | VARCHAR | PROVINCE/DISTRICT/SECTOR/CELL/VILLAGE |
| parent_id | UUID    | Foreign Key → location.id (self)   |

---

### 2. users
Stores all system users with their roles and location.

| Column       | Type    | Description                    |
|--------------|---------|--------------------------------|
| id           | UUID    | Primary Key                    |
| full_name    | VARCHAR | User full name                 |
| email        | VARCHAR | Unique email address           |
| password     | VARCHAR | User password                  |
| phone_number | VARCHAR | Contact number                 |
| role         | VARCHAR | ADMIN / CUSTOMER / STAFF       |
| location_id  | UUID    | Foreign Key → location.id      |

---

### 3. restaurant_tables
Stores all physical tables in the restaurant.

| Column                | Type    | Description                |
|-----------------------|---------|----------------------------|
| id                    | UUID    | Primary Key                |
| table_number          | VARCHAR | Unique table identifier    |
| capacity              | INTEGER | Number of seats            |
| location_in_restaurant| VARCHAR | Indoor / Outdoor / VIP     |
| available             | BOOLEAN | Table availability status  |
| table_type            | VARCHAR | STANDARD / VIP             |

---

### 4. bookings
Stores customer table reservations.

| Column          | Type    | Description                    |
|-----------------|---------|--------------------------------|
| id              | UUID    | Primary Key                    |
| booking_date    | DATE    | Date of reservation            |
| booking_time    | TIME    | Time of reservation            |
| number_of_guests| INTEGER | Number of guests               |
| status          | VARCHAR | PENDING / CONFIRMED / CANCELLED|
| special_request | VARCHAR | Any special requests           |
| customer_id     | UUID    | Foreign Key → users.id         |
| table_id        | UUID    | Foreign Key → restaurant_tables.id |

---

### 5. current_sittings
Tracks when customers physically arrive at the restaurant.

| Column            | Type      | Description                  |
|-------------------|-----------|------------------------------|
| id                | UUID      | Primary Key                  |
| seated_at         | TIMESTAMP | When customer arrived        |
| expected_check_out| TIMESTAMP | Expected departure time      |
| actual_check_out  | TIMESTAMP | Actual departure time        |
| notes             | VARCHAR   | Additional notes             |
| booking_id        | UUID      | UNIQUE FK → bookings.id      |

---

### 6. table_service (Join Table)
Auto-created Many-to-Many join table between staff and tables.

| Column   | Type | Description                      |
|----------|------|----------------------------------|
| staff_id | UUID | Foreign Key → users.id           |
| table_id | UUID | Foreign Key → restaurant_tables.id |

---

## Relationships

| Relationship | Tables                          | Type         |
|--------------|---------------------------------|--------------|
| Location → Users | location to users           | One-to-Many  |
| User → Bookings  | users to bookings           | One-to-Many  |
| Table → Bookings | restaurant_tables to bookings| One-to-Many |
| Booking → Sitting| bookings to current_sittings| One-to-One  |
| Staff ↔ Tables   | users to restaurant_tables  | Many-to-Many |

---

## Project Structure
```
src/main/java/auca/ac/rw/RestaurantTableBooking/
│
├── modal/
│   ├── ERole.java                    ← Enum: ADMIN, CUSTOMER, STAFF
│   ├── ELocationType.java            ← Enum: PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
│   ├── Location.java                 ← Self-referencing entity
│   ├── User.java                     ← User entity with ManyToMany
│   ├── RestaurantTable.java          ← Table entity
│   ├── Booking.java                  ← Booking entity
│   └── CurrentSitting.java           ← Sitting entity with OneToOne
│
├── repository/
│   ├── LocationRepository.java       ← existsByCode, findByCode
│   ├── UserRepository.java           ← existsByEmail, traversal methods
│   ├── RestaurantTableRepository.java← existsByTableNumber, pagination
│   ├── BookingRepository.java        ← existsByCustomerIdAndTableId
│   └── CurrentSittingRepository.java ← existsByBookingId
│
├── service/
│   ├── LocationService.java          ← parent/child save logic
│   ├── UserService.java              ← sorting, pagination, traversal
│   ├── RestaurantTableService.java   ← assignStaff ManyToMany
│   ├── BookingService.java           ← booking validation
│   └── CurrentSittingService.java    ← OneToOne sitting logic
│
├── controller/
│   ├── LocationController.java       ← full CRUD endpoints
│   ├── UserController.java           ← full CRUD + traversal endpoints
│   ├── RestaurantTableController.java← full CRUD + assign staff
│   ├── BookingController.java        ← full CRUD + by customer
│   └── CurrentSittingController.java ← full CRUD endpoints
│
└── RestaurantTableBookingApplication.java ← Main entry point
```

---

## API Endpoints

### Location Endpoints
| Method | URL                              | Description              |
|--------|----------------------------------|--------------------------|
| POST   | /api/locations/save              | Save location            |
| POST   | /api/locations/save?parentId=x   | Save child location      |
| GET    | /api/locations/all               | Get all locations        |
| GET    | /api/locations/{id}              | Get location by ID       |
| PUT    | /api/locations/update/{id}       | Full update location     |
| PATCH  | /api/locations/patch/{id}?name=x | Update location name     |
| DELETE | /api/locations/delete/{id}       | Delete location          |

---

### User Endpoints
| Method | URL                                         | Description              |
|--------|---------------------------------------------|--------------------------|
| POST   | /api/users/save?locationId=x                | Save user                |
| GET    | /api/users/all                              | Get all users            |
| GET    | /api/users/{id}                             | Get user by ID           |
| GET    | /api/users/sorted?sortBy=x&direction=asc    | Get sorted users         |
| GET    | /api/users/paginated?page=0&size=5          | Get paginated users      |
| GET    | /api/users/by-village?code=V1               | Get users by village     |
| GET    | /api/users/by-cell?code=C1                  | Get users by cell        |
| GET    | /api/users/by-sector?code=S1                | Get users by sector      |
| GET    | /api/users/by-district?code=D1              | Get users by district    |
| GET    | /api/users/by-province?provinceCode=P1      | Get users by province    |
| PUT    | /api/users/update/{id}                      | Full update user         |
| PATCH  | /api/users/patch/{id}?phoneNumber=x         | Update phone number      |
| DELETE | /api/users/delete/{id}                      | Delete user              |

---

### Restaurant Table Endpoints
| Method | URL                                              | Description           |
|--------|--------------------------------------------------|-----------------------|
| POST   | /api/tables/save                                 | Save table            |
| POST   | /api/tables/assign-staff?tableId=x&staffId=x     | Assign staff to table |
| GET    | /api/tables/all                                  | Get all tables        |
| GET    | /api/tables/{id}                                 | Get table by ID       |
| GET    | /api/tables/paginated?page=0&size=5              | Get paginated tables  |
| PUT    | /api/tables/update/{id}                          | Full update table     |
| PATCH  | /api/tables/patch/{id}?available=true            | Update availability   |
| DELETE | /api/tables/delete/{id}                          | Delete table          |

---

### Booking Endpoints
| Method | URL                                              | Description           |
|--------|--------------------------------------------------|-----------------------|
| POST   | /api/bookings/save?customerId=x&tableId=x        | Save booking          |
| GET    | /api/bookings/all                                | Get all bookings      |
| GET    | /api/bookings/{id}                               | Get booking by ID     |
| GET    | /api/bookings/paginated?page=0&size=5            | Get paginated bookings|
| GET    | /api/bookings/by-customer?customerId=x           | Get bookings by customer|
| PUT    | /api/bookings/update/{id}                        | Full update booking   |
| PATCH  | /api/bookings/patch/{id}?status=CONFIRMED        | Update booking status |
| DELETE | /api/bookings/delete/{id}                        | Delete booking        |

---

### Current Sitting Endpoints
| Method | URL                                         | Description           |
|--------|---------------------------------------------|-----------------------|
| POST   | /api/sittings/save?bookingId=x              | Save sitting          |
| GET    | /api/sittings/all                           | Get all sittings      |
| GET    | /api/sittings/{id}                          | Get sitting by ID     |
| PUT    | /api/sittings/update/{id}                   | Full update sitting   |
| PATCH  | /api/sittings/patch/{id}?notes=x            | Update notes          |
| DELETE | /api/sittings/delete/{id}                   | Delete sitting        |

---

##  Assessment Requirements Covered

| Requirement                        | Implementation                              | 
|------------------------------------|---------------------------------------------|
| ERD with 5+ tables                 | 6 tables with all relationships             | 
| Save Location parent/child         | parentId param, self-referencing            | 
| Sorting and Pagination             | Sort.by(), PageRequest.of()                 | 
| Many-to-Many relationship          | Staff ↔ Tables via table_service            | 
| One-to-Many relationship           | User → Bookings, Table → Bookings           |
| One-to-One relationship            | Booking → CurrentSitting (unique FK)        |
| existsBy() method                  | existsByEmail, existsByCode, existsByTableNumber |
| Retrieve by province code OR name  | 4-level traversal query in UserRepository   |                                         

---

### Application starts at:
http://localhost:8080
```

---

## Postman Test Screenshots

All API endpoints have been tested using Postman.
Screenshots are available in the [postman-screenshots/`](https://github.com/irerajochebed/midterm_27453_C/tree/main/postman-screenshots) folder.

---

## Author
```
Name:        IRERA Mukawera Jockebed
Student ID:  27453
Course:      Web Technology
University:  Adventist University of Central Africa (AUCA)
GitHub:      https://github.com/irerajochebed/midterm_27453_C
Year:        2026
```
