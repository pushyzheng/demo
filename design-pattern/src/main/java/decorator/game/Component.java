package decorator.game;

/**
 * @author Pushy
 * @since 2018/11/15 14:48
 */
public abstract class Component {

    String name = "Unknown Component";

    public String getName() {
        return name;
    }

    public abstract double count();

}
