package Image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * JUnit tests for AccumulatedImage Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class AccumulatedImageTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    private final int DEFAULT_IMAGE_SIZE = 1000;
    private AccumulatedImage accumulatedImage;
    @Before
    public void setUp() {
        accumulatedImage = new AccumulatedImage(DEFAULT_IMAGE_SIZE, DEFAULT_PULSE_TIME);
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
    public void setPulseTimeToNegativeLongThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        accumulatedImage.setPulseTime(newPulseTime);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativePulseTimeThrowsInvalidParameterException() {
        long newPulseTime = -1L;
        accumulatedImage = new AccumulatedImage(DEFAULT_IMAGE_SIZE, newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, accumulatedImage.getImageSize());
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativeSizeThrowsInvalidParameterException() {
        accumulatedImage = new AccumulatedImage(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithZeroSizeThrowsInvalidParameterException() {
        accumulatedImage = new AccumulatedImage(0);
    }

    // Get image tests

    @Test
    public void getImageReturnsEmptyArrayOfCorrectSizeOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, accumulatedImage.getImage().length);
        Assert.assertEquals(0, accumulatedImage.getImage()[0]);
    }

    // Get frequency tests.

    @Test
    public void getFrequencyOfFirstIndexReturnsZeroOnDefaultImage() {
        Assert.assertEquals(0, accumulatedImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNegativeIndexThrowsInvalidParameterException() {
        accumulatedImage.getFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        accumulatedImage.getFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
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
    public void setFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        accumulatedImage.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        accumulatedImage.setFrequency(9999, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        accumulatedImage.setFrequency(DEFAULT_IMAGE_SIZE, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsInvalidParameterException() {
        accumulatedImage.setFrequency(0, -1);
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfFirstIndexReturnsCorrect() {
        accumulatedImage.incrementFrequency(0);
        Assert.assertEquals(1, accumulatedImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        accumulatedImage.incrementFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        accumulatedImage.incrementFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        accumulatedImage.incrementFrequency(DEFAULT_IMAGE_SIZE);
    }

    // Add frame image tests

    @Test(expected = InvalidParameterException.class)
    public void addingFrameImageWithNonMatchingSizeThrowsInvalidParameterException() {
        FrameImage frameImage = new FrameImage(1, DEFAULT_PULSE_TIME);
        accumulatedImage.addFrameImage(frameImage);
    }

}
