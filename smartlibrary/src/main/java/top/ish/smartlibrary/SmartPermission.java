package top.ish.smartlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by yanjie on 17/6/28.
 * 权限检测入口
 */

public class SmartPermission {
    private static final SmartPermission ourInstance = new SmartPermission();

    public static SmartPermission getInstance() {
        if (Build.VERSION.SDK_INT < 23) {
            throw new IllegalStateException("API小于23时不需要进行权限检测。。。");
        }
        return ourInstance;
    }

    private SmartPermission() {
    }

    /**
     * 检测是否拥有某个权限
     *
     * @param context    上下文
     * @param permission 权限名称
     * @return 是否拥有此权限
     */
    public boolean hasPermission(@NonNull Object context, @NonNull String permission) {
        if (context instanceof Activity) {
            return ContextCompat.checkSelfPermission((Activity) context, permission) != PackageManager.PERMISSION_GRANTED;
        } else if (context instanceof Fragment) {
            return ContextCompat.checkSelfPermission(((Fragment) context).getContext(), permission) != PackageManager.PERMISSION_GRANTED;
        } else if (context instanceof android.app.Fragment) {
            return ContextCompat.checkSelfPermission(((Fragment) context).getContext(), permission) != PackageManager.PERMISSION_GRANTED;
        } else {
            throw new IllegalArgumentException("Context只适用于Activity和Fragment");
        }
    }

    /**
     * 检测是否拥有某一组权限
     *
     * @param context     上下文
     * @param permissions 权限名称数组
     * @return 没有权限数组
     */
    public String[] hasPermission(@NonNull Object context, @NonNull String[] permissions) {
        return null;
    }
}
