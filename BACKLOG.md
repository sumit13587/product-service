# Backlog

_Remarks about the tasks in backlog before deploying this service into Production_

## Exception Handling

In this version I have handled only one exception scenario for the invalid currency. 
So the exception handling is done simply with _ResponseStatusException_.
A better way is to use a global `@ExceptionHandler` with the `@ControllerAdvice` annotation.

## Additional Features

#### Security

* Handle application secrets using secret management tools like [Vault](https://www.vaultproject.io/) or [AWS Secrets Manager](https://aws.amazon.com/secrets-manager/)
* Add authentication/authorization for the REST endpoint
* Use Transport Layer Security (TLS)

#### Data type for Currency

* Consider better data type to handle currency. May be BigDecimal?

#### Documentation

* Use swagger for documenting the API

#### API versioning

* Enable versioning for the API

#### Deployment

* Containerize the service into docker
* Enable environment specific configuration 

#### Monitoring and Logging

* Better monitoring and logging   

