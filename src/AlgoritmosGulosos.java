/** Executa as mesmas instancias com Guloso e DP para comparacao (Aula 10). */
public class AlgoritmosGulosos {

    private static final long VALOR_TESTE_MEDIO = 2873;

    public static void main(String[] args) throws Exception {
        RelatorioComparativo relatorio = new RelatorioComparativo(new TrocoGuloso(), new TrocoOtimoDP());

        relatorio.imprimir("TESTE 1 - PEQUENA",
            LeitorEntrada.carregarDeJson("entradas/teste1_pequena.json"), true);
        relatorio.imprimir("TESTE 2 - MEDIA",
            LeitorEntrada.carregarDeCsv("entradas/teste2_media_moedas.csv", VALOR_TESTE_MEDIO,
                "Teste 2 - media instancia (via CSV)"), false);
        relatorio.imprimir("TESTE 3 - GRANDE, COMPARAVEL",
            LeitorEntrada.carregarDeJson("entradas/teste3_comparavel.json"), false);
        relatorio.imprimir("TESTE EXTRA - NAO CANONICO",
            LeitorEntrada.carregarDeJson("entradas/teste_nao_canonico.json"), true);
        relatorio.imprimir("TESTE EXTRA - GULOSO SEM TROCO, DP COM TROCO",
            LeitorEntrada.carregarDeJson("entradas/teste_guloso_falha.json"), true);
        relatorio.imprimir("TESTE EXTRA - IMPOSSIVEL PARA AMBOS",
            LeitorEntrada.carregarDeJson("entradas/teste_impossivel.json"), true);

        // O arquivo grande original foi mantido para a demonstracao do limite da DP.
        relatorio.imprimir("ESTRESSE ORIGINAL - 987654321 (DP NAO ALOCADA)",
            LeitorEntrada.carregarDeJson("entradas/teste3_grande.json"), false);
    }
}
