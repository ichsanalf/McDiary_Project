package com.tubes.mcdiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements RecycleItemOnClickListener {
    private static Context context;
    private ListAdapter lad;
    private FloatingActionButton floatingNewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingNewButton = (FloatingActionButton) findViewById(R.id.floatingNewButton);

        try {
            lad = new ListAdapter
                    (
                            MainActivity.getAppContext(),
                            new LinearLayoutManager(this),
                            (RecyclerView) findViewById(R.id.list)
                    );
        } catch (Exception e) {
            Messenger.showError("", e.getMessage(), this);
        }

        lad.setClickListener(this);
    }

    // mengarahkan ke NewDataActivity class ketika button New diclik
    public void newButtonClick(View v) {
        Intent intent = new Intent(MainActivity.this, NewDataActivity.class);
        intent.putExtra("MODE", "new");
        this.startActivityForResult(intent, 1);
    }

    // activity result saat add, edit, remove data
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String mode = data.getStringExtra("MODE");

                if (mode.equals("new"))//jika user ingin menambahkan data
                {
                    try {
                        String text = data.getStringExtra("text");
                        lad.addData(new UserData(text));
                    } catch (Exception e) {
                        Messenger.showError("", e.getMessage(), this);
                    }
                } else if (mode.equals("edit"))// jika user ingin mengedit data
                {
                    String text = data.getStringExtra("text");
                    String id = data.getStringExtra("id");

                    try {
                        lad.editData(new UserData(text, id));
                    } catch (Exception e) {
                        Messenger.showError("", e.getMessage(), this);
                    }
                } else if (mode.equals("delete"))// jika user ingin meremove atau delete data
                {
                    String text = data.getStringExtra("text");
                    String id = data.getStringExtra("id");

                    try {
                        lad.removeData(new UserData(text, id));
                    } catch (Exception e) {
                        Messenger.showError("", e.getMessage(), this);
                    }
                }
            }
        }
    }

    // Return app context
    public static Context getAppContext() {
        return MainActivity.context;
    }

    // On item click pada setiap data yang di click,
    // maka akan menampilkan NewDataActivity mode Edit
    @Override
    public void onItemClick(View v, int position) {
        UserData data = lad.getData(position); // mengampbil posisi data pada RV

        Intent intent = new Intent(MainActivity.this, NewDataActivity.class);
        intent.putExtra("text", data.getText()); // mengambil text data
        intent.putExtra("id", data.getId()); // mengambil id Data
        intent.putExtra("MODE", "edit"); // mengubah mode jadi EDIT
        this.startActivityForResult(intent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));

                break;



        }
        return true;
    }

}
