#FROM openjdk:17-jdk-slim
FROM openjdk:16-alpine3.13
WORKDIR /app
COPY repository ./root/.m2/repository
COPY pom.xml .
#COPY src ./src
COPY . .
RUN #mvn clean package
COPY out/artifacts/Jpa_vs_Hibernate_Implementation_jar/Jpa-vs-Hibernate-Implementation.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]


#COPY /repository . /root/.m2/repository
#COPY /target/Jpa-vs-Hibernate-Implementation-1.0.jar app.jar
#RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
#COPY C:/Users/ahmet/.m2/repository ./root/.m2/repository
