package com.tubes.mcdiary;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;


// untuk membuat dialog baru dengan 2 button dan message, saat membutuhkan action dari user
public class CheckDialog extends DialogFragment
{
    private android.app.AlertDialog.Builder builder;
    private DialogClickListener listener;

    //membuat dialog baru
    CheckDialog(String title, String msg, Activity activity, DialogClickListener listener)
    {
        this.listener = listener;
        setDialog(title, msg, activity);
    }

    //set dialog dengan :
    // title = judul dialog,
    // msg = pesan untuk user,
    // activity = activity yang akan di display di dialog
    public void setDialog(String title, String msg, Activity activity)
    {
        builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(title);//Sets title

        //Sets messages di buttons
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

    // fungsi untuk memanggil dialog untuk ditampilkan
    public void show()
    {
        builder.show();
    }
}
