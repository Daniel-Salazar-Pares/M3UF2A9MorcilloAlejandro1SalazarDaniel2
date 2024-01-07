import Targetes.Companion.nomAmbCodi
import Targetes.Companion.preuAmbCodi
import java.util.*
import kotlin.math.roundToInt

val dotLocale = Locale("en", "US")
val scan = Scanner(System.`in`).useLocale(Locale.US)

/**
 * @author Alejandro Morcillo i Daniel Salazar
 * Realitza el procés de compra de targetes del metro.
 *
 * @param targetes Llista de compres on es guardaran les targetes seleccionades.
 */
fun comprarTarjeta(targetes: MutableList<Compra>) {
    var codi = -1
    var cas = 1
    var count = 0
    do {
        when (cas) {
            1 -> {
                codi = escollirTargeta()
                cas++
            }
            2 -> {
                println("T'has equivocat d'elecció? (S/N)")
                val equivocat = demanarOpcio()
                if (equivocat == "S") cas--
                else {
                    count++
                    escollirZona(targetes, codi)
                    println("Vols afegir una altra targeta? (S/N)")
                    val opcio = demanarOpcio()
                    if (opcio == "S") cas--
                    else if (opcio == "N") cas++
                }
            }
        }
    } while (cas != 3 && count < 3)
}

/**
 * @author Alejandro Morcillo i Daniel Salazar
 * Permet a l'usuari escollir una targeta del metro.
 *
 * @return El codi de la targeta seleccionada.
 */
fun escollirTargeta(): Int {
    println("Quin billet desitja adquirir?")
    opcionsTargetes()
    val targeta = demanarTargeta()
    val nom = nomAmbCodi(targeta)
    println("Has escollit la targeta ${nom}")
    return targeta
}

/**
 * @author Daniel Salazar
 * Mostra les opcions disponibles per a les targetes del metro.
 */
fun opcionsTargetes () {
    println(
        "1 -> Billet sensill\n" +
                "2 -> T-Casual\n" +
                "3 -> T-Usual \n" +
                "4 -> T-Familiar\n" +
                "5 -> T-Jove\n"
    )
}

/**
 * @author Daniel Salazar
 * Fa una validació de la resposta de l'usuari amb relació a la targeta.
 *
 * @return El codi de la targeta seleccionada.
 */
fun demanarTargeta ():Int {
    var enter = 0
    do {
        if (!scan.hasNextInt()) {
            print("Introduiexi un numero: ")
            scan.nextLine()
        } else {
            enter = scan.nextInt()
            if (enter < 1 || enter > 5) {
                print("Introdueixi una opcio valida (1 a 5): ")
                scan.nextLine()
            }
        }
    } while (enter !in 1..5)
    return enter
}

/**
 * @author Daniel Salazar
 * Demana a l'usuari una opció (S/N) i la retorna.
 *
 * @return L'opció seleccionada per l'usuari.
 */
fun demanarOpcio () : String {
    scan.nextLine()
    var res = scan.nextLine().uppercase()
    do {
        if (res != "S" && res != "N") {
            print("Introdueixi una opcio valida (S/N): ")
            res = scan.nextLine().trim().uppercase()
        }
    } while (res != "S" && res != "N")
    return res
}

/**
 * @author Alejandro Morcillo i Daniel Salazar
 * Permet a l'usuari escollir la zona de la targeta.
 *
 * @param targetes Llista de compres on es guardarà la nova targeta.
 * @param codi Codi de la targeta seleccionada.
 */
fun escollirZona(targetes: MutableList<Compra>, codi : Int) {
    println("Hora d'escollir zona (1, 2 o 3):")
    var zona = demanarZona()
    var factor = 0f
    for (i in Zona.entries) {
        if (i.numZona == zona) factor = i.factor
    }
    var preu = "%.2f".format(dotLocale, roundAMultiple(factor*preuAmbCodi(codi))).toFloat()
    targetes.add(Compra(nomAmbCodi(codi), zona, preu))
    println("El preu del billet és $preu€")
    println("Ha escollit la opció: ${nomAmbCodi(codi)}, zona $zona")
}

/**
 * @author Daniel Salazar
 * Fa una validació de la resposta de l'usuari en relació a les zones.
 *
 * @return La zona de la targeta seleccionada.
 */
fun demanarZona () : Int {
    var enter = 0
    do {
        if (!scan.hasNextInt()) {
            print("Introduiexi un numero: ")
        } else {
            enter = scan.nextInt()
            if (enter < 1 || enter > 3) {
                print("Introdueixi una opcio valida (1, 2 o 3): ")
            }
        }
    } while (enter !in 1..3)
    return enter
}

/**
 * @author Alejandro Morcillo
 * Efectua el pagament de les targetes seleccionades i imprimeix el tiquet.
 *
 * @param targetes Llista de compres amb les targetes seleccionades.
 */
fun pagar(targetes: MutableList<Compra>) {
    var total = roundAMultiple(preutotal(targetes))
    println("Ha comprat ${targetes.size} bitllets, has de pagar $total€")
    caixa(total)
}

/**
 * @author Alejandro Morcillo
 * Calcula el preu total de les targetes seleccionades.
 *
 * @param targetes Llista de compres amb les targetes seleccionades.
 * @return El preu total de les targetes.
 */
fun preutotal(targetes: MutableList<Compra>): Float {
    var sum = 0f
    for (i in targetes) {
        sum += i.preu
    }
    return sum
}

/**
 * @author Alejandro Morcillo i Daniel Salazar
 * Processa el pagament utilitzant monedes o bitllets vàlids de l'euro.
 *
 * @param total La quantitat total a pagar.
 */
fun caixa(total: Float) {
    val dinerPermesos = listOf(0.05f, 0.10f, 0.20f, 0.50f, 1f, 2f, 5f, 10f, 20f, 50f)
    var preuRestant = total
    while (preuRestant > 0) {
        println("Introdueix monedes o bitllets vàlids de EURO")
        var diner = validateFloat()
        if (dinerPermesos.contains(diner)) {
            if (preuRestant - diner > 0) {
                preuRestant = roundAMultiple(preuRestant - diner)
                println("Pagament acceptat, et quedan $preuRestant€ per pagar")
            } else if (preuRestant - diner < 0) {
                val cambio = roundAMultiple((preuRestant - diner) * -1)
                preuRestant = 0f
                println("Reculli el seu bitllet i el seu canvi: $cambio€")
            } else {
                preuRestant = 0f
                println("Pagament exacte, reculli el seu bitllet")
            }
        } else {
            println("Cantitat invalida!")
        }
    }
}

/**
 * @author Alejandro Morcillo
 *
 * Arrodoneix la quantitat al múltiple de 0.05 més proper.
 *
 * @param total La quantitat a arrodonir.
 * @return La quantitat arrodonida.
 */
fun roundAMultiple(total: Float): Float {
    return (total / 0.05f).roundToInt() * 0.05f
}


/**
 * @author Alejandro Morcillo
 * Imprimeix el tiquet amb la informació de les targetes comprades.
 *
 * @param targetes Llista de compres amb les targetes seleccionades.
 */
fun ticket(targetes: MutableList<Compra>) {
    println("Vols el tiquet(S, N)?")
    var res = demanarOpcio()
    if (res == "S") {
        println("_____TIQUET_____")
        for (i in targetes) {
            println("${i.nom} zona ${i.zona}, Preu: ${i.preu}€")
        }
    }
    println("_______________\n" +
            "Reculli el teu tiquet.\n" +
            "Adeu!!\n" +
            "- - - - - - - - - - - - -")
}

/**
 * @author Alejandro Morcillo
 * Valida una entrada com a valor de tipus Float.
 *
 * @return El valor Float validat.
 */
fun validateFloat() : Float {
    do {
        if (!scan.hasNextFloat()) {
            print("Això no és un nombre (Float), torna a provar-ho: ")
            scan.next()
        }
    } while ( !scan.hasNextFloat() )
    var ans = scan.nextFloat()
    return ans
}