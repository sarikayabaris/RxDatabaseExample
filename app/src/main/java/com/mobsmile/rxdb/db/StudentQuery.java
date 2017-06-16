package com.mobsmile.rxdb.db;

import com.mobsmile.rxdb.db.model.Student;

import java.util.List;

/**
 * Created by barismac on 15/06/2017.
 * Params ${PARAM}
 */

public class StudentQuery extends Caller<List<Student>> {
    public StudentQuery(DBManager dbManager) {
        super(dbManager::observeAllStudents);
    }
}
