<img src="https://github.com/user-attachments/assets/cfcaeef7-6607-4dc3-b8ac-56383f12368e" height="50"/>

This is a simple backend service built using Java 21, Spring Boot 3 and Kafka as a message broken.


- **Back-end**: The backend is built using Java 21.
- **API**: The service is built using Spring Boot 3
- **Message**: The message broken is built using kafka to publish messages to a topic


## Requirements

- **Docker**: <img src="https://github.com/user-attachments/assets/908265ca-abc9-4e0b-a613-f9bd89d72920" height="40"/>
- **Docker Compose**: <img src="https://github.com/user-attachments/assets/e24be3c0-dedd-4ba4-b4fc-59093559aa59" height="40"/>
 

### Getting Started

## 1. Clone the Repository

Clone this repository to your local machine:

```
git clone https://github.com/Brunolimaa/sportygroup.git
cd tracker

git checkout master
```


### First, clean the project and compile the source code:
```
mvn clean install
```

Next, package the application:
```
mvn clean package
```
![Screenshot from 2025-06-08 19-28-21](https://github.com/user-attachments/assets/5d041795-452a-42e1-979a-6718ed7f99fd)

### To start the containers, run the following command:

## 2. Docker Compose Setup

### Start the Docker Containers

```
docker-compose up --build 
```

### Access the Swagger documentation 

```
http://localhost:8080/swagger-ui/index.html
```
![image](https://github.com/user-attachments/assets/f73f4224-f813-435e-9013-046c38b2a59f)

## 3. Testing API - Sportygroup
```
curl --location 'http://localhost:8080/v1/events/status' \
--header 'Content-Type: application/json' \
--data '{
           "eventId": "12348",
           "status": "LIVE"
         }'
```
![image](https://github.com/user-attachments/assets/63f7f7bb-1db5-454f-a579-e7e65eac2755)

request body information

![image](https://github.com/user-attachments/assets/ddde87be-37fc-4c74-800e-1864c8b7048a)


### Logs from API - through docker 

Execute this command to follow in real-time logs from api 

```
docker compose logs -f tracker-app

```

### example:

```
2025-06-08T21:23:27.731Z  INFO 1 --- [nio-8080-exec-8] c.s.t.i.scheduler.EventSchedulerService  : Starting tracking for eventId: 12345
2025-06-08T21:23:27.743Z  INFO 1 --- [ad | producer-1] c.s.t.i.messaging.KafkaMessagePublisher  : Message sent successfully: {"eventId":"12345","currentScore":"1:1"}
2025-06-08T21:23:37.753Z  INFO 1 --- [ad | producer-1] c.s.t.i.messaging.KafkaMessagePublisher  : Message sent successfully: {"eventId":"12345","currentScore":"1:0"}
2025-06-08T21:23:47.763Z  INFO 1 --- [ad | producer-1] c.s.t.i.messaging.KafkaMessagePublisher  : Message sent successfully: {"eventId":"12345","currentScore":"3:1"}
2025-06-08T21:23:57.754Z  INFO 1 --- [ad | producer-1] c.s.t.i.messaging.KafkaMessagePublisher  : Message sent successfully: {"eventId":"12345","currentScore":"1:2"}
2025-06-08T21:24:07.753Z  INFO 1 --- [ad | producer-1] c.s.t.i.messaging.KafkaMessagePublisher  : Message sent successfully: {"eventId":"12345","currentScore":"1:3"}
2025-06-08T21:24:17.748Z  INFO 1 --- [ad | producer-1] c.s.t.i.messaging.KafkaMessagePublisher  : Message sent successfully: {"eventId":"12345","currentScore":"2:4"}
2025-06-08T21:24:27.757Z  INFO 1 --- [ad | producer-1] c.s.t.i.messaging.KafkaMessagePublisher  : Message sent successfully: {"eventId":"12345","currentScore":"3:3"}
```

## 4. UI for Apache Kafka

To access the UI you need to access this link: 

```
http://localhost:8081/
```
To see the messages you can find them in these menus: 

##### Topics > Topic Name "sports-live-ipdates" > Messages

![image](https://github.com/user-attachments/assets/3ca2c590-e017-43ed-891e-d6324f3c1386)

## 5. Design decisions

It’s a simple project; however, I decided to use Clean Architecture as the architectural approach.
By using this architecture, it’s possible to isolate the domain and application code, applying the Open/Closed Principle more easily.
For this project, it would have been simpler, of course, to use an MVC architecture, but I chose Clean Architecture to better organize the layers and responsibilities.

- According to this image, we can see how this architecture works 

![image](https://github.com/user-attachments/assets/e540df2d-a39d-4103-9074-3e19ba0d5687)



To handle centralized exceptions, we could use AOP (Aspect-Oriented Programming) through @ControllerAdvice or @ExceptionHandler.
However, I did not apply it in this project because I believe the current approach meets the project's purpose.

Another design decision was applying Dependency Inversion, by defining interfaces as contracts to avoid direct dependencies on concrete classes.

I also centralized the entire OpenAPI documentation in an interface, mainly to apply the Single Responsibility Principle.
This approach helps to keep the code standardized, easier to understand, and more maintainable.





