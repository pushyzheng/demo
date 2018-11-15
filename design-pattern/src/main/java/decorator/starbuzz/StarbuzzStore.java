package decorator.starbuzz;

/**
 * @author Pushy
 * @since 2018/11/15 12:33
 */
public class StarbuzzStore {

    public static void main(String[] args) {

        Beverage espresso = new Espresso();

        Beverage espressoWithmocha = new Mocha(espresso);
        Beverage espressoWithmochaWithwhip = new Whip(espressoWithmocha);

        double cost = espressoWithmochaWithwhip.cost();
        String desc = espressoWithmochaWithwhip.getDescription();

        System.out.println(cost);
        System.out.println("desc: " + desc);
    }

}
