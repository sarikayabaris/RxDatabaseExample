package com.mobsmile.rxdb.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.mobsmile.rxdb.R;
import com.mobsmile.rxdb.db.model.Student;
import com.mobsmile.rxdb.ui.adapter.ClickableViewHolder;

import butterknife.BindView;
import rx.subjects.PublishSubject;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public class StudentListViewHolder extends ClickableViewHolder<Student> {
    @BindView(R.id.student_item_name)
    TextView name;
    @BindView(R.id.student_item_surname)
    TextView surname;
    Student data;

    public StudentListViewHolder(View itemView, PublishSubject<Student> itemClickedSubject, PublishSubject<Student> itemLongClickedSubject) {
        super(itemView, itemClickedSubject);
        itemView.setOnLongClickListener(it -> {
            itemLongClickedSubject.onNext(data);
            return true;
        });
    }

    @Override
    protected void bind(Student student) {
        data = student;
        name.setText(student.getName());
        surname.setText(student.getSurname());
    }
}
