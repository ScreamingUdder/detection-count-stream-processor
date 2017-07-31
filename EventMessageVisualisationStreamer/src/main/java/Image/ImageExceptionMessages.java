package Image;

/**
 * Storage of standard exception messages for classes implementing ImageInterface.
 * Created by ISIS, STFC on 27/07/2017.
 */
class ImageExceptionMessages {
    static final String MISSING_KEY_ERROR_MESSAGE = "The entered detector is not present in the tree map.";
    static final String DETECTOR_ID_POSITIVE_ERROR_MESSAGE = "Detector IDs cannot be negative.";
    static final String PULSE_TIME_POSITIVE_ERROR_MESSAGE = "PulseTime cannot be negative.";
    static final String FREQUENCY_POSITIVE_ERROR_MESSAGE = "The new frequency must be positive.";
}
