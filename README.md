# Problema do Troco - Guloso x Programacao Dinamica (Aula 10)

Equipe: Amanda W. Monzon e Kelly Natasha de O. Fernandes.

Projeto Java sem bibliotecas de algoritmos externos. O codigo guloso original foi mantido; a solucao de Programacao Dinamica Bottom-Up foi complementada com reconstrucao das moedas e verificacao de estados. Entradas pequenas, medias, grande comparavel, nao canonica, impossivel e estresse original.

## Requisitos e execucao

- JDK 11 ou superior (testado com OpenJDK 21).
- No terminal, **a partir da pasta que contem `src` e `entradas`**:

```bash
mkdir -p build
javac -encoding UTF-8 -d build src/*.java
java -cp build AlgoritmosGulosos
java -cp build TestesTroco
java -cp build BenchmarkTroco
```

No Windows (PowerShell), use `mkdir build` e os mesmos comandos `javac` / `java`.

## Estrutura do codigo (src)

| Classe | Responsabilidade |
|---|---|
| `ResolvedorTroco` / `ResultadoTroco` | Contrato comum das estrategias e dos resultados |
| `TrocoGuloso` | Estrategia gulosa (maior moeda primeiro) |
| `TrocoOtimoDP` | Programacao Dinamica Bottom-Up: `preencher` a tabela e `reconstruir` as moedas |
| `ValidadorEntrada` | Regras de validacao usadas pelas duas estrategias |
| `EntradaTroco` / `LeitorEntrada` | Dados de uma instancia e leitura de JSON/CSV |
| `RelatorioComparativo` | Impressao da comparacao Guloso x DP |
| `AlgoritmosGulosos` | `main` da comparacao |
| `TestesTroco` / `OraculoBfs` | Testes automatizados e referencia independente por busca em largura |
| `BenchmarkTroco` | Mediana dos tempos |

Novas estrategias (por exemplo Top-Down) podem ser adicionadas implementando `ResolvedorTroco`, sem alterar as classes existentes.

## Entradas

- `teste1_pequena.json`: 87 com [50,20,10,5,1].
- `teste2_media_moedas.csv`: denominacoes [100,50,20,10,5,2,1], V=2873 definido em `AlgoritmosGulosos`.
- `teste3_comparavel.json`: 100000 com [100,50,20,10,5,2,1]. Novo arquivo para comparação na mesma entrada.
- `teste_nao_canonico.json`: 6 com [4,3,1]; guloso 3, DP 2.
- `teste_guloso_falha.json`: 6 com [4,3]; guloso sem troco, DP 2.
- `teste_impossivel.json`: 5 com [4,6]; ambos sem solucao.
- `teste3_grande.json`: 987654321, preservado do trabalho anterior; executado apenas com guloso, pois o limite demonstrativo da DP e 2000000.

## Notas metodologicas

- Moedas sao valores inteiros positivos, com disponibilidade ilimitada; `valor` inteiro nao negativo.
- `TrocoOtimoDP`: vetor `dp[x]` e vetor `moedaEscolhida[x]` de tamanho V+1; tempo O(nV), espaco O(V + n). Nenhuma biblioteca resolve o problema diretamente.
- `TrocoGuloso`: ordenacao por insertion sort decrescente O(n²); varredura O(n); espaco adicional O(n) incluindo copia das moedas e rastro. Nao garante otimalidade para sistemas nao canonicos.
- O algoritmo dinamico tem limite intencional de V <= 2.000.000, para nao causar uso excessivo de memoria em um exercicio academico. Acima disso ha mensagem explicativa; nao e falha silenciosa nem impossibilidade matematica.
- `BenchmarkTroco` mede apenas tempo interno de cada metodo apos aquecimento, **na maquina onde for executado**. Tempos pequenos variam com JVM/hardware. Os numeros presentes no relatorio dizem respeito ao ambiente automatizado identificado nele.
- O **link publico para o repositorio** deve ser fornecido pela dupla; nenhum link foi inventado.

## Alteracoes da revisao

1. Promocao da DP original (antes apenas verificacao) a metodo principal comparado.
2. Reconstrucao da composicao otima, tratamento de valores impossiveis e validacao.
3. Adicao do grande comparavel (V=100000) preservando teste de estresse antigo.
4. Impressao da tabela de DP em instancias pequenas; medidor de tempos e testes automatizados.
5. Correcao da analise de complexidade de espaco: vetor DP O(V), e nao O(nV).

## Referencias de fundamentos (texto original parafraseado no relatorio)

- MIT OCW, *Lecture 16: Dynamic Programming Subproblems* (2020): https://ocw.mit.edu/courses/6-006-introduction-to-algorithms-spring-2020/28461a74f81101874a13d9679a40584d_MIT6_006S20_lec16.pdf
- NYU / Joanna Klukowska, *Dynamic Programming* (2020): https://cs.nyu.edu/~joannakl/aps_s21/slides/11-dynamic_programming_1.html
- Fabio Furini, *DP for the coin change problem*: https://fabiofurini.github.io/combinatorial-optimization/dynamic-programming/dp-coin-change/
