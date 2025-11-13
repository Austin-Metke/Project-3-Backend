# Project-3-Backend
* Command to run app: ./gradlew bootRun
* How to it stop completely: ./gradlew --stop
* How to run the test: ./gradlew test
* Clean App: ./gradlew clean ./gradlew build 
# end point
GET    /auth
GET    /auth/{id}
POST   /auth/register
PUT    /auth/update/{id}
DELETE /auth/delete/{id}
POST   /auth/login

GET    /activity-logs
GET    /activity-logs/{id}
GET    /activity-logs/user/{userId}
GET    /activity-logs/activity/{activityTypeId}
POST   /activity-logs
DELETE /activity-logs/{id}

GET    /activities
GET    /activities/{id}
POST   /activities
PUT    /activities/{id}
DELETE /activities/{id}

GET    /leaderboard

GET    /// ...existing code...
# Project-3-Backend

Quick commands
- Run app: ./gradlew bootRun
- Stop Gradle daemons: ./gradlew --stop
- Run tests: ./gradlew test
- Clean & build: ./gradlew clean && ./gradlew build

Routes (click the copy button on the right of the box to copy)
```text
GET    /auth
GET    /auth/{id}
POST   /auth/register
PUT    /auth/update/{id}
DELETE /auth/delete/{id}
POST   /auth/login

GET    /activity-logs
GET    /activity-logs/{id}
GET    /activity-logs/user/{userId}
GET    /activity-logs/activity/{activityTypeId}
POST   /activity-logs
DELETE /activity-logs/{id}

GET    /activities
GET    /activities/{id}
POST   /activities
PUT    /activities/{id}
DELETE /activities/{id}

GET    /leaderboard

GET    /
```

How to install Java 17 (example for Debian/Ubuntu)
```sh
java -version
sudo apt update
sudo apt install -y openjdk-17-jdk
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH="$JAVA_HOME/bin:$PATH"
```
# Notes
- The routes above are implemented in the controller classes listed below.

# Controllers (open these files)
- [`project3.com.example.rest_service.Controllers.UserController`](src/main/java/project3/com/example/rest_service/Controllers/UserController.java)
- [`project3.com.example.rest_service.Controllers.TypeLogsController`](src/main/java/project3/com/example/rest_service/Controllers/TypeLogsController.java)
- [`project3.com.example.rest_service.Controllers.TypeActivityController`](src/main/java/project3/com/example/rest_service/Controllers/TypeActivityController.java)
- [`project3.com.example.rest_service.Controllers.LeaderboardController`](src/main/java/project3/com/example/rest_service/Controllers/LeaderboardController.java)
- [`project3.com.example.rest_service.Controllers.HomeController`](src/main/java/project3/com/example/rest_service/Controllers/HomeController.java)
// ...existing code...
## how to install java 17
java -version

sudo apt update

sudo apt install -y openjdk-17-jdk

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

export PATH="$JAVA_HOME/bin:$PATH"
