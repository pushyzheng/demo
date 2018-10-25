package site.pushy.mvp.login;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import site.pushy.mvp.base.BaseModel;
import site.pushy.mvp.bean.BaseResponse;
import site.pushy.mvp.bean.LoginBody;
import site.pushy.mvp.http.RetrofitServiceManager;

public class LoginModel extends BaseModel {

    private LoginModelService service;

    public LoginModel() {
        this.service = RetrofitServiceManager.getInstance().create(LoginModelService.class);
    }

    public Observable<BaseResponse<String>> login(LoginBody body) {
        return observe(service.login(body));
    }

    interface LoginModelService {

        @POST("/login")
        Observable<BaseResponse<String>> login(@Body LoginBody body);

    }

}
