package eventmessage;

/**
 * Created by ISIS,STFC on 07/06/2017.
 * Converts EventMessagePOJO to EventMessage
 */
public class EventMessagePOJOToEventMessage {
    private EventMessagePOJO eventMessagePOJO;

    /**
     * Constructor
     * @param eventMessagePOJO EventMessagePOJO to be converted.
     */
    public EventMessagePOJOToEventMessage(final EventMessagePOJO eventMessagePOJO) {
        this.eventMessagePOJO = eventMessagePOJO;
    }
}
