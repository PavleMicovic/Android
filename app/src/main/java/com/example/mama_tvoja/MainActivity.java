package com.example.mama_tvoja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    Button add_city;
    EditText city_name;
    ListView list;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_city=(Button) findViewById(R.id.btn_main);
        list=(ListView) findViewById(R.id.list_main);
        city_name=(EditText)findViewById(R.id.edit_main);
        list.setOnItemLongClickListener(this);
        adapter = new CustomAdapter(this);
        adapter.addElement(new List_element(getString(R.string.novi_sad)));
        adapter.addElement(new List_element(getString(R.string.beograd)));
        adapter.addElement(new List_element(getString(R.string.rakovac)));
        add_city.setOnClickListener(this);
        list.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        adapter.addElement(new List_element(city_name.getText().toString()));

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.removeElement(position);
        return true;
    }

}
