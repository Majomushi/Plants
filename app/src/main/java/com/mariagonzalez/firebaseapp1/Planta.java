package com.mariagonzalez.firebaseapp1;

import androidx.annotation.NonNull;

public class Planta {
    // Clase basica que representa lo que es una planta.
    private String nombreComun;
    private String nombreCientifico;
    private String familia;
    private Boolean especiePrincipal;

    @NonNull
    @Override
    public String toString() {
        return this.nombreComun;
    }

    public Boolean getEspeciePrincipal(){
        return this.especiePrincipal;
    }

    public void setEspeciePrincipal(Boolean especiePrincipal){
        this.especiePrincipal = especiePrincipal;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }
}
