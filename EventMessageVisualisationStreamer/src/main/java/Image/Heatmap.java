package Image;

/**
 * Heatmap Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class Heatmap {
    private long pulseTime; // Must be positive

    public Heatmap() {
        pulseTime = 0L;
    }

    public Heatmap(Long pulseTime) {
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
