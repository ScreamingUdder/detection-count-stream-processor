package Image;

import com.google.flatbuffers.FlatBufferBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

/**
 * Unit tests for PulseImageDeserialiser.
 * Created by ISIS, STFC on 04/08/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class PulseImageDeserialiserTest {
    private static final long DEFAULT_PULSE_TIME = 0L;
    private PulseImagePOJO pulseImagePOJO;
    private final int DEFAULT_DETECTOR_ZERO_FREQUENCY = 1;
    private final int DEFAULT_DETECTOR_ONE_FREQUENCY = 2;
    private final String DEFAULT_TOPIC = "Detection Events";
    private byte[] pulseImage;
    private PulseImageDeserialiser pulseImageDeserialiser;
    @Before
    public void setup() {
        pulseImagePOJO = new PulseImagePOJO(DEFAULT_PULSE_TIME);
        pulseImagePOJO.setFrequency(0, DEFAULT_DETECTOR_ZERO_FREQUENCY);
        pulseImagePOJO.setFrequency(1, DEFAULT_DETECTOR_ONE_FREQUENCY);

        // Serialiser is not used to prevent circular references

        // Collect detector ids and counts from pojo
        Object[] keys = pulseImagePOJO.getImage().navigableKeySet().toArray();
        int length = keys.length;

        int[] detectors = new int[length];
        int[] counts = new int[length];

        for (int i = 0; i < length; i++) {
            long detectorId =  (long) keys[i];
            detectors[i] = (int) detectorId;
            counts[i] = (int) pulseImagePOJO.getFrequency(detectorId);
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
        PulseImage.addPulseTime(builder, pulseImagePOJO.getPulseTime());
        // Convert to byte array and return
        int pulseImageInt = PulseImage.endPulseImage(builder);
        builder.finish(pulseImageInt);
        pulseImage = builder.sizedByteArray();

        pulseImageDeserialiser = new PulseImageDeserialiser();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getPulseTimeAfterConvertingDefaultImageReturnsDefaultValue() {
        PulseImagePOJO pulseImagePOJO = pulseImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(DEFAULT_PULSE_TIME, pulseImagePOJO.getPulseTime());
    }

    @Test
    public void getImageSizeAfterConvertingDefaultImageReturnsTwo() {
        PulseImagePOJO pulseImagePOJO = pulseImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(2, pulseImagePOJO.getImageSize());
    }

    @Test
    public void getFrequencyOfDetectorZeroAfterConvertingDefaultImageReturnsDefaultValue() {
        PulseImagePOJO pulseImagePOJO = pulseImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(DEFAULT_DETECTOR_ZERO_FREQUENCY, pulseImagePOJO.getFrequency(0));
    }

    @Test
    public void getFrequencyOfDetectorOneAfterConvertingDefaultImageReturnsDefaultValue() {
        PulseImagePOJO pulseImagePOJO = pulseImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        Assert.assertEquals(DEFAULT_DETECTOR_ONE_FREQUENCY, pulseImagePOJO.getFrequency(1));
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfDetectorTwoAfterConvertingDefaultImageThrowsInvalidParameterException() {
        PulseImagePOJO pulseImagePOJO = pulseImageDeserialiser.deserialize(DEFAULT_TOPIC, pulseImage);
        pulseImagePOJO.getFrequency(2);
    }
}
