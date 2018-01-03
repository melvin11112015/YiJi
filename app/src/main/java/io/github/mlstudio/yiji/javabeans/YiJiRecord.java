package io.github.mlstudio.yiji.javabeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import io.github.mlstudio.yiji.tools.PasswordHashCodeTool;
import io.github.mlstudio.yiji.tools.YiJiUtil;

/**
 * Created by Yangyl on 2017/1/8.
 */

public class YiJiRecord extends BmobObject {
    private Long id;
    private Float money;
    private String currency;
    private Integer tag;
    private Calendar calendar;
    private String remark = "";
    private String userId;
    private String localObjectId;
    private Boolean isUploaded = false;
    private String  hashCode;

    public YiJiRecord() {
        this.id = Long.valueOf(-1);
    }

    public YiJiRecord(long id, float money, String currency, int tag, Calendar calendar) {
        this.id = id;
        this.money = money;
        this.currency = currency;
        this.tag = tag;
        this.calendar = calendar;
    }

    public YiJiRecord(long id, float money, String currency, int tag, Calendar calendar, String remark) {
        this.id = id;
        this.money = money;
        this.currency = currency;
        this.tag = tag;
        this.calendar = calendar;
        this.remark = remark;
    }

    public String toString() {
        return "CoCoinRecord(" +
                "id = " + id + ", " +
                "money = " + money + ", " +
                "currency = " + currency + ", " +
                "tag = " + tag + ", " +
                "calendar = " +
                new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime()) + ", " +
                "remark = " + remark + ", " +
                "userId = " + userId + ", " +
                "localObjectId = " + localObjectId + ", " +
                "isUploaded = " + isUploaded + ")";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void set(YiJiRecord coCoinRecord) {
        this.id = coCoinRecord.id;
        this.money = coCoinRecord.money;
        this.currency = coCoinRecord.currency;
        this.tag = coCoinRecord.tag;
        this.calendar = coCoinRecord.calendar;
        this.remark = coCoinRecord.remark;
        this.userId = coCoinRecord.userId;
        this.localObjectId = coCoinRecord.localObjectId;
        this.isUploaded = coCoinRecord.isUploaded;
    }

    public boolean isInTime(Calendar c1, Calendar c2) {
        return (!this.calendar.before(c1)) && this.calendar.before(c2);
    }

    public boolean isInMoney(double money1, double money2, String currency) {
        return YiJiUtil.ToDollas(money1, currency) <= YiJiUtil.ToDollas(this.money, this.currency)
                && YiJiUtil.ToDollas(money2, currency) > YiJiUtil.ToDollas(this.money, this.currency);
    }

    public String getCalendarString() {
        return String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.format("%02d", calendar.get(Calendar.MINUTE)) + " "
                + YiJiUtil.GetMonthShort(calendar.get(Calendar.MONTH) + 1) + " "
                + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.get(Calendar.YEAR);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendarString) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date =sdf.parse(calendarString);
            this.calendar = Calendar.getInstance();
            this.calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocalObjectId() {
        return localObjectId;
    }

    public void setLocalObjectId(String objectId) {
        this.localObjectId = objectId;
    }

    public Boolean getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(Boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj instanceof YiJiRecord) {
            YiJiRecord anotherYiJiRecord = (YiJiRecord) obj;
            boolean isEquals = this.id.equals(  anotherYiJiRecord.id) ||
                this.money.equals(anotherYiJiRecord.money)               ||
                this.currency.equals(anotherYiJiRecord.currency)        ||
                this.tag.equals(anotherYiJiRecord.tag)                  ||
                this.calendar.equals(anotherYiJiRecord.calendar)        ||
                this.remark.equals(anotherYiJiRecord.remark)            ||
                this.userId.equals(anotherYiJiRecord.userId)            ||
                this.localObjectId.equals(anotherYiJiRecord.localObjectId);
//            private Boolean isUploaded = false;
            return isEquals;
        }
        return false;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String str) {
        this.hashCode = PasswordHashCodeTool.encryptToSHA(str);
    }
}
