package com.mobsmile.rxdb.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobsmile.rxdb.R;
import com.mobsmile.rxdb.RxApp;
import com.mobsmile.rxdb.db.model.Student;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This file created by barissarikaya on 6/14/17.
 */

public class DetailsFragment extends Fragment {
    @BindView(R.id.student_name)
    TextInputLayout nameLayout;
    @BindView(R.id.student_surname)
    TextInputLayout surnameLayout;
    @BindView(R.id.student_update_button)
    Button updateButton;
    Student student;

    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update, container, false);
        ButterKnife.bind(this, root);
        nameLayout.getEditText().setText(student.getName());
        surnameLayout.getEditText().setText(student.getSurname());
        updateButton.setOnClickListener(v -> {
            student.setName(nameLayout.getEditText().getText().toString());
            student.setSurname(surnameLayout.getEditText().getText().toString());
            RxApp.getDaoSession().getStudentDao().rx().update(student);
            getActivity().finish();
        });
        return root;
    }
}