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
public final class ExampleAccumulatedImageConsumer {

    private ExampleAccumulatedImageConsumer() {

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
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "100");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", AccumulatedImageDeserialiser.class.getName());

        KafkaConsumer<String, AccumulatedImagePOJO> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(topicName));

        System.out.println("Subscribed to topic " + topicName);
        for (int i = 0; i < 10000; i++) {
            ConsumerRecords<String, AccumulatedImagePOJO> records = consumer.poll(100);
            for (ConsumerRecord<String, AccumulatedImagePOJO> record: records) {

                AccumulatedImagePOJO accumulatedImagePOJO = record.value();

                System.out.println("First Pulse Time: " + accumulatedImagePOJO.getFirstPulseTime());
                System.out.println("Pulse Time: " + accumulatedImagePOJO.getPulseTime());
                String mapString = "Detectors: ";
                Object[] keys = accumulatedImagePOJO.getImage().navigableKeySet().toArray();

                for (Object key: keys) {
                    long detectorId = (long) key;
                    long count = accumulatedImagePOJO.getFrequency(detectorId);
                    mapString += detectorId + ":" + count + ", ";
                }

                System.out.println(mapString);
            }
        }
    }
}
