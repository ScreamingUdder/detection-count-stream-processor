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
    private FrameImage frameImage;
    @Before
    public void setUp() {
        frameImage = new FrameImage(DEFAULT_IMAGE_SIZE, DEFAULT_PULSE_TIME);
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
        frameImage = new FrameImage(DEFAULT_IMAGE_SIZE, newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, frameImage.getImageSize());
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativeSizeThrowsInvalidParameterException() {
        frameImage = new FrameImage(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithZeroSizeThrowsInvalidParameterException() {
        frameImage = new FrameImage(0);
    }

    // Get image tests

    @Test
    public void getImageReturnsEmptyArrayOfCorrectSizeOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, frameImage.getImage().length);
        Assert.assertEquals(0, frameImage.getImage()[0]);
    }

    // Get frequency tests.

    @Test
    public void getFrequencyOfFirstIndexReturnsZeroOnDefaultImage() {
        Assert.assertEquals(0, frameImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNegativeIndexThrowsInvalidParameterException() {
        frameImage.getFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        frameImage.getFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        frameImage.getFrequency(DEFAULT_IMAGE_SIZE);
    }

    // Set frequency tests.

    @Test
    public void getFrequencyAfterSetFrequencyOfFirstIndexReturnsCorrect() {
        int newFreq = 5;
        frameImage.setFrequency(0, 5);
        Assert.assertEquals(newFreq, frameImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        frameImage.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        frameImage.setFrequency(9999, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        frameImage.setFrequency(DEFAULT_IMAGE_SIZE, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsInvalidParameterException() {
        frameImage.setFrequency(0, -1);
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfFirstIndexReturnsCorrect() {
        frameImage.incrementFrequency(0);
        Assert.assertEquals(1, frameImage.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        frameImage.incrementFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        frameImage.incrementFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        frameImage.incrementFrequency(DEFAULT_IMAGE_SIZE);
    }

}
