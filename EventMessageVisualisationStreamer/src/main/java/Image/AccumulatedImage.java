package Image;

/**
 * AccumulatedImage Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class AccumulatedImage implements ImageInterface {
    private long pulseTime; // Must be positive

    public AccumulatedImage() {
        pulseTime = 0L;
    }

    public AccumulatedImage(Long pulseTime) {
        assert pulseTime >= 0;
        this.pulseTime = pulseTime;
    }

    public long getPulseTime() {
        return pulseTime;
    }

    public void setPulseTime(long pulseTime) {
        assert pulseTime >= 0;
        this.pulseTime = pulseTime;
    }
}
