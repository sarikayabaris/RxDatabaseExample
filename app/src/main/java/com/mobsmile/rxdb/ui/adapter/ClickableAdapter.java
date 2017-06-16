package com.mobsmile.rxdb.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public abstract class ClickableAdapter<T, VH extends ClickableViewHolder<T>> extends RecyclerView.Adapter<VH> {
    protected final List<T> mItems = new ArrayList<>();
    private final PublishSubject<T> mItemClickedSubject = PublishSubject.create();

    public void setItems(List<T> items) {
        if (items != null) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void appendItems(List<T> items) {
        if (items != null) {
            int previousSize = mItems.size();
            mItems.addAll(items);
            notifyItemRangeInserted(previousSize, items.size());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Observable<T> observeItemClicks() {
        return mItemClickedSubject.asObservable();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, viewType, mItemClickedSubject);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindView(mItems.get(position));
    }

    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType, PublishSubject<T> itemClickedSubject);

}