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
    private final int DEFAULT_IMAGE_SIZE = 1000;
    private AccumulatedImagePOJO accumulatedImagePOJO;
    @Before
    public void setUp() {
        accumulatedImagePOJO = new AccumulatedImagePOJO(DEFAULT_IMAGE_SIZE, DEFAULT_PULSE_TIME);
    }

    // Pulse time tests.
    @Test
    public void getPulseTimeReturnsCorrectOnDefaultHeatmap() {
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
        accumulatedImagePOJO = new AccumulatedImagePOJO(DEFAULT_IMAGE_SIZE, newPulseTime);
    }

    // Image size tests.

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, accumulatedImagePOJO.getImageSize());
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithNegativeSizeThrowsInvalidParameterException() {
        accumulatedImagePOJO = new AccumulatedImagePOJO(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNewImageWithZeroSizeThrowsInvalidParameterException() {
        accumulatedImagePOJO = new AccumulatedImagePOJO(0);
    }

    // Get image tests

    @Test
    public void getImageReturnsEmptyArrayOfCorrectSizeOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, accumulatedImagePOJO.getImage().length);
        Assert.assertEquals(0, accumulatedImagePOJO.getImage()[0]);
    }

    // Get frequency tests.

    @Test
    public void getFrequencyOfFirstIndexReturnsZeroOnDefaultImage() {
        Assert.assertEquals(0, accumulatedImagePOJO.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfNegativeIndexThrowsInvalidParameterException() {
        accumulatedImagePOJO.getFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.getFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void getFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.getFrequency(DEFAULT_IMAGE_SIZE);
    }

    // Set frequency tests.

    @Test
    public void getFrequencyAfterSetFrequencyOfFirstIndexReturnsCorrect() {
        int newFreq = 5;
        accumulatedImagePOJO.setFrequency(0, 5);
        Assert.assertEquals(newFreq, accumulatedImagePOJO.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.setFrequency(-1, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.setFrequency(9999, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.setFrequency(DEFAULT_IMAGE_SIZE, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void setFrequencyOfDetectorToNegativeThrowsInvalidParameterException() {
        accumulatedImagePOJO.setFrequency(0, -1);
    }

    // Increment frequency tests.

    @Test
    public void getFrequencyAfterIncrementFrequencyOfFirstIndexReturnsCorrect() {
        accumulatedImagePOJO.incrementFrequency(0);
        Assert.assertEquals(1, accumulatedImagePOJO.getFrequency(0));
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfNegativeDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.incrementFrequency(-1);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.incrementFrequency(9999);
    }

    @Test(expected = InvalidParameterException.class)
    public void incrementFrequencyOfOutOfBoundsByOneDetectorThrowsInvalidParameterException() {
        accumulatedImagePOJO.incrementFrequency(DEFAULT_IMAGE_SIZE);
    }

    // Add frame image tests

    @Test(expected = InvalidParameterException.class)
    public void addingFrameImageWithNonMatchingSizeThrowsInvalidParameterException() {
        FrameImage frameImage = new FrameImage(1, DEFAULT_PULSE_TIME);
        accumulatedImagePOJO.addFrameImage(frameImage);
    }

    @Test
    public void addingDefaultFrameImageToDefaultImageDoesNotAffectSizeOrContents() {
        FrameImage frameImage = new FrameImage(DEFAULT_IMAGE_SIZE, DEFAULT_PULSE_TIME);
        accumulatedImagePOJO.addFrameImage(frameImage);

        Assert.assertEquals(DEFAULT_IMAGE_SIZE, accumulatedImagePOJO.getImageSize());
        Assert.assertEquals(0, accumulatedImagePOJO.getFrequency(0));
    }

    @Test
    public void addingNonZeroFrameImageToDefaultImageUpdatesValuesCorrectly() {
        FrameImage frameImage = new FrameImage(DEFAULT_IMAGE_SIZE, DEFAULT_PULSE_TIME);
        frameImage.incrementFrequency(0);
        frameImage.incrementFrequency(1);
        accumulatedImagePOJO.addFrameImage(frameImage);

        Assert.assertEquals(1, frameImage.getFrequency(0));
        Assert.assertEquals(1, frameImage.getFrequency(1));
        Assert.assertEquals(0, frameImage.getFrequency(2));
    }

    @Test
    public void addingFrameImageWithDifferentPulseTimeToDefaultImageUpdatesValueCorrectly() {
        long pulseTime = 1L;
        FrameImage frameImage = new FrameImage(DEFAULT_IMAGE_SIZE, pulseTime);
        accumulatedImagePOJO.addFrameImage(frameImage);

        Assert.assertEquals(pulseTime, accumulatedImagePOJO.getPulseTime());
    }

    @Test
    public void addingNonZeroFrameImageToNonZeroImageUpdatesValuesCorrectly() {
        accumulatedImagePOJO.incrementFrequency(0);
        accumulatedImagePOJO.incrementFrequency(1);
        accumulatedImagePOJO.incrementFrequency(2);

        FrameImage frameImage = new FrameImage(DEFAULT_IMAGE_SIZE, DEFAULT_PULSE_TIME);
        frameImage.incrementFrequency(0);
        frameImage.incrementFrequency(1);
        accumulatedImagePOJO.addFrameImage(frameImage);

        Assert.assertEquals(2, accumulatedImagePOJO.getFrequency(0));
        Assert.assertEquals(2, accumulatedImagePOJO.getFrequency(1));
        Assert.assertEquals(1, accumulatedImagePOJO.getFrequency(2));
        Assert.assertEquals(0, accumulatedImagePOJO.getFrequency(3));
    }

}
