package decorator.io.custom.lowercase;

import java.io.*;

/**
 * @author Pushy
 * @since 2018/11/15 12:23
 */
public class Test {

    public static void main(String[] args) {

        int c;
        try {
            InputStream in = new LowerCaseInputStream(
                    new BufferedInputStream(
                            new FileInputStream(new File("E:\\test.txt"))));

            while ((c = in.read()) > 0) {
                System.out.print((char) c);
            }
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
