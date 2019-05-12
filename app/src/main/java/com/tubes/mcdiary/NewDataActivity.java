package com.tubes.mcdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewDataActivity extends AppCompatActivity implements DialogClickListener
{
    private EditText etext;
    private FloatingActionButton floatingRemoveButton;
    private String mode;
    private UserData data;
    private String dialogOption;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data_activity);//saveButtonClick cancelButtonClick
        etext = (EditText)findViewById(R.id.newText);
        floatingRemoveButton = (FloatingActionButton)findViewById(R.id.floatingDeleteButton);

        Intent intent = getIntent();
        mode = intent.getStringExtra("MODE");

        switch(mode)
        {
            case "new":
                floatingRemoveButton.hide();
                break;
            case "edit":
                String text = intent.getStringExtra("text");
                String id = intent.getStringExtra("id");
                data = new UserData(text, id);
                break;
            default:
                floatingRemoveButton.hide();
                break;
        }

        String text = getIntent().getStringExtra("text");

        if(text != null)
        {
            etext.setText(text, TextView.BufferType.EDITABLE);
        }

    }

    // Cancel activity sebelumya dan return parent activity
    private void cancelActivity()
    {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    // Fungsi jika salah satu switch mode untuk save, edit, atau do nothing pada data.
    // Lalu fungsi ini akan melakukan cancel activity sebelumnya dan return ke parent activity
    private void reactToUser()
    {
        switch(mode)
        {
            case "new":
            {
                String text = etext.getText().toString();
                if(!text.isEmpty())
                {
                    Intent intent = new Intent();
                    intent.putExtra("text", text);
                    intent.putExtra("MODE", "new");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    cancelActivity();
                }
            }
            break;

            case "edit":
            {
                String text = etext.getText().toString();
                String id = data.getId();

                if(text != null || !text.isEmpty())
                {
                    Intent intent = new Intent();
                    intent.putExtra("text", text);
                    intent.putExtra("id", id);
                    intent.putExtra("MODE", "edit");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    cancelActivity();
                }
            }
            break;

            default:
                cancelActivity();
                break;
        }
    }

    // view jika button save di click saat mode EDIT
    public void saveButtonClick(View v)
    {
        if(!mode.equals("new"))
        {
            CheckDialog dialog = new CheckDialog("Overwrite?", getString(R.string.msg_check_save), this, this);
            dialogOption = "SAVE";
            dialog.show();
        }
        else
        {
            reactToUser();
        }
    }

    //menampilkan dialog ke user jika ingin delete data
    public void deleteButtonClick(View v)
    {
        CheckDialog dialog = new CheckDialog("Delete?", getString(R.string.msg_check_delete), this, this);
        dialogOption = "DELETE";
        dialog.show();
    }

    // cancel activity sebelumnya, tidak menyimpan data yang diubah oleh user
    public void cancelButtonClick(View v)
    {
        CheckDialog dialog = new CheckDialog("Cancel?", getString(R.string.msg_check_cancel), this, this);
        dialogOption = "CANCEL";
        dialog.show();
    }


    @Override
    public void onDialogCancelClick()
    {
    }

    // fungsi ketika OK di click pada DELETE dialog
    @Override
    public void onDialogOkClick()
    {
        if(dialogOption.equals("DELETE"))
        {
            Intent intent = new Intent();
            intent.putExtra("text", data.getText());
            intent.putExtra("id", data.getId());
            intent.putExtra("MODE", "delete");
            setResult(RESULT_OK, intent);
            finish();
        }
        else if(dialogOption.equals("CANCEL"))
        {
            cancelActivity();
        }
        else
        {
            reactToUser();
        }
    }
}
