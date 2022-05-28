package org.example.demo.disruptor;

import com.lmax.disruptor.ExceptionHandler;

public class LongEventExceptionHandler implements ExceptionHandler<LongEvent> {

    @Override
    public void handleEventException(Throwable ex, long sequence, LongEvent event) {
        System.out.println("handle event exception: " + sequence);
        ex.printStackTrace();
    }

    @Override
    public void handleOnStartException(Throwable ex) {
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
    }
}
