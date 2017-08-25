package ExampleStreamConsumers;

import Image.PulseImageDeserialiser;
import Image.PulseImagePOJO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Kafka Consumer For Detection Event Messages.
 */
public final class PulseImageConsumer {

    private static final int DEFAULT_TIMEOUT = 100;
    private static final int RECORD_LIMIT = 10000;
    private static final int DETECTOR_LIMIT = 20;
    // How many detector ids to print out

    private PulseImageConsumer() {

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
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "latest");
        props.put("enable.auto.offset.store", "false");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", PulseImageDeserialiser.class.getName());

        KafkaConsumer<String, PulseImagePOJO> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(topicName));

        System.out.println("Subscribed to topic " + topicName);
        for (int i = 0; i < RECORD_LIMIT; i++) {
            ConsumerRecords<String, PulseImagePOJO> records = consumer.poll(DEFAULT_TIMEOUT);
            for (ConsumerRecord<String, PulseImagePOJO> record: records) {

                PulseImagePOJO frameImagePOJO = record.value();

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
