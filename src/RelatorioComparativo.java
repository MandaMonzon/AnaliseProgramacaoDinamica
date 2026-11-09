import java.util.Arrays;
import java.util.Map;

/** Executa uma instancia com Guloso e DP e imprime a comparacao no console. */
public class RelatorioComparativo {

    /** A tabela de estados so e impressa para valores pequenos. */
    private static final int VALOR_MAXIMO_PARA_TABELA = 25;

    private final TrocoGuloso guloso;
    private final TrocoOtimoDP dp;

    public RelatorioComparativo(TrocoGuloso guloso, TrocoOtimoDP dp) {
        this.guloso = guloso;
        this.dp = dp;
    }

    public void imprimir(String nome, EntradaTroco entrada, boolean detalhar) {
        System.out.println("\n========== " + nome + " ==========");
        System.out.println("Descricao: " + entrada.descricao());
        System.out.println("Moedas: " + Arrays.toString(entrada.moedas()) + " | Valor: " + entrada.valor());

        TrocoGuloso.ResultadoGuloso g = guloso.resolver(entrada.moedas(), entrada.valor());
        imprimirGuloso(g, detalhar);

        if (entrada.valor() > TrocoOtimoDP.LIMITE_VALOR_DP) {
            System.out.println("DP: nao executada (limite de " + TrocoOtimoDP.LIMITE_VALOR_DP
                + "; evitar custo O(n*V)/memoria O(V)).");
            return;
        }
        TrocoOtimoDP.ResultadoOtimo d = dp.resolver(entrada.moedas(), entrada.valor());
        imprimirDp(d);
        System.out.println("Guloso otimo? " + (gulosoEhOtimo(g, d) ? "SIM" : "NAO"));
        if (detalhar && entrada.valor() <= VALOR_MAXIMO_PARA_TABELA) {
            imprimirTabela(d);
        }
    }

    private void imprimirGuloso(TrocoGuloso.ResultadoGuloso g, boolean detalhar) {
        if (detalhar) {
            System.out.println("DECISOES GULOSAS:");
            for (TrocoGuloso.PassoDecisao passo : g.passos()) {
                System.out.println("  " + passo);
            }
        }
        System.out.println("Guloso: " + (g.conseguiuTrocoExato()
            ? g.totalDeMoedas() + " moeda(s)"
            : "nao obteve troco exato, resto " + g.valorRestanteFinal()));
        System.out.printf("Tempo guloso: %.6f ms%n", g.tempoExecucaoNanos() / 1_000_000.0);
    }

    private void imprimirDp(TrocoOtimoDP.ResultadoOtimo d) {
        System.out.println("DP Bottom-Up: " + (d.conseguiuTrocoExato()
            ? d.totalDeMoedas() + " moeda(s) - " + formatar(d.quantidadePorMoeda())
            : "impossivel obter troco exato"));
        System.out.printf("Tempo DP: %.6f ms%n", d.tempoExecucaoNanos() / 1_000_000.0);
    }

    private boolean gulosoEhOtimo(TrocoGuloso.ResultadoGuloso g, TrocoOtimoDP.ResultadoOtimo d) {
        return g.conseguiuTrocoExato() && d.conseguiuTrocoExato() && g.totalDeMoedas() == d.totalDeMoedas();
    }

    private void imprimirTabela(TrocoOtimoDP.ResultadoOtimo d) {
        System.out.print("TABELA DP (estado x -> minimo): ");
        for (int x = 0; x < d.tamanhoDaTabela(); x++) {
            System.out.print(x + "=" + d.estadoComoTexto(x) + (x + 1 == d.tamanhoDaTabela() ? "\n" : " | "));
        }
    }

    private String formatar(Map<Long, Long> porMoeda) {
        StringBuilder texto = new StringBuilder();
        for (Map.Entry<Long, Long> par : porMoeda.entrySet()) {
            if (texto.length() > 0) {
                texto.append(" + ");
            }
            texto.append(par.getValue()).append("x").append(par.getKey());
        }
        return texto.toString();
    }
}
