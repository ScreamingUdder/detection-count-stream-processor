package Image;

import com.google.flatbuffers.FlatBufferBuilder;

/**
 * Converts AccumulatedImagePOJO to AccumulatedImage
 * Created by ISIS, STFC on 01/08/2017.
 */
public class AccumulatedImagePOJOToAccumulatedImage {
    /**
     * Function for converting between AccumulatedImage POJO and Flatbuffers object
     * @param accumulatedImagePOJO The AccumulatedImagePOJO to be converted
     * @return A AccumulatedImage Flatbuffer object, in the form of a byte array
     */
    public static byte[] convert(final AccumulatedImagePOJO accumulatedImagePOJO) {
        // Collect detector ids and counts from pojo
        Object[] keys = accumulatedImagePOJO.getImage().navigableKeySet().toArray();
        int length = keys.length;

        int[] detectors = new int[length];
        int[] counts = new int[length];

        for (int i = 0; i < length; i++) {
            int detectorId = (int) keys[i];
            detectors[i] = detectorId;
            counts[i] = (int) accumulatedImagePOJO.getFrequency(detectorId);
        }

        // Builder must be initialised first
        FlatBufferBuilder builder = new FlatBufferBuilder();
        // Positions in the byte array must first be calculated for the two arrays
        int detPos = AccumulatedImage.createDetectorIdVector(builder, detectors);
        int ctsPos = AccumulatedImage.createDetectionCountVector(builder, counts);
        AccumulatedImage.startAccumulatedImage(builder);
        // detectors and counts can only be added after the flatbuffer is started
        AccumulatedImage.addDetectorId(builder, detPos);
        AccumulatedImage.addDetectionCount(builder, ctsPos);
        // Also add pulse times
        AccumulatedImage.addFirstPulseTime(builder, accumulatedImagePOJO.getFirstPulseTime());
        AccumulatedImage.addPulseTime(builder, accumulatedImagePOJO.getPulseTime());
        // Convert to byte array and return
        int accumulatedImage = AccumulatedImage.endAccumulatedImage(builder);
        builder.finish(accumulatedImage);
        return builder.sizedByteArray();
    }
}
