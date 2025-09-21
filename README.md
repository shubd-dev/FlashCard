# FlashCard Application

# Things I learned
## Spring Boot Security with JWT Authentication
The diagram shows flow of how we implement User Registration, User Login and Authorization process.

![spring-boot-jwt-authentication-spring-security-flow](spring-boot-jwt-authentication-spring-security-flow.png)

## Spring Boot Server Architecture with Spring Security
You can have an overview of our Spring Boot Server with the diagram below:

![spring-boot-jwt-authentication-spring-security-architecture](spring-boot-jwt-authentication-spring-security-architecture.png)

## Features Added

*   **User Authentication:** Secure user authentication using JWT (JSON Web Tokens).
*   **Role-Based Access Control:** Different roles for users (USER, MODERATOR, ADMIN) with different levels of access.
*   **Card Management:** Create, Read, Update, and Delete flashcards.
*   **Subject Management:** Create, Read, Update, and Delete subjects to organize flashcards.
*   **Data Validation:** Validation for user input to ensure data integrity.
*   **Custom Error Handling:** Global exception handler for consistent and clear error responses.
*   **Pagination:** Efficiently load lists of cards and subjects.
*   **Data Transfer Objects (DTOs):** Optimized API responses for the frontend.
*   **CORS Configuration:** Secure CORS configuration for frontend integration.
*   **Ownership and Security:** Users can only access and modify their own cards and subjects.

