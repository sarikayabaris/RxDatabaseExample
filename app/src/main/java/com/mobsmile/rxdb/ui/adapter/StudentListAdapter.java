package com.mobsmile.rxdb.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobsmile.rxdb.R;
import com.mobsmile.rxdb.db.model.Student;
import com.mobsmile.rxdb.ui.fragment.StudentListViewHolder;

import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public class StudentListAdapter extends ClickableAdapter<Student, StudentListViewHolder> {
    private PublishSubject<Student> publishSubjectLongPress = PublishSubject.create();

    @Override
    protected StudentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType, PublishSubject<Student> itemClickedSubject) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        StudentListViewHolder viewHolder = new StudentListViewHolder(itemView, itemClickedSubject, publishSubjectLongPress);
        ButterKnife.bind(viewHolder, itemView);
        return viewHolder;
    }

    public Observable<Student> observeLongClickItem() {
        return publishSubjectLongPress.asObservable();
    }
}
