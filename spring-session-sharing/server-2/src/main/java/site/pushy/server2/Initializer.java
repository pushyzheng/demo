package site.pushy.server2;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * @author Pushy
 * @since 2019/2/27 11:05
 */
public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(SessionConfig.class);
    }

}
