package observer;

import java.util.Observable;

/**
 * @author Pushy
 * @since 2018/11/13 17:16
 */
public class Weather extends Observable {

    private float temperature;

    public void setTemperature(float temperature) {
        this.temperature = temperature;
        handleChange();
    }

    public float getTemperature() {
        return temperature;
    }

    private void handleChange() {
        setChanged();
        notifyObservers();
    }

}
