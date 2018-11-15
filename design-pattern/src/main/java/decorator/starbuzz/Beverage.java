package decorator.starbuzz;

/**
 * @author Pushy
 * @since 2018/11/15 12:28
 */
public abstract class Beverage {

    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();

}
