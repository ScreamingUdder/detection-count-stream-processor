package eventmessage;

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

        if (eventMessagePOJO.getMessageId() < 0) {
            throw new RuntimeException("MessageID cannot be lower than 0.");
        } else if (eventMessagePOJO.getPulseTime() < 0) {
            throw new RuntimeException("Pulse Time cannot be lower than 0.");
        } else {
            FlatBufferBuilder builder = new FlatBufferBuilder();
            EventMessage.startEventMessage(builder);

            EventMessage.addMessageId(builder, eventMessagePOJO.getMessageId());
            EventMessage.addPulseTime(builder, eventMessagePOJO.getPulseTime());

            ArrayList<Integer> detectors = eventMessagePOJO.getDetectors();
            for (int detectorId : detectors) {
                EventMessage.addDetectorId(builder, detectorId);
            }

            int eventMessage = EventMessage.endEventMessage(builder);
            builder.finish(eventMessage);
            return builder.sizedByteArray();
        }
    }


}
