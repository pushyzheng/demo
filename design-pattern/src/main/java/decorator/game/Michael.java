package decorator.game;

/**
 * @author Pushy
 * @since 2018/11/15 14:51
 */
public class Michael extends Component {

    public Michael() {
        this.name = "Michael";
    }

    @Override
    public double count() {
        return 20;
    }
}
