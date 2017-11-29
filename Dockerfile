FROM openjdk:jre-alpine

RUN apk --no-cache add openssl

RUN wget https://github.com/ScreamingUdder/detection-count-stream-processor/releases/download/1.0/EventMessageVisualisationStreamer.jar

COPY docker_launch.sh .
ENTRYPOINT ["/bin/sh"]
CMD ["./docker_launch.sh"]
