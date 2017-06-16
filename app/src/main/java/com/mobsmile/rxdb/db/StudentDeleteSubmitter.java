package com.mobsmile.rxdb.db;

/**
 * Created by barismac on 15/06/2017.
 * Params ${PARAM}
 */

public class StudentDeleteSubmitter extends Caller<Void> {
    public StudentDeleteSubmitter(DBManager dbManager, long id) {
        super(() -> dbManager.observeDeleteStudent(id));
    }
}
