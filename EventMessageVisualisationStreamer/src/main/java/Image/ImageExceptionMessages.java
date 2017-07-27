package Image;

/**
 * Storage of standard exception messages for classes implementing ImageInterface.
 * Created by ISIS, STFC on 27/07/2017.
 */
public class ImageExceptionMessages {
    public static final String DETECTOR_WITHIN_BOUNDS_ERROR_MESSAGE = "Detector index must be within image bounds.";
    public static final String IMAGE_SIZE_ABOVE_ZERO_ERROR_MESSAGE = "Image size must be above zero.";
    public static final String PULSE_TIME_POSITIVE_ERROR_MESSAGE = "PulseTime cannot be negative.";
    public static final String FREQUENCY_POSITIVE_ERROR_MESSAGE = "The new frequency must be positive.";
}
