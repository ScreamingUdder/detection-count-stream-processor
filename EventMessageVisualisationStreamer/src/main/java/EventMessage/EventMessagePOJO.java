package EventMessage;

import java.util.ArrayList;

/**
 * POJO for the Event Message, designed for easier use when not being sent over Kafka.
 * Created by ISIS, STFC on 07/06/2017.
 */
public class EventMessagePOJO {
    private int messageId;
    private long pulseTime;
    private ArrayList<Integer> detectors;

    /**
     *
     * @param messageId Detection Event Message ID.
     * @param pulseTime Time since pulse
     *
     */
    public EventMessagePOJO(final int messageId, final long pulseTime) {
        this.messageId = messageId;
        this.pulseTime = pulseTime;
        detectors = new ArrayList<>();
    }

    /**
     * Set the message id.
     * @param messageId Detection Event Message ID
     */
    public void setMessageId(final int messageId) {
        this.messageId = messageId;
    }

    /**
     * Get the message id.
     * @return Detection Event Message ID
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Set the pulse time.
     * @param pulseTime Time since pulse
     */
    public void setPulseTime(final long pulseTime) {
        this.pulseTime = pulseTime;
    }

    /**
     * Get the pulse time.
     * @return Time since pulse
     */
    public long getPulseTime() {
        return pulseTime;
    }

    /**
     * Set the detectors.
     * @param detectors The detectors of the Detection Event Message
     */
    public void setDetectors(final ArrayList detectors) {
        this.detectors = detectors;
    }

    /**
     * Get the detectors.
     * @return The detectors of the Detection Event Message
     */
    public ArrayList<Integer> getDetectors() {
        return detectors;
    }

    /**
     * Add a detector to the detector event message.
     * @param detector The Detector ID
     */
    public void addDetector(final int detector) {
        detectors.add(detector);
    }

    /**
     * Get a detector from the Detector Event Message.
     * @param index The index of the detectors array, not the detector ID
     * @return The ID of the detector
     */
    public int getDetector(final int index) {
        return detectors.get(index);
    }
}
