package Image;

import java.util.Map;

/**
 * Converts PulseImage to FrameImagePOJO
 * Created by ISIS, STFC on 04/08/2017.
 */
public class FrameImageDeserialiser implements org.apache.kafka.common.serialization.Deserializer<FrameImagePOJO> {
    /**
     * Function for converting between PulseImage Flatbuffer and FrameImage POJO
     * @param bytes The PulseImage byte array to be converted
     * @return PulseImagePOJO
     */
    public FrameImagePOJO deserialize(final String topic, final byte[] bytes) {
        // convert byte array to java flatbuffer object
        PulseImage pulseImage = PulseImage.getRootAsPulseImage(java.nio.ByteBuffer.wrap(bytes));
        // Assign simple attributes
        FrameImagePOJO frameImagePOJO = new FrameImagePOJO(pulseImage.pulseTime());
        // Add detectors
        for (int i = 0; i < pulseImage.detectorIdLength(); i++) {
            int detId = (int) pulseImage.detectorId(i);
            int count = (int) pulseImage.detectionCount(i);
            frameImagePOJO.setFrequency(detId, count);
        }
        return frameImagePOJO;
    }

    @Override
    public void close() {


    }

    @Override
    public void configure(final Map map, final boolean b) {

    }
}
