package com.smapps.agenda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.model.Note;

import java.sql.SQLException;

public class ApplicationDatabase extends OrmLiteSqliteOpenHelper {

    private static final String dbName = "AgendaDb";
    private static final int dbVersion = 1;

    private static ApplicationDatabase applicationDatabase = null;

    private Dao<Jour, String> jourDao;
    private Dao<Note, String> noteDao;

    public static ApplicationDatabase getInstance(Context context) {
        if (applicationDatabase == null) {
            applicationDatabase = new ApplicationDatabase(context);
        }
        return applicationDatabase;
    }

    public ApplicationDatabase(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Jour.class);
            TableUtils.createTable(connectionSource, Note.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Jour, String> getJourDao() {
        try {
            if (jourDao == null) {
                jourDao = getDao(Jour.class);
            }
        } catch (Exception e) {
            Log.e("ApplicationDatabase", "getJourDao: ", e);
        }
        return jourDao;
    }

    public Dao<Note, String> getNoteDao() {
        try {
            if (noteDao == null) {
                noteDao = getDao(Note.class);
            }
        } catch (Exception e) {
            Log.e("ApplicationDatabase", "noteDao: ", e);
        }
        return noteDao;
    }
}
