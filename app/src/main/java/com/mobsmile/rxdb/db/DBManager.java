package com.mobsmile.rxdb.db;

import com.mobsmile.rxdb.RxApp;
import com.mobsmile.rxdb.db.model.Student;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import rx.Observable;

/**
 * Created by barismac on 16/06/2017.
 * Params ${PARAM}
 */

public class DBManager {
    private RxQuery<Student> studentRxQuery;
    private RxDao<Student, Long> studentLongRxDao;

    public DBManager() {
        studentRxQuery = RxApp.getDaoSession().getStudentDao().queryBuilder().rx();
        studentLongRxDao = RxApp.getDaoSession().getStudentDao().rx();
    }

    public Observable<List<Student>> observeAllStudents() {
        return studentRxQuery.list();
    }

    public Observable<Void> observeDeleteStudent(long id) {
        return studentLongRxDao.deleteByKey(id);
    }
}
