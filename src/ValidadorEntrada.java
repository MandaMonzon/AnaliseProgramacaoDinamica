/** Regras de entrada compartilhadas por todas as estrategias de troco. */
public final class ValidadorEntrada {

    private ValidadorEntrada() {
    }

    public static void validar(long[] moedas, long valor) {
        if (moedas == null || moedas.length == 0) {
            throw new IllegalArgumentException("Informe ao menos uma denominacao de moeda.");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("O valor nao pode ser negativo.");
        }
        for (long moeda : moedas) {
            if (moeda <= 0) {
                throw new IllegalArgumentException("Moedas devem ser positivas: " + moeda);
            }
        }
    }
}
