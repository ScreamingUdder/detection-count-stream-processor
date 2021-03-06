package Image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * JUnit tests for AccumulatedImagePOJO Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class AccumulatedImagePOJOTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    private AccumulatedImagePOJO accumulatedImagePOJO;
    @Before
    public void setUp() {
        accumulatedImagePOJO = new AccumulatedImagePOJO(DEFAULT_PULSE_TIME);
    }

    // Pulse time tests.

    @Test
    public void getFirstPulseTimeReturnsCorrectOnImage() {
        Assert.assertEquals(DEFAULT_PULSE_TIME, accumulatedImagePOJO.getFirstPulseTime());
    }

    @Test
    public void getPulseTimeReturnsCorrectOnDefaultImage() {
        Assert.assertEquals(DEFAULT_PULSE_TIME, accumulatedImagePOJO.getPulseTime());
    }

    @Test
    public void setPulseTimeToPositiveLongWorksCorrectly() {
        long newPulseTime = 1L;
        accumulatedImagePOJO.setPulseTime(newPulseTime);

        Assert.assertEquals(newPulseTime, accumulatedImagePOJO.getPulseTime());
    }

    @Test(expected = InvalidParameterException.class)
    public void setPulseTimeToNegativeLongThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        accumulatedImagePOJO.setPulseTime(newPulseTime);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativePulseTimeThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        accumulatedImagePOJO = new AccumulatedImagePOJO(newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(0, accumulatedImagePOJO.getImageSize());
    }

    // Get image tests

    @Test
    public void getImageReturnsEmptyArrayOfCorrectSizeOnDefaultImage() {
        Assert.assertEquals(0, accumulatedImagePOJO.getImage().size());
        Assert.assertEquals(0, accumulatedImagePOJO.getImage().keySet().size());
    }

    // Get frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNonSetDetectorReturnsThrowsInvalidParameter() {
        accumulatedImagePOJO.getFrequency(0);
    }

    @Test
    public void getFrequencyOfSetDetectorReturnsCorrect() {
        int detector = 1;
        int newFreq = 5;
        accumulatedImagePOJO.setFrequency(detector, newFreq);
        Assert.assertEquals(newFreq, accumulatedImagePOJO.getFrequency(detector));

    }

    // Set frequency tests.

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsInvalidParameterException() {
        accumulatedImagePOJO.setFrequency(0, -1);
    }

    @Test
    public void getFrequencyOfDetectorAfterSettingFrequencyOfNewDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 1;
        accumulatedImagePOJO.setFrequency(0, 1);
        Assert.assertEquals(newFreq, accumulatedImagePOJO.getFrequency(detector));
    }

    @Test public void getFrequencyOfDetectorAfterUpdatingExistingDetectorReturnsCorrect() {
        int detector = 0;
        int newFreq = 5;
        accumulatedImagePOJO.setFrequency(detector, 0);
        accumulatedImagePOJO.setFrequency(detector, newFreq);

        Assert.assertEquals(newFreq, accumulatedImagePOJO.getFrequency(detector));
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfNewDetectorReturnsCorrect() {
        accumulatedImagePOJO.incrementFrequency(0);
        Assert.assertEquals(1, accumulatedImagePOJO.getFrequency(0));
    }

    @Test
    public void getFrequencyAfterIncrementOfExistingDetectorReturnsCorrect() {
        int detector = 0;
        int origFreq = 1;
        accumulatedImagePOJO.setFrequency(detector, origFreq);
        accumulatedImagePOJO.incrementFrequency(detector);
        Assert.assertEquals(origFreq + 1, accumulatedImagePOJO.getFrequency(detector));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.incrementFrequency(-1);
    }

    // Add frame image tests

    @Test
    public void addingDefaultFrameImageToDefaultImageDoesNotAffectSizeOrContents() {
        PulseImagePOJO pulseImagePOJO = new PulseImagePOJO(DEFAULT_PULSE_TIME);
        accumulatedImagePOJO.addFrameImage(pulseImagePOJO);

        Assert.assertEquals(0, accumulatedImagePOJO.getImageSize());
    }

    @Test
    public void addingNonZeroFrameImageToDefaultImageUpdatesValuesCorrectly() {
        PulseImagePOJO pulseImagePOJO = new PulseImagePOJO(DEFAULT_PULSE_TIME);
        pulseImagePOJO.incrementFrequency(0);
        pulseImagePOJO.incrementFrequency(1);
        accumulatedImagePOJO.addFrameImage(pulseImagePOJO);

        Assert.assertEquals(1, pulseImagePOJO.getFrequency(0));
        Assert.assertEquals(1, pulseImagePOJO.getFrequency(1));
    }

    @Test
    public void addingFrameImageWithDifferentPulseTimeToDefaultImageUpdatesValueCorrectly() {
        long pulseTime = 1L;
        PulseImagePOJO pulseImagePOJO = new PulseImagePOJO(pulseTime);
        accumulatedImagePOJO.addFrameImage(pulseImagePOJO);

        Assert.assertEquals(pulseTime, accumulatedImagePOJO.getPulseTime());
    }

    @Test public void addingFrameImageWithDifferentPulseTimeToDefaultImageDoesNotAffectFirstPulseTime() {
        long pulseTime = 1L;
        PulseImagePOJO pulseImagePOJO = new PulseImagePOJO(pulseTime);
        accumulatedImagePOJO.addFrameImage(pulseImagePOJO);

        Assert.assertEquals(DEFAULT_PULSE_TIME, accumulatedImagePOJO.getFirstPulseTime());
    }

    @Test
    public void addingNonZeroFrameImageToNonZeroImageUpdatesValuesCorrectly() {
        accumulatedImagePOJO.incrementFrequency(0);
        accumulatedImagePOJO.incrementFrequency(1);
        accumulatedImagePOJO.incrementFrequency(2);

        PulseImagePOJO pulseImagePOJO = new PulseImagePOJO(DEFAULT_PULSE_TIME);
        pulseImagePOJO.incrementFrequency(0);
        pulseImagePOJO.incrementFrequency(1);
        accumulatedImagePOJO.addFrameImage(pulseImagePOJO);

        Assert.assertEquals(2, accumulatedImagePOJO.getFrequency(0));
        Assert.assertEquals(2, accumulatedImagePOJO.getFrequency(1));
        Assert.assertEquals(1, accumulatedImagePOJO.getFrequency(2));
    }

}
