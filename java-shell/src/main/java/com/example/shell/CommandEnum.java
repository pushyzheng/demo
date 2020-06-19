package com.example.shell;


/**
 * @author Pushy
 */
public enum CommandEnum {

    CAT("cat"),
    WC("wc"),
    GREP("grep"),
    EXIT("exit");

    public String value;

    CommandEnum(String value) {
        this.value = value;
    }

    public static boolean contains(String cmd) {
        for (CommandEnum v : CommandEnum.values()) {
            if (cmd.equals(v.value)) {
                return true;
            }
        }
        return false;
    }
}
