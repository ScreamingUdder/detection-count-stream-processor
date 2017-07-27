package Image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * JUnit tests for AccumulatedImage Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class FrameImageTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    private final int DEFAULT_IMAGE_SIZE = 1000;
    private FrameImage accumulatedImage;
    @Before
    public void setUp() {
        accumulatedImage = new FrameImage(DEFAULT_IMAGE_SIZE, DEFAULT_PULSE_TIME);
    }

    // Pulse time tests.
    @Test
    public void getPulseTimeReturnsCorrectOnDefaultHeatmap() {
        Assert.assertEquals(DEFAULT_PULSE_TIME, accumulatedImage.getPulseTime());
    }

    @Test
    public void setPulseTimeToPositiveLongWorksCorrectly() {
        long newPulseTime = 1L;
        accumulatedImage.setPulseTime(newPulseTime);

        Assert.assertEquals(newPulseTime, accumulatedImage.getPulseTime());
    }

    @Test(expected = InvalidParameterException.class)
    public void setPulseTimeToNegativeLongThrowsAssertionError() {
        long newPulseTime = -1L;
        accumulatedImage.setPulseTime(newPulseTime);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativePulseTimeThrowsAssertionError() {
        long newPulseTime = -1L;
        accumulatedImage = new FrameImage(DEFAULT_IMAGE_SIZE, newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, accumulatedImage.getImageSize());
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativeSizeThrowsAssertionError() {
        accumulatedImage = new FrameImage(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithZeroSizeThrowsAssertionError() {
        accumulatedImage = new FrameImage(0);
    }

    // Get frequency tests.

    @Test
    public void getFrequencyOfFirstIndexReturnsZeroOnDefaultImage() {
        Assert.assertEquals(0, accumulatedImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNegativeIndexThrowsAssertionError() {
        accumulatedImage.getFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsDetectorThrowsAssertionError() {
        accumulatedImage.getFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsByOneDetectorThrowsAssertionError() {
        accumulatedImage.getFrequency(DEFAULT_IMAGE_SIZE);
    }

    // Set frequency tests.

    @Test
    public void getFrequencyAfterSetFrequencyOfFirstIndexReturnsCorrect() {
        int newFreq = 5;
        accumulatedImage.setFrequency(0, 5);
        Assert.assertEquals(newFreq, accumulatedImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfNegativeDetectorThrowsAssertionError() {
        accumulatedImage.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsDetectorThrowsAssertionError() {
        accumulatedImage.setFrequency(9999, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsByOneDetectorThrowsAssertionError() {
        accumulatedImage.setFrequency(DEFAULT_IMAGE_SIZE, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsAssertionError() {
        accumulatedImage.setFrequency(0, -1);
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfFirstIndexReturnsCorrect() {
        accumulatedImage.incrementFrequency(0);
        Assert.assertEquals(1, accumulatedImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsAssertionError() {
        accumulatedImage.incrementFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsDetectorThrowsAssertionError() {
        accumulatedImage.incrementFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsByOneDetectorThrowsAssertionError() {
        accumulatedImage.incrementFrequency(DEFAULT_IMAGE_SIZE);
    }

}
