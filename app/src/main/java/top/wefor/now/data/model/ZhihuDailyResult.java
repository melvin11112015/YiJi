package top.wefor.now.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.github.yylyingy.yiji.base.zhihubase.BaseResult;
import top.wefor.now.data.model.entity.Zhihu;

/**
 * Created by tangqi on 8/20/15.
 */
public class ZhihuDailyResult extends BaseResult {
    @SerializedName("date")
    public String date;
    @SerializedName("stories")
    public List<Zhihu> stories;
}
