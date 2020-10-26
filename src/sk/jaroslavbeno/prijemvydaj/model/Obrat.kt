package sk.jaroslavbeno.prijemvydaj.model

import java.math.BigDecimal

abstract class Obrat(val suma: BigDecimal, val rok: Int,
                 val mesiac: Int, val den: Int) {
    var popis = "";
}