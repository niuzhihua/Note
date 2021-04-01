package com.nzh.note.view.dispatchEvent.Sample2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nzh.note.R;

import java.util.ArrayList;
import java.util.List;

public class Demo2Activity extends AppCompatActivity {
    private List<View> viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
//        https://www.imooc.com/article/283253
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewList = new ArrayList<>();
        initData(true);
        viewPager.setAdapter(new MyPagerAdapter(viewList));

    }

    private void initData(boolean flag) {
        for (int j = 0; j < 4; j++) {
            View view;
            if (flag) {
//                ListView listView = new ListView(this);
                ListViewFixed listView = new ListViewFixed(this);
                List<String> dataList = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    dataList.add("Item " + i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
                listView.setAdapter(adapter);
                view = listView;
            } else {
                TextView textView = new TextView(this);
                textView.setBackgroundColor(Color.GRAY);
                textView.setGravity(Gravity.CENTER);
                textView.setText("TextView " + j);
                textView.setClickable(true);
                view = textView;
            }
            viewList.add(view);
        }
    }


    class MyPagerAdapter extends PagerAdapter {
        List<View> views;

        public MyPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);

            return v;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "position" + position;
        }
    }


}