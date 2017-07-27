package EventMessage;

/**
 * Heatmap Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class Heatmap {
    private long pulseTime;

    public Heatmap() {
        pulseTime = 0L;
    }

    public Heatmap(Long pulseTime) {
        this.pulseTime = pulseTime;
    }
}
