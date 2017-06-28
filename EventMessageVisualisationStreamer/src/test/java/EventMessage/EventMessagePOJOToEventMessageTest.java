package EventMessage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by STFC,ISIS on 07/06/2017.
 * Unit tests for EventMessagePOJO to EventMessage converter.
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
        exception.expect(RuntimeException.class);
        eventMessagePOJO.setMessageId(-1);
        EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
    }

    @org.junit.Test
    public void runtimeExceptionWhenPulseTimeNegative() {
        exception.expect(RuntimeException.class);
        eventMessagePOJO.setPulseTime(-1);
        EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
    }

    @org.junit.Test
    public void convertPOJOWithNoDetectorsReturnsByteArray() {
        byte[] result = EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
        assertNotNull("Should not be null", result);
    }

    @org.junit.Test
    public void convertPOJOWithDetectorsReturnsByteArray() {
        eventMessagePOJO.addDetector(1);
        eventMessagePOJO.addDetector(2);
        eventMessagePOJO.addDetector(3);
        byte[] result = EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
        assertNotNull("Should not be null", result);
    }

    @org.junit.Test
    public void getMessageIDCorrectWhenConvertingDefaultPOJO() {
        byte[] eventMessage = EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
        EventMessagePOJO result = EventMessageToEventMessagePOJO.convert(eventMessage);

        assertEquals(DEFAULT_MESSAGE_ID, result.getMessageId());
    }

    @org.junit.Test
    public void getPulseTimeCorrectWhenConvertingDefaultPOJO() {
        byte[] eventMessage = EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
        EventMessagePOJO result = EventMessageToEventMessagePOJO.convert(eventMessage);

        assertEquals(DEFAULT_PULSE_TIME, result.getPulseTime());
    }

    @org.junit.Test
    public void getDetectorsReturnsNullWhenConvertingDefaultPOJO() {
        byte[] eventMessage = EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
        EventMessagePOJO result = EventMessageToEventMessagePOJO.convert(eventMessage);

        assertEquals(new ArrayList<Integer>(), result.getDetectors());
    }

    @org.junit.Test
    public void getDetectorsReturnsCorrectWhenConvertingPOJOWithTwoDetectors() {
        eventMessagePOJO.addDetector(1);
        eventMessagePOJO.addDetector(2);
        byte[] eventMessage = EventMessagePOJOToEventMessage.convert(eventMessagePOJO);
        EventMessagePOJO result = EventMessageToEventMessagePOJO.convert(eventMessage);

        assertEquals(2, result.getDetectors().size());
        assertEquals(1,result.getDetector(0));
        assertEquals(2,result.getDetector(1));
    }


}