FROM debian:buster-slim

RUN apt-get update
RUN apt-get install -y openjdk-11-jre curl


WORKDIR /srv
COPY . /srv
EXPOSE 5000

CMD ["java", "-jar", "target/server-1.0-SNAPSHOT-jar-with-dependencies.jar"]
