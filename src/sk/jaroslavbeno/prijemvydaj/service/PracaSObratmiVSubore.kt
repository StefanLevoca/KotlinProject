package sk.jaroslavbeno.prijemvydaj.service

import sk.jaroslavbeno.prijemvydaj.enums.KategoriaVydaju
import sk.jaroslavbeno.prijemvydaj.enums.TypObratu
import sk.jaroslavbeno.prijemvydaj.model.Obrat
import sk.jaroslavbeno.prijemvydaj.model.Prijem
import sk.jaroslavbeno.prijemvydaj.model.Vydaj
import java.io.File
import java.math.BigDecimal

class PracaSObratmiVSubore(val subor: File) : PracaSObratmi {

    val riadky = subor.readLines()

    override fun dajPocetObratov(): Int {
        //prvy riadok v subore
        return riadky[0].toInt()
    }

    override fun dajNazvyStlpcov(): List<String> {
        return riadky[1].split(';')
    }

    override fun dajZaznamy(typ: TypObratu): List<Obrat> {
        when (typ) {
            TypObratu.ALL -> {
                return convertToObraty(riadky.subList(2, riadky.size))
            }
            TypObratu.PRIJEM -> {
                return convertToPrijem(riadky.subList(2, riadky.size))
            }
            TypObratu.VYDAJ -> {
                return convertToVydaj(riadky.subList(2, riadky.size))
            }
        }
    }

    private fun convertToPrijem(subList: List<String>): List<Obrat> {
        var obraty = mutableListOf<Obrat>()
        for (s in subList) {
            val zaznamy = s.split(";")

            if (zaznamy[1].equals("PRIJEM")) {
                //suma; typ; popis; rok; mesiac; den
                var prijem = createPrijem(zaznamy)
                obraty.add(prijem)
            }
        }

        return obraty
    }

    private fun createPrijem(zaznamy: List<String>): Prijem {
        var prijem = Prijem(
            BigDecimal(zaznamy[0].toInt()), zaznamy[3].toInt(),
            zaznamy[4].toInt(), zaznamy[5].toInt()
        )
        prijem.popis = zaznamy[2]
        return prijem
    }

    private fun convertToVydaj(subList: List<String>): List<Obrat> {
        var obraty = mutableListOf<Obrat>()
        for (s in subList) {
            val zaznamy = s.split(";")

            if (zaznamy[1].equals("VYDAJ")) {
                //suma; typ; popis; rok; mesiac; den
                var vydaj = createVydaj(zaznamy)
                obraty.add(vydaj)
            }
        }

        return obraty
    }

    private fun createVydaj(zaznamy: List<String>): Vydaj {
        var vydaj = Vydaj(
            BigDecimal(zaznamy[0].toInt()), zaznamy[3].toInt(),
            zaznamy[4].toInt(), zaznamy[5].toInt()
        )
        vydaj.popis = zaznamy[2]
        vydaj.kategoria = KategoriaVydaju.valueOf(zaznamy[6])
        return vydaj
    }


    fun convertToObraty(subList: List<String>): List<Obrat> {
        var obraty = mutableListOf<Obrat>()
        for (s in subList) {
            val zaznamy = s.split(";")

            if (zaznamy[1] == "PRIJEM") {
                //suma; typ; popis; rok; mesiac; den
                var prijem = createPrijem(zaznamy)
                obraty.add(prijem)
            } else if (zaznamy[1] == ("VYDAJ")) {
                //suma; typ; popis; rok; mesiac; den
                var vydaj = createVydaj(zaznamy)
                obraty.add(vydaj)
            }
        }

        return obraty
    }

    //ADDED
    override fun dajZaznamy(typ: TypObratu, rok: Int): List<Obrat> {
        val subList: List<Obrat> = dajZaznamy(typ)

        val data = mutableListOf<Obrat>()
        for (record in subList) {
            if (record.rok == rok) {
                data.add(record)
            }
        }
        return data
    }

    //ADDED
    override fun dajZaznamy(typ: TypObratu, rok: Int, mesiac: Int): List<Obrat> {
        val subList: List<Obrat> = dajZaznamy(typ)

        val data = mutableListOf<Obrat>()
        for (record in subList) {
            if (record.rok == rok && record.mesiac == mesiac) {
                data.add(record)
            }
        }
        return data
    }

    //ADDED
    override fun dajZaznamy(typ: TypObratu, rok: Int, mesiac: Int, den: Int): List<Obrat> {
        val subList: List<Obrat> = dajZaznamy(typ)

        val data = mutableListOf<Obrat>()
        for (record in subList) {
            if (record.rok == rok && record.mesiac == mesiac && record.den == den) {
                data.add(record)
            }
        }
        return data
    }

    override fun addObrat(obrat: Obrat) {

//        500;PRIJEM; ...;2019;12;20
//        500;VYDAJ; ...;2019;12;20;MESACNE
        if (obrat is Prijem) {
            subor.appendText(
                "${obrat.suma};PRIJEM;" +
                        "${obrat.popis};${obrat.rok};" +
                        "${obrat.mesiac};${obrat.den}\n"
            )
        } else if (obrat is Vydaj) {
            subor.appendText(
                "${obrat.suma};VYDAJ;" +
                        "${obrat.popis};${obrat.rok};" +
                        "${obrat.mesiac};${obrat.den};${obrat.kategoria}\n"
            )
        }
    }

    //ADDED
    fun balanceInYear(year: Int): Unit {
        val subList: List<Obrat> = dajZaznamy()

        val income = mutableListOf<Obrat>()
        val expenses = mutableListOf<Obrat>()

        for (record in subList) {
            if (record.rok == year) {

                when (record) {
                    is Prijem -> {
                        income.add(record)
                    }
                    is Vydaj -> {
                        expenses.add(record)
                    }
                    else -> {
                    }
                }
            }
        }
        print("Prijmy v roku $year boli: \n$income \n a vydaje zase: \n$expenses")
    }
}