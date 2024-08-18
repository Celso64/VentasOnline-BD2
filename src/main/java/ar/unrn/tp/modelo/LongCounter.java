package ar.unrn.tp.modelo;

public class LongCounter implements IdCounter<Long> {

    private Long idActual;

    public LongCounter() {
        this.idActual = 0L;
    }

    public Long getId(){
        Long actual = this.idActual;
        idActual++;
        return actual;
    }
}
