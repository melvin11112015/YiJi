package io.github.mlstudio.yiji.main.showrecord;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mlstudio.yiji.R;
import io.github.mlstudio.yiji.javabeans.YiJiRecord;
import io.github.mlstudio.yiji.tools.YiJiUtil;
import io.github.mlstudio.yiji.tools.db.DataManager;

/**
 * Created by 伟平 on 2015/11/1.
 */
public class RecordCheckDialogRecyclerViewAdapter extends RecyclerView.Adapter<RecordCheckDialogRecyclerViewAdapter.viewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private OnItemClickListener onItemClickListener;
    private List<YiJiRecord> YiJiRecords;

    public RecordCheckDialogRecyclerViewAdapter(Context context, List<YiJiRecord> list) {
        YiJiRecords = new ArrayList<>();
        YiJiRecords = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public RecordCheckDialogRecyclerViewAdapter(Context context, List<YiJiRecord> list, OnItemClickListener onItemClickListener) {
        YiJiRecords = new ArrayList<>();
        YiJiRecords = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(mLayoutInflater.inflate(R.layout.record_check_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.imageView.setImageResource(
                YiJiUtil.GetTagIcon(YiJiRecords.get(position).getTag()));
        holder.date.setText(YiJiRecords.get(position).getCalendarString());
        holder.date.setTypeface(YiJiUtil.typefaceLatoLight);
        holder.money.setTypeface(YiJiUtil.typefaceLatoLight);
        holder.money.setText(String.valueOf((int) YiJiRecords.get(position).getMoney()));
        holder.money.setTextColor(
                YiJiUtil.GetTagColorResource(DataManager.TAGS.get(YiJiRecords.get(position).getTag()).getId()));
        holder.index.setText((position + 1) + "");
        holder.index.setTypeface(YiJiUtil.typefaceLatoLight);
        holder.remark.setText(YiJiRecords.get(position).getRemark());
        holder.remark.setTypeface(YiJiUtil.typefaceLatoLight);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (YiJiRecords == null) {
            return 0;
        }
        return YiJiRecords.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_view)
        ImageView imageView;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.remark)
        TextView remark;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.index)
        TextView index;
        @BindView(R.id.material_ripple_layout)
        MaterialRippleLayout layout;

        viewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
//            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}