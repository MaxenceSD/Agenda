package com.smapps.agenda.service;

import android.content.Context;

import com.smapps.agenda.db.dao.JourDaoImpl;
import com.smapps.agenda.model.Jour;

import java.util.Calendar;
import java.util.Collection;

public class JourService {

    private final JourDaoImpl jourDao;

    public JourService(Context context) {
        this.jourDao = new JourDaoImpl(context);
    }

    /***********************************************************************************************
     * Méthodes relatives aux interactions avec la BDD
     **********************************************************************************************/

    public Jour getJourByStrId(String strId) {
        return this.jourDao.getJourByStrId(strId);
    }

    public void createOrUpdateJour(Jour jour) {
        this.jourDao.createOrUpdateJour(jour);
    }

    public void createOrUpdateJours(Collection<Jour> jours) {
        this.jourDao.createOrUpdateJours(jours);
    }

    /***********************************************************************************************
     * Méthodes relatives aux interactions l'objet Jour
     **********************************************************************************************/

    public String buildIdFromDate(Calendar date) {
        String j = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        if (j.length() == 1) {
            j = "0" + j;
        }

        String m = String.valueOf(date.get(Calendar.MONTH));
        if (m.length() == 1) {
            m = "0" + m;
        }

        String a = String.valueOf(date.get(Calendar.YEAR));
        if (a.length() == 1) {
            a = "0" + a;
        }

        return j + m + a;
    }
}
