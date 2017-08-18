package Image;

import java.util.Map;

/**
 * Converts AccumulatedImage to AccumulatedImagePOJO
 * Created by ISIS, STFC on 04/08/2017.
 */
public class AccumulatedImageDeserialiser implements org.apache.kafka.common.serialization.Deserializer<AccumulatedImagePOJO> {
    /**
     * Function for converting between AccumulatedImage Flatbuffer and POJO
     * @param bytes The AccumulatedImage byte array to be converted
     * @return AccumulatedImagePOJO
     */
    public AccumulatedImagePOJO deserialize(final String topic, final byte[] bytes) {
        // convert byte array to java flatbuffer object
        AccumulatedImage accumulatedImage = AccumulatedImage.getRootAsAccumulatedImage(java.nio.ByteBuffer.wrap(bytes));
        // Assign simple attributes
        long firstPulseTime = accumulatedImage.firstPulseTime();
        AccumulatedImagePOJO accumulatedImagePOJO = new AccumulatedImagePOJO(firstPulseTime);
        accumulatedImagePOJO.setPulseTime(accumulatedImage.pulseTime());
        // Add detectors
        for (int i = 0; i < accumulatedImage.detectorIdLength(); i++) {
            int detId = (int) accumulatedImage.detectorId(i);
            int count = (int) accumulatedImage.detectionCount(i);
            accumulatedImagePOJO.setFrequency(detId, count);
        }
        return accumulatedImagePOJO;
    }

    @Override
    public void close() {


    }

    @Override
    public void configure(final Map map, final boolean b) {

    }
}
