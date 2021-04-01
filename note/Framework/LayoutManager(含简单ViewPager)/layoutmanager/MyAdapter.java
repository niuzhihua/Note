package mvpkotlin.dongnao.com.test.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mvpkotlin.dongnao.com.test.R;

/**
 * Created by 31414 on 2018/8/12.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public MyAdapter(Context context){
        this.context =context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder h = (MyHolder) holder;
        h.textView.setText(String.valueOf(position));
        if(position%2==0){
            h.textView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        }else {
            h.textView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text);
        }
    }
}
