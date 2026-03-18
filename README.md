# Signup and Login System

A Spring Boot REST API for user authentication with signup, login, and forgot password features.

## Features
- User Signup
- User Login with JWT token
- Forgot Password via Email
- Password Reset with expiring token
- Account lockout after failed login attempts

---

## How It Works

### Signup
1. User enters first name, last name, email, phone number and password
2. System checks if email or phone number already exists
3. Password is encrypted before saving to database
4. User is registered successfully

### Login
1. User enters email and password
2. System checks if account is locked
3. If password is wrong → failed attempt is recorded
4. After 5 wrong attempts → account is locked for 15 minutes
5. After 15 minutes → account is automatically unlocked
6. If login successful → JWT access token and refresh token are returned

### Forgot Password
1. User enters their email on forgot password page
2. System checks if email exists in database
3. If email exists → system generates a unique token and saves it to database
4. System sends a reset link to the email like:
   http://localhost:3000/reset-password?token=abc123xyz
5. User clicks the link → redirected to change password page
6. User enters new password
7. System checks if token is valid and not expired
8. If token is valid → password is updated 
9. If token is expired (15 minutes passed) → request rejected 
10. Token is deleted after use → cannot be used again

---

## Account Security

### Failed Login Attempts
- User can enter wrong password maximum 5 times
- After 5 wrong attempts → account is locked for 15 minutes
- After 15 minutes → account is automatically unlocked
- User can try again after lockout period ends

### Password Reset Token System
- Token is generated when user requests forgot password
- Token expires in 15 minutes
- Token can only be used once
- Old token is deleted when new reset is requested
- After password reset → token is permanently deleted

### JWT Token System
- JWT access token expires in 15 minutes
- JWT refresh token expires in 30 days
- All protected endpoints require valid JWT token

---

## Requirements
- Java 17+
- PostgreSQL
- Maven

---

## Setup

### 1. Clone the project
git clone (https://github.com/binaya-paudel/Signup_login_page)

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


## Request Examples

### Signup
POST /auth/signup
{
    "firstName": "    ",
    "lastName": "",
    "email": "ABC@example.com",
    "phoneNumber": 9801***67,
    "password": "password"
}

### Login
POST /auth/login
{
    "email": "ABC@example.com",
    "password": "password"
}

### Forgot Password
POST /auth/forgot-password
{
    "email": "ABC@example.com"
}

### Reset Password
POST /auth/reset-password
{
    "token": "paste_token_from_email_here",
    "new_password": "newpassword"
}
