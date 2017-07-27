package Image;

import java.security.InvalidParameterException;

import static Image.ImageExceptionMessages.*;

/**
 * FrameImage Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class FrameImage implements ImageInterface {
    private long pulseTime; // Must be positive
    private int[] image;



    public FrameImage(int imageSize) {
        if (imageSize <= 0) {
            throw new InvalidParameterException(IMAGE_SIZE_ABOVE_ZERO_ERROR_MESSAGE);
        }
        image = new int[imageSize];
        pulseTime = 0L;
    }

    public FrameImage(int imageSize, Long pulseTime) {
        if (imageSize <= 0) {
            throw new InvalidParameterException(IMAGE_SIZE_ABOVE_ZERO_ERROR_MESSAGE);
        } else if (pulseTime < 0) {
            throw new InvalidParameterException(PULSE_TIME_POSITIVE_ERROR_MESSAGE);
        }
        this.pulseTime = pulseTime;
        image = new int[imageSize];
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
        return image.length;
    }

    public int getFrequency(int detector) {
        if (detector < 0 || detector >= getImageSize()) {
            throw new InvalidParameterException(DETECTOR_WITHIN_BOUNDS_ERROR_MESSAGE);
        }
        return image[detector];
    }

    public void setFrequency(int detector, int newFreq) {
        if (detector < 0 || detector >= getImageSize()) {
            throw new InvalidParameterException(DETECTOR_WITHIN_BOUNDS_ERROR_MESSAGE);
        } else if (newFreq < 0) {
            throw new InvalidParameterException(FREQUENCY_POSITIVE_ERROR_MESSAGE);
        }
        image[detector] = newFreq;
    }

    public void incrementFrequency(int detector) {
        if (detector < 0 || detector >= getImageSize()) {
            throw new InvalidParameterException(DETECTOR_WITHIN_BOUNDS_ERROR_MESSAGE);
        }
        image[detector]++;
    }
}
