package com.dxytech.oden.annotations.app.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.RelativeLayout;

import com.dxytech.oden.annotations.R;
import com.dxytech.oden.annotations.app.utils.L;
import com.dxytech.oden.annotations.app.utils.T;
import com.dxytech.oden.annotations.app.utils.Utils;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * 项目名称：Hongbaotest
 * 类描述：
 * 创建人：oden
 * 创建时间：2016/1/28 16:09
 */
@NoTitle
@EActivity
public class SettingActivity extends PreferenceActivity {

    Intent logIntent;
    Intent Settingintent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUI();
        setPrefListeners();
        logIntent = new Intent(this, LogActivity_.class);
        Settingintent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        upgradeApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
    }

    private void loadUI() {
        addPreferencesFromResource(R.xml.preferences);
        // Get rid of the fucking additional padding
        getListView().setPadding(0, 0, 0, 0);
        getListView().setBackgroundColor(0xfffaf6f1);
    }

    private void updateServiceStatus() {
        boolean serviceEnabled = false;
        Preference switchPref = findPreference("pref_switch");
        Preference infoPref = findPreference("pref_info");

        AccessibilityManager accessibilityManager =
                (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/com.dxytech.oden.annotations.app.service.HongbaoService_")) {
                serviceEnabled = true;
                break;
            }
        }
        if (serviceEnabled) {
            switchPref.setTitle("打开插件(已开启)");
        } else {
            switchPref.setTitle("打开插件(未开启)");
        }
    }

    private void setPrefListeners() {
        Preference logPref = findPreference("pre_log");
        logPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                startActivity(logIntent);
                return false;
            }
        });

        Preference switchPref = findPreference("pref_switch");
        switchPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                startActivity(Settingintent);
                return false;
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(false);
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(false);
//        L.d("onBackPressed");
//        super.onBackPressed();
//    }

    String downloadURL = "";
    String UpImformation = "";
    String versionName = "";
    String TAG = "syd";
    public void upgradeApp() {
        L.d("upgradeApp");
        PgyUpdateManager.register(this, Utils.appId, new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        L.d("发现新版本");
                        JSONObject jsonData;
                        try {
                            jsonData = new JSONObject(
                                    result);
                            L.d("result: " + result);
                            if ("0".equals(jsonData
                                    .getString("code"))) {
                                JSONObject jsonObject = jsonData
                                        .getJSONObject("data");
                                downloadURL = jsonObject
                                        .getString("downloadURL");
                                UpImformation = jsonObject
                                        .getString("releaseNote");
                                versionName = jsonObject
                                        .getString("versionName");
                                Log.d(TAG, "downloadURL:" + downloadURL);
                                Log.d(TAG, "UpImformation:" + UpImformation);
                                Log.d(TAG, "versionName:" + versionName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setTitle("发现新版本").setMessage(UpImformation);
                        builder.setPositiveButton("开始升级", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDownloadTask(SettingActivity.this, downloadURL);
                            }
                        }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setCancelable(false).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        L.d("已经是最新的版本");
                    }
                }
        );
    }
}
