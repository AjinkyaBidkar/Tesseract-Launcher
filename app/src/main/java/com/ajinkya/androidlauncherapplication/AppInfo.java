package com.ajinkya.androidlauncherapplication;

import android.graphics.drawable.Drawable;

/**
 * Created by Ajinkya on 12/01/22.
 */

class AppInfo {

    String label, packageName, versionName, versionCode;
    Drawable icon;

    public AppInfo(String label, String packageName, String versionName, String versionCode, Drawable icon) {
        this.label = label;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.icon = icon;
    }

    public AppInfo() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
