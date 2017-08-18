package ExampleStreamConsumers;

import Image.FrameImageDeserialiser;
import Image.FrameImagePOJO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Kafka Consumer For Detection Event Messages.
 */
public final class FrameImageConsumer {

    private static final int DEFAULT_TIMEOUT = 100;
    private static final int RECORD_LIMIT = 1000;
    private static final int DETECTOR_LIMIT = 20;
    // How many detector ids to print out

    private FrameImageConsumer() {

    }
    /**
     * Main function for kafka consumer.
     * <p>
     * Connects through sakura to collect all frame images and display the accumulated image generated from them.
     * </p>
     * @param args Topic name to consume from.
     * @throws Exception Generic exception
     */
    public static void main(final String[] args) throws Exception {
        // Enter the source topic as a program argument.
        if (args.length == 0) {
            System.out.println("Enter topic name:");
        }

        String topicName =  args[0];
        Properties props = new Properties();

        props.put("bootstrap.servers", "sakura:9092");
        props.put("group.id", "ExampleFrameImageConsumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "100");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", FrameImageDeserialiser.class.getName());

        KafkaConsumer<String, FrameImagePOJO> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(topicName));

        System.out.println("Subscribed to topic " + topicName);
        for (int i = 0; i < RECORD_LIMIT; i++) {
            ConsumerRecords<String, FrameImagePOJO> records = consumer.poll(DEFAULT_TIMEOUT);
            for (ConsumerRecord<String, FrameImagePOJO> record: records) {

                FrameImagePOJO frameImagePOJO = record.value();

                System.out.println("Pulse Time: " + frameImagePOJO.getPulseTime());
                String mapString = "Detectors: ";
                Object[] keys = frameImagePOJO.getImage().navigableKeySet().toArray();

                int z = 0;

                for (Object key: keys) {
                    if (z >= DETECTOR_LIMIT) {
                        break;
                    }
                    long detectorId = (long) key;
                    long count = frameImagePOJO.getFrequency(detectorId);
                    mapString += detectorId + ":" + count + ", ";
                    z++;
                }

                System.out.println(mapString);
            }
        }
    }
}
