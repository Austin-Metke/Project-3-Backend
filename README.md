# Project-3-Backend

Quick commands
- Run app: 
```sh 
./gradlew bootRun 
```
- Stop Gradle daemons: 
```sh
./gradlew --stop
```
- Run tests: 
```sh
./gradlew test
```
- Clean & build: 
```sh
./gradlew clean 
```
```sh
./gradlew build
```
# Routes (click the copy button next to any route to copy that single line)
Here is all the routes for this API
## UserController (class-level prefix /auth) — 6 routes:
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
## TypeLogsController (prefix /activity-logs) — 6 routes:
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
## TypeActivityController (prefix /activities) — 5 routes:
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
## LeaderboardController (prefix /leaderboard) — 1 route:
```text
GET    /leaderboard
```
## HomeController — 1 route:
```text
GET    /
```

# Notes
- The routes above are implemented in the controller classes listed below.

# Controllers (open these files)
- [`project3.com.example.rest_service.Controllers.UserController`](src/main/java/project3/com/example/rest_service/Controllers/UserController.java)
- [`project3.com.example.rest_service.Controllers.TypeLogsController`](src/main/java/project3/com/example/rest_service/Controllers/TypeLogsController.java)
- [`project3.com.example.rest_service.Controllers.TypeActivityController`](src/main/java/project3/com/example/rest_service/Controllers/TypeActivityController.java)
- [`project3.com.example.rest_service.Controllers.LeaderboardController`](src/main/java/project3/com/example/rest_service/Controllers/LeaderboardController.java)
- [`project3.com.example.rest_service.Controllers.HomeController`](src/main/java/project3/com/example/rest_service/Controllers/HomeController.java)


## how to install java 17
```sh
java -version
```
```sh
sudo apt update
```
```sh
sudo apt install -y openjdk-17-jdk
```
```sh
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
```
```sh
export PATH="$JAVA_HOME/bin:$PATH"
```