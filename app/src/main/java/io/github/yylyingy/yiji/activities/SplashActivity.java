package io.github.yylyingy.yiji.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;

import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.base.BaseActivity;
import io.github.yylyingy.yiji.tools.ThreadPoolTool;
import io.github.yylyingy.yiji.tools.db.DataManager;
import io.github.yylyingy.yiji.tools.permissions.PermissionListener;
import io.github.yylyingy.yiji.tools.permissions.PermissionTools;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SplashActivity extends BaseActivity implements PermissionListener {
    public static final String TAG = SplashActivity.class.getCanonicalName();
    private static final int REQUEST_PERMISSION_CODE = 0x123;
    private PermissionTools permissionTools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            permissionTools = PermissionTools.with(this);
            permissionTools.setPermissionsListener(this);
            permissionTools.addRequestCode(REQUEST_PERMISSION_CODE)
                    .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .request();
//            tryRequestPermission();

        }
    }

    @TargetApi(23)
    private void tryRequestPermission(){
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE
            },REQUEST_PERMISSION_CODE);
        }
    }

    @OnClick(R.id.activity_splash)
    void startMainActivity(){
        startActivity(new Intent(SplashActivity.this,AddRecordActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionTools.onPermissionResult(permissions,grantResults);
//        switch (requestCode){
//            case REQUEST_PERMISSION_CODE :
//                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
//                    finish();
//                }
//                break;
//        }
    }

    /**
     * 用户授权后调用
     */
    @Override
    public void onGranted() {
//        Toast.makeText(this,"权限被允许",Toast.LENGTH_SHORT).show();
        ThreadPoolTool.exeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    while (!DataManager.getsInstance(getApplicationContext()).isInit()){
                        Thread.sleep(100);
                    }
                    startMainActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 用户禁止后调用
     */
    @Override
    public void onDenied() {
        Toast.makeText(this,"权限被禁止",Toast.LENGTH_SHORT).show();
    }

    /**
     * 是否显示阐述性说明
     *
     * @param permissions 返回需要显示说明的权限数组
     */
    @Override
    public void onShowRationale(String[] permissions) {

    }
}
