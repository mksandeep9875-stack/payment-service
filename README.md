# Payment Service

**Port:** 8095 

---

## Overview
`payment-service` manages payment-related operations for the Ecommerce platform.  
It allows creating payments and retrieving payment details for a specific order.

---

## Endpoints

| Endpoint                   | Method | Description                                |
|-----------------------------|--------|--------------------------------------------|
| /payment/v1/create          | POST   | Create a new payment for an order          |
| /payment/v1/get/{orderid}   | GET    | Retrieve payment details by order ID       |

---
## Configuration

Configuration file: src/main/resources/application*.properties or application.yml.

The application properties will be taken from the profile from https://github.com/mksandeep9875-stack/config-server-properties.git using spring cloud config server

---
## Dependencies

-Spring Boot Starter Web

-Spring Boot Starter Actuator

-Spring Boot Starter MongoDB (depending on your database)

-Spring Cloud Config Client

-Eureka Client

-Spring cloud Webflux


---

## How to Run

```bash
git clone <your-repo-url>
cd customer-service
mvn clean install
mvn spring-boot:run
