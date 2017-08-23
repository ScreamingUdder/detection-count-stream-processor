package Image;

import java.security.InvalidParameterException;
import java.util.TreeMap;

import static Image.ImageExceptionMessages.*;

/**
 * PulseImagePOJO Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class PulseImagePOJO implements ImageInterface {
    private long pulseTime; // Must be positive
    private TreeMap image;
    // Assumed to be TreeMap<int, int>. Integer - int interactions are more trouble than they're worth.

    public PulseImagePOJO(Long pulseTime) {
        if (pulseTime < 0) {
            throw new InvalidParameterException(PULSE_TIME_POSITIVE_ERROR_MESSAGE);
        }
        this.pulseTime = pulseTime;
        image = new TreeMap();
    }

    public long getPulseTime() {
        return pulseTime;
    }

    public void setPulseTime(long pulseTime) {
        if (pulseTime < 0) {
            throw new InvalidParameterException(PULSE_TIME_POSITIVE_ERROR_MESSAGE);
        }
        this.pulseTime = pulseTime;
    }

    public int getImageSize() {
        return image.size();
    }

    public TreeMap getImage() {
        return image;
    }

    public long getFrequency(long detector) {
        if (!image.containsKey(detector)) {
            throw new InvalidParameterException(MISSING_KEY_ERROR_MESSAGE);
        }
        return (long) image.get(detector);
    }

    public void setFrequency(long detector, long newFreq) {
        if (detector < 0) {
            throw new InvalidParameterException(DETECTOR_ID_POSITIVE_ERROR_MESSAGE);
        } else if (newFreq < 0) {
            throw new InvalidParameterException(FREQUENCY_POSITIVE_ERROR_MESSAGE);
        }
        image.put(detector,newFreq);
    }

    public void incrementFrequency(long detector) {
        long oldFreq = 0L;
        if (image.containsKey(detector)) {
            oldFreq = (long) image.get(detector);
        } else if (detector < 0) {
            throw  new InvalidParameterException(DETECTOR_ID_POSITIVE_ERROR_MESSAGE);
        }
        image.put(detector, ++oldFreq);
    }
}
