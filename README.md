# <h1>GoEasier</h1>
## Name
GoEasier Bus Qr code payment system.

## Description
The repo contains two applications the server denoted serverapp and the mobile Application denoted Gomobile. 
The mobile application is mobile first application build using flutter.
The server is build using java with the javalin framework

## Installation
- Server -javalin
- Running the server with docker
    - Docker -t goserver build
    - Docker run goserver
- Running the server using maven
    - mvn initialize
    - mvn clean 
    - mvn validate
    - mvn compile
    - mvn exec:java
- Running the jar file
    -  java -jar target/server-1.0-SNAPSHOT-jar-with-dependencies.jar
-  Flutter mobile app Mobile first
    -flutter pub get
    - edit the ip address to your shared ip address in lib\controller\controller.dart lin 15
    - flutter run       

## Authors and acknowledgment
Thabang soulo.

## License
For private use only.

