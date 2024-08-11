package org.ventas.model.util;

import java.time.LocalDate;

public class RangoFechas {

    private final LocalDate inicio,fin;

    public RangoFechas(LocalDate inicio, LocalDate fin) {
        if (inicio.isAfter(fin)) throw new IllegalArgumentException("La fecha de Inicio es mayor a la de Fin");
        this.inicio = inicio;
        this.fin = fin;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }
}
