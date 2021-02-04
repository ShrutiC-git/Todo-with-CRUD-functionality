package com.chaturvedi.shruti.todoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etUpdate;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etUpdate = findViewById(R.id.etUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);

        getSupportActionBar().setTitle("Edit Your Todo");

        etUpdate.setText(getIntent().getStringExtra(MainActivity.EDIT_ITEM));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an intent
                Intent intent = new Intent();

                ///Putting the value in the key that we got from the intent as the same key...
                intent.putExtra(MainActivity.EDIT_ITEM, etUpdate.getText().toString());

                //Pass the position to supply to the main and the adapter to edit the UI
                intent.putExtra(MainActivity.EDIT_POSITION, getIntent().getExtras().getInt(MainActivity.EDIT_POSITION));

                //setting the result (we have startActivityForResult, this will pass the code and the data)
                setResult(RESULT_OK, intent);

                //Finish the activity
                finish();

            }
        });
    }
}