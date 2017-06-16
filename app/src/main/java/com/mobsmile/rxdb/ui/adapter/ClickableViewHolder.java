package com.mobsmile.rxdb.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rx.subjects.PublishSubject;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public abstract class ClickableViewHolder<T> extends RecyclerView.ViewHolder {
    private T data;

    public ClickableViewHolder(View itemView, final PublishSubject<T> itemClickedSubject) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickedSubject.onNext(data);
            }
        });
    }

    public void bindView(T data) {
        this.data = data;
        bind(data);
    }

    protected abstract void bind(T data);
}