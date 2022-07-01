package com.smapps.agenda.utils;

import androidx.annotation.NonNull;

public enum JourLibelleEnum {
    Dimanche,
    Lundi,
    Mardi,
    Mercredi,
    Jeudi,
    Vendredi,
    Samedi;

    @NonNull
    public String toString() {
        if (this.equals(Dimanche)) {
            return "Dimanche";
        } else if (this.equals(Lundi)) {
            return "Lundi";
        } else if (this.equals(Mardi)) {
            return "Mardi";
        } else if (this.equals(Mercredi)) {
            return "Mercredi";
        } else if (this.equals(Jeudi)) {
            return "Jeudi";
        } else if (this.equals(Vendredi)) {
            return "Vendredi";
        } else {
            return "Samedi";
        }
    }

    public static JourLibelleEnum fromString(String str) {
        switch (str) {
            case "Dimanche":
                return Dimanche;
            case "Lundi":
                return Lundi;
            case "Mardi":
                return Mardi;
            case "Mercredi":
                return Mercredi;
            case "Jeudi":
                return Jeudi;
            case "Vendredi":
                return Vendredi;
            default:
                return Samedi;
        }
    }
}
