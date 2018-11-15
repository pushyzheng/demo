package decorator.starbuzz;

/**
 * @author Pushy
 * @since 2018/11/15 12:35
 */
public class Whip extends CondimentDecorator {

    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    public double cost() {
        return .3 + beverage.cost();
    }

}
