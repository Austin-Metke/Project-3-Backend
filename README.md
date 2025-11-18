
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
[`project3.com.example.rest_service.Controllers.UserController`](src/main/java/project3/com/example/rest_service/Controllers/UserController.java)

- GET /auth
  - Description: Returns all users as a HATEOAS collection (DTOs).
  - Example:
    ```sh
    curl -sS http://localhost:8080/auth
    ```

- GET /auth/{id}
  - Description: Returns a single user DTO by id (404 if missing).
  - Example:
    ```sh
    curl -i http://localhost:8080/auth/10
    ```

- POST /auth/register
  - Description: Create a new user. Request body: RegisterRequestDto { name, email, password }.
  - Example:
    ```sh
    curl -i -X POST http://localhost:8080/auth/register \
      -H "Content-Type: application/json" \
      -d '{"name":"Erin","email":"erin@example.com","password":"plainpw"}'
    ```
  - See DTO: [project3.com.example.rest_service.dto.RegisterRequestDto](http://_vscodecontentref_/0)

- PUT /auth/update/{id}
  - Description: Update an existing user (optional fields: name,email,password). Returns 404 if user not found.
  - Example:
    ```sh
    curl -i -X PUT http://localhost:8080/auth/update/5 \
      -H "Content-Type: application/json" \
      -d '{"name":"Frank New","email":"frank@new.com","password":"newpw"}'
    ```
  - See DTO: [project3.com.example.rest_service.dto.UpdateUserDto](http://_vscodecontentref_/1)

- DELETE /auth/delete/{id}
  - Description: Delete a user by id. Returns 204 when deleted.
  - Example:
    ```sh
    curl -i -X DELETE http://localhost:8080/auth/delete/42
    ```

- POST /auth/login
  - Description: Authenticate a user. Request body: LoginRequestDto { name, password }. Returns user DTO on success.
  - Example:
    ```sh
    curl -i -X POST http://localhost:8080/auth/login \
      -H "Content-Type: application/json" \
      -d '{"name":"Henry","password":"plain"}'
    ```
  - See DTO: [project3.com.example.rest_service.dto.LoginRequestDto](http://_vscodecontentref_/2)

## TypeLogsController (prefix /activity-logs) — 6 routes:
[project3.com.example.rest_service.Controllers.TypeLogsController](http://_vscodecontentref_/3)

- GET /activity-logs
  - Description: Return all activity logs.
  - Example:
    ```sh
    curl -sS http://localhost:8080/activity-logs
    ```

- GET /activity-logs/{id}
  - Description: Return a single activity log (404 if not found).
  - Example:
    ```sh
    curl -i http://localhost:8080/activity-logs/1
    ```

- GET /activity-logs/user/{userId}
  - Description: Return logs for a specific user.
  - Example:
    ```sh
    curl -sS http://localhost:8080/activity-logs/user/2
    ```

- GET /activity-logs/activity/{activityTypeId}
  - Description: Return logs for a specific activity type.
  - Example:
    ```sh
    curl -sS http://localhost:8080/activity-logs/activity/2
    ```

- POST /activity-logs
  - Description: Create a new activity log. Body: ActivityLogRequest { userId, activityTypeId, occurredAt? }.
  - Example:
    ```sh
    curl -i -X POST http://localhost:8080/activity-logs \
      -H "Content-Type: application/json" \
      -d '{"userId":2,"activityTypeId":2,"occurredAt":"2025-01-01T00:00:00Z"}'
    ```
  - See request class: [project3.com.example.rest_service.ActivityLogRequest](http://_vscodecontentref_/4)
  - Backing service: [project3.com.example.rest_service.Services.TypeLogsService](http://_vscodecontentref_/5)

- DELETE /activity-logs/{id}
  - Description: Delete an activity log by id (404 if missing).
  - Example:
    ```sh
    curl -i -X DELETE http://localhost:8080/activity-logs/99
    ```

## TypeActivityController (prefix /activities) — 5 routes:
[project3.com.example.rest_service.Controllers.TypeActivityController](http://_vscodecontentref_/6)

- GET /activities
  - Description: Return all activity types.
  - Example:
    ```sh
    curl -sS http://localhost:8080/activities
    ```

- GET /activities/{id}
  - Description: Return a single activity type (404 if missing).
  - Example:
    ```sh
    curl -i http://localhost:8080/activities/1
    ```

- POST /activities
  - Description: Create a new activity type. Body: { name, points, co2gSaved }.
  - Example:
    ```sh
    curl -i -X POST http://localhost:8080/activities \
      -H "Content-Type: application/json" \
      -d '{"name":"Cycling","points":10,"co2gSaved":50.0}'
    ```

- PUT /activities/{id}
  - Description: Replace or create activity type at id.
  - Example:
    ```sh
    curl -i -X PUT http://localhost:8080/activities/3 \
      -H "Content-Type: application/json" \
      -d '{"name":"Walking","points":5,"co2gSaved":10.0}'
    ```

- DELETE /activities/{id}
  - Description: Delete activity type by id (404 if missing).
  - Example:
    ```sh
    curl -i -X DELETE http://localhost:8080/activities/123
    ```
  - Repository: [project3.com.example.rest_service.Repositories.TypeActivityRepository](http://_vscodecontentref_/7)


## ChallengesController (class-level prefix /challenges) — 6 routes:
- Description: Endpoints to manage challenges (create, read, update, delete, and list by user). These routes are shown as the API design; if the controller is implemented, it should follow the shapes and status codes shown below.

- GET /challenges
  - Description: Return a list of challenges.
  - Success: 200 OK with JSON array of challenge objects.
  - Example:
    ```sh
    curl -sS http://localhost:8080/challenges
    ```

- GET /challenges/{id}
  - Description: Return a single challenge by id.
  - Success: 200 OK with challenge JSON.
  - Not found: 404 Not Found.
  - Example:
    ```sh
    curl -i http://localhost:8080/challenges/123
    ```

- GET /challenges/user/{userId}
  - Description: Return challenges that a specific user is participating in (or created by).
  - Success: 200 OK with JSON array.
  - Example:
    ```sh
    curl -sS http://localhost:8080/challenges/user/42
    ```

- POST /challenges
  - Description: Create a new challenge.
  - Request body (example JSON):
    ```json
    {
      "name": "Plastic-Free Week",
      "description": "Avoid single-use plastics for one week",
      "startDate": "2025-06-01",
      "endDate": "2025-06-07",
      "createdByUserId": 5
    }
    ```
  - Success: 201 Created with Location header pointing to the new resource and body containing created challenge.
  - Validation failure: 400 Bad Request.
  - Example:
    ```sh
    curl -i -X POST http://localhost:8080/challenges \
      -H "Content-Type: application/json" \
      -d '{"name":"Plastic-Free Week","description":"Avoid single-use plastics","startDate":"2025-06-01","endDate":"2025-06-07","createdByUserId":5}'
    ```

- PUT /challenges/{id}
  - Description: Update an existing challenge (partial or full replace depending on implementation).
  - Request body: same shape as POST; fields may be optional for partial updates.
  - Success: 200 OK with updated challenge.
  - Not found: 404 Not Found.
  - Example:
    ```sh
    curl -i -X PUT http://localhost:8080/challenges/123 \
      -H "Content-Type: application/json" \
      -d '{"name":"Updated Challenge Name"}'
    ```

- DELETE /challenges/{id}
  - Description: Delete a challenge by id.
  - Success: 204 No Content.
  - Not found: 404 Not Found.
  - Example:
    ```sh
    curl -i -X DELETE http://localhost:8080/challenges/123
    ```
## LeaderboardController (prefix /leaderboard) — 1 route:


- GET /leaderboard
  - Description: Return leaderboard entries. Query params: range (WEEK|MONTH|SIX_MONTHS|YEAR|ALL_TIME), limit
  - Example:
    ```sh
    curl -sS 'http://localhost:8080/leaderboard?range=ALL_TIME&limit=10'
    ```
  - Service: 

## HomeController — 1 route:


- GET /
  - Description: Health / welcome endpoint returning "Welcome to EcoPoint API"
  - Example:
    ```sh
    curl http://localhost:8080/
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
test push