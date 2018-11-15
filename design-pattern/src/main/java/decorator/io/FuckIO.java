package decorator.io;

import java.io.*;

/**
 * @author Pushy
 * @since 2018/11/15 15:18
 */
public class FuckIO {

    public static void main(String[] args) throws FileNotFoundException {

        new PushbackInputStream(
                new BufferedInputStream(
                        new FileInputStream(new File("/usr/xxx"))));
    }

}
