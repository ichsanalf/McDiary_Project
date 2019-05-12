package com.tubes.mcdiary;

import android.app.Activity;
import android.content.DialogInterface;

// Class ini menampilkan message kepada user
public class Messenger
{
    public static void showError(String title, String msg, Activity activity)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg).setPositiveButton(R.string.msg_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });
        builder.create().show();
    }
}
