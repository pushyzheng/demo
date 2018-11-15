package decorator.game;

/**
 * @author Pushy
 * @since 2018/11/15 15:08
 */
public class AdidasPants extends ClothesDecorator {

    private Component component;

    public AdidasPants(Component component) {
        this.component = component;
    }

    @Override
    public String getName() {
        return component.getName() + " 穿着Adidas裤子";
    }

    @Override
    public double count() {
        return component.count() + 10;
    }

}
