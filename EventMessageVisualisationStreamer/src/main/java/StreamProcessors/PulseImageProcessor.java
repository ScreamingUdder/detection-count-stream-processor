package StreamProcessors;

import Image.AccumulatedImagePOJO;
import Image.PulseImagePOJO;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;


/**
 * Created by ISIS, STFC on 14/08/2017.
 */
public class PulseImageProcessor implements Processor<Long, PulseImagePOJO> {

    private ProcessorContext context;
    private AccumulatedImagePOJO accumulatedImagePOJO;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        // keep the processor context locally because we need it in punctuate() and commit()
        this.context = context;
    }

    @Override
    public void process(Long pulse, PulseImagePOJO frameImagePOJO) {
        if (accumulatedImagePOJO == null) {
            accumulatedImagePOJO = new AccumulatedImagePOJO(frameImagePOJO.getPulseTime());
        }

        accumulatedImagePOJO.addFrameImage(frameImagePOJO);

        context.forward(accumulatedImagePOJO.getPulseTime(), accumulatedImagePOJO);
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
