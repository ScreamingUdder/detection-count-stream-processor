# Kafka EventMessage Visualisation Streaming

A Kafka Stream client intended for taking [EventMessage](https://github.com/ess-dmsc/streaming-data-types/blob/master/schemas/ev42_events.fbs) Flatbuffer objects and outputting a Detector ID frequency table at set intervals, intended for use in pulse visualisation.

Please use [the ScreamingUdder Checkstyle](https://github.com/ScreamingUdder/checkstyle_configuration) when contributing.

Developed with TDD

## How to import into IntelliJ

In IntelliJ IDE:

* Select File->Project From Existing Sources...
* Navigate to the local clone of this project and press 'OK'
* Select 'Import project from external model', select 'Maven, then click 'Next'
* Click 'Next' on the next three pages
* Finally, click 'Finish'
