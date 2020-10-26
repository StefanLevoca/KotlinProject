package sk.jaroslavbeno.prijemvydaj

import sk.jaroslavbeno.prijemvydaj.enums.KategoriaVydaju
import sk.jaroslavbeno.prijemvydaj.enums.TypObratu
import sk.jaroslavbeno.prijemvydaj.model.Obrat
import sk.jaroslavbeno.prijemvydaj.model.Prijem
import sk.jaroslavbeno.prijemvydaj.model.Vydaj
import sk.jaroslavbeno.prijemvydaj.service.PracaSObratmiVSubore
import java.io.File
import java.math.BigDecimal

/**
 * vlozit novy obrat (typ - obrat)
 * dajPocetObratov
 * dajNazvyStlpcov
 * dajVsetkyZaznamy / rok / rok, mesiac / rok, mesiac, den
 * dajVsetkyPrijmy / rok / rok, mesiac / rok, mesiac, den
 * dajVsetkyVydaje / rok / rok, mesiac / rok, mesiac, den
 *
 *
 * prijem =
 * vydaj = kategoria
 * obrat = suma , popis, rok, mesiac, den
 */
fun main(args: Array<String>) {
    val prijem = Prijem(BigDecimal.ONE,2019,12,20)
    prijem.popis = "toto je prijem"

    val vydaj = Vydaj(BigDecimal.TEN,2019,12,20)
    vydaj.kategoria = KategoriaVydaju.JEDLO
    vydaj.popis = "toto je vydaj"

    val subor = File("prijemVydajDb.txt")
//    val pocetObratov = PracaSObratmiVSubore(subor).dajPocetObratov()
//    println(pocetObratov)
//    println("===========================")
//    PracaSObratmiVSubore(subor).dajNazvyStlpcov().forEach{ print(it + ", ")}
//    println()

//    val zaznamyPrijem = PracaSObratmiVSubore(subor).dajZaznamy(TypObratu.PRIJEM)
//    printObraty(zaznamyPrijem)
//    val zaznamyVydaj = PracaSObratmiVSubore(subor).dajZaznamy(TypObratu.VYDAJ)
//    printObraty(zaznamyVydaj)

    PracaSObratmiVSubore(subor).addObrat(prijem)
    PracaSObratmiVSubore(subor).addObrat(vydaj)

    val zaznamyALl = PracaSObratmiVSubore(subor).dajZaznamy()
    printObraty(zaznamyALl)
    println("++++++++++++++++++++++++++++++++++++++")
    printObraty(PracaSObratmiVSubore(subor).dajZaznamy(TypObratu.ALL, 2019))
    println("++++++++++++++++++++++++++++++++++++++")
    printObraty(PracaSObratmiVSubore(subor).dajZaznamy(TypObratu.ALL, 2019, 12))
    println("++++++++++++++++++++++++++++++++++++++")
    printObraty(PracaSObratmiVSubore(subor).dajZaznamy(TypObratu.ALL, 2019, 12, 20))
    println("++++++++++++++++++++++++++++++++++++++")
    PracaSObratmiVSubore(subor).balanceInYear(2019)
}

fun printObraty(zaznamyALl: List<Obrat>) {
    for(obrat in zaznamyALl){
        if(obrat is Prijem){
            println("prijem> (${obrat.suma}, ${obrat.rok}, " +
                    "${obrat.mesiac}, ${obrat.den}, ${obrat.popis})")
        }else if(obrat is Vydaj){
            println("vydaj> (${obrat.suma}, ${obrat.rok}, " +
                    "${obrat.mesiac}, ${obrat.den}, ${obrat.popis}, " +
                    "${obrat.kategoria})")

        }
    }
}
