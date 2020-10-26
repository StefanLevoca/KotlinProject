package sk.jaroslavbeno.prijemvydaj.service

import sk.jaroslavbeno.prijemvydaj.enums.TypObratu
import sk.jaroslavbeno.prijemvydaj.model.Obrat

interface PracaSObratmi {
    fun dajPocetObratov(): Int
    fun dajNazvyStlpcov(): List<String>
    fun dajZaznamy(typ: TypObratu = TypObratu.ALL): List<Obrat>
    fun dajZaznamy(typ: TypObratu = TypObratu.ALL,
                   rok: Int): List<Obrat>
    fun dajZaznamy(typ: TypObratu = TypObratu.ALL,
                   rok: Int, mesiac: Int): List<Obrat>
    fun dajZaznamy(typ: TypObratu = TypObratu.ALL,
                   rok: Int, mesiac: Int, den: Int): List<Obrat>
    fun addObrat(obrat: Obrat)
}