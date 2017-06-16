package com.mobsmile.rxdb.ui.presenter;

import com.mobsmile.rxdb.db.StudentDeleteSubmitter;
import com.mobsmile.rxdb.db.StudentQuery;
import com.mobsmile.rxdb.db.model.Student;

import java.util.List;

import rx.Observable;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public class StudentsPresenter extends Presenter<StudentsPresenter.View> {

    private StudentQuery studentQuery;
    private StudentDeleteSubmitter studentDeleteSubmitter;

    public StudentsPresenter(StudentQuery studentQuery, StudentDeleteSubmitter studentDeleteSubmitter) {
        this.studentQuery = studentQuery;
        this.studentDeleteSubmitter = studentDeleteSubmitter;
    }

    @Override
    protected void onViewAttached(StudentsPresenter.View view) {
        super.onViewAttached(view);

        addSubscription(studentQuery.observeData().subscribe(view::displayAllStudents));

        addSubscription(view.onListItemClicked().subscribe(view::displayStudent));
        addSubscription(view.onListItemLongClicked().subscribe(it -> studentDeleteSubmitter.observeData()));
    }

    public interface View extends Presenter.View {

        Observable<Student> onListItemClicked();

        Observable<Student> onListItemLongClicked();

        void displayAllStudents(List<Student> students);

        void displayStudent(Student student);

    }
}
