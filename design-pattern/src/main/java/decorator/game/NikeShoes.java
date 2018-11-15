package decorator.game;

/**
 * @author Pushy
 * @since 2018/11/15 14:54
 */
public class NikeShoes extends ClothesDecorator {

    private Component component;

    public NikeShoes(Component component) {
        this.component = component;
    }

    @Override
    public String getName() {
        return component.getName() + " 穿着Nike鞋";
    }

    @Override
    public double count() {
        return component.count() + 15;
    }
}
