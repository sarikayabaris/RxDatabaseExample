package com.mobsmile.rxdb.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobsmile.rxdb.R;
import com.mobsmile.rxdb.db.DBManager;
import com.mobsmile.rxdb.db.StudentDeleteSubmitter;
import com.mobsmile.rxdb.db.StudentQuery;
import com.mobsmile.rxdb.db.model.Student;
import com.mobsmile.rxdb.ui.activity.DetailsActivity;
import com.mobsmile.rxdb.ui.adapter.StudentListAdapter;
import com.mobsmile.rxdb.ui.presenter.StudentsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * This file created by barissarikaya on 6/14/17.
 */

public class DBListFragment extends Fragment implements StudentsPresenter.View {
    boolean mDualPane;
    int mCurCheckPosition = 0;
    @BindView(R.id.slider_recycler_view)
    public RecyclerView recyclerView;
    StudentListAdapter adapter = new StudentListAdapter();
    StudentsPresenter presenter = new StudentsPresenter(new StudentQuery(new DBManager()), new StudentDeleteSubmitter(new DBManager(), 1));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment, container, false);
        ButterKnife.bind(this, view);
        presenter.attach(this);
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public Observable<Student> onListItemClicked() {
        return adapter.observeItemClicks();
    }

    @Override
    public void displayAllStudents(List<Student> students) {
        adapter.setItems(students);
    }

    @Override
    public void displayStudent(Student student) {

        if (mDualPane) {
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
            if (details == null) {
                details = DetailsFragment.newInstance((int) student.getId());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);

            // pass the current position
            intent.putExtra("index", (int) student.getId());

            startActivity(intent);
        }
    }

    @Override
    public Observable<Student> onListItemLongClicked() {
        return adapter.observeLongClickItem().flatMap(student -> showAndObserveDeleteDialog());
    }

    private Observable<Student> showAndObserveDeleteDialog() {
        PublishSubject<Student> yesClickSubject = PublishSubject.create();
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete")
                .setMessage("Do you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (DialogInterface dialog, int which) -> yesClickSubject.onNext(null))
                .setNegativeButton(android.R.string.no, null)
                .show();

        return yesClickSubject;
    }
}