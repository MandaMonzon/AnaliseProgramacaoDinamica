import java.util.Arrays;

/**
 * Referencia independente para os testes: busca em largura sobre os valores 0..V.
 * Cada aresta soma uma moeda, entao a distancia ate V e o minimo de moedas.
 * Nao usa a recorrencia da DP, por isso serve para conferi-la.
 */
public final class OraculoBfs {

    private OraculoBfs() {
    }

    /** Menor numero de moedas para formar o valor, ou -1 se for impossivel. */
    public static int minimoDeMoedas(long[] moedas, int valor) {
        int[] distancia = new int[valor + 1];
        Arrays.fill(distancia, -1);
        int[] fila = new int[valor + 1];
        int inicio = 0;
        int fim = 0;
        distancia[0] = 0;
        fila[fim++] = 0;
        while (inicio < fim) {
            int x = fila[inicio++];
            for (long moeda : moedas) {
                long proximo = x + moeda;
                if (proximo <= valor && distancia[(int) proximo] < 0) {
                    distancia[(int) proximo] = distancia[x] + 1;
                    fila[fim++] = (int) proximo;
                }
            }
        }
        return distancia[valor];
    }
}
