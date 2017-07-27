package Image;

/**
 * Image Java interface for storing image data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public interface ImageInterface {
    long pulseTime = 0L;
    int[] image = {};


    void setPulseTime(long newPulseTime);
    long getPulseTime();

    int getImageSize();

    int getFrequency(int detector);
    int setFrequency(int detector, int newFrequency);

}
