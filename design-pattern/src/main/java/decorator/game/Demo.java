package decorator.game;

/**
 * @author Pushy
 * @since 2018/11/15 14:59
 */
public class Demo {

    public static void main(String[] args) {

        Component michael = new Michael();

        Component michaelWithCoat = new PumaCoat(michael);

        Component michaelWithCoatPants = new AdidasPants(michaelWithCoat);

        Component michaelWithCoatPantsShoes = new NikeShoes(michaelWithCoatPants);

        System.out.println(michaelWithCoatPantsShoes.getName() + " ，着装度为：" + michaelWithCoatPantsShoes.count());

    }

}
