# Goal

Project create to training of base api to QA

## How to run the application

First you will need to clone this repository. Afterwards, follow the steps below.

### Using command line

1. Install Apache Maven and configure it in your PATH
2. Run, in Terminal or Command Prompt `mvn spring-boot:run`
3. To know that everything happened successfully, the last lines in the terminal must be identical to these:

```shell
Exposing 1 endpoint(s) beneath base path '/actuator'
Tomcat started on port(s): 8081 (http) with context path ''
libraryapi.LibraryApiApplication : Started LibraryApiApplication in `XXXX` seconds
```

4. To stop the application press `CTRL + C`

### By your favorite IDE

1. Import the project as a Maven project
2. Open folder: br/com/diego/licensedriveapi/
3. Run the class: LicenseDriveApiApplication.java

### Requirements

Windows, MacOS, linux

## How to view application documentation in Swagger

After starting the application, open the link in the browser: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

## How to view the H2 database

After start application, open link in browser: [localhost:8081/h2-console/](localhost:8081/h2-console/)

JDBC URL: jdbc:h2:mem:testdb

User Name: sa

Password: password

### Technologies

The following tools were used in building the project:

- Java 11
- DataBase h2
