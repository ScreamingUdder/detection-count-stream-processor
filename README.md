# Detection Count Stream Processor

A Kafka Stream client intended for taking [EventMessage](https://github.com/ess-dmsc/streaming-data-types/blob/master/schemas/ev42_events.fbs) Flatbuffer objects and outputting a Detector ID frequency table at set intervals, intended for use in pulse visualisation.

Please use [the ScreamingUdder Checkstyle](https://github.com/ScreamingUdder/checkstyle_configuration) when contributing.

Developed with TDD

An example appplication for using this processor to process SANS2D data is hosted at [detection-count-visualisation](https://github.com/ScreamingUdder/detection-count-visualisation).

## How to import into IntelliJ

In IntelliJ IDE:

* Select File->Project From Existing Sources...
* Navigate to the local clone of this project and press 'OK'
* Select 'Import project from external model', select 'Maven, then click 'Next'
* Click 'Next' on the next three pages
* Finally, click 'Finish'

## Run

Can be run with the following options:
```
--app-id [-app] - Unique identifier for the application
--bootstrap [-b] - Kafka broker and port to connect to
--zookeeper [-z] - Zookeeper node and port to connect to
--source-topic [-src] - Kafka topic to receive event message from
--pulse-topic [-pt] - Output topic for the output of EventMessageProcessor
--accumulated-topic [-at] - Output topic for the output of PulseImageProcessor
--process-pulse-images [-ppi] - Boolean for whether pulse images created by the EventMessageProcessor are further processed by PulseImageProcessor, true by default
```

For example, run jar (can be downloaded from releases page):
```
java -jar EventMessageVisualisationStreamer.jar -b localhost:9092 -z localhost:2181 -src VIZ_events -pt VIZ_eventSum -at VIZ_eventSumAccumulated
```
