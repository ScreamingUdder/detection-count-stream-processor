package EventMessage;

import java.util.Map;

/**
 * Utility class for converting Event Message Flat Buffer objects to EventMessagePOJOs.
 * Created by ISIS, STFC on 12/06/2017.
 */
public class EventMessageDeserializer implements org.apache.kafka.common.serialization.Deserializer<EventMessagePOJO> {

    @Override
    public EventMessagePOJO deserialize(final String topic, final byte[] eventMessageBytes) {
        EventMessage eventMessage = EventMessage.getRootAsEventMessage(java.nio.ByteBuffer.wrap(eventMessageBytes));
        int eventMessageId = (int) eventMessage.messageId();
        EventMessagePOJO eventMessagePOJO = new EventMessagePOJO(eventMessageId, eventMessage.pulseTime());

        for (int i = 0; i < eventMessage.detectorIdLength(); i++) {
            int detectorID = (int) eventMessage.detectorId(i);
            eventMessagePOJO.addDetector(detectorID);
        }
        return eventMessagePOJO;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(final Map map, final boolean b) {

    }
}
