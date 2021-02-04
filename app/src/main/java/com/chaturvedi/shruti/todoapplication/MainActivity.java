package com.chaturvedi.shruti.todoapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvList;
    EditText etItem;
    Button btnAdd;
    ItemsAdapter itemsAdapter;
    ArrayList<Boolean> checked;
    public List<String> items;
    public static final String EDIT_ITEM = "edit_item";
    public static final String EDIT_POSITION = "edit_position";
    public static final int REQ_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rvList);
        etItem = findViewById(R.id.etItem);
        btnAdd = findViewById(R.id.btnAdd);

        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongCLicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //Notify the adapter to re-render
                itemsAdapter.notifyItemRemoved(position);
                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                //Here is where we will be implementing the logic to update the UI

                //Creating the intent
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                //Adding Data to the event
                intent.putExtra(EDIT_ITEM, items.get(position));
                intent.putExtra(EDIT_POSITION, position);
                startActivityForResult(intent, REQ_CODE);
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvList.setAdapter(itemsAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredTodo = etItem.getText().toString();
                items.add(enteredTodo);

                //Notify the adapter that an item has been added
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "ToDo Added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    public File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //Read the file
    public void loadItems() {

        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            items = new ArrayList<>();
        }
    }

    //Write the file
    public void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Main", "Error in Main", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            //get the data from the intent (passed as data to the params)
            String itemText = data.getStringExtra(EDIT_ITEM);
            int position = data.getExtras().getInt(EDIT_POSITION);

            //update the items model
            items.set(position, itemText);

            //Notify the adapter of item changed
            itemsAdapter.notifyItemChanged(position);

            //save the changes
            saveItems();

            Log.d("MainActivity", "Item updated");
            Toast.makeText(this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to the Activity");
        }
    }
}