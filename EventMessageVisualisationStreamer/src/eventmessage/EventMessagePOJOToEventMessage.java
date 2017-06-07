package eventmessage;

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
        }
        return null;
    }


}
