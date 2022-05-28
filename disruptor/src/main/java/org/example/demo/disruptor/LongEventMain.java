package org.example.demo.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LongEventMain {

    private static final int BUFFER_SIZE = 1;

    private static final EventHandler<LongEvent> EVENT_HANDLER = new LongEventHandler();

    public static void main(String[] args) throws InterruptedException {
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new,
                BUFFER_SIZE,                   // buffer size
                DaemonThreadFactory.INSTANCE,  // thread factory
                ProducerType.SINGLE,           // producer type
                new BlockingWaitStrategy());   // wait strategy

        disruptor.handleEventsWith(EVENT_HANDLER);  // consumer
        disruptor.setDefaultExceptionHandler(new LongEventExceptionHandler());
        disruptor.start();

        startProducer(disruptor);
    }

    private static void startProducer(Disruptor<LongEvent> disruptor) throws InterruptedException {
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        Random rand = new Random();
        //noinspection InfiniteLoopStatement
        while (true) {
            ringBuffer.publishEvent((event, sequence, buffer) -> {
                event.setValue(rand.nextLong());
            });
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
