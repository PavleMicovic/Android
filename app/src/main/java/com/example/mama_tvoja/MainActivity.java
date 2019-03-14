package com.example.mama_tvoja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button display;
    EditText city_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display= findViewById(R.id.display);
        city_name=(EditText)findViewById(R.id.city_name);
        display.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.display:
                Intent intent=new Intent(MainActivity.this, Forecast.class);
                intent.putExtra("city_name", city_name.getText());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
