package com.xys.accessibilitydemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xys.accessibilitydemo.utils.BaseAccessibilityService;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private PackageManager mPackageManager;
    /**
     *  包名数组
     */
    private String[] mPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseAccessibilityService.getInstance().init(this);
        mPackageManager = this.getPackageManager();
        mPackages = new String[]{"com.jshejia.pm","com.signway.assist"};
    }

    /**
     * 打开辅助服务模块
     * @param view
     */
    public void goAccess(View view) {
        BaseAccessibilityService.getInstance().goAccess();
    }

    /**
     * 打开 App
     * @param view
     */
    public void goApp(View view) {
        Intent intent = mPackageManager.getLaunchIntentForPackage("com.signway.assist");
        // Log.i("Info","Launch Intent = "+intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * 跳转到设置 - 应用信息详情模块
     * @param view
     */
    public void cleanProcess(View view) {
        for (String mPackage : mPackages) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mPackage, null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    /**
     * 自动安装
     * @param view
     */
    public void autoInstall(View view) {
        String apkPath = Environment.getExternalStorageDirectory() + "/test.apk";
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }
}
