package site.pushy.mvp.http;

public class ServerException extends RuntimeException {

    public int code;

    public String message;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
