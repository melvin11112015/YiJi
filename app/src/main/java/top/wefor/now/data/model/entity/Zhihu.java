package top.wefor.now.data.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangqi on 8/20/15.
 */
public class Zhihu implements Serializable {
    @SerializedName("images")
    public List<String> images;
    @SerializedName("type")
    public int type;
    @SerializedName("id")
    public int id;
    @SerializedName("ga_prefix")
    public String ga_prefix;
    @SerializedName("title")
    public String title;
    @SerializedName("multipic")
    public boolean multipic;
}
