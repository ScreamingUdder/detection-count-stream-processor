package Image;

/**
 * AccumulatedImage Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class AccumulatedImage implements ImageInterface {
    private long pulseTime; // Must be positive
    private int[] image;

    private final String DETECTOR_POSITIVE_ASSERTION_MESSAGE = "Detector index must be positive.";
    private final String DETECTOR_WITHIN_BOUNDS_ASSERTION_MESSAGE = "Detector index must be within image bounds.";
    private final String IMAGE_SIZE_ABOVE_ZERO_ASSERTION_MESSAGE = "Image size must be above zero.";
    private final String PULSE_TIME_POSITIVE_ASSERTION_MESSAGE = "PulseTime cannot be negative.";

    public AccumulatedImage(int imageSize) {
        assert imageSize > 0: IMAGE_SIZE_ABOVE_ZERO_ASSERTION_MESSAGE;
        image = new int[imageSize];
        pulseTime = 0L;
    }

    public AccumulatedImage(int imageSize, Long pulseTime) {
        assert imageSize > 0: IMAGE_SIZE_ABOVE_ZERO_ASSERTION_MESSAGE;
        assert pulseTime >= 0: PULSE_TIME_POSITIVE_ASSERTION_MESSAGE;
        this.pulseTime = pulseTime;
        image = new int[imageSize];
    }

    public long getPulseTime() {
        return pulseTime;
    }

    public void setPulseTime(long pulseTime) {
        assert pulseTime >= 0: PULSE_TIME_POSITIVE_ASSERTION_MESSAGE;
        this.pulseTime = pulseTime;
    }

    public int getImageSize() {
        return image.length;
    }

    public int getFrequency(int detector) {
        assert detector >= 0: DETECTOR_POSITIVE_ASSERTION_MESSAGE;
        assert detector < getImageSize(): DETECTOR_WITHIN_BOUNDS_ASSERTION_MESSAGE;
        return image[detector];
    }

    public void setFrequency(int detector, int newFreq) {
        assert detector >= 0: DETECTOR_POSITIVE_ASSERTION_MESSAGE;
        assert detector < getImageSize(): DETECTOR_WITHIN_BOUNDS_ASSERTION_MESSAGE;
        image[detector] = newFreq;
    }
}
