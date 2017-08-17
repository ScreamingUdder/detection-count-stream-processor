package Image;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for FrameImagePOJO to PulseImage converter.
 * Created by ISIS, STFC on 02/08/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class FrameImageSerializerTest {
    private static final long DEFAULT_PULSE_TIME = 0L;
    private static final String DEFAULT_TOPIC = "Detection events.";
    private FrameImagePOJO frameImagePOJO;
    private FrameImageSerializer frameImageSerializer;
    @Before
    public void setup() {
        frameImagePOJO = new FrameImagePOJO(DEFAULT_PULSE_TIME);
        frameImageSerializer = new FrameImageSerializer();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void convertPOJOWithNoDetectorsReturnsByteArray() {
        byte[] result = frameImageSerializer.serialize(DEFAULT_TOPIC, frameImagePOJO);
        assertNotNull("Should not be null", result);
    }

    @Test
    public void convertPOJOWithDetectorsReturnsByteArray() {
        frameImagePOJO.incrementFrequency(1);
        frameImagePOJO.incrementFrequency(2);
        frameImagePOJO.incrementFrequency(3);
        byte[] result = frameImageSerializer.serialize(DEFAULT_TOPIC, frameImagePOJO);
        assertNotNull("Should not be null", result);
    }

}
