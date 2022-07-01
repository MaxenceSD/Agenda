package com.smapps.agenda.model;

import static com.smapps.agenda.db.util.Constantes.JOUR_ID;
import static com.smapps.agenda.db.util.Constantes.NOTE_DETAIL;
import static com.smapps.agenda.db.util.Constantes.NOTE_ID;
import static com.smapps.agenda.db.util.Constantes.NOTE_TITRE;
import static com.smapps.agenda.db.util.Constantes.T_NOTE;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = T_NOTE)
public class Note {

    @DatabaseField(generatedId = true, columnName = NOTE_ID)
    private Long id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = JOUR_ID, canBeNull = false)
    private Jour jour;

    @DatabaseField(columnName = NOTE_TITRE)
    private String titre;

    @DatabaseField(columnName = NOTE_DETAIL)
    private String detail;

    public Note() {
    }

    public Note(String titre, String detail) {
        this.id = id;
        this.titre = titre;
        this.detail = detail;
    }

    public Note(Jour jour, String titre, String detail) {
        this.jour = jour;
        this.titre = titre;
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
