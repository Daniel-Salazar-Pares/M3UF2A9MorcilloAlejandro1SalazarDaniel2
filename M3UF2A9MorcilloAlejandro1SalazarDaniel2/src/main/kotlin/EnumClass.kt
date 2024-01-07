enum class Targetes (val codi: Int, val targeta: String, val preu:Float) {
    Senzill(1, "Billet Senzill", 2.40f),
    Casual(2, "T-Casual", 11.35f),
    Usual(3, "T-Usual", 40f),
    Familiar(4, "T-Familiar", 10f),
    Jove(5, "T-Jove", 80f);
    companion object {

        fun preuAmbCodi(codi: Int): Float {
            return entries.find { it.codi == codi }!!.preu
        }

        fun nomAmbCodi(codi: Int): String {
            return entries.find { it.codi == codi }!!.targeta
        }

    }
}

enum class Zona(val numZona: Int, val factor: Float) {
    ZONA_1(1, 1.0f),
    ZONA_2(2, 1.3125f),
    ZONA_3(3, 1.8443f)
}

data class Compra(
    var nom : String,
    var zona : Int,
    var preu: Float
)

enum class ColorANSI(val codi: String) {
    RESET("\u001B[0m"),
    VERMELL("\u001B[31m"),
    VERD("\u001B[32m"),
    GROC("\u001B[33m"),
    BLAU("\u001B[34m"),
    TARONJA("\u001B[38;5;208m"),
    MORAT("\u001B[38;5;165m");

}
