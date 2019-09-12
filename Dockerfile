FROM openjdk:11.0.2-jdk-slim

RUN mkdir -p /opt/app && \
    mkdir -p /opt/datadog

ADD application/build/distributions/application-1.0.tar /opt/app/
ADD libs/dd-java-agent-0.31.0.jar /opt/datadog/

EXPOSE 8080
CMD ["/opt/app/application-1.0/bin/application"]
