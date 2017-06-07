package eventmessage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Created by STFC,ISIS on 07/06/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class EventMessagePOJOToEventMessageTest {
    private static final int DEFAULT_MESSAGE_ID = 0;
    private static final int DEFAULT_PULSE_TIME = 0;
    private EventMessagePOJO eventMessagePOJO;

    @Before
    public void setUp() throws Exception {
        eventMessagePOJO = new EventMessagePOJO(DEFAULT_MESSAGE_ID, DEFAULT_PULSE_TIME);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @org.junit.Test
    public void runtimeExceptionWhenMessageIdNegative() {
        eventMessagePOJO.setMessageId(-1);
        EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
    }

}
