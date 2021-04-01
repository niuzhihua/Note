package mvpkotlin.dongnao.com.test.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

/**
 * 自定义 LinearLayoutManager
 */
public class MyLinearLayoutManager2 extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {

    // 知识点1 ： 滑动方向判断
    // 竖直滑动方向判断： 正数 ：手指向上滑 负数：手指向下滑动
    int dy;
    // 水平滑动方向判断： 正数：手指向左滑  负数： 手指向右滑动
    int dx;

    // 知识点3：recyclerview的回弹工具类
    // 专门针对 recyclerview 对item进行回弹处理的 工具类。
    PagerSnapHelper snapHelper;

    Context context;

    public MyLinearLayoutManager2(Context context) {
        super(context);
        this.context = context;
        snapHelper = new PagerSnapHelper();
    }

    // 滑动状态监听
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        // 手指松开后当前选中的item。
        View view = snapHelper.findSnapView(this);
        int position = getPosition(view);
        System.out.println("position:"+position);

        //为什么出发两遍？
        // 如果要在这里设置onSelect选中监听，那么回调方法也会出发两遍，怎么解决？

        // 手指松开后 触发
        if(state == RecyclerView.SCROLL_STATE_IDLE ){
            System.out.println("item 归位");
        }
    }

    // 知识点2：添加item 滑动监听 addOnChildAttachStateChangeListener

    /**
     * 当一个RecyclerView 对象被添加到window对象 之后 回调此方法。
     * 可以在这里 设置 child的监听
     *
     * @param view
     */
    @Override
    public void onAttachedToWindow(RecyclerView view) {

        // 监听 item 滑动状态接口：
        // 当item被 滑出去 和 或者 添加进RecyclerView  都会出发这个接口。
        view.addOnChildAttachStateChangeListener(this);
        // 设置 专门针对recyclerview的回弹效果。简单。
        snapHelper.attachToRecyclerView(view);
        super.onAttachedToWindow(view);
    }

    // child 被添加进recycleview
    @Override
    public void onChildViewAttachedToWindow(View view) {
        int position = getPosition(view);
        System.out.println("child 被添加进recycleview");

        // 知识点2.1：
        // 根据滑动方向 dy,dx 做业务处理。
    }

    // child 被移除进recycleview
    @Override
    public void onChildViewDetachedFromWindow(View view) {
        // 知识点2.2：
        // 根据滑动方向 dy,dx 做业务处理。

        System.out.println("child 被移除 出recycleview");
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        this.dx = dx;

        if (dx < 0) {
            // 手指向右滑
            //System.out.println("手指向右滑,View向右滑");

        } else {
           // System.out.println("手指向左滑，View向左滑");
        }
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        this.dy = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }


}
