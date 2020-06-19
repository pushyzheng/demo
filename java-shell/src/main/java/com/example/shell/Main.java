package com.example.shell;

import com.example.shell.exception.CommandNotFoundException;
import com.example.shell.exception.InvalidFileInputException;

import java.io.IOException;

/**
 * @author Pushy
 * @since 2020/6/7
 */
public class Main {

    public static void main(String[] args) {
        boolean flag = true;
        while (flag) {
            try {
                flag = Controller.handleCommand();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CommandNotFoundException e) {
                System.out.println(e.getCmd() + ": command not found");
            } catch (InvalidFileInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
