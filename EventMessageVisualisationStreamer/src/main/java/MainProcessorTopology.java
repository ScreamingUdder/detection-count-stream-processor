import EventMessage.EventMessageDeserializer;
import Image.AccumulatedImageSerializer;
import Image.PulseImageSerializer;
import StreamProcessors.EventMessageProcessor;
import StreamProcessors.PulseImageProcessor;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * Created by ISIS, STFC on 14/08/2017.
 */
public class MainProcessorTopology {
    @Parameter(names = {"--app-id", "-app"}, description = "unique identifier for the application")
    private static String APPID = "eventMessage-visualisation-streamer";
    @Parameter(names = {"--bootstrap", "-b"}, description = "bootstrap server and port to connect to")
    private static String BOOTSTRAP;
    @Parameter(names = {"--zookeeper", "-z"}, description = "zookeeper server and port to connect to")
    private static String ZOOKEEPER;
    // Topic arguments
    @Parameter(names = {"--source-topic", "-src"}, description = "kafka topic to receive EventMessages from")
    private static String SOURCE_TOPIC;
    @Parameter(names = {"--pulse-topic", "-pt"}, description = "output topic for the output of EventMessageProcessor")
    private static String PULSE_TOPIC;
    @Parameter(names = {"--accumulated-topic", "-at"}, description = "output topic for the output of PulseImageProcessor")
    private static String ACCUMULATED_TOPIC;

    @Parameter(names = {"--process-pulse-images", "-ppi"}, description = "Boolean for whether pulse images created by the EventMessageProcessor are further processed by PulseImageProcessor, true by default.", arity = 1)
    private boolean PROCESS_PULSE_IMAGES;

    public static void main(String ... args) throws Exception {
        MainProcessorTopology main = new MainProcessorTopology();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    public void run() {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP);
        streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, ZOOKEEPER);

        StringDeserializer stringDeserializer = new StringDeserializer();

        TopologyBuilder builder = new TopologyBuilder();

        builder.addSource("source",stringDeserializer, new EventMessageDeserializer(), SOURCE_TOPIC)
                .addProcessor("pulseImage", EventMessageProcessor::new, "source")
                .addSink("pulseSink" , PULSE_TOPIC, new LongSerializer(), new PulseImageSerializer(), "pulseImage");

        if (PROCESS_PULSE_IMAGES) {
            builder.addProcessor("accumulatedImage", PulseImageProcessor::new, "pulseImage")
                    .addSink("accSink", ACCUMULATED_TOPIC, new LongSerializer(), new AccumulatedImageSerializer(), "accumulatedImage");
        }

        KafkaStreams streaming = new KafkaStreams(builder, streamsConfiguration);
        streaming.start();
    }

}
