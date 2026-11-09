import java.util.Map;

/** Testes de regressao e verificacao independente (oraculo BFS) das duas estrategias. */
public class TestesTroco {

    private static final int MOEDAS_NO_TESTE_EXAUSTIVO = 7;
    private static final int VALOR_MAXIMO_NO_TESTE_EXAUSTIVO = 60;
    private static final ResolvedorTroco GULOSO = new TrocoGuloso();
    private static final ResolvedorTroco DP = new TrocoOtimoDP();

    private static int verificacoes = 0;

    public static void main(String[] args) {
        verificarCasosConhecidos();
        verificarEntradasInvalidas();
        verificarTodosOsSistemasPequenos();
        System.out.println("OK: " + verificacoes
            + " verificacoes; inclusos 127 sistemas x 61 valores e casos especiais.");
    }

    private static void verificarCasosConhecidos() {
        verificar(new long[]{50, 20, 10, 5, 1}, 87, 6, 6);
        verificar(new long[]{100, 50, 20, 10, 5, 2, 1}, 2873, 32, 32);
        verificar(new long[]{100, 50, 20, 10, 5, 2, 1}, 100000, 1000, 1000);
        verificar(new long[]{4, 3, 1}, 6, 3, 2); // guloso erra
        verificar(new long[]{4, 3}, 6, -1, 2);   // guloso nao completa o troco
        verificar(new long[]{4, 6}, 5, -1, -1);  // impossivel para ambos
        verificar(new long[]{7}, 0, 0, 0);
        verificar(new long[]{5, 2}, 3, -1, -1);
    }

    private static void verificarEntradasInvalidas() {
        exigirErro(new long[]{0, 1}, 4);
        exigirErro(new long[]{-1, 1}, 4);
        exigirErro(new long[]{1}, -1);
        exigirErro(new long[]{1}, TrocoOtimoDP.LIMITE_VALOR_DP + 1L);
    }

    /** Todos os subconjuntos de {1..7} com valores de 0 a 60, comparando a DP com o oraculo. */
    private static void verificarTodosOsSistemasPequenos() {
        for (int mascara = 1; mascara < (1 << MOEDAS_NO_TESTE_EXAUSTIVO); mascara++) {
            long[] moedas = subconjunto(mascara);
            for (int valor = 0; valor <= VALOR_MAXIMO_NO_TESTE_EXAUSTIVO; valor++) {
                TrocoOtimoDP.ResultadoOtimo resultado = (TrocoOtimoDP.ResultadoOtimo) DP.resolver(moedas, valor);
                assertIgual(OraculoBfs.minimoDeMoedas(moedas, valor), resultado.totalDeMoedas(),
                    "BFS valor=" + valor + " mascara=" + mascara);
                conferirReconstrucao(resultado, valor);
            }
        }
    }

    private static long[] subconjunto(int mascara) {
        long[] moedas = new long[Integer.bitCount(mascara)];
        int posicao = 0;
        for (int moeda = 1; moeda <= MOEDAS_NO_TESTE_EXAUSTIVO; moeda++) {
            if ((mascara & (1 << (moeda - 1))) != 0) {
                moedas[posicao++] = moeda;
            }
        }
        return moedas;
    }

    private static void verificar(long[] moedas, int valor, long esperadoGuloso, long esperadoDp) {
        ResultadoTroco guloso = GULOSO.resolver(moedas, valor);
        TrocoOtimoDP.ResultadoOtimo dp = (TrocoOtimoDP.ResultadoOtimo) DP.resolver(moedas, valor);
        assertIgual(esperadoGuloso, guloso.totalDeMoedas(), "Guloso");
        assertIgual(esperadoDp, dp.totalDeMoedas(), "DP");
        conferirReconstrucao(dp, valor);
    }

    /** A soma das moedas reconstruidas deve ser o valor, e a contagem deve ser o minimo. */
    private static void conferirReconstrucao(TrocoOtimoDP.ResultadoOtimo resultado, long valor) {
        long soma = 0;
        long quantidade = 0;
        for (Map.Entry<Long, Long> par : resultado.quantidadePorMoeda().entrySet()) {
            soma += par.getKey() * par.getValue();
            quantidade += par.getValue();
        }
        boolean exato = resultado.conseguiuTrocoExato();
        assertIgual(exato ? valor : 0, soma, "soma do troco reconstruido");
        assertIgual(exato ? resultado.totalDeMoedas() : 0, quantidade, "quantidade reconstruida");
    }

    private static void exigirErro(long[] moedas, long valor) {
        try {
            DP.resolver(moedas, valor);
        } catch (IllegalArgumentException esperado) {
            verificacoes++;
            return;
        }
        throw new AssertionError("Era esperada IllegalArgumentException");
    }

    private static void assertIgual(long esperado, long recebido, String nome) {
        verificacoes++;
        if (esperado != recebido) {
            throw new AssertionError(nome + ": esperado " + esperado + ", recebido " + recebido);
        }
    }
}
