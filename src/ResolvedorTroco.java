/**
 * Contrato de uma estrategia para o Problema do Troco.
 * Guloso e Programacao Dinamica o implementam, entao quem as usa
 * (benchmark, testes) nao depende de uma estrategia especifica.
 */
public interface ResolvedorTroco {

    ResultadoTroco resolver(long[] moedas, long valor);
}
