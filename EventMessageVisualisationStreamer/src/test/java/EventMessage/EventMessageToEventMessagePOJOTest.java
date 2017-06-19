package EventMessage;

import com.google.flatbuffers.FlatBufferBuilder;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for EventMessageToEventMessagePOJO utility class.
 * Created by ISIS, STFC on 12/06/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class EventMessageToEventMessagePOJOTest {
    private static final long DEFAULT_MESSAGE_ID = 1;
    private static final long DEFAULT_PULSE_TIME = 1;
    private static final int[] DEFAULT_DETECTORS = new int[10];
    private byte[] eventMessageBytes;
    @Before
    public void setUp() throws Exception {
        //fill detectors array
        for (int i = 0; i < DEFAULT_DETECTORS.length; i++) {
            DEFAULT_DETECTORS[i] = i;
        }
        //create flatbuffer
        FlatBufferBuilder builder = new FlatBufferBuilder();

        int detPos = EventMessage.createDetectorIdVector(builder, DEFAULT_DETECTORS);


        EventMessage.startEventMessage(builder);
        //add parameters
        EventMessage.addMessageId(builder, DEFAULT_MESSAGE_ID);
        EventMessage.addPulseTime(builder, DEFAULT_PULSE_TIME);
        //add detector ids
        EventMessage.addDetectorId(builder, detPos);
        int event = EventMessage.endEventMessage(builder);
        builder.finish(event);
        eventMessageBytes = builder.sizedByteArray();
    }

    @org.junit.Test
    public void getMessageIdReturnsCorrectWhenConvertingDefaultEventMessage() {
        EventMessagePOJO eventMessagePOJO = EventMessageToEventMessagePOJO.convert(eventMessageBytes);
        int messageId = eventMessagePOJO.getMessageId();
        assertEquals(DEFAULT_MESSAGE_ID, messageId);
    }

    @org.junit.Test
    public void getPulseTimeReturnsCorrectWhenConvertingDefaultEventMessage() {
        EventMessagePOJO eventMessagePOJO = EventMessageToEventMessagePOJO.convert(eventMessageBytes);
        long pulseTime = eventMessagePOJO.getPulseTime();
        assertEquals(DEFAULT_PULSE_TIME, pulseTime);
    }

    @org.junit.Test
    public void getDetectorsReturnsCorrectWhenConvertingDefaultEventMessage() {
        EventMessagePOJO eventMessagePOJO = EventMessageToEventMessagePOJO.convert(eventMessageBytes);
        ArrayList<Integer> detectorIds = eventMessagePOJO.getDetectors();
        assertEquals(DEFAULT_DETECTORS.length, detectorIds.size());
        assertEquals(DEFAULT_DETECTORS[0], (int) detectorIds.get(0));
        assertEquals(DEFAULT_DETECTORS[DEFAULT_DETECTORS.length - 1], (int) detectorIds.get(DEFAULT_DETECTORS.length - 1));
        // TODO
        // array comparisons
    }





}
