package site.pushy.mvp.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 统一生成接口实例的管理类
 */
public class RetrofitServiceManager {

    private static final String BASE_URL = "http://192.168.0.164:8360";
    private static final int DEFAULT_TIME_OUT = 5;  //超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;

    private Retrofit mRetrofit;

    public RetrofitServiceManager() {
        // 初始化OkHttpClient对象，并配置相关的属性
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) // 支持Gson自动解析JSON
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // 支持RxJava
                .build();
    }

    private static class SingletonHolder{
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    public static RetrofitServiceManager getInstance() {
        // 返回一个单例对象
        return SingletonHolder.INSTANCE;
    }

    public <T> T create(Class<T> service) {
        // 返回Retrofit创建的接口代理类
        return mRetrofit.create(service);
    }

}
