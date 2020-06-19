package com.example.shell;

import com.example.shell.exception.CommandNotFoundException;
import com.example.shell.exception.InvalidFileInputException;
import com.example.shell.handler.CatPipeHandler;
import com.example.shell.handler.GrepPipeHandler;
import com.example.shell.handler.StdoutPipeHandler;
import com.example.shell.handler.WcPipeHandler;
import com.example.shell.utils.FileUtils;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Pushy
 * @since 2020/6/6
 */
public class Controller {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * 处理每一行命令
     */
    public static boolean handleCommand() throws IOException {
        System.out.print("JavaShell " + FileUtils.getCurrentPath() + "$ ");
        String line = sc.nextLine().trim();

        if (line.length() == 0) return true;
        if (line.equals("exit")) return false;

        handleCommand0(line);
        return true;
    }

    private static void handleCommand0(String line) throws IOException {
        PipedOutputStream head = new PipedOutputStream();
        PipedInputStream tail = new PipedInputStream();

        String[] groups = line.split("\\|");
        // 初始化输入流列表和输出流列表
        PipedInputStream[] inputArray = getInputList(groups.length);
        PipedOutputStream[] outputArray = getOutputList(groups.length);
        outputArray[0] = head;
        inputArray[inputArray.length - 1] = tail;

        for (int i = 0; i < groups.length; i++) {
            String item = groups[i];
            item = item.trim();

            String[] args = item.split(" ");
            String command = args[0];
            if (!CommandEnum.contains(command)) {
                throw new CommandNotFoundException(command);
            }
            List<String> files = getFiles(args, command);

            /*
                假设 group = [cat 1.txt, grep f]，则 inputArray 和 outputArray：
                inputArray:    cat -> grep -> tail
                outputArray:   head -> cat -> grep
                所以在获取当前指令的输入流时索引为 i，而在获取输出流时为 i + 1
             */
            PipedInputStream curInStream = inputArray[i];
            PipedOutputStream curOutStream = outputArray[i + 1];
            // 默认情况下（即下一个命令的文件参数为空时），将当前的输出流与下一个命令的输入流连接
            curOutStream.connect(inputArray[i + 1]);

            if (files.size() != 0) {
                for (String filename : files) {
                    String errMsg = FileUtils.checkFile(command, filename);
                    if (errMsg != null) {
                        throw new InvalidFileInputException(errMsg);
                    }
                }

                // 如果文件参数不为空的话，则新建一个管道输入流，与读取文件的输出流 fileStream 连接
                // 所以需要创建一个读取文件的线程，在读取完文件之后将读取的内容输出到 fileStream 流
                // 这样 curInStream 可以读取到文件的内容，然后根据不同的命令逻辑再做处理
                curInStream = new PipedInputStream();
                // 替换掉当前指令的输入流，更换为从文件读取输入
                inputArray[i] = curInStream;

                PipedOutputStream fileStream = new PipedOutputStream();
                fileStream.connect(curInStream);
                new ReadFileThread(fileStream, files).start();
            }
            Runnable runnable = getCommandRunnable(command, args, curInStream, curOutStream);
            new Thread(runnable).start();
        }

        // 将最后一个命令的输出流与负责打印在控制台的最后一个线程的输入流绑定：
        Thread stdoutThread = new Thread(new StdoutPipeHandler(tail));
        stdoutThread.start();

        try {
            stdoutThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取每个命令的参数列表
     */
    private static List<String> getFiles(String[] arr, String command) {
        int start = 1;
        if (command.equals(CommandEnum.GREP.value)) {
            start = 2;
        }
        List<String> result = new ArrayList<>();
        if (arr.length > 1) {
            for (int i = start; i < arr.length; i++) {
                result.add(arr[i].trim());
            }
        }
        return result;
    }

    /**
     * 根据命令来返回指定的 PipeHandler
     */
    private static Runnable getCommandRunnable(String cmd, String[] args,
                                               PipedInputStream in, PipedOutputStream out) {
        if (cmd.equals(CommandEnum.CAT.value)) {
            return new CatPipeHandler(in, out);
        } else if (cmd.equals(CommandEnum.WC.value)) {
            return new WcPipeHandler(in, out);
        } else if (cmd.equals(CommandEnum.GREP.value)) {
            // grep 的第二个参数为匹配的关键字
            String keyword = args[1];
            return new GrepPipeHandler(in, out, keyword);
        }
        return null;
    }

    /**
     * 初始化输入流列表
     */
    private static PipedInputStream[] getInputList(int groupLength) {
        PipedInputStream[] result = new PipedInputStream[groupLength + 1];
        for (int i = 0; i < groupLength; i++) {
            result[i] = new PipedInputStream();
        }
        return result;
    }

    /**
     * 初始化输出流列表
     */
    private static PipedOutputStream[] getOutputList(int groupLength) {
        PipedOutputStream[] result = new PipedOutputStream[groupLength + 1];
        for (int i = 1; i < groupLength + 1; i++) {
            result[i] = new PipedOutputStream();
        }
        return result;
    }

}



