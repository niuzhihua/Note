package com.nzh.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nzh.note.launchmode.StandardActivity;
import com.nzh.note.kotlin.base.Config;
import com.nzh.note.kotlin.myContinueation.sample.AndroidSample.CoroutineActivity;


public class MainActivity extends AppCompatActivity {

    Config config;//= new Config(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = new Config(this);
    }


    public void propertySet(View view) {
        config.setName(String.valueOf(System.currentTimeMillis()));
        config.setAge(28);
        config.setLongProperty(System.currentTimeMillis());
        config.setXX(true);
        config.setMoney(180.88f);
        config.setFirstName("first name");
        Toast.makeText(this, "Set:" + config, Toast.LENGTH_SHORT).show();

    }

    public void propertyGet(View view) {

        Toast.makeText(this, "Get:" + config, Toast.LENGTH_SHORT).show();
    }

    public void callback2coroutine(View view) {

        startActivity(new Intent(this, CoroutineActivity.class));
    }

    public void testLaunchmode(View view) {
        startActivity(new Intent(this, StandardActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity is destroyed");
    }
}
