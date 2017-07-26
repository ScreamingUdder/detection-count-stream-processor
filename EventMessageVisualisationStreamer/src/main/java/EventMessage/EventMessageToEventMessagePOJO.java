package EventMessage;

/**
 * Utility class for converting Event Message Flat Buffer objects to EventMessagePOJOs.
 * Created by ISIS, STFC on 12/06/2017.
 */
public class EventMessageToEventMessagePOJO {
    public static EventMessagePOJO convert(final byte[] eventMessageBytes) {
        EventMessage eventMessage = EventMessage.getRootAsEventMessage(java.nio.ByteBuffer.wrap(eventMessageBytes));
        int eventMessageId = (int) eventMessage.messageId();
        EventMessagePOJO eventMessagePOJO = new EventMessagePOJO(eventMessageId, eventMessage.pulseTime());

        for (int i = 0; i < eventMessage.detectorIdLength(); i++) {
            int detectorID = (int) eventMessage.detectorId(i);
            eventMessagePOJO.addDetector(detectorID);
        }
        return eventMessagePOJO;
    }
}
