package io.github.yylyingy.yiji.main.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment {


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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

}
