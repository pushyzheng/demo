package org.example.demo.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zuqin.zheng
 */
public class SpyTest {

    /**
     * 只代理部分的方法
     * <p>
     * 其实没代理的方法委托给原对象
     */
    @Test
    public void spyArrayList() {
        List<Integer> list = new ArrayList<>();
        List<Integer> mockList = Mockito.spy(list);

        Mockito.when(mockList.size()).thenReturn(100);

        Assertions.assertEquals(100, mockList.size());
    }
}
