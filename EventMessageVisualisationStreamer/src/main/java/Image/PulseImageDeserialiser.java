package Image;

import java.util.Map;

/**
 * Converts PulseImage to PulseImagePOJO
 * Created by ISIS, STFC on 04/08/2017.
 */
public class PulseImageDeserialiser implements org.apache.kafka.common.serialization.Deserializer<PulseImagePOJO> {
    /**
     * Function for converting between PulseImage Flatbuffer and PulseImage POJO
     * @param bytes The PulseImage byte array to be converted
     * @return PulseImagePOJO
     */
    public PulseImagePOJO deserialize(final String topic, final byte[] bytes) {
        // convert byte array to java flatbuffer object
        PulseImage pulseImage = PulseImage.getRootAsPulseImage(java.nio.ByteBuffer.wrap(bytes));
        // Assign simple attribute s
        PulseImagePOJO pulseImagePOJO = new PulseImagePOJO(pulseImage.pulseTime());
        // Add detectors
        for (int i = 0; i < pulseImage.detectorIdLength(); i++) {
            int detId = (int) pulseImage.detectorId(i);
            int count = (int) pulseImage.detectionCount(i);
            pulseImagePOJO.setFrequency(detId, count);
        }
        return pulseImagePOJO;
    }

    @Override
    public void close() {


    }

    @Override
    public void configure(final Map map, final boolean b) {

    }
}
