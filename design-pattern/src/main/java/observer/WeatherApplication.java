package observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Pushy
 * @since 2018/11/13 17:43
 */
public class WeatherApplication implements Observer {

    public WeatherApplication(Observable observable) {
        observable.addObserver(this);
    }

    public void update(Observable o, Object arg) {
        if (o instanceof Weather) {
            Weather weather = (Weather) o;
            float updateTemp = weather.getTemperature();
            System.out.println("WeatherApplication got the new temperature => " + updateTemp);
        }
    }
}
