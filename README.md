# Employee Creator

Employee Creator is a full-stack web application for managing employee records. It allows users to view, create, edit, and delete employees through a React frontend backed by a Java Spring Boot REST API.

The project was developed to practise full-stack development, REST API design, authentication, database integration, and automated testing.

<img width="931" height="734" alt="image" src="https://github.com/user-attachments/assets/26143205-4348-45ca-9b85-00f6504a830b" />

## Features

- View a list of employees and their details
- Create new employee records
- Edit existing employee information
- Delete employees
- Validate form input and display error messages
- Authenticate users and protect endpoints
- Support role-based access to application functionality

## Technologies Used

### Frontend
- React
- TypeScript
- Vite
- Tailwind CSS
- React Hook Form
- Zod for form validation
- React Router

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- REST API
- JWT authentication
- ModelMapper
- MySQL
- GitHub Faker for DataSeeder

### Testing and Automation
- JUnit and Mockito for backend testing
- RestAssured used for REST-based services
- Selenium WebDriver for browser automation
- Cucumber for behaviour-driven development (BDD)
- TestNG for test execution
- Maven for dependency management and test execution
- GitHub Actions for continuous integration

## Project Structure

The repository contains the application code and backend tests, with a separate repository for Cucumber end-to-end tests.

- **Frontend:** React and TypeScript application
- **Backend:** Spring Boot REST API and business logic
- **Backend tests:** Automated tests for backend functionality
- **Cucumber tests:** Separate Selenium-based end-to-end test project

The application uses a REST API to communicate between the frontend and backend, with MySQL providing persistent data storage.

## Getting Started

### Prerequisites

Install the following before running the project:

- Java (the version specified by the project configuration)
- Maven
- Node.js and npm
- MySQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/jodielmclaughlin/EmployeeCreatorProject.git
cd EmployeeCreatorProject
```

### 2. Configure the environment

Configure the required environment variables and application properties for your local environment, including:

- MySQL connection details
- Database name
- JWT secret
- Any required authentication or application settings

Use your existing environment configuration as a reference. Do not commit passwords, JWT secrets, or other sensitive credentials to the repository.

Ensure the database exists and the application is configured to connect to it.

### 3. Start the backend

Navigate to the backend directory and run the Spring Boot application using your IDE or Maven.

```bash
mvn spring-boot:run
```

The API is expected to run at:

`http://localhost:8080`

Run the command from the backend directory containing its `pom.xml`.

### 4. Start the frontend

Open a separate terminal, navigate to the frontend directory, install dependencies, and start the development server.

```bash
npm install
npm run dev
```

The frontend is expected to run at:

`http://localhost:5173`

Open the URL in your browser to access the application.

## Testing

### Backend Tests

The backend includes automated tests to verify application functionality and business logic.

From the backend directory, run:

```bash
mvn test
```

### Cucumber End-to-End Tests

The separate [Cucumber test repository](https://github.com/jodielmclaughlin/ecCucumber) contains browser-based tests using Selenium WebDriver, Cucumber, and TestNG.

These tests cover key user journeys, including employee management, form validation, and authentication.

The backend, frontend, database, and required test accounts must be configured before running the end-to-end tests locally.

### Continuous Integration

GitHub Actions runs the automated Cucumber test suite in a configured test environment. The workflow starts the required services and executes the tests to help verify application functionality.

## Security

- JWT-based authentication protects API endpoints.
- Authentication and authorisation are handled by the backend.
- Sensitive configuration values should be supplied through environment variables or secrets.
- Test configuration should use a separate test database to avoid modifying development data.

## Related Repository

**Cucumber End-to-End Tests:**  
https://github.com/jodielmclaughlin/ecCucumber

This repository contains the automated browser tests for the Employee Creator application.

## Future Improvements

Potential future enhancements include:

- Expanding automated test coverage
- Improving application deployment and hosting
- Adding further employee search and filtering functionality
