package EventMessage;

import com.google.flatbuffers.FlatBufferBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for EventMessageDeserializer utility class.
 * Created by ISIS, STFC on 12/06/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class EventMessageDeserializerTest {
    private static final long DEFAULT_MESSAGE_ID = 1;
    private static final long DEFAULT_PULSE_TIME = 1;
    private static final int[] DEFAULT_DETECTORS = new int[10];
    private static final String DEFAULT_TOPIC = "DetectionEvents";
    private byte[] eventMessageBytes;
    private static EventMessageDeserializer eventMessageDeserializer;
    @Before
    public void setUp() throws Exception {
        // Fill detectors array
        for (int i = 0; i < DEFAULT_DETECTORS.length; i++) {
            DEFAULT_DETECTORS[i] = i;
        }
        // Create FlatBuffer builder.
        FlatBufferBuilder builder = new FlatBufferBuilder();
        // The detector vector needs to be created, but detectors can't be added before the EventMessage is started.
        int detPos = EventMessage.createDetectorIdVector(builder, DEFAULT_DETECTORS);

        EventMessage.startEventMessage(builder);
        // Add parameters
        EventMessage.addMessageId(builder, DEFAULT_MESSAGE_ID);
        EventMessage.addPulseTime(builder, DEFAULT_PULSE_TIME);
        // Add detector ids
        EventMessage.addDetectorId(builder, detPos);
        int event = EventMessage.endEventMessage(builder);
        builder.finish(event);
        eventMessageBytes = builder.sizedByteArray();

        eventMessageDeserializer = new EventMessageDeserializer();
    }

    private int[] arrayListToNative(ArrayList<Integer> arrayList) {
        int[] nativeArray = new int[arrayList.size()];
        for (int i : arrayList) {
            nativeArray[i] = arrayList.get(i);
        }
        return nativeArray;
    }

    @Test
    public void getMessageIdReturnsCorrectWhenConvertingDefaultEventMessage() {
        EventMessagePOJO eventMessagePOJO = eventMessageDeserializer.deserialize(DEFAULT_TOPIC, eventMessageBytes);
        int messageId = eventMessagePOJO.getMessageId();
        assertEquals(DEFAULT_MESSAGE_ID, messageId);
    }

    @Test
    public void getPulseTimeReturnsCorrectWhenConvertingDefaultEventMessage() {
        EventMessagePOJO eventMessagePOJO = eventMessageDeserializer.deserialize(DEFAULT_TOPIC, eventMessageBytes);
        long pulseTime = eventMessagePOJO.getPulseTime();
        assertEquals(DEFAULT_PULSE_TIME, pulseTime);
    }

    @Test
    public void getDetectorsReturnsCorrectWhenConvertingDefaultEventMessage() {
        EventMessagePOJO eventMessagePOJO = eventMessageDeserializer.deserialize(DEFAULT_TOPIC, eventMessageBytes);
        ArrayList<Integer> detectorIds = eventMessagePOJO.getDetectors();
        int[] nativeDetectorIds = arrayListToNative(detectorIds);
        assertArrayEquals(DEFAULT_DETECTORS, nativeDetectorIds);
    }

    @Test
    public void getDetectorReturnsCorrectGettingFirstDetector() {
        EventMessagePOJO eventMessagePOJO = eventMessageDeserializer.deserialize(DEFAULT_TOPIC, eventMessageBytes);
        int detectorId = eventMessagePOJO.getDetector(0);
        assertEquals(DEFAULT_DETECTORS[0], detectorId);
    }

    @Test
    public void getDetectorReturnsCorrectGettingLastDetector() {
        EventMessagePOJO eventMessagePOJO = eventMessageDeserializer.deserialize(DEFAULT_TOPIC, eventMessageBytes);
        int detectorId = eventMessagePOJO.getDetector(DEFAULT_DETECTORS.length - 1);
        assertEquals(DEFAULT_DETECTORS[DEFAULT_DETECTORS.length - 1], detectorId);
    }

}
