package mvpkotlin.dongnao.com.test.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * 自定义 LayoutManager
 */
public class MyLayoutManager extends RecyclerView.LayoutManager {
    //自定义layoutManager必须步骤：生成 LayoutParams
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    //自定义layoutManager必须步骤： 负责摆放 ，测量 子控件

    /**
     * @param recycler recylerview 的第二级回收池
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        // 摆放步骤：

        //1：摆放前异常处理：如果recycleview正在执行动画，停止摆放，直接return.
        //   如果item数量没有了 也停止摆放，直接返回。
        if (state.isPreLayout() || getItemCount() <= 0) {
            return;
        }
        //2： 在摆放子view前，需要对所有的子view 放入一级缓存scrap 中。 同时从二级缓存中移除。调用这个方法就可以了。
        detachAndScrapAttachedViews(recycler);

        // 3： 测量子view : 在测量子view前需要将子view 添加到 RecyclerView中。 子view从缓存池中获取.
        // 一个view没有被添加到ViewGroup中时，是无法知道宽高的。所以这里要添加到RecyclerView 中

        int viewSize = getItemCount();
        for (int i = 0; i < viewSize; i++) {

            //3.1 从缓存中获取view 并添加到recyclerview
            View view = recycler.getViewForPosition(i);
            addView(view);

            //3.2 测量子view
//            measureChildWithMargins(view,0,0);
            measureChild(view, 0, 0);

            // 3.3 测量完以后 我们就可以获取子view的宽高了。
            // 下面两种方式都可以。
            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);
//            int viewWidth = view.getMeasuredWidth();
//            int viewHeight = view.getMeasuredHeight();


            //3.4：自己的业务： 根据我们的业务 计算每一个item的 left,top,right,bottom.

            // 3.5 将计算的item的left,top,right,bottom. 封装在Rect 对象中，并保存。
//            Rect r = list.get(i);
//            if(r==null)
//                    r = new Rect();
            // 计算的item的left,top,right,bottom ,封装在Rect 对象中
//            list.add(r);


        }

        //4: 处理滑动过程中view的添加和删除
        //   当view被滑出屏幕时，回收view，同时从回收池取取一个view，添加进来。
        int total = getChildCount();
        Rect visiableRect = new Rect();

        for (int i = 0; i < total; i++) {
            View v = getChildAt(i);
            Rect inVisiableRect = new Rect(); // 保存的item的Rect：Rect r = list.get(i)
            //  使用Rect 工具判断 是否在可见区域, intersects : 如果两个矩形存在重叠区域，就返回true
            if (!Rect.intersects(visiableRect, inVisiableRect)) {

                //4.1 view 已经不可见了 ，回收view

                detachAndScrapView(v, recycler);
            } else {
                //4.2  从回收池取一个view 摆放进来。

                View v2 = recycler.getViewForPosition(i);
                // 摆放进来
                measureChildWithMargins(v2, 0, 0);
                addView(v2);
                //根据业务摆放
                layoutDecorated(v2, 0, 0, 0, 0);
            }
        }


    }

    //5:自定义layoutManager必须步骤：如果要竖直滑动的 ，必须复写此方法。

    /**
     *
     * @param dy  滑动方向永远时第一个点 - 第二个点，所以
     *            正数：手指向上滑动
     *            负数： 手指向下滑动
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //5.1: 动画边界处理： 滑动到顶部和底部判断

        boolean isScrollTop = true;  // 滑动到顶部
        boolean isScrollBottom = false; // 滑动到底部
        if (isScrollTop) {

            // 重置 dy
        } else if (isScrollBottom) {
            // 重置 dy
        }


        // 竖直滑动容器的item
        offsetChildrenVertical(dy);

        return super.scrollVerticallyBy(dy, recycler, state);
    }

    //5:自定义layoutManager必须步骤：如果水平滑动的话 必须复写此方法。
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        // 横向滑动容器内的item
        offsetChildrenHorizontal(dx);
        return super.scrollHorizontallyBy(dx, recycler, state);
    }


    // 自定义layoutManager必须步骤：返回true: 表示 允许水平滑动
    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    // 自定义layoutManager必须步骤：返回true: 表示允许竖直滑动
    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }
}
