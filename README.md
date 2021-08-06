# Quiz application

Design and implement a REST API using Spring-Boot without frontend.
Authentication with JWT.
For API documentation use Swagger

- 2 types of users: admin and regular users
- Admin can create a quiz
- Users can use quiz

Register a new user

### Tech stack
- JDK 8+;
- Maven;
- Spring boot;
- PostgreSQL;
- Lombok;


### Configuration File

Set properties in files (db properties, mail properties, jwt properties, etc): 
- application.properties (common properties)
- application-hosting.properties (for hosting server)
- application-local.properties (for local use)

On hosting server set environment variable SPRING_PROFILES_ACTIVE="hosting" for use application-hosting.properties and set properties in application-hosting.properties 
For email testing use mailtrap 

  
## API Documentation
### USER
Functionality administrator:
- DELETE /api/auth/{userId} - Delete user with id = userId

Functionality user:
- GET /api/authentication/profile - Get user profile
- PUT /api/authentication/profile - Update user profile
- POST /api/auth/logout - Log out
- POST /api/auth/update-password - Update password

Functionality anonymous user:
- PUT /api/auth/register - Register new user
- POST /api/auth/activate - Activate user
- POST /api/auth/login - Log in
- POST /api/auth/resend-activate-email - Send activation user link to email
- POST /api/auth/send-reset-password-email - Send reset password link to email

### QUIZ
Functionality administrator:
- POST /api/admin/quiz - Create quiz
- PUT /api/admin/quiz/{quizId} - Update quiz with id = quizId
- DELETE /api/admin/quiz/{quizId} - Delete quiz with id = quizId

Functionality user:
- GET /api/quiz/all - Get all quizzes
- GET /api/quiz/{quizId} - Get quiz with id = quizId

### QUESTION
Functionality administrator:
- POST /api/admin/question/{quizId} - Create question for quiz with id = quizId 
- PUT /api/admin/question/{questionId} - Update question with id = questionId
- DELETE /api/admin/question/{questionId} - Delete question with id = questionId

Functionality user:
- GET /api/question/{quizId}/all - Get all questions for quiz with id = quizId
- GET /api/question/{questionId} - Get question with id = questionId

### ANSWER
Functionality administrator:
- POST /api/admin/answer/{questionId} - Create answer for question with id = questionId
- PUT /api/admin/answer/{answerId} - Update answer with id = answerId
- DELETE /api/admin/answer/{answerId} - Delete answer with id = answerId

Functionality user:
- GET /api/answer/{questionId}/all - Get answers for question with id = questionId 
- GET /api/answer/{answerId} - Get answer with id = answerId
- POST /api/answer/{questionId}/{answerId} - Choose answer with id = answerId for question with id = questionId

### STATISTIC
Functionality administrator:
- GET /api/statistic/all - Get statistics for all quizzes
- GET /api/statistic/{quizId} - Get statistic for quiz with id = quizId

Functionality user:
- GET /api/statistic/all - Get statistics for all quizzes
- GET /api/statistic/{quizId} - Get statistic for quiz with id = quizId
