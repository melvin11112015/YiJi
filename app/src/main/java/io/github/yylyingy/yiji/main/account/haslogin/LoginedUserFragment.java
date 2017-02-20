package io.github.yylyingy.yiji.main.account.haslogin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.base.BaseFragment;
import io.github.yylyingy.yiji.main.account.IAccountLogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginedUserFragment extends BaseFragment {
    @BindView(R.id.logout)
    TextView logout;
    IAccountLogin mIAccountLogin;

    public LoginedUserFragment() {
        // Required empty public constructor
    }

    public void bindAccountListener(IAccountLogin login){
        mIAccountLogin = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_logined_user, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick(R.id.logout)
    public void logout(){
        BmobUser.logOut();
        mIAccountLogin.logout();
    }
}
