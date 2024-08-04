FROM  openjdk:17
WORKDIR /app
#RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
COPY repository . /root/.m2/repository
COPY pom.xml .
COPY src ./src
#RUN mvn clean package
COPY Jpa-vs-Hibernate-Implementation.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
