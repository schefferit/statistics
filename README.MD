[![Coverage Status](https://coveralls.io/repos/github/schefferit/statistics/badge.svg?branch=master)](https://coveralls.io/github/schefferit/statistics?branch=master)

# Statistics

Restful API for ***transaction statistics***. 
The main use case for API is to calculate realtime statistic from the last 60 seconds. 
This app uses in-memory queue based solution [PriorityBlockingQueue](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/PriorityBlockingQueue.html) without database.

##### Endpoints 
  
  * ***POST /transactions***   - add transaction to the queue in constant time O(1)
    
    example ```curl -H "Content-Type: application/json" -X POST -d '{"amount":"12.3", "timestamp":" 1478192204000"}' http://localhost:8080/transactions``` 
  
  * ***GET /statistics***      - get transactions statistic in constant time O(1) for last 60 seconds
  
    example ```curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/statistics``` 
    
    
# Prerequisites

 * Install JDK 1.8
 * Maven
 
# Build
 
 * mvn clean install

# Run 
 
 * mvn spring-boot:run 
  
