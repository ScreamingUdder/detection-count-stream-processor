package Image;

/**
 * Image Java interface for storing image data in a static context
 * Created by ISIS, STFC on 27/07/2017.
 */
public interface ImageInterface {

    void setPulseTime(long newPulseTime);
    long getPulseTime();

    int getImageSize();

    int[] getImage();
    int getFrequency(int detector);
    void setFrequency(int detector, int newFrequency);
    void incrementFrequency(int detector);

}
