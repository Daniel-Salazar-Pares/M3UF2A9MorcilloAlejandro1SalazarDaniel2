import java.util.*

fun main() {
    Locale.setDefault(Locale.ENGLISH)
    val scan = Scanner(System.`in`)
    var cas = 1
    var comanda:String
    var targetes = mutableListOf<Int>()
    do {
        var pagat = false
        var error = false
        when (cas) {
            1 -> {
                esollirTargeta(targetes)
                if (targetes[targetes.lastIndex] == 4321) {
                    println("${ColorANSI.VERMELL.codi}Codi secret introduit aturant el programa${ColorANSI.RESET.codi}")
                    error = true
                } else {
                    cas++
                }

            }

            2 -> {
                escollirZona()
                println("Vols afegir una altra targeta? (S/N)")
                val opcio = scan.next().uppercase().trim()
                if (opcio == "S") {
                    cas--
                } else if (opcio == "N") {
                    cas++
                } //falta una manera de comprobar entrada sigui s o n
            }

            3 -> {
                pagar()


            }
        }

    } while (!pagat && !error)

}