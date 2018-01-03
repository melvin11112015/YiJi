package io.github.mlstudio.yiji.main.account.notlogin;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.CircularProgressButton;
import com.github.johnpersano.supertoasts.SuperToast;
import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import io.github.mlstudio.yiji.BuildConfig;
import io.github.mlstudio.yiji.R;
import io.github.mlstudio.yiji.YiJiApplication;
import io.github.mlstudio.yiji.base.BaseFragment;
import io.github.mlstudio.yiji.javabeans.User;
import io.github.mlstudio.yiji.main.account.IAccountLogin;
import io.github.mlstudio.yiji.tools.YiJiToast;
import io.github.mlstudio.yiji.tools.YiJiUtil;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoUserFragment extends BaseFragment {
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.login_use_name)
    MaterialEditText userName;
    @BindView(R.id.login_password)
    MaterialEditText userPassword;

    IAccountLogin mIAccountLogin;

    public NoUserFragment() {
        // Required empty public constructor
    }

    public void bindAccountListener(IAccountLogin iAccountLogin){
        mIAccountLogin = iAccountLogin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_no_account, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.login_button)
    public void login(){
        if (userName.getText().toString().equals("") || userPassword.getText().toString().equals("")){
            YiJiToast.getInstance().showToast("请填写完整",SuperToast.Background.RED);
            return;
        }
        final User user = new User();
        user.setUsername(userName.getText().toString());
        user.setPassword(userPassword.getText().toString());
//新增加的Observable
        user.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
            @Override
            public void onCompleted() {
                Logger.d("----onCompleted----");
            }

            @Override
            public void onError(Throwable e) {
                YiJiToast.getInstance().showToast("登录失败，请确认网络及账号密码",SuperToast.Background.RED);
                Logger.d(new BmobException(e));
            }

            @Override
            public void onNext(BmobUser bmobUser) {
                mIAccountLogin.login();
            }
        });

    }

    @OnClick(R.id.register)
    public void register(){
        MaterialDialog registerDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.go_register)
                .typeface(YiJiUtil.GetTypeface(), YiJiUtil.GetTypeface())
                .customView(R.layout.dialog_user_register, true)
                .build();
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        MaterialEditText registerUserName = (MaterialEditText) registerDialog.findViewById(R.id.register_user_name);
        MaterialEditText registerPassword = (MaterialEditText) registerDialog.findViewById(R.id.register_password);
        MaterialEditText registerUserEmail = (MaterialEditText) registerDialog.findViewById(R.id.register_user_email);
        View registerDialogView = registerDialog.getCustomView();
        CircularProgressButton registerDialogButton;
        registerDialogButton = registerDialogView.findViewById(R.id.button);
        registerDialogButton.setTypeface(YiJiUtil.GetTypeface());
        registerDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDialogButton.setProgress(1);
                registerDialog.setCancelable(false);
// User register, a new user////////////////////////////////////////////////////////////////////////
                final User user = new User();
                // basic info
                user.setUsername(registerUserName.getText().toString());
                user.setPassword(registerPassword.getText().toString());
                user.setEmail(registerUserEmail.getText().toString());
                user.setAndroidId(YiJiApplication.getAndroidId());
                if (BuildConfig.DEBUG) Log.d("CoCoin", "Android Id: " + user.getAndroidId());
                user.setLogoObjectId("");
                addSubscription(user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User s, BmobException e) {
                        if(e == null){
                            registerDialogButton.setProgress(0);
                            registerDialog.setCancelable(true);
                            registerDialogButton.setIdleText((String) getActivity().getResources().getText(R.string.register_complete));
                            showToast(4, registerUserName.getText().toString());
                            Logger.d("注册成功");
                            mIAccountLogin.login();
                            registerDialog.dismiss();
                        }else {
                            String tip = getResourceString(R.string.network_disconnection);
                            if (BuildConfig.DEBUG) Logger.d("Register failed: " + e);
                            if (e.toString().charAt(1) == 's') tip = getResourceString(R.string.user_name_exist);
                            if (e.toString().charAt(0) == 'e') tip = getResourceString(R.string.user_email_exist);
                            if (e.toString().charAt(1) == 'n') tip = getResourceString(R.string.user_mobile_exist);
                            registerDialogButton.setIdleText(tip);
                            registerDialogButton.setProgress(0);
                            registerDialog.setCancelable(true);
                        }
                    }
                }));
//                user.signUp(YiJiApplication.getAppContext(), new SaveListener() {
//                    @Override
//                    public void onSuccess() {
//                        registerDialogButton.setProgress(0);
//                        registerDialog.setCancelable(true);
//                        registerDialogButton.setIdleText(getResourceString(R.string.register_complete));
//// if register successfully/////////////////////////////////////////////////////////////////////////
//                        SettingManager.getInstance().setLoggenOn(true);
//                        SettingManager.getInstance().setUserName(registerUserName.getText().toString());
//                        SettingManager.getInstance().setUserEmail(registerUserEmail.getText().toString());
//                        SettingManager.getInstance().setUserPassword(registerPassword.getText().toString());
//                        showToast(4, registerUserName.getText().toString());
//// if login successfully////////////////////////////////////////////////////////////////////////////
//                        user.login(CoCoinApplication.getAppContext(), new SaveListener() {
//                            @Override
//                            public void onSuccess() {
//                                SettingManager.getInstance().setTodayViewInfoShouldChange(true);
//                                updateViews();
//                                // use a new method
////                                RecordManager.updateOldRecordsToServer();
//                            }
//                            @Override
//                            public void onFailure(int code, String msg) {
//// if login failed//////////////////////////////////////////////////////////////////////////////////
//                            }
//                        });
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (registerDialog != null) registerDialog.dismiss();
//                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,0);
//                            }
//                        }, 500);
//                    }
//                    // if register failed///////////////////////////////////////////////////////////////////////////////
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        if (BuildConfig.DEBUG) Log.d("CoCoin", "Register failed: " + msg);
//                        String tip = getResourceString(R.string.network_disconnection);
//                        if (msg.charAt(1) == 's') tip = getResourceString(R.string.user_name_exist);
//                        if (msg.charAt(0) == 'e') tip = getResourceString(R.string.user_email_exist);
//                        if (msg.charAt(1) == 'n') tip = getResourceString(R.string.user_mobile_exist);
//                        registerDialogButton.setIdleText(tip);
//                        registerDialogButton.setProgress(0);
//                        registerDialog.setCancelable(true);
//                    }
//                });
            }
        });
        registerDialog.show();
    }

    // Show toast///////////////////////////////////////////////////////////////////////////////////////
    private void showToast(int toastType, String msg) {
        Log.d("CoCoin", msg);
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(getActivity());

        superToast.setAnimations(YiJiUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);

        String tip;

        switch (toastType) {
            case 0:
                // the new account book name is updated to server successfully
                superToast.setText(YiJiApplication.getAppContext().getResources().getString(
                        R.string.change_and_update_account_book_name_successfully));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 1:
                // the new account book name is failed to updated to server
                superToast.setText(YiJiApplication.getAppContext().getResources().getString(
                        R.string.change_and_update_account_book_name_fail));
                superToast.setBackground(SuperToast.Background.RED);
                break;
            case 2:
                // the new account book name is changed successfully
                superToast.setText(YiJiApplication.getAppContext().getResources().getString(
                        R.string.change_account_book_name_successfully));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 3:
                // the new account book name is failed to change
                superToast.setText(YiJiApplication.getAppContext().getResources().getString(
                        R.string.change_account_book_name_fail));
                superToast.setBackground(SuperToast.Background.RED);
                break;
            case 4:
                // register successfully
                tip = msg;
                superToast.setText(getResourceString(R.string.register_successfully) + tip);
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 5:
                // register failed
                tip = getResourceString(R.string.network_disconnection);
                if (msg.charAt(1) == 's') tip = getResourceString(R.string.user_name_exist);
                if (msg.charAt(0) == 'e') tip = getResourceString(R.string.user_email_exist);
                if (msg.charAt(1) == 'n') tip = getResourceString(R.string.user_mobile_exist);
                superToast.setText(getResourceString(R.string.register_fail) + tip);
                superToast.setBackground(SuperToast.Background.RED);
                break;
            case 6:
                // login successfully
                tip = msg;
                superToast.setText(getResourceString(R.string.login_successfully) + tip);
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 7:
                // login failed
                tip = getResourceString(R.string.network_disconnection);
                if (msg.charAt(0) == 'u') tip = getResourceString(R.string.user_name_or_password_incorrect);
                if (msg.charAt(1) == 'n') tip = getResourceString(R.string.user_mobile_exist);
                superToast.setText(getResourceString(R.string.login_fail) + tip);
                superToast.setBackground(SuperToast.Background.RED);
                break;
            case 8:
                // log out successfully
                superToast.setText(getResourceString(R.string.log_out_successfully));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 9:
                // sync settings successfully
                superToast.setText(getResourceString(R.string.sync_to_server_successfully));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 10:
                // sync settings failed
                tip = getResourceString(R.string.network_disconnection);
                superToast.setText(getResourceString(R.string.sync_to_server_failed) + tip);
                superToast.setBackground(SuperToast.Background.RED);
                break;

        }
        superToast.getTextView().setTypeface(YiJiUtil.GetTypeface());
        superToast.show();
    }

    // Get string///////////////////////////////////////////////////////////////////////////////////////
    private String getResourceString(int resourceId) {
        return YiJiApplication.getAppContext().getResources().getString(resourceId);
    }



}
