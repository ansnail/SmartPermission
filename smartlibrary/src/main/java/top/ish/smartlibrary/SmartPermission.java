package top.ish.smartlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanjie on 17/6/28.
 * 权限检测入口
 */

public class SmartPermission {
    private static SmartPermission ourInstance = new SmartPermission();
    private Dialog dialog;
    private Context mContext;
    private Object mActivityOrFragment;
    private String[] requestArrays;
    private PermissionRequestCallback requestCallback;

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
    private boolean hasPermission(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检测是否拥有某一组权限
     *
     * @param context     上下文
     * @param permissions 权限名称数组
     * @return 权限组中的一个或多个没有权限
     */
    public boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否拥有了请求的所有权限
     *
     * @param context     上下文
     * @param permissions 权限名称数组
     * @return 是否拥有了请求的所有权限
     */
    public boolean isOwnAllPermission(@NonNull Context context, @NonNull String... permissions) {
        return hasPermission(context, permissions);
    }


    /**
     * 检测是否有需要解释的权限
     *
     * @param mActivityOrFragment 上下文
     * @param permission          权限名称
     * @return 是否拥有此权限
     */
    private boolean hasRationalePermission(Object mActivityOrFragment, @NonNull String permission) {
        if (mActivityOrFragment instanceof Activity) {
            Activity activity = (Activity) mActivityOrFragment;
            return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        } else if (mActivityOrFragment instanceof Fragment) {
            Fragment fragment = (Fragment) mActivityOrFragment;
            return fragment.shouldShowRequestPermissionRationale(permission);
        } else if (mActivityOrFragment instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) mActivityOrFragment;
            return fragment.shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }


    /**
     * 检测是否有需要解释的权限
     *
     * @param mActivityOrFragment 上下文
     * @param permissions         权限名称数组
     * @return 权限组中的一个或多个没有权限
     */
    private boolean hasRationalePermission(Object mActivityOrFragment, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (hasRationalePermission(mActivityOrFragment, permission)) {
                return true;
            }
        }
        return false;
    }


    public boolean somePermissionPermanentlyDenied(@NonNull List<String> perms) {
        for (String deniedPermission : perms) {
            if (!hasRationalePermission(deniedPermission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 权限请求
     *
     * @param permissions 请求的权限
     * @return SmartPermission
     */
    public SmartPermission permission(@NonNull String... permissions) {
        requestArrays = permissions;
        return this;
    }

    public SmartPermission context(Activity activity) {
        mActivityOrFragment = activity;
        mContext = activity;
        return this;
    }

    public SmartPermission context(Fragment fragment) {
        mActivityOrFragment = fragment;
        mContext = fragment.getContext();
        return this;
    }

    public SmartPermission context(android.app.Fragment fragment) {
        mActivityOrFragment = fragment;
        mContext = fragment.getContext();
        return this;
    }


    public void request(int requestCode, PermissionRequestCallback callback) {
        this.requestCallback = callback;
        ArrayList<String> withoutLists = new ArrayList<>();
        ArrayList<String> deniedLists = new ArrayList<>();
        //检查是否已经拥有全部权限,如果是直接返回
        if (hasPermission(mContext, requestArrays)) {
            int[] grantResults = new int[requestArrays.length];
            for (int i = 0; i < requestArrays.length; i++) {
                grantResults[i] = PackageManager.PERMISSION_GRANTED;
            }
            dealRequestPermissionsResult(requestCode, requestArrays, grantResults);
            return;
        }
        //开始请求权限
        requestPermission(mActivityOrFragment, requestCode, requestArrays);

    }

    private void requestPermission(Object mActivityOrFragment, int requestCode, @NonNull String... permissions) {
        if (mActivityOrFragment instanceof Activity) {
            Activity activity = (Activity) mActivityOrFragment;
            activity.requestPermissions(permissions, requestCode);
        } else if (mActivityOrFragment instanceof Fragment) {
            Fragment fragment = (Fragment) mActivityOrFragment;
            fragment.requestPermissions(permissions, requestCode);
        } else if (mActivityOrFragment instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) mActivityOrFragment;
            fragment.requestPermissions(permissions, requestCode);
        }
    }


    /**
     * 处理请求权限返回结果
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限集合
     * @param grantResults 请求的结果集合
     */
    public void dealRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 从结果中分离"通过"和"拒绝"的权限
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        if (!granted.isEmpty()) {
            requestCallback.onPermissionsGranted(requestCode, granted);
        }

        if (!denied.isEmpty()) {
            requestCallback.onPermissionsDenied(requestCode, denied);
        }

    }
}
