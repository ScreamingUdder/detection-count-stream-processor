package Image;

import com.google.flatbuffers.FlatBufferBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

/**
 * Unit tests for FrameImageDeserialiser.
 * Created by ISIS, STFC on 04/08/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class FrameImageDeserialiserTest {
    private static final long DEFAULT_PULSE_TIME = 0L;
    private FrameImagePOJO frameImagePOJO;
    private final int DEFAULT_DETECTOR_ZERO_FREQUENCY = 1;
    private final int DEFAULT_DETECTOR_ONE_FREQUENCY = 2;
    private final String DEFAULT_TOPIC = "Detection Events";
    private byte[] pulseImage;
    private FrameImageDeserialiser frameImageDeserialiser;
    @Before
    public void setup() {
        frameImagePOJO = new FrameImagePOJO(DEFAULT_PULSE_TIME);
        frameImagePOJO.setFrequency(0, DEFAULT_DETECTOR_ZERO_FREQUENCY);
        frameImagePOJO.setFrequency(1, DEFAULT_DETECTOR_ONE_FREQUENCY);

        // Serialiser is not used to prevent circular references

        // Collect detector ids and counts from pojo
        Object[] keys = frameImagePOJO.getImage().navigableKeySet().toArray();
        int length = keys.length;

        int[] detectors = new int[length];
        int[] counts = new int[length];

        for (int i = 0; i < length; i++) {
            long detectorId =  (long) keys[i];
            detectors[i] = (int) detectorId;
            counts[i] = (int) frameImagePOJO.getFrequency(detectorId);
        }

        // Builder must be initialised first
        FlatBufferBuilder builder = new FlatBufferBuilder();
        // Positions in the byte array must first be calculated for the two arrays
        int detPos = PulseImage.createDetectorIdVector(builder, detectors);
        int ctsPos = PulseImage.createDetectionCountVector(builder, counts);
        PulseImage.startPulseImage(builder);
        // detectors and counts can only be added after the flatbuffer is started
        PulseImage.addDetectorId(builder, detPos);
        PulseImage.addDetectionCount(builder, ctsPos);
        // Also add pulse time
        PulseImage.addPulseTime(builder, frameImagePOJO.getPulseTime());
        // Convert to byte array and return
        int pulseImageInt = PulseImage.endPulseImage(builder);
        builder.finish(pulseImageInt);
        pulseImage = builder.sizedByteArray();

        frameImageDeserialiser = new FrameImageDeserialiser();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getPulseTimeAfterConvertingDefaultImageReturnsDefaultValue() {
        FrameImagePOJO frameImagePOJO = frameImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(DEFAULT_PULSE_TIME, frameImagePOJO.getPulseTime());
    }

    @Test
    public void getImageSizeAfterConvertingDefaultImageReturnsTwo() {
        FrameImagePOJO frameImagePOJO = frameImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(2, frameImagePOJO.getImageSize());
    }

    @Test
    public void getFrequencyOfDetectorZeroAfterConvertingDefaultImageReturnsDefaultValue() {
        FrameImagePOJO frameImagePOJO = frameImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(DEFAULT_DETECTOR_ZERO_FREQUENCY, frameImagePOJO.getFrequency(0));
    }

    @Test
    public void getFrequencyOfDetectorOneAfterConvertingDefaultImageReturnsDefaultValue() {
        FrameImagePOJO frameImagePOJO = frameImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(DEFAULT_DETECTOR_ONE_FREQUENCY, frameImagePOJO.getFrequency(1));
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfDetectorTwoAfterConvertingDefaultImageThrowsInvalidParameterException() {
        FrameImagePOJO frameImagePOJO = frameImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        frameImagePOJO.getFrequency(2);
    }
}
