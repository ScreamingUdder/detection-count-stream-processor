import EventMessage.EventMessageDeserializer;
import Image.AccumulatedImageSerializer;
import Image.FrameImageSerializer;
import StreamProcessors.EventMessageProcessor;
import StreamProcessors.FrameImageProcessor;
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


    public static void main(String[] args) throws Exception {

        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "eventMessage-visualisation-streamer");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "sakura:9092");
        streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "shizune:2181");

        StringDeserializer stringDeserializer = new StringDeserializer();

        TopologyBuilder builder = new TopologyBuilder();
        builder.addSource("SOURCE",stringDeserializer, new EventMessageDeserializer(), "DCTestTopic_events")
            .addProcessor("FRAMEIMAGE", EventMessageProcessor::new, "SOURCE")
            .addProcessor("ACCUMULATEDIMAGE", FrameImageProcessor::new, "FRAMEIMAGE")
            .addSink("FRAMESINK" , "FrameImageTest", new LongSerializer(), new FrameImageSerializer(), "FRAMEIMAGE")
            .addSink("ACCSINK", "AccumulatedImageTest", new LongSerializer(), new AccumulatedImageSerializer(), "ACCUMULATEDIMAGE");

        KafkaStreams streaming = new KafkaStreams(builder, streamsConfiguration);
        streaming.start();

    }

}
