package com.example.session;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Pushy
 * @since 2019/3/19 20:37
 */
public class SessionManager {

    private static final HashMap<String, HttpSession> sessionMap = new HashMap<>();

    /**
     * 注册Session，并返回新注册的 session id
     */
    public static HttpSession addSession() {
        String sessionId = getSessionId();
        synchronized (sessionMap) {
            HttpSession session = new HttpSession(sessionId);
            sessionMap.put(sessionId, session);
            return session;
        }
    }

    /**
     * 判断当前服务端是否有该 session id 的记录
     */
    public static boolean containsSession(String sessionId){
        synchronized (sessionMap) {
            return sessionMap.containsKey(sessionId);
        }
    }

    public static HttpSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    private static String getSessionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
