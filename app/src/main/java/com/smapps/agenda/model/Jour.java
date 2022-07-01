package com.smapps.agenda.model;

import static com.smapps.agenda.db.util.Constantes.JOUR_DATE;
import static com.smapps.agenda.db.util.Constantes.JOUR_ID;
import static com.smapps.agenda.db.util.Constantes.JOUR_LIBELLE;
import static com.smapps.agenda.db.util.Constantes.JOUR_MARQUAGE;
import static com.smapps.agenda.db.util.Constantes.JOUR_NOTE;
import static com.smapps.agenda.db.util.Constantes.JOUR_STR_ID;
import static com.smapps.agenda.db.util.Constantes.T_JOUR;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.smapps.agenda.utils.JourLibelleEnum;
import com.smapps.agenda.utils.MarquageEnum;

import java.util.Collection;
import java.util.Date;

@DatabaseTable(tableName = T_JOUR)
public class Jour {

    @DatabaseField(generatedId = true, columnName = JOUR_ID)
    private Long id;

    @DatabaseField(columnName = JOUR_STR_ID)
    private String strId;

    @DatabaseField(columnName = JOUR_DATE)
    private Date date;

    @DatabaseField(columnName = JOUR_LIBELLE)
    private JourLibelleEnum libelle;

    @DatabaseField(columnName = JOUR_MARQUAGE)
    private MarquageEnum marquage;

    @ForeignCollectionField(columnName = JOUR_NOTE, eager = true)
    private Collection<Note> notes;

    public Jour() {
    }

    public Jour(Long id, String strId, Date date, JourLibelleEnum libelle, MarquageEnum marquage, Collection<Note> notes) {
        this.id = id;
        this.strId = strId;
        this.date = date;
        this.libelle = libelle;
        this.marquage = marquage;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public JourLibelleEnum getLibelle() {
        return libelle;
    }

    public void setLibelle(JourLibelleEnum libelle) {
        this.libelle = libelle;
    }

    public MarquageEnum getMarquage() {
        return marquage;
    }

    public void setMarquage(MarquageEnum marquage) {
        this.marquage = marquage;
    }

    public Collection<Note> getNotes() {
        return notes;
    }

    public void setNotes(Collection<Note> notes) {
        this.notes = notes;
    }
}
