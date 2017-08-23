package Image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * JUnit tests for PulseImagePOJO Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class PulseImagePOJOTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    private PulseImagePOJO pulseImagePOJO;
    @Before
    public void setUp() {
        pulseImagePOJO = new PulseImagePOJO(DEFAULT_PULSE_TIME);
    }

    // Pulse time tests.
    @Test
    public void getPulseTimeReturnsCorrectOnDefaultHeatmap() {
        Assert.assertEquals(DEFAULT_PULSE_TIME, pulseImagePOJO.getPulseTime());
    }

    @Test
    public void setPulseTimeToPositiveLongWorksCorrectly() {
        long newPulseTime = 1L;
        pulseImagePOJO.setPulseTime(newPulseTime);

        Assert.assertEquals(newPulseTime, pulseImagePOJO.getPulseTime());
    }

    @Test(expected = InvalidParameterException.class)
    public void setPulseTimeToNegativeLongThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        pulseImagePOJO.setPulseTime(newPulseTime);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativePulseTimeThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        pulseImagePOJO = new PulseImagePOJO(newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(0, pulseImagePOJO.getImageSize());
    }

    // Get image tests

    @Test
    public void getImageReturnsEmptyArrayOfCorrectSizeOnDefaultImage() {
        Assert.assertEquals(0, pulseImagePOJO.getImage().size());
        Assert.assertEquals(0, pulseImagePOJO.getImage().keySet().size());
    }

    // Get frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNonSetDetectorReturnsThrowsInvalidParameter() {
        pulseImagePOJO.getFrequency(0);
    }

    @Test
    public void getFrequencyOfSetDetectorReturnsCorrect() {
        int detector = 1;
        int newFreq = 5;
        pulseImagePOJO.setFrequency(detector, newFreq);
        Assert.assertEquals(newFreq, pulseImagePOJO.getFrequency(detector));

    }

    // Set frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        pulseImagePOJO.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsInvalidParameterException() {
        pulseImagePOJO.setFrequency(0, -1);
    }

    @Test
    public void getFrequencyOfDetectorAfterSettingFrequencyOfNewDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 1;
        pulseImagePOJO.setFrequency(0, 1);
        Assert.assertEquals(newFreq, pulseImagePOJO.getFrequency(detector));
    }

    @Test public void getFrequencyOfDetectorAfterUpdatingExistingDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 5;
        pulseImagePOJO.setFrequency(detector, 0);
        pulseImagePOJO.setFrequency(detector, newFreq);

        Assert.assertEquals(newFreq, pulseImagePOJO.getFrequency(detector));
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfNewDetectorReturnsCorrect() {
        pulseImagePOJO.incrementFrequency(0);
        Assert.assertEquals(1, pulseImagePOJO.getFrequency(0));
    }

    @Test
    public void getFrequencyAfterIncrementOfExistingDetectorReturnsCorrect() {
        int detector = 0;
        int origFreq = 1;
        pulseImagePOJO.setFrequency(detector, origFreq);
        pulseImagePOJO.incrementFrequency(detector);
        Assert.assertEquals(origFreq + 1, pulseImagePOJO.getFrequency(detector));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        pulseImagePOJO.incrementFrequency(-1);
    }

}
