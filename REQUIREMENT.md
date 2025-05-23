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
