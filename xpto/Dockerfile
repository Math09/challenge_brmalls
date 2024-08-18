FROM maven:3.8.4-openjdk-17 AS build

RUN rm -rf ~/.m2/repository

WORKDIR /xpto_api
COPY pom.xml .
COPY src ./src

RUN mvn clean install package -DskipTests

FROM openjdk:17-oracle

COPY --from=build /xpto_api/target/xpto-0.0.1-SNAPSHOT.jar xpto.jar

ENV DEBUG=false

EXPOSE 8080
EXPOSE 5005

CMD ["sh", "-c", "java $([ \"$DEBUG\" = 'true' ] && echo '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005') -jar /xpto.jar"]
