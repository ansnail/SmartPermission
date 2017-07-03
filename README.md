## 目的：让权限申请更简单
## 用法
1. 在activity或fragment的基类里面覆写onRequestPermissionsResult方法，如下
```
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SmartPermission.getInstance().dealRequestPermissionsResult(requestCode, permissions, grantResults);
    }
```
2.在用到的地方请求需要的权限即可，例如：
```
SmartPermission.getInstance()
            .context(MainActivity.this)
            .permission("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA")
            .request(1, new PermissionRequestCallback() {
                @Override
                public void onPermissionsGranted(int requestCode, ArrayList<String> deniedLists) {
                    Toast.makeText(MainActivity.this, "通过的权限", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionsDenied(int requestCode, ArrayList<String> deniedLists) {
                    Toast.makeText(MainActivity.this, "拒绝的权限", Toast.LENGTH_SHORT).show();
                }
            });
```
回调里面可以进行自己业务的处理

## 提供的接口
1.判断是否有某个或某几个权限
> public boolean hasPermission(@NonNull Context context, @NonNull String... permissions)

2.请求权限
```SmartPermission.getInstance()
            .context(context)
            .permission(permissions)
            .request(requestCode, PermissionRequestCallback);
```
