package org.example.demo.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Maps;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class FirebaseAppRegistry {

    static {
        FirebaseOptions options;
        try {
            options = FirebaseOptions.builder()
                    .setCredentials(createFromFile("/Users/pushyzheng/Documents/serviceAccountKey.json"))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FirebaseApp.initializeApp(options);
    }

    public static FirebaseApp getDefault() {
        return get(FirebaseApp.DEFAULT_APP_NAME);
    }

    public static FirebaseApp get(String name) {
        return FirebaseApp.getInstance(name);
    }

    /**
     * 默认的方式, 会从环境变量读取 GOOGLE_APPLICATION_CREDENTIALS
     */
    private static GoogleCredentials createDefault() throws IOException {
        return GoogleCredentials.getApplicationDefault();
    }

    /**
     * 从文件中读取
     */
    private static GoogleCredentials createFromFile(String filename) throws IOException {
        return GoogleCredentials.fromStream(new FileInputStream(filename));
    }

    /**
     * 传递动态字符串, 将其转换成 {@link java.io.InputStream}
     * <p>
     * 比较适合拓展
     */
    private static GoogleCredentials createFromString(String s) throws IOException {
        return GoogleCredentials.fromStream(
                new ByteArrayInputStream(s.getBytes()));
    }
}
