# Products API

A simple REST API implementation using spring boot to list products.

## Build through maven
mvn clean package - To build jar with unit tests 
mvn clean verify - To run with unit and integration tests

## Running locally
* Run ProductApplication.java to run application locally in IDE (IntelliJ/Eclipse)
* Use curl to test on command line

## Display different currencies
* Use the optional `currency` query parameter to display desired currency
* Valid list of currencies supported by this product service is controlled by the `CurrencyEnum`

#### Example request with default currency (currency parameter not provided)

_**Note**: EUR is the default currency as it is the base currency on free subscription of [fixer](fixer.io)_

```
Request:

curl -X GET http://localhost:8080/products/list

Response:

[
    {
        "id": 100,
        "name": "GenX Tablet",
        "description": "Next generation tablet",
        "displayPrice": "279.05 EUR"
    },
    {
        "id": 101,
        "name": "Smartphone 100",
        "description": "All new smartphone",
        "displayPrice": "349.05 EUR"
    },
    {
        "id": 102,
        "name": "SmartBook Pro",
        "description": "Professional laptop",
        "displayPrice": "349.05 EUR"
    }
]

```

#### Example request/response with supported currency
```
Request:

curl -X GET http://localhost:8080/products/list?currency=INR

Response: 

[
    {
        "id": 100,
        "name": "GenX Tablet",
        "description": "Next generation tablet",
        "displayPrice": "24232.70 INR"
    },
    {
        "id": 101,
        "name": "Smartphone 100",
        "description": "All new smartphone",
        "displayPrice": "30311.50 INR"
    },
    {
        "id": 102,
        "name": "SmartBook Pro",
        "description": "Professional laptop",
        "displayPrice": "30311.50 INR"
    }
]

``` 

#### Example request/response with an unsupported currency
```
Request:

curl -X GET http://localhost:8080/products/list?currency=ABC

Response:

{
    "timestamp": "2021-03-12T19:28:30.194+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "",
    "path": "/products/list"
}

```


