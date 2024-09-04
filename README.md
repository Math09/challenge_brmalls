# Back-end Challenge - brMalls

<div align="center">
  <img src="https://investidorsardinha.r7.com/wp-content/uploads/2020/04/brmalls.png"/>
</div>

</br>

<div align="center">

![Java Version](https://img.shields.io/badge/Java-v17-important)
![Spring Boot](https://img.shields.io/badge/spring_boot-%236DB33F)
![Hibernate](https://img.shields.io/badge/hibernate-%2359666C)
![Maven](https://img.shields.io/badge/maven-%23C71A36)

</div>

<div align="center">

![MySQL](https://img.shields.io/badge/mysql-%234479A1)
![Docker](https://img.shields.io/badge/docker-%232496ED)
![Docker Compose](https://img.shields.io/badge/docker--compose-%230055A4)

</div>

<div align="center">

[➜ Leia-me em Português](tools/readme/README.pt-BR.md)

</div>

## Table of Contents

  - [Stack](#stack)
  - [Project tree](#project-tree)
  - [About the Challenge and the Project](#about-the-challenge-and-the-project)
  - [Solution Details](#solution-details)
  - [How to Run the Project](#how-to-run-the-project)
  - [Running Unit Tests](#running-unit-tests)
    - [Placeholder Substitution](#placeholder-substitution)
  - [License](#license)
  - [Acknowledgment](#acknowledgment)

## Stack

  - `Java 17`
  - `Spring Boot 3.3.2`
  - `Maven 3.8.4`
  - `Hibernate 6.5.2`
  - `JUnit 5.10.3`
  - `MySQL 8.0`
  - `RestTemplate` (for API integration)
  - `RegEx`
  - `Docker`
  - `Postman`


<details>
    <summary><h2 id="project-tree">Project tree</h2></summary>

```bash
.
├── LICENSE
├── README.md
├── docker-compose.yml
├── mysql
│   ├── Dockerfile
│   └── queries
│       ├── 0.database.sql
│       ├── 1.table_empresa.sql
│       └── 2.insert_data.sql
├── tools
│   ├── postman
│   │   └── challange_brmalls.postman_collection.json
│   ├── readme
│   │   └── README.pt-BR.md
│   └── scripts
│       ├── clean.sh
│       ├── docker_java.sh
│       └── docker_mysql.sh
└── xpto
    ├── Dockerfile
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── br
        │   │       └── com
        │   │           └── brmalls
        │   │               └── xpto
        │   │                   ├── XptoApplication.java
        │   │                   ├── controllers
        │   │                   │   ├── CompanyController.java
        │   │                   │   └── TestDockerController.java
        │   │                   ├── daos
        │   │                   │   └── CompanyDAO.java
        │   │                   ├── dtos
        │   │                   │   ├── CompanyDataResponseDTO.java
        │   │                   │   └── ErrorResponseDTO.java
        │   │                   ├── exceptions
        │   │                   │   └── GlobalExceptionHandler.java
        │   │                   ├── models
        │   │                   │   ├── AbstractModel.java
        │   │                   │   └── CompanyModel.java
        │   │                   ├── services
        │   │                   │   ├── CompanyService.java
        │   │                   │   └── impls
        │   │                   │       └── CompanyServiceImpl.java
        │   │                   └── utils
        │   │                       ├── CNPJUtils.java
        │   │                       ├── CONSTANTS.java
        │   │                       ├── FormatUtils.java
        │   │                       └── logs
        │   │                           ├── ControllerLoggingAspect.java
        │   │                           ├── OriginalClassName.java
        │   │                           └── ServiceLoggingAspect.java
        │   └── resources
        │       └── application.properties
        └── test
            └── java
                └── br
                    └── com
                        └── brmalls
                            └── xpto
                                ├── XptoApplicationTests.java
                                ├── controllers
                                │   └── CompanyControllerTest.java
                                ├── daos
                                │   └── CompanyDAOTest.java
                                ├── exceptions
                                │   └── GlobalExceptionHandlerTest.java
                                ├── services
                                │   └── impls
                                │       └── CompanyServiceImplTest.java
                                └── utils
                                    ├── CNPJUtilsTest.java
                                    └── FormatUtilsTest.java
```
</details>

## About the Challenge and the Project

XPTO company is developing a new sales system and has requested the *Integration Team* to create an *API* to retrieve customer data when creating a sales order. The *API* follows these rules:

- It should return the following data: Company Name and Trade Name;
- The parameter used for the search will be the company's CNPJ, without special characters;
- The information should be retrieved primarily from the "COMPANY" table in the customer registration system's database;
- When the CNPJ is not found in the database, the system should attempt to retrieve this information from a third-party service, creating this new record in the "COMPANY" table before returning the result in the request.

**Note:** You are not prohibited from adding "something extra" to your project, feel free to showcase all your creativity and knowledge, but keep in mind that the evaluation focuses on quality rather than the number of features. For this test, the use of **Java** and the *Spring framework* is *mandatory*, but any *relational database* is *allowed*. If you're unfamiliar with any public service for CNPJ data query, we suggest ReceitaWS (https://www.receitaws.com.br/), which currently offers a free service option sufficient for this test. The source code should be shared in a git repository.

This project was developed as part of a technical challenge for a Java Developer position at brMalls. The task involves creating an API to retrieve customer data using Java and Spring Boot, with support for database queries and external service integration.

## Solution Details

The API was designed to be modular and extensible, following a layered architecture. The application performs the following:

1. **CNPJ Validation:** The provided CNPJ is validated and formatted before being used in queries.
2. **Database Query:** Initially, the API attempts to retrieve the company's data from the "COMPANY" table. If the CNPJ is not found, the application makes a second attempt using an external service.
3. **External Service Integration:** The suggested service, ReceitaWS, is used to retrieve data for companies that are not in the local database. After the query, the data is stored for future requests.

## How to Run the Project

<details>
    <summary><strong>Note</strong></summary>
    <p>Before running the project, you need to go to the application.properties and MySQL Dockerfile files and modify the following:</p>
    <ul style="list-style-type: none;">
        <li><strong>PLACEHOLDER_DB_USERNAME</strong>: Replace with the database username "root".</li>
        <li><strong>PLACEHOLDER_DB_PASSWORD</strong>: Replace with the desired database password.</li>
    </ul>
</details>

**Step 0:** Clone the repository
```bash
git clone https://github.com/matheus-srego/challenge_brmalls.git
```

**Step 1:** Open the project folder
```bash
cd challenge_brmalls
```

**Step 2:** Running with Docker

**Java Application**
   - **Build the application image**
     ```bash
     docker build -f Dockerfile -t openjdk .
     ```
   - **Run the application container**
     ```bash
     docker run -d --name api -p 8080:8080 -t openjdk
     ```
   - **Run the application container in debug mode**
     ```bash
     docker run -d --name api_debug -p 8080:8080 -p 5005:5005 -e DEBUG=true openjdk
     ```

   **MySQL Database**
   - **Build the database image**
     ```bash
     docker build -f Dockerfile -t mysql .
     ```
   - **Run the database container**
     ```bash
     docker run -d --name database -p 3306:3306 -t mysql
     ```

**Step 3:** Check running containers
```bash
docker ps
```

## Running Unit Tests

> **Note:** Ensure that all placeholders are replaced before running the tests.
<details>
  <summary><strong id="placeholder-substitution">Placeholder Substitution</strong></summary>
  <p>For tests to run properly, placeholders in the tests need to be replaced. Below is each file with its placeholders.</p>

  <h3>CompanyControllerTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Replace with a valid CNPJ.</li>
    <li><strong>SOCIAL_NAME_PLACEHOLDER</strong>: Replace with the company name corresponding to the CNPJ.</li>
    <li><strong>INVALID_CNPJ_PLACEHOLDER</strong>: Replace with an invalid CNPJ.</li>
  </ul>

  <h3>CompanyServiceImplTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Replace with a valid CNPJ.</li>
    <li><strong>SOCIAL_NAME_PLACEHOLDER</strong>: Replace with the company name corresponding to the CNPJ.</li>
    <li><strong>FANTASY_NAME_PLACEHOLDER</strong>: Replace with the trade name of the company corresponding to the CNPJ.</li>
  </ul>

  <h3>CompanyDAOTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>PLACEHOLDER_DB_USERNAME</strong>: Replace with the database username (e.g., "root").</li>
    <li><strong>PLACEHOLDER_DB_PASSWORD</strong>: Replace with the database password.</li>
    <li><strong>CNPJ_PLACEHOLDER</strong>: Replace with a valid CNPJ.</li>
    <li><strong>INVALID_CNPJ_PLACEHOLDER</strong>: Replace with an invalid CNPJ.</li>
  </ul>

  <h3>GlobalExceptionHandlerTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Replace with a valid CNPJ.</li>
  </ul>

  <h3>CNPJUtilsTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Replace with a valid CNPJ.</li>
  </ul>

  <h3>FormatUtilsTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_VARIABLE</strong>: Replace with a valid CNPJ.</li>
  </ul>
</details>

The tests were written to ensure the integrity of the application, including CNPJ validation and external service integration. Below is how to run them:

**Run all tests**
```bash
mvn test
```

**Run specific tests**
```bash
mvn -Dtest=TestName test
```

## License
This repository is licensed under the [MIT Licensed](https://github.com/matheus-srego/challenge_brmalls/blob/main/LICENSE).

---

### Acknowledgment

I used ChatGPT to assist me in improving the translation of this README to English.
