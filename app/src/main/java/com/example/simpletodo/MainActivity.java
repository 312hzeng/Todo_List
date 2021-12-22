package com.example.simpletodo;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String>items;

    Button btnAdd;
    EditText ETItem;
    RecyclerView rvItem;
    ItemsAdaptor itemsAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        ETItem = findViewById(R.id.ETItem);
        rvItem = findViewById(R.id.rvItem);

        loadItems();

        ItemsAdaptor.OnLongClickListener onLongClickListener = new ItemsAdaptor.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //delete
                items.remove(position);
                //notify the adapter
                itemsAdaptor.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        itemsAdaptor = new ItemsAdaptor(items, onLongClickListener);
        rvItem.setAdapter(itemsAdaptor);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TodoItem = ETItem.getText().toString();
                //add item to model
                items.add(TodoItem);
                //modify adapter
                itemsAdaptor.notifyItemInserted(items.size()-1);
                ETItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    //load item by reading every line of the file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("mainActivity", "Error reading Items", e);
            items = new ArrayList<>();
        }
    }
    //write item into the file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("mainActivity", "Error saving Items", e);
        }
    }
}