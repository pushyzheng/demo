package com.example.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pushy
 * @since 2019/3/19 20:37
 */
public class HttpSession {

    private String id;

    private Map<String, Object> attributes = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void addAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public String getId() {
        return id;
    }

}
