package io.github.mlstudio.yiji.addrecord;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.johnpersano.supertoasts.SuperToast;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.Calendar;

import butterknife.BindView;
import io.github.mlstudio.yiji.R;
import io.github.mlstudio.yiji.base.BaseFragment;
import io.github.mlstudio.yiji.javabeans.YiJiRecord;
import io.github.mlstudio.yiji.tools.YiJiToast;
import io.github.mlstudio.yiji.tools.db.DataManager;
import io.github.mlstudio.yiji.ui.EditMoneyFragment;
import io.github.mlstudio.yiji.ui.EditMoneyRemarkFragmentAdapter;
import io.github.mlstudio.yiji.ui.TagChooseFragmentAdapter;
import io.github.mlstudio.yiji.ui.WrapContentHeightViewPager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecordFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.viewpager)
    public WrapContentHeightViewPager tagViewPager;
    public EditMoneyFragment editMoneyFragment = EditMoneyFragment.newInstance(0, 1);
    @BindView(R.id.check)
    MaterialIconView check;
    @BindView(R.id.eraser)
    MaterialIconView eraser;
//    @BindView(R.id.edit_pager)
//    YiJiScrollableViewPager editViewPager;
    private FragmentPagerAdapter tagAdapter;


    public AddRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecordFragment newInstance(String param1, String param2) {
        AddRecordFragment fragment = new AddRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_record, container, false);

        if (DataManager.TAGS.size() % 8 == 0)
            tagAdapter = new TagChooseFragmentAdapter(getFragmentManager(), DataManager.TAGS.size() / 8);
        else
            tagAdapter = new TagChooseFragmentAdapter(getFragmentManager(), DataManager.TAGS.size() / 8 + 1);
        FragmentPagerAdapter editAdapter = new EditMoneyRemarkFragmentAdapter(getFragmentManager(), 0);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tagViewPager.setAdapter(tagAdapter);
//        if (editViewPager != null){
////            editViewPager.setAdapter(editAdapter);
//        }
        getFragmentManager().beginTransaction()
                .replace(R.id.edit_pager,editMoneyFragment)
                .commit();
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMoneyFragment.getTagId() == -1){
                    YiJiToast.getInstance().showToast(R.string.toast_no_tag, SuperToast.Background.RED);
                    YoYo.with(Techniques.Shake).duration(1000).playOn(tagViewPager);
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                try {
                    YiJiRecord record = new YiJiRecord(
                            -1,
                            Float.valueOf(editMoneyFragment.getNumberText()),
                            "RMB",
                            editMoneyFragment.getTagId(),
                            calendar);
                    String source = String.valueOf(record.getMoney()) +
                            record.getCurrency() +
                            record.getTag() +
                            record.getCalendar().toString() +
                            record.getRemark() +
                            record.getUserId() +
                            record.getObjectId();
                    record.setHashCode(source);
                    DataManager.getsInstance(getActivity().getApplicationContext())
                            .saveRecord(record);
                    editMoneyFragment.setTagImage(R.color.transparent);
                    editMoneyFragment.setTagName("");
                    editMoneyFragment.setTagId(-1);
                    editMoneyFragment.setNumberText("");
                    editMoneyFragment.setHelpText(" ");
                    YiJiToast.getInstance().showToast(R.string.save_success, SuperToast.Background.GREEN);
                } catch (NumberFormatException e) {
                    YiJiToast.getInstance().showToast(R.string.number_format_error, SuperToast.Background.RED);
                    YoYo.with(Techniques.Shake).duration(1000).playOn(editMoneyFragment.editView);
                    e.printStackTrace();
                }
            }
        });

 

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMoneyFragment.setTagImage(R.color.transparent);
                editMoneyFragment.setTagName("");
                editMoneyFragment.setTagId(-1);
                editMoneyFragment.setNumberText("");
                editMoneyFragment.setHelpText(" ");
            }
        });

    }

}
