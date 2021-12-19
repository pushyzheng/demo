package org.example.rpc.service;

import java.util.List;

public interface ResourceService {

    /**
     * Get the resource name of current environment
     */
    String getName();

    List<String> getHostNameList();
}
