package Image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * JUnit tests for FrameImage Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class FrameImageTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    private FrameImage frameImage;
    @Before
    public void setUp() {
        frameImage = new FrameImage(DEFAULT_PULSE_TIME);
    }

    // Pulse time tests.
    @Test
    public void getPulseTimeReturnsCorrectOnDefaultHeatmap() {
        Assert.assertEquals(DEFAULT_PULSE_TIME, frameImage.getPulseTime());
    }

    @Test
    public void setPulseTimeToPositiveLongWorksCorrectly() {
        long newPulseTime = 1L;
        frameImage.setPulseTime(newPulseTime);

        Assert.assertEquals(newPulseTime, frameImage.getPulseTime());
    }

    @Test(expected = InvalidParameterException.class)
    public void setPulseTimeToNegativeLongThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        frameImage.setPulseTime(newPulseTime);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativePulseTimeThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        frameImage = new FrameImage(newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(0, frameImage.getImageSize());
    }

    // Get image tests

    @Test
    public void getImageReturnsEmptyArrayOfCorrectSizeOnDefaultImage() {
        Assert.assertEquals(0, frameImage.getImage().size());
        Assert.assertEquals(0, frameImage.getImage().keySet().size());
    }

    // Get frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNonSetDetectorReturnsThrowsInvalidParameter() {
        frameImage.getFrequency(0);
    }

    @Test
    public void getFrequencyOfSetDetectorReturnsCorrect() {
        int detector = 1;
        int newFreq = 5;
        frameImage.setFrequency(detector, newFreq);
        Assert.assertEquals(newFreq, frameImage.getFrequency(detector));

    }

    // Set frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        frameImage.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsInvalidParameterException() {
        frameImage.setFrequency(0, -1);
    }

    @Test
    public void getFrequencyOfDetectorAfterSettingFrequencyOfNewDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 1;
        frameImage.setFrequency(0, 1);
        Assert.assertEquals(newFreq, frameImage.getFrequency(detector));
    }

    @Test public void getFrequencyOfDetectorAfterUpdatingExistingDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 5;
        frameImage.setFrequency(detector, 0);
        frameImage.setFrequency(detector, newFreq);

        Assert.assertEquals(newFreq, frameImage.getFrequency(detector));
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfNewDetectorReturnsCorrect() {
        frameImage.incrementFrequency(0);
        Assert.assertEquals(1, frameImage.getFrequency(0));
    }

    @Test
    public void getFrequencyAfterIncrementOfExistingDetectorReturnsCorrect() {
        int detector = 0;
        int origFreq = 1;
        frameImage.setFrequency(detector, origFreq);
        frameImage.incrementFrequency(detector);
        Assert.assertEquals(origFreq + 1, frameImage.getFrequency(detector));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        frameImage.incrementFrequency(-1);
    }

}
