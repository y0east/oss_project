# backend
## Short-description
This backend provides RESTful APIs for a bulletin board system, primarily designed to manage department announcements and related posts. It supports user login, session-based authentication, and features for viewing and managing posts. Built with Spring Boot and MySQL, it enables seamless data handling and frontend integration via JSON responses.
## Component
Spring Boot: Backend framework for building RESTful APIs
Version: 3.4.5
MySQL: Relational database for data storage
Version: 8.0.42
IntelliJ IDEA Ultimate Edition: Integrated Development Environment (IDE) for backend development

## Specs
### 📄 API Specifications

#### 1. Login API

**Method**: `POST`
**Endpoint**: `/api/login`
**Description**: Handles user login.

**Request (JSON)**:

```json
{
  "username": "user1",
  "password": "password123"
}
```

**Response (Success)**:

```json
{
  "message": "Login successful",
  "userId": 1,
  "sessionToken": "abc123xyz"
}
```

**Response (Failure)**:

```json
{
  "message": "Invalid username or password."
}
```

---
#### 4. Get Post List

**Method**: `GET`
**Endpoint**: `/api/posts`
**Description**: Retrieves a list of all posts. Optionally filter by theme.

**Query Parameters (Optional)**:

* `theme`: Returns only posts under the specified theme.

**Response**:

```json
[
  {
    "id": 1,
    "title": "First Post",
    "author": "user1",
    "createdAt": "2025-05-20T12:00:00"
  },
  {
    "id": 2,
    "title": "Second Post",
    "author": "user2",
    "createdAt": "2025-05-20T13:00:00"
  }
]
```

---

#### 5. Get Post Details

**Method**: `GET`
**Endpoint**: `/api/posts/{postId}`
**Description**: Retrieves detailed information about a specific post, including comments.

**Response**:

```json
{
  "id": 1,
  "title": "First Post",
  "content": "This is the first post.",
  "author": "user1",
  "createdAt": "2025-05-20T12:00:00",
  "comments": [
    {
      "user": "user2",
      "content": "Nice post!",
      "createdAt": "2025-05-20T13:00:00"
    }
  ]
}
```

---

## Functional Requirements
1. User can log in using studentId and password  
2. Session-based authentication (not token-based)  
3. Users can create, read, update, and delete (CRUD) posts  
4. Each post contains a title, content, author, and timestamp  
5. Users can view a list of posts  
6. Users can view detailed information of a specific post  
7. All data is stored in a MySQL database  
8. API endpoints will return data in JSON format for frontend integration  
9. Error handling and response codes follow RESTful API conventions  

## Non-Functional Requirements
* **Database**:
  The database must be backed up daily to prevent data loss.
  All data should be securely stored with protection against unauthorized access.

* **Usability**:
  APIs must provide clear and consistent JSON responses.
  Appropriate HTTP status codes should be used to clearly indicate success or error states.

* **Maintainability**:
  Development must follow Object-Oriented Programming (OOP) principles, with particular emphasis on the **Open-Closed Principle (OCP)**.
  The system should be designed to allow extension without modifying existing code.
  Code readability and modularity should be ensured to facilitate easy maintenance and updates.
