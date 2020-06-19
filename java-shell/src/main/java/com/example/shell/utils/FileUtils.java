package com.example.shell.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Pushy
 */
public class FileUtils {

    public static String getCurrentPath() {
        return new File("").getAbsoluteFile().getPath();
    }

    public static File[] getFiles() {
        return new File("./").listFiles();
    }

    public static void printFiles() {
        File[] files = new File("./").listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    /**
     * 检测文件是否存在，且是否为文件而不是目录，对于 cat、wc、grep 指令有效。如果是文件，将返回 @java.io.File 对象
     */
    public static String checkFile(String command, String filename) {
        File file = new File(getCurrentPath() + File.separatorChar + filename);
        // 文件不存在
        if (!file.exists()) {
            return String.format("%s: %s: No such file or directory", command, filename);
        }
        // 判断是文件还是目录
        if (file.isDirectory()) {
            return String.format("%s: %s: Is a directory", command, filename);
        }
        return null;
    }

    public static String readFile(String filename) throws IOException {
        return readFile(new File(filename));
    }

    public static String readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader bis = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        while (bis.ready()) {
            sb.append((char) bis.read());
        }
        fis.close();
        bis.close();
        return sb.toString();
    }

    public static int getLinesCount(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader bis = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));

        int result = 0;
        while (bis.ready()) {
            bis.readLine();
            result++;
        }
        fis.close();
        bis.close();
        return result;
    }

}
