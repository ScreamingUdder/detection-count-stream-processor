package EventMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for Heatmap Java Object
 * Created by ISIS, STFC on 27/07/2017.
 */
public class HeatmapTest {
    private final long DEFAULT_PULSE_TIME = 0L;
    private Heatmap heatmap;
    @Before
    public void setUp() {
        heatmap = new Heatmap(DEFAULT_PULSE_TIME);
    }

    @Test
    public void getPulseTimeReturnsCorrectOnDefaultHeatmap() {
        Assert.assertEquals(DEFAULT_PULSE_TIME, heatmap.getPulseTime());
    }
}
