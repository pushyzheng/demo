package decorator.game;

/**
 * @author Pushy
 * @since 2018/11/15 15:01
 */
public class PumaCoat extends ClothesDecorator {

    private Component component;

    public PumaCoat(Component component) {
        this.component = component;
    }

    @Override
    public String getName() {
        return component.getName() + " 穿着Puma上衣";
    }

    @Override
    public double count() {
        return component.count() + 8;
    }
}
