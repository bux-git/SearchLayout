package com.example.lym.searchlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final SearchLayout searchLayout = findViewById(R.id.searchLayout);


       findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchLayout.showSoftInput();
           }
       });

       findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchLayout.hideSoftInput();
           }
       });
 /*
        SearchLayout searchLayout1 = findViewById(R.id.searchLayout1);
        searchLayout1.setLeftText("通用名称:")
                .setHint("例如阿维菌素");

        SearchLayout searchLayout2 = findViewById(R.id.searchLayout2);
        searchLayout2
                .setHint("例如阿维菌素");*/
    }
}
