package com.dxytech.oden.annotations.model;

import android.provider.ContactsContract;
import android.view.accessibility.AccessibilityNodeInfo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.dxytech.oden.annotations.app.utils.L;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 项目名称：Hongbaotest
 * 类描述：
 * 创建人：oden
 * 创建时间：2016/1/28 14:13
 */
@Table(name = "HongbaoInfos")
public class HongbaoInfo extends Model {

    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;

    @Column(name = "sender")
    public String sender;

    @Column(name = "money")
    public String money;

    @Column(name = "time")
    public String time;

    public void getInfo(AccessibilityNodeInfo node) {

        AccessibilityNodeInfo hongbaoNode = node.getParent();
//        for (int i = 0; i < hongbaoNode.getChildCount(); i++) {
//            L.d("hongbaoNode.getChild(i).toString(): " + i + ",:" + hongbaoNode.getChild(i).toString());
//        }
        sender = hongbaoNode.getChild(0).getText().toString();
        money = hongbaoNode.getChild(2).getText().toString();
        time = getStringTime();
    }

    private String getStringTime() {
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        sec = c.get(Calendar.SECOND);
        return month+"月"+day+"日  "+hour+":"+min+":"+sec;
    }

    @Override
    public String toString() {
        return "HongbaoInfo [sender=" + sender + ", money=" + money + ", time=" + time + "]";
    }


    public static List<HongbaoInfo> getAll() {
        return new Select()
                .from(HongbaoInfo.class)
                .orderBy("Id ASC")
                .execute();
    }

    public static void deleteALL() {
        new Delete().from(HongbaoInfo.class).execute();
    }

    public float getMoney() {
        return Float.parseFloat(money);
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }
}
