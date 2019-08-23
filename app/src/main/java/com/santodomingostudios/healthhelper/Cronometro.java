package com.santodomingostudios.healthhelper;

import java.util.Date;

public class Cronometro {
    private final Date fechaInicial = new Date();

    public int segundosPasados() {
        java.util.Date now = new java.util.Date();
        return (int)((now.getTime() - this.fechaInicial.getTime()) / 1000);
    }

    public int minutosPasados() {
        java.util.Date now = new java.util.Date();
        return (int)((now.getTime() - this.fechaInicial.getTime()) / 60000);
    }
}
