package io.github.yylyingy.yiji.main.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.activities.MainActivity;
import io.github.yylyingy.yiji.base.BaseFragment;
import io.github.yylyingy.yiji.javabeans.User;
import io.github.yylyingy.yiji.main.account.haslogin.LoginedUserFragment;
import io.github.yylyingy.yiji.main.account.notlogin.NoUserFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment implements IAccountLogin{


    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment getInstance(){
        AccountFragment fragment = new AccountFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser == null){
            NoUserFragment noUserFragment = new NoUserFragment();
            noUserFragment.bindAccountListener(this);
            getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainer
                    ,noUserFragment,"Logout").commit();
        }else {
            LoginedUserFragment loginedUserFragment = new LoginedUserFragment();
            loginedUserFragment.bindAccountListener(this);
            getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainer
                    ,loginedUserFragment,"Login").commit();
        }
    }

    @Override
    public void login() {
        LoginedUserFragment loginedUserFragment = new LoginedUserFragment();
        loginedUserFragment.bindAccountListener(this);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer
                ,loginedUserFragment,"Login").commit();

    }

    @Override
    public void logout() {
        NoUserFragment noUserFragment = new NoUserFragment();
        noUserFragment.bindAccountListener(this);
        getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainer
                ,noUserFragment,"Logout").commit();
    }
}
