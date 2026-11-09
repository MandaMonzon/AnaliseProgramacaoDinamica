import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Algoritmo guloso para o Problema do Troco.
 *
 * Escolha local: a cada passo usa a MAIOR moeda que cabe no valor restante,
 * quantas vezes for possivel, e nunca reconsidera decisoes anteriores.
 * Tempo O(n^2) por causa do insertion sort, mais varredura O(n); espaco O(n).
 *
 * Nao usa biblioteca pronta de resolucao do problema do troco.
 */
public class TrocoGuloso implements ResolvedorTroco {

    /** Um passo de decisao, usado para mostrar o rastro do algoritmo. */
    public static class PassoDecisao {
        private final long moeda;
        private final long quantidadeUsada;
        private final long valorRestanteAntes;
        private final long valorRestanteDepois;

        PassoDecisao(long moeda, long quantidadeUsada, long valorRestanteAntes, long valorRestanteDepois) {
            this.moeda = moeda;
            this.quantidadeUsada = quantidadeUsada;
            this.valorRestanteAntes = valorRestanteAntes;
            this.valorRestanteDepois = valorRestanteDepois;
        }

        @Override
        public String toString() {
            return String.format(
                "Resto antes = %d  ->  escolhe a moeda %d (maior <= resto)  ->  usa %d unidade(s)  ->  resto depois = %d",
                valorRestanteAntes, moeda, quantidadeUsada, valorRestanteDepois);
        }
    }

    /** Resultado imutavel de uma execucao gulosa. */
    public static class ResultadoGuloso implements ResultadoTroco {
        private final List<PassoDecisao> passos;
        private final long moedasUsadas;
        private final long valorRestanteFinal;
        private final long tempoExecucaoNanos;

        ResultadoGuloso(List<PassoDecisao> passos, long moedasUsadas, long valorRestanteFinal, long tempoExecucaoNanos) {
            this.passos = Collections.unmodifiableList(passos);
            this.moedasUsadas = moedasUsadas;
            this.valorRestanteFinal = valorRestanteFinal;
            this.tempoExecucaoNanos = tempoExecucaoNanos;
        }

        public List<PassoDecisao> passos() {
            return passos;
        }

        public long valorRestanteFinal() {
            return valorRestanteFinal;
        }

        @Override
        public boolean conseguiuTrocoExato() {
            return valorRestanteFinal == 0;
        }

        @Override
        public long totalDeMoedas() {
            return conseguiuTrocoExato() ? moedasUsadas : -1;
        }

        @Override
        public long tempoExecucaoNanos() {
            return tempoExecucaoNanos;
        }
    }

    /**
     * Para quando o resto chega a zero (troco exato) ou quando nenhuma moeda
     * cabe mais no resto (a estrategia gulosa nao consegue completar o troco).
     */
    @Override
    public ResultadoGuloso resolver(long[] moedas, long valor) {
        ValidadorEntrada.validar(moedas, valor);
        long inicio = System.nanoTime();

        long[] moedasOrdenadas = moedas.clone();
        ordenarDecrescente(moedasOrdenadas);

        List<PassoDecisao> passos = new ArrayList<>();
        long moedasUsadas = 0;
        long restante = valor;

        for (long moeda : moedasOrdenadas) {
            if (restante == 0) {
                break;
            }
            if (moeda > restante) {
                continue; // moeda grande demais para este resto
            }
            long quantidade = restante / moeda; // escolha gulosa: o maximo desta moeda
            long restanteAntes = restante;
            restante -= quantidade * moeda;
            passos.add(new PassoDecisao(moeda, quantidade, restanteAntes, restante));
            moedasUsadas += quantidade;
        }

        return new ResultadoGuloso(passos, moedasUsadas, restante, System.nanoTime() - inicio);
    }

    /** Insertion sort decrescente: o conjunto de moedas costuma ser pequeno. */
    private void ordenarDecrescente(long[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            long chave = vetor[i];
            int j = i - 1;
            while (j >= 0 && vetor[j] < chave) {
                vetor[j + 1] = vetor[j];
                j--;
            }
            vetor[j + 1] = chave;
        }
    }
}
