package Image;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for AccumulatedImagePOJO to AccumulatedImage converter.
 * Created by ISIS, STFC on 02/08/2017.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public class AccumulatedImagePOJOToAccumulatedImageTest {
    private static final long DEFAULT_PULSE_TIME = 0L;
    private AccumulatedImagePOJO accumulatedImagePOJO;
    @Before
    public void setup() {
        accumulatedImagePOJO = new AccumulatedImagePOJO(DEFAULT_PULSE_TIME);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void convertPOJOWithNoDetectorsReturnsByteArray() {
        byte[] result = AccumulatedImagePOJOToAccumulatedImage.convert(accumulatedImagePOJO);
        assertNotNull("Should not be null", result);
    }

    @Test
    public void convertPOJOWithDetectorsReturnsByteArray() {
        accumulatedImagePOJO.incrementFrequency(1);
        accumulatedImagePOJO.incrementFrequency(2);
        accumulatedImagePOJO.incrementFrequency(3);
        byte[] result = AccumulatedImagePOJOToAccumulatedImage.convert(accumulatedImagePOJO);
        assertNotNull("Should not be null", result);
    }

}
