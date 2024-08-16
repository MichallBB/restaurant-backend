FROM openjdk:17-jdk-alpine
EXPOSE 8080
# build maven
RUN apk add maven
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean install
# build jar
RUN mvn package
# run jar
ARG JAR_FILE=target/restaurant-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]