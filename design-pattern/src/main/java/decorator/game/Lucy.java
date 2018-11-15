package decorator.game;

/**
 * @author Pushy
 * @since 2018/11/15 14:52
 */
public class Lucy extends Component {

    public Lucy() {
        this.name = "Lucy";
    }

    @Override
    public double count() {
        return 25;
    }
}
