package com.nzh.note.optimize.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {

    public Holder(@NonNull View itemView) {
        super(itemView);
    }

    public void createViewHolder() {
        RecyclerView r = null;
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        View v = null;
        Holder holder = new Holder(v);
        pool.putRecycledView(holder);
        r.setRecycledViewPool(pool);
    }
}