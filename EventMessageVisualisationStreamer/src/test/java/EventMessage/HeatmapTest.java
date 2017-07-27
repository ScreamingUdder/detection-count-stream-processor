package EventMessage;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for Heatmap Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class HeatmapTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    @Before
    public void setUp() {
        Heatmap heatmap = new Heatmap(DEFAULT_PULSE_TIME);
    }
}
