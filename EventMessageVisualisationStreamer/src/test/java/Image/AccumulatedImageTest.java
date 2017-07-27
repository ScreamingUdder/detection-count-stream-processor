package Image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    @Test(expected = AssertionError.class)
    public void setPulseTimeToNegativeLongThrowsAssertionError() {
        long newPulseTime = -1L;
        accumulatedImage.setPulseTime(newPulseTime);
    }

    @Test(expected = AssertionError.class)
    public void createNewHeatmapWithNegativePulseTimeThrowsAssertionError() {
        long newPulseTime = -1L;
        accumulatedImage = new AccumulatedImage(DEFAULT_IMAGE_SIZE, newPulseTime);
    }

    @Test
    public void getImageSizeCorrectOnDefaultImage() {
        Assert.assertEquals(DEFAULT_IMAGE_SIZE, accumulatedImage.getImageSize());
    }

    @Test(expected = AssertionError.class)
    public void createNewImageWithNegativeSizeThrowsAssertionError() {
        accumulatedImage = new AccumulatedImage(-1);
    }

    @Test(expected = AssertionError.class)
    public void createNewImageWithZeroSizeThrowsAssertionError() {
        accumulatedImage = new AccumulatedImage(0);
    }

    @Test
    public void getFrequencyOfFirstIndexReturnsZeroOnDefaultImage() {
        Assert.assertEquals(0, accumulatedImage.getFrequency(0));
    }

    @Test(expected = AssertionError.class)
    public void getFrequencyOfNegativeIndexThrowsAssertionError() {
        accumulatedImage.getFrequency(-1);
    }

    @Test(expected = AssertionError.class)
    public void getFrequencyOfOutOfBoundsDetectorThrowsAssertionError() {
        accumulatedImage.getFrequency(9999);
    }

    @Test(expected = AssertionError.class)
    public void getFrequencyOfOutOfBoundsByOneDetectorThrowsAssertionError() {
        accumulatedImage.getFrequency(DEFAULT_IMAGE_SIZE);
    }




}
