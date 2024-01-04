import Targetes.Companion.nomAmbCodi
import Targetes.Companion.preuAmbCodi
import java.util.*
val scan = Scanner(System.`in`)
fun esollirTargeta (targetes: MutableList<Int>) {
    println("Quin villet desitja adquirir?")
    opcionsTargetes()
    val targeta = demanarOpcio()
    targetes.add(targeta)
    if (targeta != 4321) {
        val nom = nomAmbCodi(targeta)
        println("Has escollit la targeta ${nom}")
    }
}

fun opcionsTargetes () {
    println(
        "1 -> Billet sensill\n" +
                "2 -> T-Casual\n" +
                "3 -> T-Usual \n" +
                "4 -> T-Familiar\n" +
                "5 -> T-Jove\n" +
                "6 -> T-Grup"
    )
}

fun demanarOpcio ():Int {
    var enter = 0
    do {
        if (!scan.hasNextInt()) {
            print("Introduiexi un numero: ")
        } else {
            enter = scan.nextInt()
            if (enter < 1 || enter > 6 && enter != 4321) {
                print("Introdueixi una opcio valida: ")
            }
        }
    } while (enter !in 1..6 && enter != 4321)
    return enter
}

fun escollirZona() {
    println("Hora d'escollir zona")
    val patata = scan.nextInt()
}
fun opcionsZona () {
    println("Escollir zona 1 a 3")
}
fun pagar() {

}