# Back-end Challange - brMalls

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

[➜ Read me in English](https://github.com/matheus-srego/challenge_brmalls/blob/main/README.md)

</div>

## Índice

  - [Stack](#stack)
  - [Árvore do Projeto](#arvore-do-projeto)
  - [Sobre o desafio e o projeto](#sobre-o-desafio-e-o-projeto)
  - [Detalhes da solução](#detalhes-da-solução)
  - [Como executar o projeto](#como-executar-o-projeto)
  - [Execução dos testes unitários](#execução-dos-testes-unitários)
    - [Substituição de Placeholders](#substituição-de-placeholders)
  - [Licença](#licença)

## Stack

  - `Java 17`
  - `Spring Boot 3.3.2`
  - `Maven 3.8.4`
  - `Hibernate 6.5.2`
  - `JUnit 5.10.3`
  - `MySQL 8.0`
  - `RestTemplate` (para integração com API)
  - `RegEx`
  - `Docker`
  - `Postman`

<details>
    <summary><h2 id="arvore-do-projeto">Árvore do projeto</h2></summary>

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

## Sobre o desafio e o projeto

A empresa XPTO está desenvolvendo um novo sistema de vendas e solicita ao *Time de Integração* uma *API* para recuperar os dados dos clientes no momento de criar uma ordem de venda. A *API* segue as seguintes regras:

- Deve retornar os seguintes dados: Razão Social e Nome Fantasia;
- O parâmetro informado para a busca será o CNPJ da empresa, sem caracteres especiais;
- As informações devem ser buscadas primariamente na tabela "EMPRESA" do banco de dados do sistema de cadastro de clientes;
- Quando o CNPJ não for encontrado no banco de dados, deve-se tentar buscar essas informações em algum serviço de terceiros, criando esse novo registro na tabela "EMPRESA" antes de retornar na requisição.

**Observação:** não é proibido de colocar "algo a mais" no seu projeto, fique à vontade para nos mostrar toda a sua criatividade e conhecimento, mas é importante ter em mente que o que está sendo avaliado não é a quantidade de features. Para esse teste é *obrigatório* do uso da **linguagem Java** e do *framework Spring* mas qualquer *banco dados relacional* é *permitido*. Caso não conheça nenhum serviço público de consulta de dados via CNPJ, sugerimos o ReceitaWS (https://www.receitaws.com.br/) que nesse momento oferece uma opção gratuita de utilização do serviço suficiente para este teste. O código fonte deve ser compartilhado em um repositório git.

Este projeto foi desenvolvido como parte de um desafio técnico para uma vaga de Desenvolvedor Java na brMalls. A tarefa envolve a criação de uma API para recuperação de dados de clientes, utilizando Java e Spring Boot, com suporte para consultas em um banco de dados e em um serviço externo.

## Detalhes da solução

A API foi desenvolvida com o objetivo de ser modular e extensível, seguindo o padrão de camadas. A aplicação realiza:

1. **Validação do CNPJ:** O CNPJ informado é validado e formatado antes de ser utilizado nas consultas.
2. **Consulta ao Banco de Dados:** Inicialmente, a API tenta recuperar os dados da empresa na tabela "EMPRESA". Caso o CNPJ não seja encontrado, a aplicação realiza uma segunda tentativa através de um serviço externo.
3. **Integração com Serviço Externo:** O serviço sugerido, ReceitaWS, é utilizado para buscar os dados de empresas que não estão no banco de dados local. Após a consulta, os dados são armazenados para futuras consultas.

## Como executar o projeto

<details>
    <summary><strong>Observação</strong></summary>
    <p>Antes de executar o projeto é necessário ir até os arquivos application.properties e Dockerfile do MySQL e modificar:</p>
    <ul style="list-style-type: none;">
        <li><strong>PLACEHOLDER_DB_USERNAME</strong>: Substitua pelo nome de usuário do banco de dados "root".</li>
        <li><strong>PLACEHOLDER_DB_PASSWORD</strong>: Substitua pela senha do banco de dados que desejar.</li>
    </ul>
</details>

**Passo 0:** Clonando o repositório
```bash
git clone https://github.com/matheus-srego/challenge_brmalls.git
```

**Passo 1:** Abrindo a pasta do projeto
```bash
cd challenge_brmalls
```

**Passo 2:** Executando com Docker

**Aplicação Java**
   - **Construir a imagem da aplicação**
     ```bash
     docker build -f Dockerfile -t openjdk .
     ```
   - **Executar o container da aplicação**
     ```bash
     docker run -d --name api -p 8080:8080 -t openjdk
     ```
   - **Executar o container da aplicação no modo debug**
     ```bash
     docker run -d --name api_debug -p 8080:8080 -p 5005:5005 -e DEBUG=true openjdk
     ```
  
   **Banco de dados MySQL**
   - **Construir a imagem do banco de dados**
     ```bash
     docker build -f Dockerfile -t mysql .
     ```
   - **Executar o container do banco de dados**
     ```bash
     docker run -d --name database -p 3306:3306 -t mysql
     ```
    
**Passo 3:** Verificar containers em execução
```bash
docker ps
```

## Execução dos testes unitários

> **Observação:** certifique-se de substituir todos os placeholders antes de executar os testes.
<details>
  <summary><strong id="substituição-de-placeholders">Substituição de Placeholders</strong></summary>
  <p>Para que se realizem os testes, é necessário substituir os placeholders que estão nos testes. Abaixo se encontra cada arquivo com seus placeholders.</p>

  <h3>CompanyControllerTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Substitua por um CNPJ válido.</li>
    <li><strong>SOCIAL_NAME_PLACEHOLDER</strong>: Substitua pelo nome social da empresa correspondente ao CNPJ.</li>
    <li><strong>INVALID_CNPJ_PLACEHOLDER</strong>: Substitua por um CNPJ inválido.</li>
  </ul>

  <h3>CompanyServiceImplTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Substitua por um CNPJ válido.</li>
    <li><strong>SOCIAL_NAME_PLACEHOLDER</strong>: Substitua pelo nome social da empresa correspondente ao CNPJ.</li>
    <li><strong>FANTASY_NAME_PLACEHOLDER</strong>: Substitua pelo nome fantasia da empresa correspondente ao CNPJ.</li>
  </ul>

  <h3>CompanyDAOTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>PLACEHOLDER_DB_USERNAME</strong>: Substitua pelo nome de usuário do banco de dados (por exemplo, "root").</li>
    <li><strong>PLACEHOLDER_DB_PASSWORD</strong>: Substitua pela senha do banco de dados.</li>
    <li><strong>CNPJ_PLACEHOLDER</strong>: Substitua por um CNPJ válido.</li>
    <li><strong>INVALID_CNPJ_PLACEHOLDER</strong>: Substitua por um CNPJ inválido.</li>
  </ul>

  <h3>GlobalExceptionHandlerTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Substitua por um CNPJ válido.</li>
  </ul>

  <h3>CNPJUtilsTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_PLACEHOLDER</strong>: Substitua por um CNPJ válido.</li>
  </ul>

  <h3>FormatUtilsTest</h3>
  <ul style="list-style-type: none;">
    <li><strong>CNPJ_VARIABLE</strong>: Substitua por um CNPJ válido.</li>
  </ul>
</details>

Os testes foram escritos para garantir a integridade da aplicação, incluindo a validação do CNPJ e a integração com o serviço externo. Abaixo mostro como executá-los:

**Executar todos os testes**
```bash
mvn test
```

**Executar testes específicos**
```bash
mvn -Dtest=NomeDoTeste test
```

## Licença
Este repositório é licenciado sob a [MIT Licensed](https://github.com/matheus-srego/challenge_brmalls/blob/main/LICENSE).
