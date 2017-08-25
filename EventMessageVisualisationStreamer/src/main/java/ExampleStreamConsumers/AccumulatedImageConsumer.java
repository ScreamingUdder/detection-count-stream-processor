package ExampleStreamConsumers;

import Image.AccumulatedImageDeserialiser;
import Image.AccumulatedImagePOJO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Kafka Consumer For Detection Event Messages.
 */
public final class AccumulatedImageConsumer {

    private static final int DEFAULT_TIMEOUT = 100;
    private static final int RECORD_LIMIT = 1000;
    private static final int DETECTOR_LIMIT = 200;
    // How many detector ids to print out

    private AccumulatedImageConsumer() {

    }
    /**
     * Main function for kafka consumer.
     * <p>
     * Connects through sakura to collect all event messages from the specified topic,and display a frame image generated from it.
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
        props.put("value.deserializer", AccumulatedImageDeserialiser.class.getName());

        KafkaConsumer<String, AccumulatedImagePOJO> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(topicName));

        System.out.println("Subscribed to topic " + topicName);
        for (int i = 0; i < RECORD_LIMIT; i++) {
            ConsumerRecords<String, AccumulatedImagePOJO> records = consumer.poll(DEFAULT_TIMEOUT);
            for (ConsumerRecord<String, AccumulatedImagePOJO> record: records) {

                AccumulatedImagePOJO accumulatedImagePOJO = record.value();

                System.out.println("First Pulse Time: " + accumulatedImagePOJO.getFirstPulseTime());
                System.out.println("Pulse Time: " + accumulatedImagePOJO.getPulseTime());
                String mapString = "Detectors: ";
                Object[] keys = accumulatedImagePOJO.getImage().navigableKeySet().toArray();
                
                int z = 0;

                for (Object key: keys) {
                    if (z >= DETECTOR_LIMIT) {
                        break;
                    }
                    long detectorId = (long) key;
                    long count = accumulatedImagePOJO.getFrequency(detectorId);
                    mapString += detectorId + ":" + count + ", ";
                    z++;
                }

                System.out.println(mapString);
            }
        }
    }
}
