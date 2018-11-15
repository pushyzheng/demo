package decorator.io.custom.plus;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Pushy
 * @since 2018/11/15 17:35
 */
public class Test {

    public static void main(String[] args) throws IOException {
        byte[] bytes = {1, 2, 3};

        InputStream is = new PlusInputStream(
                new BufferedInputStream(
                        new ByteArrayInputStream(bytes)));

        int c;
        while ((c = is.read()) > 0) {
            System.out.print(c);
        }
        is.close();
    }

}
