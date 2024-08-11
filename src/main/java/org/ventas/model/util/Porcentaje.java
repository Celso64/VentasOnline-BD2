package org.ventas.model.util;

public class Porcentaje {

    private final Integer porcentaje;

    public Porcentaje(Integer porcentaje) {
        if (esMenor(porcentaje) || esMayor(porcentaje)) throw new IllegalArgumentException("Porcentaje fuera de rango.");
        this.porcentaje = porcentaje;
    }

    public Double getCoeficiente(){
        return this.porcentaje / 100.0;
    }

    private Boolean esMenor(Integer numero){
        return numero < 0;
    }

    private Boolean esMayor(Integer numero){
        return numero > 100;
    }
}
