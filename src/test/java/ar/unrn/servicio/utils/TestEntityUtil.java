package ar.unrn.servicio.utils;

import ar.unrn.tp.servicio.utils.EntityUtil;

public class TestEntityUtil<T> extends EntityUtil<T> {
    public TestEntityUtil() {
        super.nombre = "objectdb:myDbTestFile.tmp;drop";
    }
}
