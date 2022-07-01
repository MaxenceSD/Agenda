package com.smapps.agenda.utils;

import androidx.annotation.NonNull;

public enum MarquageEnum {
    AUCUN,
    DEMI,
    COMPLET;

    public MarquageEnum avancerEtape() {
        if (this.equals(AUCUN)) {
            return DEMI;
        } else if (this.equals(DEMI)) {
            return COMPLET;
        } else {
            return AUCUN;
        }
    }

    @NonNull
    public String toString() {
        if (this.equals(AUCUN)) {
            return "AUCUN";
        } else if (this.equals(DEMI)) {
            return "DEMI";
        } else {
            return "COMPLET";
        }
    }

    public static MarquageEnum fromString(String str) {
        if (str.equals("AUCUN")) {
            return AUCUN;
        } else if (str.equals("DEMI")) {
            return DEMI;
        } else {
            return COMPLET;
        }
    }
}
