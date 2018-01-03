package io.github.mlstudio.yiji.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.mlstudio.yiji.R;
import io.github.mlstudio.yiji.tools.YiJiUtil;
import io.github.mlstudio.yiji.tools.db.DataManager;


public class TagChooseGridViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private int fragmentPosition;
    private int count = 0;

    public TagChooseGridViewAdapter(Context context, int fragmentPosition) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.fragmentPosition = fragmentPosition;
    }

    @Override
    public int getCount() {
        if ((fragmentPosition + 1) * 8 >= (DataManager.TAGS.size() - 2)) {
            return (DataManager.TAGS.size() - 2) % 8;
        } else {
            return 8;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.item_tag_choose, null);
            holder.tagName = convertView.findViewById(R.id.tag_name);
            holder.tagImage = convertView.findViewById(R.id.tag_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tagName.setText(YiJiUtil.GetTagName(mContext,DataManager.TAGS.
                get(fragmentPosition * 8 + position + 2).getId()));
//        holder.tagName.setTypeface(CoCoinUtil.typefaceLatoLight);
        holder.tagImage.setImageResource(
                YiJiUtil.GetTagIcon(DataManager.TAGS.
                        get(fragmentPosition * 8 + position + 2).getId()));

        return convertView;
    }

    private class ViewHolder {
        TextView tagName;
        ImageView tagImage;
    }
}
