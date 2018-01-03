package top.wefor.now.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.mlstudio.yiji.R;


/**
 * Created by florentchampigny on 24/04/15.
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_CELL = 1;
    protected List<T> mList;
    protected Context context;
    private Integer bigViewResId, smallViewResId;

    public BaseListAdapter(Context context, List<T> mList) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public Integer getBigViewResId() {
        return bigViewResId == null ? R.layout.list_item_card_big : bigViewResId;
    }

    public void setBigViewResId(Integer bigViewResId) {
        this.bigViewResId = bigViewResId;
    }

    public Integer getSmallViewResId() {
        return smallViewResId == null ? R.layout.list_item_card_small : smallViewResId;
    }

    public void setSmallViewResId(Integer smallViewResId) {
        this.smallViewResId = smallViewResId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(getBigViewResId(), parent, false);


                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(getSmallViewResId(), parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }

    protected void bindHeaderViewHolder(RecyclerView.ViewHolder headViewHolder, int position) {
    }

    protected abstract void bindCellViewHolder(RecyclerView.ViewHolder cellViewHolder, int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                bindHeaderViewHolder(holder, position);
                break;
            case TYPE_CELL:
                bindCellViewHolder(holder, position);
                break;
        }
    }
}