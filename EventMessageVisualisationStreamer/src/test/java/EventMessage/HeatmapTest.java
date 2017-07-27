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

    @Test
    public void setPulseTimeToPositiveLongWorksCorrectly() {
        long newPulseTime = 1L;
        heatmap.setPulseTime(newPulseTime);

        Assert.assertEquals(newPulseTime, heatmap.getPulseTime());
    }

    @Test(expected = AssertionError.class)
    public void setPulseTimeToNegativeLongThrowsAssetionError() {
        long newPulseTime = -1L;
        heatmap.setPulseTime(newPulseTime);
    }
}
