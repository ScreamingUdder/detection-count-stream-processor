package Image;

import com.google.flatbuffers.FlatBufferBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

/**
 * Unit tests for AccumulatedImageDeserialiser.
 * Created by ISIS, STFC on 04/08/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class AccumulatedImageDeserialiserTest {
    private static final long DEFAULT_PULSE_TIME = 0L;
    private AccumulatedImagePOJO accumulatedImagePOJO;
    private final int DEFAULT_DETECTOR_ZERO_FREQUENCY = 1;
    private final int DEFAULT_DETECTOR_ONE_FREQUENCY = 2;
    private final String DEFAULT_TOPIC = "Detection Events";
    private byte[] accumulatedImage;
    private AccumulatedImageDeserialiser accumulatedImageDeserialiser;
    @Before
    public void setup() {
        accumulatedImagePOJO = new AccumulatedImagePOJO(DEFAULT_PULSE_TIME);
        accumulatedImagePOJO.setFrequency(0, DEFAULT_DETECTOR_ZERO_FREQUENCY);
        accumulatedImagePOJO.setFrequency(1, DEFAULT_DETECTOR_ONE_FREQUENCY);

        // Serialiser is not used to prevent circular references

        // Collect detector ids and counts from pojo
        Object[] keys = accumulatedImagePOJO.getImage().navigableKeySet().toArray();
        int length = keys.length;

        int[] detectors = new int[length];
        int[] counts = new int[length];

        for (int i = 0; i < length; i++) {
            int detectorId = (int) keys[i];
            detectors[i] = detectorId;
            counts[i] = accumulatedImagePOJO.getFrequency(detectorId);
        }

        // Builder must be initialised first
        FlatBufferBuilder builder = new FlatBufferBuilder();
        // Positions in the byte array must first be calculated for the two arrays
        int detPos = AccumulatedImage.createDetectorIdVector(builder, detectors);
        int ctsPos = AccumulatedImage.createDetectionCountVector(builder, counts);
        AccumulatedImage.startAccumulatedImage(builder);
        // detectors and counts can only be added after the flatbuffer is started
        AccumulatedImage.addDetectorId(builder, detPos);
        AccumulatedImage.addDetectionCount(builder, ctsPos);
        // Also add pulse time
        AccumulatedImage.addPulseTime(builder, accumulatedImagePOJO.getPulseTime());
        // Convert to byte array and return
        int accumulatedImageInt = AccumulatedImage.endAccumulatedImage(builder);
        builder.finish(accumulatedImageInt);
        accumulatedImage = builder.sizedByteArray();

        accumulatedImageDeserialiser = new AccumulatedImageDeserialiser();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getPulseTimeAfterConvertingDefaultImageReturnsDefaultValue() {
        AccumulatedImagePOJO accumulatedImagePOJO = accumulatedImageDeserialiser.deserialize(DEFAULT_TOPIC, accumulatedImage);
        Assert.assertEquals(DEFAULT_PULSE_TIME, accumulatedImagePOJO.getPulseTime());
    }

    @Test
    public void getImageSizeAfterConvertingDefaultImageReturnsTwo() {
        AccumulatedImagePOJO accumulatedImagePOJO = accumulatedImageDeserialiser.deserialize(DEFAULT_TOPIC, accumulatedImage);
        Assert.assertEquals(2, accumulatedImagePOJO.getImageSize());
    }

    @Test
    public void getFrequencyOfDetectorZeroAfterConvertingDefaultImageReturnsDefaultValue() {
        AccumulatedImagePOJO accumulatedImagePOJO = accumulatedImageDeserialiser.deserialize(DEFAULT_TOPIC, accumulatedImage);
        Assert.assertEquals(DEFAULT_DETECTOR_ZERO_FREQUENCY, accumulatedImagePOJO.getFrequency(0));
    }

    @Test
    public void getFrequencyOfDetectorOneAfterConvertingDefaultImageReturnsDefaultValue() {
        AccumulatedImagePOJO accumulatedImagePOJO = accumulatedImageDeserialiser.deserialize(DEFAULT_TOPIC, accumulatedImage);
        Assert.assertEquals(DEFAULT_DETECTOR_ONE_FREQUENCY, accumulatedImagePOJO.getFrequency(1));
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfDetectorTwoAfterConvertingDefaultImageThrowsInvalidParameterException() {
        AccumulatedImagePOJO accumulatedImagePOJO = accumulatedImageDeserialiser.deserialize(DEFAULT_TOPIC, accumulatedImage);
        accumulatedImagePOJO.getFrequency(2);
    }



    //TODO Add tests for first pulse time once field is added to flatbuffer (issue 21)

}
