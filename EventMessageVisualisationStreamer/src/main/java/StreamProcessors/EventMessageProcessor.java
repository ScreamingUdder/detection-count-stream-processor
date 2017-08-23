package StreamProcessors;

import EventMessage.EventMessagePOJO;
import Image.PulseImagePOJO;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.ArrayList;


/**
 * Created by ISIS, STFC on 14/08/2017.
 */
public class EventMessageProcessor implements Processor<String, EventMessagePOJO> {

    private ProcessorContext context;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        // keep the processor context locally because we need it in punctuate() and commit()
        this.context = context;
    }

    @Override
    public void process(String key, EventMessagePOJO eventMessage) {
        PulseImagePOJO frameImagePOJO = new PulseImagePOJO(eventMessage.getPulseTime());
        ArrayList<Integer> detections = eventMessage.getDetectors();

        for (int detector : detections) {
            frameImagePOJO.incrementFrequency(detector);
        }
        context.forward(frameImagePOJO.getPulseTime(), frameImagePOJO);
        // commit the current processing progress
        context.commit();
    }

    @Override
    public void punctuate(long timestamp) {

    }

    @Override
    public void close() {
        // close any resources managed by this processor.
        // Note: Do not close any StateStores as these are managed
        // by the library
    }
}
