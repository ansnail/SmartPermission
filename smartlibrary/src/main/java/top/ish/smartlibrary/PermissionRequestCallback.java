package top.ish.smartlibrary;

import java.util.ArrayList;

/**
 * Created by yanjie on 17/6/29.
 * 权限请求返回
 */

public interface PermissionRequestCallback {
    void onPermissionsGranted(int requestCode, ArrayList<String> deniedLists);//通过权限

    void onPermissionsDenied(int requestCode, ArrayList<String> deniedLists);//拒绝权限
}
