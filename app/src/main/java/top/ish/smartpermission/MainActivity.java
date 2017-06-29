package top.ish.smartpermission;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import top.ish.smartlibrary.SmartPermission;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button btn_check, btn_getSingle, btn_getMulti;
    private TextView tv_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_check = (Button) findViewById(R.id.btn_check);
        btn_getSingle = (Button) findViewById(R.id.btn_getSingle);
        btn_getMulti = (Button) findViewById(R.id.btn_getMulti);
        tv_log = (TextView) findViewById(R.id.tv_log);

        btn_check.setOnClickListener(this);
        btn_getSingle.setOnClickListener(this);
        btn_getMulti.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        tv_log.setText("");
        switch (v.getId()){
            case R.id.btn_check:

                ArrayList<String> result = SmartPermission.getInstance().hasPermission(MainActivity.this,"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.RECORD_AUDIO");
                tv_log.setText(String.valueOf(result.toString()));
                break;
            case R.id.btn_getSingle:
                break;
            case R.id.btn_getMulti:
                break;

        }
    }
}
