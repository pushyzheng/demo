package decorator.io.custom.plus;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Pushy
 * @since 2018/11/15 17:34
 */
public class PlusInputStream extends FilterInputStream {

    protected PlusInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1 ? c : c + 1);
    }
}
