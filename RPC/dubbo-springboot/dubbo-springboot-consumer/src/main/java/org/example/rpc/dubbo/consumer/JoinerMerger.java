package org.example.rpc.dubbo.consumer;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.dubbo.rpc.cluster.Merger;

import java.util.List;

public class JoinerMerger implements Merger<String> {
    public static final JoinerMerger INSTANCE = new JoinerMerger();

    private static final Joiner JOINER = Joiner.on(",").skipNulls();

    @Override
    public String merge(String... items) {
        return JOINER.join(items);
    }

    public List<String> split(String result) {
        return Splitter.on(",").splitToList(result);
    }
}
