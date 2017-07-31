package Image;

import java.security.InvalidParameterException;

import static Image.ImageExceptionMessages.*;

/**
 * AccumulatedImagePOJO Java Object for storing heatmap data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public class AccumulatedImagePOJO implements ImageInterface {
    private long pulseTime; // Must be positive
    private int[] image;

    public AccumulatedImagePOJO(int imageSize) {
        if (imageSize <= 0) {
            throw new InvalidParameterException(IMAGE_SIZE_ABOVE_ZERO_ERROR_MESSAGE);
        }
        image = new int[imageSize];
        pulseTime = 0L;
    }

    public AccumulatedImagePOJO(int imageSize, Long pulseTime) {
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

    public int[] getImage() {
        return image;
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

    /**
     * Function to take a current frame image and update the image frequencies.
     * Accumulated image pulse time is changed to the frame image's value.
     * @param frameImage The frame image must be the same size as the accumulated image
     * and its indexes must correspond to the same detectors.
     * Passed frame image is assumed to be most recent, or at least more recent than the current pulse tine.
     */
    public void addFrameImage(FrameImage frameImage) {
        if (frameImage.getImageSize() != this.getImageSize()) {
            throw new InvalidParameterException(IMAGE_SIZE_MISMATCH_ERROR_MESSAGE);
        }
        this.setPulseTime(frameImage.getPulseTime());

        for (int i = 0; i < this.getImageSize(); i++) {
            int newFreq = this.getFrequency(i) + frameImage.getFrequency(i);
            this.setFrequency(i, newFreq);
        }
    }
}
