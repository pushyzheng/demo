package site.pushy.mvp.login;

import site.pushy.mvp.http.ServerException;

public class LoginContract {

    interface View {

        void setLoading(boolean v);

        void callback(boolean v);

        void errorCallback(ServerException e);

    }

    interface Presenter {

        void login(String name, String password);

    }


}
