# frontend
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
