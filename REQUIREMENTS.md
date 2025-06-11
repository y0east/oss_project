
# Backend
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
    "content": "content",
    "viewCount" : "3",
    "createdAt": "2025-05-20T12:00:00"
  },
  {
    "id": 2,
    "title": "Second Post",
    "content": "content",
    "viewCount" : "3",
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
  "createdAt": "2025-05-20T12:00:00",
  "viewCount" : "3",
  "createdAt": "2025-05-20T12:00:00",
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

#
#
#

# Frontend
## short-description
A web-based academic management interface designed for the Department of Information and Communication Engineering at Hankuk University of Foreign Studies. It includes features such as login, department introduction, curriculum structure, and announcements.The UI is built with HTML, Tailwind CSS, and JavaScript (some parts with React).

## components
Tailwind CSS

For rapid UI design with utility-first responsive classes.

Vanilla JavaScript

Handles login form submission, validation, and Mega Menu interactivity.

HTML5

Used for structuring pages including course map, notices, and layout.

Fetch API

Used to send login requests via JSON to the backend server.

React (Partially)

Optional for modularizing UI components in scalable versions.

Google Fonts (Nanum Gothic)

For Korean-style modern typography.

## specs
ogin System

Input validation and JSON-based API login request.

LocalStorage token handling and page redirection after success.

Mega Menu Navigation

Interactive dropdown with category highlighting and persistence.

Dynamic navigation section switching.

Course Curriculum Display

Semester-based subject rendering with type-based styling (major, basic, liberal arts, elective).

Includes subject flow diagrams for prerequisite tracking.

Announcements

Card-style display with title, post date, view count, and post ID.

Chatbot Prompt Area

Shows preset questions to guide users in using the academic chatbot HUBOT.

Responsive Design

All pages are mobile-friendly and adapt to screen sizes using Tailwind’s grid and flex utilities.

## functional requirements
Login Handling

Captures student ID/password and sends a JSON request.

Handles error/success messages and redirects accordingly.

Mega Menu Interaction

Highlights column on hover.

Menu opens and closes based on mouse interaction.

Curriculum Display

Renders subjects by semester.

Includes course color codes and prerequisite flow.

Announcement Display

Shows announcements with metadata like date and views.

Responsive UI

Supports screen sizes from mobile to desktop.

#
#
#

# Chatbot  
## Short Description  
A simple chatbot that uses the KoSimCSE model for embedding.

## Components  
- **Language:** Python 3.8+  
- **Server:** Flask  
- **Embedding Model:** KoSimCSE (Korean sentence embeddings)  
- **Data Storage:** File system or simple database (as needed)  
- **API:** RESTful API (for Q&A and dataset management)  

## Specs

### 1. Architecture Overview
- Utilizes the **KoSimCSE embedding model** to vectorize user queries and employs a RAG pipeline to retrieve and generate relevant answers.
- Provides services through a **Flask-based RESTful API server**.
- Supports automated dataset updates via the **auto_data system**.

### 2. Data Flow
1. The user submits a question through the web/app interface.
2. The question is vectorized using the KoSimCSE embedding model.
3. Relevant documents/answer candidates are retrieved via vector search.
4. The final answer is generated based on the retrieved candidates and returned to the user.
5. The manager can periodically update the chatbot’s dataset using the auto_data system.

## Functional Requirements
1. **Department-related Q&A**  
   - Users can ask questions about the department, and the chatbot provides relevant information.
2. **Casual Chatting**  
   - Users can have casual conversations with HUBOT.
3. **Automated Dataset Updates**  
   - Managers can easily update HUBOT’s dataset using the auto_data system.
  
## Non-Functional Requirements
1. **Availability**  
   - The service must be available 24/7.
2. **Performance**  
   - Response time should be within a few seconds.
3. **Usability**  
   - The interface should be simple and user-friendly.
4. **Scalability**  
   - The system should be able to handle more users and data as needed.
5. **Security**  
   - User data must be protected and privacy ensured.
6. **Maintainability**  
   - The dataset and system components should be easy to update.
7. **Logging**  
   - Interactions should be recorded for monitoring and troubleshooting.



front - YU JAE DONG / Seo sung douk
Back - EuihyunLee
Chatbot - Kim Kyu Min



My role is design, front end html, css development.

