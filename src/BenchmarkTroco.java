import java.util.Arrays;

/** Mediana de chamadas repetidas apos aquecimento simples da JVM; nao inclui I/O. */
public class BenchmarkTroco {

    private static final ResolvedorTroco GULOSO = new TrocoGuloso();
    private static final ResolvedorTroco DP = new TrocoOtimoDP();

    public static void main(String[] args) {
        medir("Pequena", new long[]{50, 20, 10, 5, 1}, 87, 600, 25);
        medir("Media", new long[]{100, 50, 20, 10, 5, 2, 1}, 2873, 200, 20);
        medir("Grande comparavel", new long[]{100, 50, 20, 10, 5, 2, 1}, 100000, 45, 10);
        medir("Nao canonico", new long[]{4, 3, 1}, 6, 600, 25);
    }

    private static void medir(String nome, long[] moedas, long valor, int repeticoes, int aquecimento) {
        for (int i = 0; i < aquecimento; i++) {
            GULOSO.resolver(moedas, valor);
            DP.resolver(moedas, valor);
        }
        long[] temposGuloso = new long[repeticoes];
        long[] temposDp = new long[repeticoes];
        long controle = 0; // soma de resultados: impede o JIT de descartar as chamadas
        for (int i = 0; i < repeticoes; i++) {
            ResultadoTroco guloso = GULOSO.resolver(moedas, valor);
            ResultadoTroco dp = DP.resolver(moedas, valor);
            temposGuloso[i] = guloso.tempoExecucaoNanos();
            temposDp[i] = dp.tempoExecucaoNanos();
            controle += guloso.totalDeMoedas() + dp.totalDeMoedas();
        }
        System.out.printf("%s | V=%d | n=%d | reps=%d | mediana guloso=%.6f ms | mediana DP=%.6f ms | controle=%d%n",
            nome, valor, moedas.length, repeticoes, medianaEmMs(temposGuloso), medianaEmMs(temposDp), controle);
    }

    private static double medianaEmMs(long[] nanos) {
        long[] ordenados = nanos.clone();
        Arrays.sort(ordenados);
        return ordenados[ordenados.length / 2] / 1e6;
    }
}
