# Project-3-Backend
* Command to run app: ./gradlew bootRun
* How to it stop completely: ./gradlew --stop
* How to run the test: ./gradlew test
* Clean App: ./gradlew clean ./gradlew build 

# Project-3-Backend

Quick commands
- Run app: ./gradlew bootRun
- Stop Gradle daemons: ./gradlew --stop
- Run tests: ./gradlew test
- Clean & build: ./gradlew clean && ./gradlew build

Routes (click the copy button next to any route to copy that single line)

```text
GET    /auth
```

```text
GET    /auth/{id}
```

```text
POST   /auth/register
```

```text
PUT    /auth/update/{id}
```

```text
DELETE /auth/delete/{id}
```

```text
POST   /auth/login
```

```text
GET    /activity-logs
```

```text
GET    /activity-logs/{id}
```

```text
GET    /activity-logs/user/{userId}
```

```text
GET    /activity-logs/activity/{activityTypeId}
```

```text
POST   /activity-logs
```

```text
DELETE /activity-logs/{id}
```

```text
GET    /activities
```

```text
GET    /activities/{id}
```

```text
POST   /activities
```

```text
PUT    /activities/{id}
```

```text
DELETE /activities/{id}
```

```text
GET    /leaderboard
```

```text
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
