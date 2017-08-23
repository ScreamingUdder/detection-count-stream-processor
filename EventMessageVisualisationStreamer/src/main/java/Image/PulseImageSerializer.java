package Image;

import com.google.flatbuffers.FlatBufferBuilder;

import java.util.Map;

/**
 * Converts PulseImagePOJO to PulseImage
 * Created by ISIS, STFC on 17/08/2017.
 */
public class PulseImageSerializer implements org.apache.kafka.common.serialization.Serializer<PulseImagePOJO> {
    /**
     * Function for converting between PulseImage POJO and Flatbuffers object
     * @param pulseImagePOJO The PulseImagePOJO to be converted
     * @return A PulseImage Flatbuffer object, in the form of a byte array
     */
    public byte[] serialize(final String topic, final PulseImagePOJO pulseImagePOJO) {
        // Collect detector ids and counts from pojo
        Object[] keys = pulseImagePOJO.getImage().navigableKeySet().toArray();
        int length = keys.length;

        int[] detectors = new int[length];
        int[] counts = new int[length];

        for (int i = 0; i < length; i++) {
            long detectorId = (long) keys[i];
            detectors[i] = (int) detectorId;
            counts[i] = (int) pulseImagePOJO.getFrequency(detectorId);
        }

        // Builder must be initialised first
        FlatBufferBuilder builder = new FlatBufferBuilder();
        // Positions in the byte array must first be calculated for the two arrays
        int detPos = PulseImage.createDetectorIdVector(builder, detectors);
        int ctsPos = PulseImage.createDetectionCountVector(builder, counts);
        PulseImage.startPulseImage(builder);
        // detectors and counts can only be added after the flatbuffer is started
        PulseImage.addDetectorId(builder, detPos);
        PulseImage.addDetectionCount(builder, ctsPos);
        // Also add pulse time
        PulseImage.addPulseTime(builder, pulseImagePOJO.getPulseTime());
        // Convert to byte array and return
        int pulseImage = PulseImage.endPulseImage(builder);
        builder.finish(pulseImage);
        return builder.sizedByteArray();
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(final Map map, final boolean b) {

    }
}
