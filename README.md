# Temelio-Assignment

## Introduction

This is a web application developed using React JS for the frontend and Java with Spring Boot for the backend. The backend uses an H2 in-memory database for data storage.

## Technologies Used

- React JS
- Java
- Spring Boot
- H2 in-memory database

## Getting Started

### Prerequisites

- IntelliJ IDEA
- Node.js and npm (for React development)

### Running the Application

1. **Clone the Repository**: Clone this repository to your local machine using the following command: `git clone https://github.com/shreeharinw/temelio-assignment.git`
   
2. **Open Project in IntelliJ IDEA**:
- Open IntelliJ IDEA.
- Choose `File` > `Open` and select the Backend directory of the cloned repository.

3. **Start the Backend Server**:
- Navigate to the Java backend directory (`src/main/java`) in the project.
- Run the main application file (usually `Application.java`) to start the Spring Boot backend server.

4. **Start the Frontend Development Server**:
- Open a new terminal.
- Navigate to the `frontend` directory in the project.
- Run the following commands to install dependencies and start the development server:
  ```
  npm install
  npm start
  ```

5. **Access the Application**:
- Once both the backend and frontend servers are running, open a web browser.
- Go to `http://localhost:3000` to access the application.

6. **How to integrate Backend and Frontend**:
- Run the following commands in the `fronend` directory
    ```
    npm run build
    cp -R ./build/ ../Backend/src/main/resources/public
    ```
- Run the backend application and open the `http:localhost:8080` to find the full stack application.


