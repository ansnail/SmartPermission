package top.ish.smartlibrary;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * Created by yanjie on 17/6/28.
 * 工具类
 */

public class SmartUtils {


    /**
     * 展示一个默认的解释dialog
     *
     * @param mContext 上下文
     */
    public static void showDialog(final Context mContext) {
        new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle(mContext.getString(R.string.hiti))
                .setMessage(mContext.getString(R.string.content))
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        mContext.startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
