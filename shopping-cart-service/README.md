# Shopping Cart Application using Spring boot
### Tech Stack used
- spring boot
- mysql
- jwt authentication
- Spring Data jpa
- Java 11

### Below is the architectural Diagram 

![Alt text](https://github.com/AbhisekGhosh1988/GateWayFE/blob/master/Architecture.jpg?raw=true "Optional Title")

#### Steps to follow to run the Application
- import the project and open in sts or intellij
- connect to mysql and change the username,password and db url accordingly in the application.properties file.
- start the application , the tables will be automatically created in the DB.
- Need to run only one scripts to add the predifined values for roles
```sh
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```

Swagger URL

```sh
http://localhost:8080/swagger-ui/index.html
```
**PS -- POstman collection attached to root folder of this project