package com.example.chapter05;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter05.utils.ViewUtil;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_login_password;
    private CheckBox cb_remember;
    private Button btn_forget;
    private EditText et_password;
    private EditText et_phone;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    //    测试使用的密码，在代码成型后需删除
    private String textPassword = "123456";
    //    测试使用的验证码，在代码成型后需删除
    private String textVerifycode = "123456";
    private RadioButton rb_password;
    private RadioButton rb_verifycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        RadioGroup rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        tv_login_password = findViewById(R.id.tv_login_password);
        cb_remember = findViewById(R.id.cb_remember);
        btn_forget = findViewById(R.id.btn_forget);
        et_password = findViewById(R.id.et_password);
        et_phone = findViewById(R.id.et_phone);
        btn_login = findViewById(R.id.btn_login);
//        给RadioGroup设置监听器，使用setOnCheckedChangeListener方法
        rg_login.setOnCheckedChangeListener(this);
//         给手机号码输入框设置监听器，使用addTextChangedListener方法
        et_phone.addTextChangedListener(new HindTextWatcher(et_phone, getMaxLength(et_phone)));
        et_password.addTextChangedListener(new HindTextWatcher(et_password, getMaxLength(et_password)));
        btn_forget.setOnClickListener(this);
//      使用registerForActivityResult方法,第一个参数为ActivityResultContracts.StartActivityForResult()方法，
//      第二个参数为回调函数，使用lambda表达式实现回调函数，第三个参数为回调函数的返回值
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });
//
        btn_login.setOnClickListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        如果是密码登录，显示密码登录的布局，隐藏验证码登录的布局
        if (checkedId == R.id.rb_password) {
            tv_login_password.setText(getText(R.string.login_password));
            cb_remember.setVisibility(View.VISIBLE);
            btn_forget.setText(getText(R.string.forget_password));
            et_password.setHint(getText(R.string.input_password));
//            设置密码输入框的最大输入长度为16
            InputFilter[] filters = {new InputFilter.LengthFilter(16)};
            et_password.setFilters(filters);
//            给密码输入框设置监听器，使用addTextChangedListener方法
//            并且修改密码输入框的最大输入长度为16
            et_password.addTextChangedListener(new HindTextWatcher(et_password, getMaxLength(et_password)));
            et_password.setText("");
        }
//        如果是验证码登录，显示验证码登录的布局，隐藏密码登录的布局
        if (checkedId == R.id.rb_verifycode) {
            tv_login_password.setText(getText(R.string.verifycode));
            cb_remember.setVisibility(View.GONE);
            btn_forget.setText(getText(R.string.get_verifycode));
            et_password.setHint(getText(R.string.input_verifycode));
//            设置验证码入框的最大输入长度为6
            InputFilter[] filters = {new InputFilter.LengthFilter(6)};
            et_password.setFilters(filters);
//            给验证码设置监听器，使用addTextChangedListener方法
//            并且验证码输入框的最大输入长度为6
            et_password.addTextChangedListener(new HindTextWatcher(et_password, getMaxLength(et_password)));
            et_password.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();

        if (v.getId() == R.id.btn_forget) {
            if (phone.length() < 11) {
//                弹出提示信息,使用Toast类的makeText方法,使用show方法显示,使用LENGTH_SHORT参数设置显示时长
                Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (btn_forget.getText().equals(getText(R.string.forget_password))) {
//                跳转到忘记密码页面
//                使用Intent类的构造方法,第一个参数为当前页面,第二个参数为目标页面
                Intent intent = new Intent(this, LoginForgetActivity.class);
//                使用Bundle类的构造方法,创建一个Bundle对象
                Bundle bundle = new Bundle();
//                使用Bundle类的putString方法,第一个参数为键,第二个参数为值
                bundle.putString("phone", phone);
//                使用Intent类的putExtras方法,第一个参数为Bundle对象
                intent.putExtras(bundle);
//                使用register.launch方法,第一个参数为Intent对象
                register.launch(intent);

            }
            if (btn_forget.getText().equals(getText(R.string.get_verifycode))) {
//                创建一个AlertDialog.Builder对象，使用AlertDialog.Builder的构造方法，第一个参数为当前页面
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("验证码：");
                builder.setMessage("123456");
                builder.setPositiveButton("确定", null);
//                使用AlertDialog.Builder的create方法创建一个AlertDialog对象
                AlertDialog dialog = builder.create();
//                使用AlertDialog对象的show方法显示对话框
                dialog.show();
            }
        }

        if (v.getId() == R.id.btn_login) {
//            密码登录
            if (rb_password.isChecked()) {
                String password = et_password.getText().toString();
                /**
                 * 此处需要后端发送过来的密码进行比对
                 *
                 *
                 * String userPassword="";
                 *
                 *
                 */
                if (!password.equals(textPassword)) {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccess();
            }
            //验证码登录
            if (rb_verifycode.isChecked()) {
                String verifycode = et_password.getText().toString();
                /**
                 * 此处需要后端发送过来的验证码进行比对
                 *
                 *
                 * String userVerifycode="";
                 *
                 *
                 */
                if (!verifycode.equals(textVerifycode)) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccess();
            }
        }
    }

    //    登录成功后的操作
    private void loginSuccess() {
        String desc = String.format("手机号码为：%s的用户，您已登录成功", et_phone.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                跳转到主页面
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        builder.setNegativeButton("我再看看", null);
        builder.create().show();
    }


    //    定义一个内部类，用于监听手机号码输入框的输入内容
    private class HindTextWatcher implements TextWatcher {
        private EditText mView;
        private int maxLength;

        public HindTextWatcher(EditText mView, int maxLength) {
            this.mView = mView;
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        //        输入框内容的长度等于最大长度时，隐藏输入法
        @Override
        public void afterTextChanged(Editable s) {
//            获取输入框中的内容
            String phoneNumber = s.toString();
//          如果输入框中的内容长度等于最大长度，隐藏输入法
            if (phoneNumber.length() == maxLength) {
                ViewUtil.hideInputMethod(LoginMainActivity.this, mView);
            }
        }
    }


    //    定义一个内部类，用于获得文本框的最大输入长度
    public int getMaxLength(EditText et) {
        InputFilter[] filters = et.getFilters();
        for (InputFilter filter : filters) {
            if (filter instanceof InputFilter.LengthFilter) {
                int maxLength = ((InputFilter.LengthFilter) filter).getMax();
                return maxLength;
            }
        }
        return 0;
    }


}