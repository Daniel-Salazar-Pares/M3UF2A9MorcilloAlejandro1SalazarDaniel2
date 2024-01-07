fun main() {
    while (true) {
        var targetes = mutableListOf<Compra>()
        comprarTarjeta(targetes)
        pagar(targetes)
        ticket(targetes)
    }
}