package com.example.shell.exception;

/**
 * @author Pushy
 * @since 2020/6/7
 */
public class CommandNotFoundException extends RuntimeException {

    private String cmd;

    public CommandNotFoundException(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }
}
