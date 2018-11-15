package decorator.io;

import java.io.*;

/**
 * @author Pushy
 * @since 2018/11/15 17:22
 */
public class IODemo {

    public static void main(String[] args) throws IOException {

        byte[] bytes = {1,2,3};

        InputStream bas = new ByteArrayInputStream(bytes);

        InputStream bfs = new BufferedInputStream(bas);

        PushbackInputStream pushbackInputStream = new PushbackInputStream(bfs);

        int c;
        while ((c = pushbackInputStream.read()) > 0) {
            System.out.print((char) c);
        }
        pushbackInputStream.close();

    }

}
