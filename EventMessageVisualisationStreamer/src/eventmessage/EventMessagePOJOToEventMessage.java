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

        //check for invalid inputs
        if (eventMessagePOJO.getMessageId() < 0) {
            throw new RuntimeException("MessageID cannot be lower than 0.");
        } else if (eventMessagePOJO.getPulseTime() < 0) {
            throw new RuntimeException("Pulse Time cannot be lower than 0.");
        } else {
            FlatBufferBuilder builder = new FlatBufferBuilder();
            EventMessage.startEventMessage(builder);
            //set params
            EventMessage.addMessageId(builder, eventMessagePOJO.getMessageId());
            EventMessage.addPulseTime(builder, eventMessagePOJO.getPulseTime());
            //serialise main eventmessage
            int eventMessage = EventMessage.endEventMessage(builder);
            builder.finish(eventMessage);
            //convert detectors arraylist to primitive arrsy
            ArrayList<Integer> detectors = eventMessagePOJO.getDetectors();
            int[] detectorsArray = new int[detectors.size()];
            for (int i = 0; i < detectors.size(); i++) {
                detectorsArray[i] = detectors.get(i);
            }
            //add detectors array to event message
            EventMessage.createDetectorIdVector(builder, detectorsArray);
            //convert to byte array and return
            return builder.sizedByteArray();
        }
    }


}
