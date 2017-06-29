package top.ish.smartlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

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
    public boolean hasPermission(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检测是否拥有某一组权限
     *
     * @param context     上下文
     * @param permissions 权限名称数组
     * @return 没有权限的list，调用者可以根据list的情况进行判断
     */
    public ArrayList<String> hasPermission(@NonNull Context context, @NonNull String... permissions) {
        ArrayList<String> noPermissionLists = new ArrayList<>();
        for (String permission:permissions) {
            if (!hasPermission(context,permission)){
                noPermissionLists.add(permission);
            }
        }
        return noPermissionLists;
    }


    public void requestPermission(@NonNull Object context, @NonNull String permission) {
        if (context instanceof Activity) {
        } else if (context instanceof Fragment) {
        } else if (context instanceof android.app.Fragment) {
        } else {
            throw new IllegalArgumentException("Context只适用于Activity和Fragment");
        }
    }

}
