package mvpkotlin.dongnao.com.test.layoutmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import mvpkotlin.dongnao.com.test.R;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        // 实现了对recycleview的回弹效果。这个效果和viewPager回弹效果一样。
        // 因此下面的横向滚动的recyclerview效果就 和viewPager 一样。
        MyLinearLayoutManager2 layoutManager = new MyLinearLayoutManager2(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        MyAdapter adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);





    }
}
