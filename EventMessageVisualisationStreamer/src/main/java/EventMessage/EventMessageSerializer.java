package EventMessage;

import com.google.flatbuffers.FlatBufferBuilder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Converts EventMessagePOJO to EventMessage.
 * Created by ISIS, STFC on 07/06/2017.
 */
public final class EventMessageSerializer implements org.apache.kafka.common.serialization.Serializer<EventMessagePOJO> {
    /**
     * Main method.
     * @param eventMessagePOJO The eventMessagePOJO to be converted.
     * @return Event Message FLatBuffer Byte Array.
     * @throws RuntimeException for invalid values.
     */
    public byte[] serialize(final String topic, final EventMessagePOJO eventMessagePOJO) throws RuntimeException {

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

    @Override
    public void close() {

    }

    @Override
    public void configure(final Map map, final boolean b) {

    }

}
