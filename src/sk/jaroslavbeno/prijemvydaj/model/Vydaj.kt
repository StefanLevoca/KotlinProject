package sk.jaroslavbeno.prijemvydaj.model

import sk.jaroslavbeno.prijemvydaj.enums.KategoriaVydaju
import java.math.BigDecimal

class Vydaj(suma: BigDecimal, rok: Int,
            mesiac: Int, den: Int) : Obrat(suma, rok, mesiac, den) {
    var kategoria: KategoriaVydaju = KategoriaVydaju.NEZASPECIFIKOVANE

    override fun toString(): String {
        return "Vydaj: suma $suma, rok $rok, mesiac $mesiac, den $den"
    }
}