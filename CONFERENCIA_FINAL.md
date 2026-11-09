# Conferencia de entrega — Aula 10

## Comprovado na revisao

- [x] Mesmo problema do trabalho guloso anterior: minimo de moedas.
- [x] Descricao e comparacao com abordagem gulosa; caracteristicas e limites.
- [x] Fundamentacao bibliografica com fontes academicas verificaveis e trechos sinalizados no relatorio.
- [x] Estados, decisoes, recorrencia, casos basicos, ordem de resolucao, resultado.
- [x] PD Bottom-Up implementada em Java sem biblioteca que resolva o problema.
- [x] Composicao otima reconstruida e tabela demonstrativa do caso nao canonico.
- [x] Tres tamanhos comparaveis (V=87, 2873 e 100000), mais casos especiais.
- [x] Tempos medidos em multiplas execucoes e analise de memoria e complexidade.
- [x] Quadro completo de comparacao segundo a Aula 10 e 20 itens de analise critica.
- [x] Conclusao responde se maior sofisticacao sempre compensa.
- [x] Relatorio segue a ordem de secoes do Template — Relatorio.
- [x] Apresentacao PPTX editavel com 8 slides e notas do apresentador, roteiro separado em Word.
- [x] 23.277 verificacoes passaram em TestesTroco (busca em largura como oraculo).

## Pendencias que dependem da dupla

- [x] E-mails recuperados do relatorio anterior da propria dupla e inseridos no cabecalho.
- [ ] Publicar o projeto no GitHub ou Drive e substituir o marcador de link no relatorio; testar permissao de leitura.
- [x ] Conferir hardware, Java e SO da maquina utilizada pela dupla na execucao definitiva, se necessario.
- [ ] Opcional: executar o benchmark na maquina de apresentacao e atualizar os tempos, porque a tabela registra o ambiente automatizado Linux desta revisao.
- [ ] Conferir exigencias de metricas adicionais no material **Aula 2 - Relatorio**, citado pelo template, mas nao anexado.
- [ ] Verificar com a professora a diferenca entre a duracao de ~15 minutos informada na Aula 10 e o roteiro de 10 minutos solicitado pela dupla.
- [ ] Entregar o relatorio e os slides nas tarefas Moodle e inscrever a dupla na planilha, conforme prazo informado no PDF.

## Resultados significativos

- {4,3,1}, V=6: guloso 4+1+1 (3 moedas), PD 3+3 (2).
- {4,3}, V=6: guloso nao produz troco exato, PD produz 3+3.
- {4,6}, V=5: nenhum algoritmo pode produzir troco exato.
- Pequena: 6/6. Media: 32/32. Grande comparavel: 1000/1000.
- Stress original V=987654321: guloso 9876545 moedas; PD nao executada por limite de memoria/tempo definido.
- Complexidades nesta implementacao: guloso O(n²) tempo e O(n) memoria adicional; PD O(nV) tempo e O(V+n) memoria.
