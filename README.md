# Products API

A simple REST API implementation using spring boot to list products.

## Build through maven
mvn clean package - To build jar with unit tests 
mvn clean verify - To run with unit and integration tests

## Running locally
* Run ProductApplication.java to run application locally in IDE (IntelliJ/Eclipse)
* Use curl to test on command line
```
curl -X GET http://localhost:8080/products/list
```



