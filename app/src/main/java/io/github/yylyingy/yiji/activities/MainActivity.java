package io.github.yylyingy.yiji.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.yangyl.encryptdecrypt.secrect.EncryptDecryptTool;
import com.yangyl.encryptdecrypt.secrect.base64.BASE64Encoder;
import com.yangyl.myspending.R;
import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.base.BaseActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCplL7aQorlDn8U+hhYGQ6PM4UUKkNMsgwgf/Ia\n" +
            "VTFuFXIurpsjAeJjSiNj+nfEqzbb1Fwp+GPg/69bwwWJSN9xLBka3DiBCAKZZ8r2AQy60RA6DCui\n" +
            "MJ9Fu3Vg+v9IhdrGG7QjCx/JJsMtcPgUzQ99EQVhY2uBO29g9UW2NY6L0wIDAQAB";
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.exit)
    MaterialRippleLayout exit;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (mToolbar != null){
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        setTitle("");
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close
        );
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        EncryptDecryptTool encryptDecryptTool = new EncryptDecryptTool();
        try {
            byte []secret = encryptDecryptTool.encryptByPublicKey("测试".getBytes(),publicKey);
            Toast.makeText(this, new BASE64Encoder().encode(secret), Toast.LENGTH_SHORT).show();
            Log.d(TAG,"密文："  + new BASE64Encoder().encode(secret));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.exit)
    protected void exitApp(){
        ((YiJiApplication)getApplication()).exitApp();
    }

    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
