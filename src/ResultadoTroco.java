/** Contrato comum aos resultados de qualquer estrategia de troco. */
public interface ResultadoTroco {

    boolean conseguiuTrocoExato();

    /** Total de moedas do troco, ou -1 quando nao existe troco exato. */
    long totalDeMoedas();

    long tempoExecucaoNanos();
}
