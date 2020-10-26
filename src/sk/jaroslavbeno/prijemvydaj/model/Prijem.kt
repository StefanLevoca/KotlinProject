package sk.jaroslavbeno.prijemvydaj.model

import java.math.BigDecimal

class Prijem(suma: BigDecimal, rok: Int,
             mesiac: Int, den: Int) : Obrat(suma, rok, mesiac, den) {

    override fun toString(): String {
        return "Prijem: suma $suma, rok $rok, mesiac $mesiac, den $den"
    }
}