package com.example.chapter05;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter05.utils.GetViewMaxLength;
import com.example.chapter05.utils.HindTextWatcher;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_phone;
    private EditText newPassword;
    private EditText confirmPassword;
    private EditText verifycode;
    private Button btn_verifycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
//        获取上一个页面传递的手机号码
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
//        获取控件
        et_phone = findViewById(R.id.et_phone);
        newPassword = findViewById(R.id.et_input_new_password);
        confirmPassword = findViewById(R.id.et_confirm_new_password);
        verifycode = findViewById(R.id.et_verifycode);
        btn_verifycode = findViewById(R.id.btn_get_verifycode);
//        给手机号码输入框设置文本
        et_phone.setText(phone);
//        给手机号码输入框设置监听器，使用addTextChangedListener方法
        et_phone.addTextChangedListener(new HindTextWatcher(LoginForgetActivity.this, et_phone, GetViewMaxLength.getMaxLength(et_phone)));
        newPassword.addTextChangedListener(new HindTextWatcher(LoginForgetActivity.this, newPassword, GetViewMaxLength.getMaxLength(newPassword)));
        confirmPassword.addTextChangedListener(new HindTextWatcher(LoginForgetActivity.this, confirmPassword, GetViewMaxLength.getMaxLength(confirmPassword)));
        verifycode.addTextChangedListener(new HindTextWatcher(LoginForgetActivity.this, verifycode, GetViewMaxLength.getMaxLength(verifycode)));


        findViewById(R.id.btn_ok).setOnClickListener(this);
        btn_verifycode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String verifyCode="123456";
        /**
         * 验证码需要从服务器获取
         *
         *
         *
         */
        String password="123456";
        /**
         * 密码需要从后端获取
         */
        if (v.getId() == R.id.btn_get_verifycode) {
//            Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show();
//            显示对话框,提示验证码已发送,并给出确定按钮,使用AlertDialog.Builder类,
//            链式调用setTitle、setMessage、setPositiveButton、show方法
            String desc = String.format("验证码'%s'已发送，请注意查收"+verifyCode);
            /**
             * 验证码需要从服务器获取
             *verifyCode=？？？
             *
             *
             */
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(desc)
                    .setPositiveButton("确定", null)
                    .show();
        }
        if (v.getId() == R.id.btn_ok) {
            /**
             * 密码需要从后端获取
             * password=？？？
             */
            if(!verifycode.getText().toString().equals(verifyCode)){
                Toast.makeText(this, "验证码错误，请重新验证", Toast.LENGTH_SHORT).show();
                return;
            }else if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            } else if (newPassword.getText().toString().length() < 6) {
                Toast.makeText(this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                return;
            } else if (newPassword.getText().toString().length() > 16) {
                Toast.makeText(this, "密码长度不能大于16位", Toast.LENGTH_SHORT).show();
                return;
            } else{
                /**
                 * 修改密码
                 * 将修改后的密码写入数据库
                 */
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

}