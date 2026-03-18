# Signup and Login Project

## Requirements
- Java 17+
- PostgreSQL
- Maven

## Setup

### 1. Clone the project
git clone https://github.com/binaya-paudel/Signup_login_page

### 2. Create PostgreSQL database
CREATE DATABASE InternProjectSignupandLogin;

### 3. Create .env file
Create a file called .env inside src/main/resources/ with:

appjwtsecret=any_random_secret_key
dbusername=your_postgres_username
dbpassword=your_postgres_password
mailusername=your_mailtrap_username
mailpassword=your_mailtrap_password

### 4. Run the project
mvn spring-boot:run
```
