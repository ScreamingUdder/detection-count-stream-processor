package Image;

/**
 * AccumulatedImage Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class AccumulatedImage implements ImageInterface {
    private long pulseTime; // Must be positive
    private int[] image;

    public AccumulatedImage(int imageSize) {
        image = new int[imageSize];
        pulseTime = 0L;
    }

    public AccumulatedImage(int imageSize, Long pulseTime) {
        assert pulseTime >= 0;
        this.pulseTime = pulseTime;
        image = new int[imageSize];
    }

    public long getPulseTime() {
        return pulseTime;
    }

    public void setPulseTime(long pulseTime) {
        assert pulseTime >= 0;
        this.pulseTime = pulseTime;
    }

    public int getImageSize() {
        return image.length;
    }
}
