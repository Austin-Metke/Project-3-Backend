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

GET    /
## how to install java 17
java -version

sudo apt update

sudo apt install -y openjdk-17-jdk

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

export PATH="$JAVA_HOME/bin:$PATH"
