package top.ish.smartpermission;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import top.ish.smartlibrary.SmartPermission;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SmartPermission.getInstance().dealRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
