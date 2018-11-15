package decorator.starbuzz;

/**
 * @author Pushy
 * @since 2018/11/15 12:32
 */
public class Espresso extends Beverage {

    public Espresso() {
        this.description = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }

}
