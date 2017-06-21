package EventMessage;

import com.google.flatbuffers.FlatBufferBuilder;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
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

    private int[] arrayListToNative(ArrayList<Integer> arrayList) {
        int[] nativeArray = new int[arrayList.size()];
        for (int i : arrayList) {
            nativeArray[i] = arrayList.get(i);
        }
        return nativeArray;
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
        int[] nativeDetectorIds = arrayListToNative(detectorIds);
        assertArrayEquals(DEFAULT_DETECTORS, nativeDetectorIds);
    }

    @org.junit.Test
    public void getDetectorReturnsCorrectGettingFirstDetector() {
        EventMessagePOJO eventMessagePOJO = EventMessageToEventMessagePOJO.convert(eventMessageBytes);
        int detectorId = eventMessagePOJO.getDetector(0);
        assertEquals(DEFAULT_DETECTORS[0], detectorId);
    }

    @org.junit.Test
    public void getDetectorReturnsCorrectGettingLastDetector() {
        EventMessagePOJO eventMessagePOJO = EventMessageToEventMessagePOJO.convert(eventMessageBytes);
        int detectorId = eventMessagePOJO.getDetector(DEFAULT_DETECTORS.length - 1);
        assertEquals(DEFAULT_DETECTORS[DEFAULT_DETECTORS.length - 1], detectorId);
    }

}
