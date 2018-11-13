package observer;

/**
 * @author Pushy
 * @since 2018/11/13 17:51
 */
public class Demo {

    public static void main(String[] args) {

        Weather weather = new Weather();

        new Thread(() -> {
            float start = 20f;
            while (true) {
                weather.setTemperature(start++);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        WeatherApplication application = new WeatherApplication(weather);
    }

}
