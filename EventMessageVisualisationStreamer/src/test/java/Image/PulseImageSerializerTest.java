package Image;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for PulseImagePOJO to PulseImage converter.
 * Created by ISIS, STFC on 02/08/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class PulseImageSerializerTest {
    private static final long DEFAULT_PULSE_TIME = 0L;
    private static final String DEFAULT_TOPIC = "Detection events.";
    private PulseImagePOJO pulseImagePOJO;
    private PulseImageSerializer pulseImageSerializer;
    @Before
    public void setup() {
        pulseImagePOJO = new PulseImagePOJO(DEFAULT_PULSE_TIME);
        pulseImageSerializer = new PulseImageSerializer();
    }

    @Test
    public void convertPOJOWithNoDetectorsReturnsByteArray() {
        byte[] result = pulseImageSerializer.serialize(DEFAULT_TOPIC, pulseImagePOJO);
        assertNotNull("Should not be null", result);
    }

    @Test
    public void convertPOJOWithDetectorsReturnsByteArray() {
        pulseImagePOJO.incrementFrequency(1);
        pulseImagePOJO.incrementFrequency(2);
        pulseImagePOJO.incrementFrequency(3);
        byte[] result = pulseImageSerializer.serialize(DEFAULT_TOPIC, pulseImagePOJO);
        assertNotNull("Should not be null", result);
    }

}
