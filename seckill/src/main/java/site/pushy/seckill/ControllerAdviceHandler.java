package site.pushy.seckill;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Pushy
 * @since 2019/3/3 18:55
 */
@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String runtimeException(RuntimeException e) {
//        e.printStackTrace();
        return e.getMessage();
    }

}
