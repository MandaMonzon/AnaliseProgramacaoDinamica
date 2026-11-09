/** Uma instancia do Problema do Troco: moedas disponiveis, valor a trocar e descricao. */
public class EntradaTroco {

    private final long[] moedas;
    private final long valor;
    private final String descricao;

    public EntradaTroco(long[] moedas, long valor, String descricao) {
        this.moedas = moedas;
        this.valor = valor;
        this.descricao = descricao;
    }

    public long[] moedas() {
        return moedas;
    }

    public long valor() {
        return valor;
    }

    public String descricao() {
        return descricao;
    }
}
