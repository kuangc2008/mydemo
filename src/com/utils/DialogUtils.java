package com.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class DialogUtils {

    public static  Dialog createRefreshDialog(Context context, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("确认刷新吗");
        builder.setMessage("刷新前，请保证当天笔记可已标记为链接了。 \n\n每天只能刷新一次");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("我保证",listener);
        return builder.create();
    }
}
