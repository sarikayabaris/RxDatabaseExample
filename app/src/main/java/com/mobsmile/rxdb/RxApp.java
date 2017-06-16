package com.mobsmile.rxdb;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mobsmile.rxdb.db.model.DaoMaster;
import com.mobsmile.rxdb.db.model.DaoSession;
import com.mobsmile.rxdb.db.model.Student;

import org.greenrobot.greendao.database.Database;

/**
 * Created by barismac on 15/06/2017.
 * Params ${PARAM}
 */

public class RxApp extends Application {
    public static final boolean ENCRYPTED = false;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "student-db-encrypted" : "student-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
