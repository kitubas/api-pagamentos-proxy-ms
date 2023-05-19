FROM amazoncorretto:20
COPY  target/api-pagamentos-proxy-ms-0.0.1-SNAPSHOT.jar api-pagamentos-proxy-ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","api-pagamentos-proxy-ms-0.0.1-SNAPSHOT.jar"]