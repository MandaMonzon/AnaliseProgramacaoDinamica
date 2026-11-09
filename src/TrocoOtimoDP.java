import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Problema do troco ilimitado por Programacao Dinamica, Bottom-Up.
 *
 * Estado:      dp[x] = menor numero de moedas que soma exatamente x (INF = impossivel).
 * Recorrencia: dp[0] = 0; dp[x] = min(1 + dp[x - moeda]) para moeda <= x e dp[x - moeda] alcancavel.
 * Resultado:   dp[V]; o vetor de escolhas permite reconstruir as moedas.
 * Tempo O(n * V); espaco O(V + n), com n = numero de denominacoes e V = valor.
 */
public class TrocoOtimoDP implements ResolvedorTroco {

    /** Limite operacional: evita alocar tabelas enormes para valores gigantes. */
    public static final int LIMITE_VALOR_DP = 2_000_000;

    private static final int SEM_ESCOLHA = -1;

    /** Vetores da programacao dinamica, indexados pelo valor x. */
    private static class Tabela {
        private final int[] minimo;
        private final int[] indiceDaMoeda;
        private final int infinito;

        Tabela(int valor) {
            infinito = valor + 1; // nenhuma solucao viavel usa mais de V moedas
            minimo = new int[valor + 1];
            indiceDaMoeda = new int[valor + 1];
            Arrays.fill(minimo, infinito);
            Arrays.fill(indiceDaMoeda, SEM_ESCOLHA);
            minimo[0] = 0; // caso basico
        }

        boolean alcancavel(int x) {
            return minimo[x] != infinito;
        }
    }

    /** Resultado imutavel de uma execucao da PD. */
    public static class ResultadoOtimo implements ResultadoTroco {
        private final Tabela tabela;
        private final Map<Long, Long> quantidadePorMoeda;
        private final long tempoExecucaoNanos;

        ResultadoOtimo(Tabela tabela, Map<Long, Long> quantidadePorMoeda, long tempoExecucaoNanos) {
            this.tabela = tabela;
            this.quantidadePorMoeda = quantidadePorMoeda;
            this.tempoExecucaoNanos = tempoExecucaoNanos;
        }

        public Map<Long, Long> quantidadePorMoeda() {
            return quantidadePorMoeda;
        }

        public int tamanhoDaTabela() {
            return tabela.minimo.length;
        }

        public String estadoComoTexto(int valor) {
            return tabela.alcancavel(valor) ? Integer.toString(tabela.minimo[valor]) : "INF";
        }

        @Override
        public boolean conseguiuTrocoExato() {
            return tabela.alcancavel(tabela.minimo.length - 1);
        }

        @Override
        public long totalDeMoedas() {
            return conseguiuTrocoExato() ? tabela.minimo[tabela.minimo.length - 1] : -1;
        }

        @Override
        public long tempoExecucaoNanos() {
            return tempoExecucaoNanos;
        }
    }

    @Override
    public ResultadoOtimo resolver(long[] moedas, long valor) {
        ValidadorEntrada.validar(moedas, valor);
        if (valor > LIMITE_VALOR_DP) {
            throw new IllegalArgumentException(
                "Valor " + valor + " excede o limite demonstrativo da DP (" + LIMITE_VALOR_DP
                + "). A DP de vetor requer O(V) memoria e O(n*V) tempo.");
        }
        long inicio = System.nanoTime();
        int v = (int) valor;

        Tabela tabela = preencher(moedas, v);
        Map<Long, Long> quantidades = reconstruir(tabela, moedas, v);

        return new ResultadoOtimo(tabela, quantidades, System.nanoTime() - inicio);
    }

    /** Preenche os estados em ordem crescente: dp[x - moeda] ja esta pronto quando x e calculado. */
    private Tabela preencher(long[] moedas, int v) {
        Tabela tabela = new Tabela(v);
        for (int x = 1; x <= v; x++) {
            for (int j = 0; j < moedas.length; j++) {
                considerarMoeda(tabela, x, j, moedas[j]);
            }
        }
        return tabela;
    }

    private void considerarMoeda(Tabela tabela, int x, int indice, long moeda) {
        if (moeda > x) {
            return;
        }
        int anterior = (int) (x - moeda);
        if (!tabela.alcancavel(anterior)) {
            return;
        }
        int candidata = tabela.minimo[anterior] + 1;
        if (candidata < tabela.minimo[x]) {
            tabela.minimo[x] = candidata;
            tabela.indiceDaMoeda[x] = indice; // guarda a decisao otima para reconstruir
        }
    }

    /** Caminha de V ate 0 seguindo as moedas escolhidas em cada estado. */
    private Map<Long, Long> reconstruir(Tabela tabela, long[] moedas, int v) {
        Map<Long, Long> quantidades = new LinkedHashMap<>();
        if (!tabela.alcancavel(v)) {
            return quantidades;
        }
        int resto = v;
        while (resto > 0) {
            int indice = tabela.indiceDaMoeda[resto];
            if (indice == SEM_ESCOLHA) {
                throw new IllegalStateException("Estado sem predecessor: " + resto);
            }
            long moeda = moedas[indice];
            quantidades.merge(moeda, 1L, Long::sum);
            resto -= (int) moeda;
        }
        return quantidades;
    }
}
