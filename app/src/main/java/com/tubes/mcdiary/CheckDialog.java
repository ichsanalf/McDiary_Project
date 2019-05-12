package com.tubes.mcdiary;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;



public class CheckDialog extends DialogFragment
{
    private android.app.AlertDialog.Builder builder;
    private DialogClickListener listener;


    CheckDialog(String title, String msg, Activity activity, DialogClickListener listener)
    {
        this.listener = listener;
        setDialog(title, msg, activity);
    }


    public void setDialog(String title, String msg, Activity activity)
    {
        builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(title);//Sets title for the dialog

        //Sets messages of buttons
        builder.setMessage(msg).setPositiveButton(R.string.msg_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                listener.onDialogOkClick();
            }
        }).setNegativeButton(R.string.msg_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                listener.onDialogCancelClick();
            }
        });

        builder.create();
    }


    public void show()
    {
        builder.show();
    }
}
