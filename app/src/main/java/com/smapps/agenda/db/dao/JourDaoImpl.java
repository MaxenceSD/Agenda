package com.smapps.agenda.db.dao;

import static com.smapps.agenda.db.util.Constantes.JOUR_STR_ID;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.smapps.agenda.db.ApplicationDatabase;
import com.smapps.agenda.model.Jour;

import java.util.Collection;
import java.util.concurrent.Callable;

public class JourDaoImpl {

    private static final String TAG = JourDaoImpl.class.getSimpleName();

    private final ApplicationDatabase applicationDatabase;

    private Dao<Jour, String> getJourDao() {
        return applicationDatabase.getJourDao();
    }

    public JourDaoImpl(Context context) {
        this.applicationDatabase = ApplicationDatabase.getInstance(context);
    }

    public Jour getJourByStrId(String strId) {
        Jour jour = null;
        try {
            jour = getJourDao().queryBuilder().where().eq(JOUR_STR_ID, strId).queryForFirst();
        } catch (Exception e) {
            Log.e(TAG, "getJourById: ", e);
        }
        return jour;
    }

    public void createOrUpdateJour(Jour jour) {
        try {
            Jour jourBdd = getJourByStrId(jour.getStrId());
            if (jourBdd != null) {
                getJourDao().update(jour);
            } else {
                getJourDao().create(jour);
            }
        } catch (Exception e) {
            Log.e(TAG, "createOrUpdateJour: ", e);
        }
    }

    public void createOrUpdateJours(Collection<Jour> jours) {
        try {
            getJourDao().callBatchTasks((Callable<Void>) () -> {
                for (Jour j : jours) {
                    createOrUpdateJour(j);
                }
                return null;
            });
        } catch (Exception e) {
            Log.e(TAG, "createOrUpdateJours: ", e);
        }
    }
}
