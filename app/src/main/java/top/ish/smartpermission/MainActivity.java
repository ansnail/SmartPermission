package top.ish.smartpermission;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import top.ish.smartlibrary.PermissionRequestCallback;
import top.ish.smartlibrary.SmartPermission;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_check, btn_getSingle;
    private TextView without_tv;
    private TextView denied_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_check = (Button) findViewById(R.id.btn_check);
        btn_getSingle = (Button) findViewById(R.id.btn_getSingle);
        without_tv = (TextView) findViewById(R.id.without_tv);
        denied_tv = (TextView) findViewById(R.id.denied_tv);

        btn_check.setOnClickListener(this);
        btn_getSingle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        without_tv.setText("");
        denied_tv.setText("");
        switch (v.getId()) {
            case R.id.btn_check:
//                ArrayList<String> result = SmartPermission.getInstance()
//                        .hasPermission(MainActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA");
//                without_tv.setText(String.valueOf(result.toString()));

                Toast.makeText(this, String.valueOf(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, "android.permission.CAMERA")), Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_getSingle:
                SmartPermission.getInstance()
                        .context(MainActivity.this)
                        .permission("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA")
                        .request(1, new PermissionRequestCallback() {
                            @Override
                            public void onPermissionsGranted(int requestCode, ArrayList<String> deniedLists) {
                                Toast.makeText(MainActivity.this, "tongguola", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionsDenied(int requestCode, ArrayList<String> deniedLists) {
                                Toast.makeText(MainActivity.this, "juejuela", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }

}
