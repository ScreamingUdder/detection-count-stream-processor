package EventMessage;

import com.google.flatbuffers.FlatBufferBuilder;

import java.util.ArrayList;

/**
 * Created by ISIS,STFC on 07/06/2017.
 * Converts EventMessagePOJO to EventMessage
 */
public final class EventMessagePOJOToEventMessage {

    /**
     * Constructor. Not for use.
     */
    private EventMessagePOJOToEventMessage() {
    }

    /**
     * Main method.
     * @param eventMessagePOJO The eventMessagePOJO to be converted.
     * @return Event Message FLatBuffer Byte Array.
     * @throws RuntimeException for invalid values.
     */
    public static byte[] convert(final EventMessagePOJO eventMessagePOJO) throws RuntimeException {

        // Check for invalid inputs
        if (eventMessagePOJO.getMessageId() < 0) {
            throw new RuntimeException("MessageID cannot be lower than 0.");
        } else if (eventMessagePOJO.getPulseTime() < 0) {
            throw new RuntimeException("Pulse Time cannot be lower than 0.");
        } else {
            ArrayList<Integer> detectors = eventMessagePOJO.getDetectors();
            int[] detectorsArray = new int[detectors.size()];
            for (int i = 0; i < detectors.size(); i++) {
                detectorsArray[i] = detectors.get(i);
            }

            // Builder must be initialised first
            FlatBufferBuilder builder = new FlatBufferBuilder();
            // Detector vector must be calculated before eventmessage is started
            int detPos = EventMessage.createDetectorIdVector(builder, detectorsArray);
            // Start event message
            EventMessage.startEventMessage(builder);
            // Detectorid can only be added after eventmessage is started
            EventMessage.addDetectorId(builder, detPos);
            // Set other params
            EventMessage.addMessageId(builder, eventMessagePOJO.getMessageId());
            EventMessage.addPulseTime(builder, eventMessagePOJO.getPulseTime());
            // Convert to byte array and return
            int eventMessage = EventMessage.endEventMessage(builder);
            builder.finish(eventMessage);
            return builder.sizedByteArray();
        }
    }


}