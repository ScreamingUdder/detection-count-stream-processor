package eventmessage;

import org.junit.Before;

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

}
