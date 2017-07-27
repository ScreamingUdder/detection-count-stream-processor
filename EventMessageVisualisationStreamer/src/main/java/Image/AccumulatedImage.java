package Image;

/**
 * AccumulatedImage Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class AccumulatedImage implements ImageInterface {
    private long pulseTime; // Must be positive
    private int[] image;

    public AccumulatedImage(int imageSize) {
        assert imageSize > 0: "Image size must be above zero.";
        image = new int[imageSize];
        pulseTime = 0L;
    }

    public AccumulatedImage(int imageSize, Long pulseTime) {
        assert imageSize > 0: "Image size must be above zero.";
        assert pulseTime >= 0: "PulseTime cannot be negative.";
        this.pulseTime = pulseTime;
        image = new int[imageSize];
    }

    public long getPulseTime() {
        return pulseTime;
    }

    public void setPulseTime(long pulseTime) {
        assert pulseTime >= 0: "PulseTime cannot be negative.";
        this.pulseTime = pulseTime;
    }

    public int getImageSize() {
        return image.length;
    }

    public int getFrequency(int detector) {
        assert detector >= 0: "Detector index must be positive.";
        assert detector > getImageSize(): "Detector index must be within image bounds.";
        return image[detector];
    }
}
