package site.pushy.mvp.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import site.pushy.mvp.R;
import site.pushy.mvp.http.ServerException;
import site.pushy.mvp.http.ServerError;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {

    private LoginPresenter presenter;
    private ProgressDialog mDialog;

    private EditText etName;
    private EditText etPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();

        presenter = new LoginPresenter(this, new LoginModel());
    }

    private void initWidget() {
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setLoading(boolean v) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("正在加载 ...");
            mDialog.setCanceledOnTouchOutside(false);
        }
        if (v) {
            mDialog.show();
        } else {
            mDialog.hide();
        }
    }

    @Override
    public void callback(boolean v) {
        if (v) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void errorCallback(ServerException e) {
        switch (e.code) {
            case ServerError.NO_USER:
                Toast.makeText(this, "没有该用户", Toast.LENGTH_LONG).show();
                break;
            case ServerError.UNAUTHORIZED:
                Toast.makeText(this, "密码错误", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                presenter.login(etName.getText().toString(), etPassword.getText().toString());
                break;
        }
    }
}
