package Image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * JUnit tests for FrameImagePOJO Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class FrameImagePOJOTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    private FrameImagePOJO frameImagePOJO;
    @Before
    public void setUp() {
        frameImagePOJO = new FrameImagePOJO(DEFAULT_PULSE_TIME);
    }

    // Pulse time tests.
    @Test
    public void getPulseTimeReturnsCorrectOnDefaultHeatmap() {
        Assert.assertEquals(DEFAULT_PULSE_TIME, frameImagePOJO.getPulseTime());
    }

    @Test
    public void setPulseTimeToPositiveLongWorksCorrectly() {
        long newPulseTime = 1L;
        frameImagePOJO.setPulseTime(newPulseTime);

        Assert.assertEquals(newPulseTime, frameImagePOJO.getPulseTime());
    }

    @Test(expected = InvalidParameterException.class)
    public void setPulseTimeToNegativeLongThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        frameImagePOJO.setPulseTime(newPulseTime);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativePulseTimeThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        frameImagePOJO = new FrameImagePOJO(newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(0, frameImagePOJO.getImageSize());
    }

    // Get image tests

    @Test
    public void getImageReturnsEmptyArrayOfCorrectSizeOnDefaultImage() {
        Assert.assertEquals(0, frameImagePOJO.getImage().size());
        Assert.assertEquals(0, frameImagePOJO.getImage().keySet().size());
    }

    // Get frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNonSetDetectorReturnsThrowsInvalidParameter() {
        frameImagePOJO.getFrequency(0);
    }

    @Test
    public void getFrequencyOfSetDetectorReturnsCorrect() {
        int detector = 1;
        int newFreq = 5;
        frameImagePOJO.setFrequency(detector, newFreq);
        Assert.assertEquals(newFreq, frameImagePOJO.getFrequency(detector));

    }

    // Set frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        frameImagePOJO.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsInvalidParameterException() {
        frameImagePOJO.setFrequency(0, -1);
    }

    @Test
    public void getFrequencyOfDetectorAfterSettingFrequencyOfNewDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 1;
        frameImagePOJO.setFrequency(0, 1);
        Assert.assertEquals(newFreq, frameImagePOJO.getFrequency(detector));
    }

    @Test public void getFrequencyOfDetectorAfterUpdatingExistingDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 5;
        frameImagePOJO.setFrequency(detector, 0);
        frameImagePOJO.setFrequency(detector, newFreq);

        Assert.assertEquals(newFreq, frameImagePOJO.getFrequency(detector));
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfNewDetectorReturnsCorrect() {
        frameImagePOJO.incrementFrequency(0);
        Assert.assertEquals(1, frameImagePOJO.getFrequency(0));
    }

    @Test
    public void getFrequencyAfterIncrementOfExistingDetectorReturnsCorrect() {
        int detector = 0;
        int origFreq = 1;
        frameImagePOJO.setFrequency(detector, origFreq);
        frameImagePOJO.incrementFrequency(detector);
        Assert.assertEquals(origFreq + 1, frameImagePOJO.getFrequency(detector));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        frameImagePOJO.incrementFrequency(-1);
    }

}
