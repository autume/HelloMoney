package com.dxytech.oden.annotations.core;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultFloat;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by oden on 2015/9/28.
 */
@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface MyPrefs {
    @DefaultInt(0)
    int totalNum();

    @DefaultInt(0)
    int successNum();

    @DefaultInt(0)
    int failNum();

    @DefaultFloat(0)
    float totalMoney();
}
