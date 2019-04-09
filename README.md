# Flights
The application aggregates return flights from providers and provides an API to search over available flights with sorting, filtering and pagination. 
In the project as a backend framework [SpringBoot](https://spring.io/projects/spring-boot) is used. [Lombok](https://projectlombok.org/) framework is also used for simple looking classes by not writing getter, setter, equals methods.
As testing frameworks, [JUnit 5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/) are used.          

## Application Parameters 
Parameters of the Aplication can be easily changed with changing application.properties file. Each parameter is explained below: <br />
**server.port:** port number the application is listening <br />
**api.request.proxy.url:** If there is a proxy in the network, It is supposed to be set for requesting Restful APIs <br />
**api.request.proxy.port:** If there is a proxy in the network, It is supposed to be set for requesting Restful APIs <br />

## Build & Run 
After setting the parameters of the aplication, The application can be easily up running the boot class named FlightsApplication.  

## Api usage
The application can return available flights according to sorting, filtering and pagination criteria. 
### Request Parameters
Parameter Key: **departureAirport** <br />
Parameter Key: **arrivalAirport** <br />
Parameter Key: **departureDate** <br />
Parameter Key: **arrivalDate** <br />
Parameter Key: **pageSize** _default value:_ 20 <br />
Parameter Key: **pageNo** _default value:_ 1 <br />
Parameter Key: **sort**  _values:_ DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DEPARTURE_TIME, ARRIVAL_TIME_ <br />
Parameter Key: **order** _values:_ ASC, DESC <br />

### Sample Requests 
http://localhost:8030/flights?sort=DEPARTURE_AIRPORT&order=DESC <br />
http://localhost:8030/flights?pageSize=3&pageNo=4&sort=DEPARTURE_AIRPORT <br />
http://localhost:8030/flights?departureDate=2019-04-09 <br />
http://localhost:8030/flights?departureDate=2019-04-09&pageSize=3&pageNo=4&sort=DEPARTURE_AIRPORT <br />
http://localhost:8030/flights?departureAirport=Portena <br />

 


