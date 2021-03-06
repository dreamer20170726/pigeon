package com.example.administrator.pigeon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

import java.util.List;
import java.util.regex.Pattern;

import bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import model.UserModel;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {

    @ViewInject(R.id.edit_register_name)
    EditText edit_register_name;
    @ViewInject(R.id.edit_register_pass)
    EditText edit_register_pass;
    @ViewInject(R.id.edit_repass)
    EditText edit_repass;
    @ViewInject(R.id.image_back)
    ImageView image_back;
    @ViewInject(R.id.image_name)
    ImageView image_name;
    @ViewInject(R.id.image_pass)
    ImageView image_pass;
    @ViewInject(R.id.image_repass)
    ImageView image_repass;
    @ViewInject(R.id.button_register)
    Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        edit_register_name.addTextChangedListener(this);
        edit_register_pass.addTextChangedListener(this);
        edit_repass.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String name = edit_register_name.getText().toString().trim();
        String pass = edit_register_pass.getText().toString().trim();
        String repass = edit_repass.getText().toString().trim();
        if (name.length() > 0) {
            image_name.setVisibility(View.VISIBLE);
        } else {
            image_name.setVisibility(View.INVISIBLE);
        }
        if (pass.length() > 0) {
            image_pass.setVisibility(View.VISIBLE);
        } else {
            image_pass.setVisibility(View.INVISIBLE);
        }
        if (repass.length() > 0) {
            image_repass.setVisibility(View.VISIBLE);
        } else {
            image_repass.setVisibility(View.INVISIBLE);
        }
    }


    @OnCompoundButtonCheckedChange(R.id.checkbox_agree)
    private void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            button_register.setEnabled(true);
        }else{
            button_register.setEnabled(false);
        }
    }

    @OnClick({R.id.image_back, R.id.image_name, R.id.image_pass,R.id.image_repass})
    private void onClickImage(View view){
        switch (view.getId()) {
            case R.id.image_name:
                edit_register_name.setText("");
                break;
            case R.id.image_pass:
                edit_register_pass.setText("");
                break;
            case R.id.image_repass:
                edit_repass.setText("");
                break;
            case R.id.image_back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.button_register)
    private void onClickRegister(View view){
        String name = edit_register_name.getText().toString().replace(" ", "");
        String pass = edit_register_pass.getText().toString();
        String repass = edit_repass.getText().toString();

        UserModel userModel = UserModel.getInstance(RegisterActivity.this);
        userModel.register(name,pass,repass);
    }

    @OnClick(R.id.img_avatar)
    public void getAvatar(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, 1);
    }



    public void onBackPressed() {
        //方式一：将此任务转向后台
//        moveTaskToBack(false);

//        方式二：返回手机的主屏幕
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
