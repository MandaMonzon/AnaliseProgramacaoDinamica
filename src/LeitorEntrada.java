import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Le instancias do problema a partir de arquivos, sem biblioteca externa.
 *  - JSON: {"descricao": "...", "moedas": [...], "valor": n}
 *  - CSV: uma moeda por linha, com cabecalho; o valor vem separado, pois o
 *    CSV descreve so o sistema monetario.
 */
public final class LeitorEntrada {

    private LeitorEntrada() {
    }

    public static EntradaTroco carregarDeJson(String caminho) throws IOException {
        String conteudo = Files.readString(Path.of(caminho));
        return new EntradaTroco(
            extrairArrayNumeros(conteudo, "moedas"),
            extrairNumero(conteudo, "valor"),
            extrairTexto(conteudo, "descricao"));
    }

    public static EntradaTroco carregarDeCsv(String caminho, long valor, String descricao) throws IOException {
        long[] moedas = Files.readAllLines(Path.of(caminho)).stream()
            .skip(1) // cabecalho
            .map(String::trim)
            .filter(linha -> !linha.isEmpty())
            .mapToLong(Long::parseLong)
            .toArray();
        return new EntradaTroco(moedas, valor, descricao);
    }

    private static String extrairTexto(String json, String chave) {
        Matcher m = Pattern.compile("\"" + chave + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
        return m.find() ? m.group(1) : "";
    }

    private static long extrairNumero(String json, String chave) {
        Matcher m = Pattern.compile("\"" + chave + "\"\\s*:\\s*(-?\\d+)").matcher(json);
        if (!m.find()) {
            throw new IllegalArgumentException("Campo numerico '" + chave + "' nao encontrado no JSON.");
        }
        return Long.parseLong(m.group(1));
    }

    private static long[] extrairArrayNumeros(String json, String chave) {
        Matcher m = Pattern.compile("\"" + chave + "\"\\s*:\\s*\\[([^\\]]*)\\]").matcher(json);
        if (!m.find()) {
            throw new IllegalArgumentException("Campo array '" + chave + "' nao encontrado no JSON.");
        }
        String[] partes = m.group(1).split(",");
        long[] numeros = new long[partes.length];
        for (int i = 0; i < partes.length; i++) {
            numeros[i] = Long.parseLong(partes[i].trim());
        }
        return numeros;
    }
}
