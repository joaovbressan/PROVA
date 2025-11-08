📖 Tabela de Conteúdo





🏛️ Questão 1: Sistema de Análise de Risco (Strategy Pattern)
Contexto: Você está desenvolvendo um sistema de processamento de dados para uma empresa financeira que lida com diferentes tipos de análise de risco. O sistema precisa calcular métricas de risco usando diferentes algoritmos (Value at Risk, Expected Shortfall, Stress Testing) que podem mudar dinamicamente durante a execução.

Problema:

Cada algoritmo de risco deve ser intercambiável em tempo de execução.

Os algoritmos devem ser capazes de compartilhar um contexto complexo com múltiplos parâmetros financeiros.

Deve ser possível trocar de algoritmo de acordo com a necessidade de negócios.

Padrão de Design: Strategy
O padrão Strategy foi escolhido porque ele permite definir uma família de algoritmos (as estratégias de risco), encapsular cada um deles e torná-los intercambiáveis.

Justificativa da Escolha
Intercambialidade (Requisito Chave): O padrão permite que o cliente (RiskProcessor) altere o algoritmo (RiskAlgorithm) em tempo de execução através do método setAlgorithm().

Encapsulamento e OCP (SOLID): Cada algoritmo (ValueAtRiskStrategy, StressTestingStrategy, etc.) é encapsulado em sua própria classe. Isso segue o Princípio da Responsabilidade Única (SRP). O sistema está Aberto para Extensão (podemos adicionar novos algoritmos sem alterar o RiskProcessor) e Fechado para Modificação (OCP).

Compartilhamento de Contexto: A interface RiskAlgorithm define um método calculate(FinancialContext context). Isso garante que qualquer estratégia, nova ou existente, receba o mesmo objeto de contexto complexo, satisfazendo o requisito.

Estrutura de Arquivos
Código-Chave (Highlights)
A interface Strategy define o contrato:

O Contexto (RiskProcessor) mantém uma referência à estratégia atual e a delega:

🏦 Questão 2: Integração com Legado Bancário (Adapter Pattern)
Contexto: Sua empresa está integrando com um legado bancário que possui uma interface complexa para processamento de transações. A interface legada SistemaBancarioLegado possui métodos com assinaturas incompatíveis e usa tipos de dados obsoletos.

Problema:

Converter a interface atualizada ProcessadorTransacoes para a interface legada.

Legado usa: processarTransacao(HashMap<String, Object> parametros)

Atual usa: autorizar(String cartao, double valor, String moeda)

Implementar de forma bidirecional (converter respostas).

Tratar campos obrigatórios do legado que não existem na interface moderna.

Padrão de Design: Adapter (Wrapper)
O padrão Adapter é usado para "traduzir" chamadas de uma interface (a moderna) para outra incompatível (a legada).

Justificativa da Escolha
Conversão de Interface: O LegadoBancarioAdapter implementa a interface moderna ProcessadorTransacoes (o "Target") e, internamente, "embrulha" (wraps) uma instância do SistemaBancarioLegado (o "Adaptee").

Tradução de Dados (Bidirecional): O Adapter é responsável por todo o "trabalho sujo" de tradução:

Ida: Converte os parâmetros simples (String, double) no HashMap<String, Object> esperado pelo legado.

Volta: Recebe o HashMap de resposta do legado e o converte em um DTO RespostaModerna.

Tratamento de Restrições: O Adapter é o local perfeito para encapsular lógicas de tradução específicas, como a codificação de moedas (ex: "BRL" -> 3) e a adição de campos obrigatórios que o legado exige, mas a interface moderna não fornece (ex: id_loja).

Estrutura de Arquivos
Código-Chave (Highlights)
O LegadoBancarioAdapter implementa a interface moderna e contém a legada.

☢️ Questão 3: Controle de Usina Nuclear (State Pattern)
Contexto: Você está modelando um sistema de controle para uma usina nuclear com estados complexos de operação (DESLIGADA, OPERACAO_NORMAL, ALERTA_AMARELO, ALERTA_VERMELHO, EMERGENCIA).

Problema:

Cada transição de estado deve validar condições complexas (temperatura, pressão).

Transições devem ser controladas (unidirecionais/bidirecionais).

Restrição: EMERGENCIA só pode ser ativado após ALERTA_VERMELHO.

Adicionar um modo "manutenção" que sobreescreva os estados normais.

Padrão de Design: State
O padrão State permite que um objeto (a UsinaNuclear) altere seu comportamento quando seu estado interno muda. O objeto parecerá mudar de classe.

Justificativa da Escolha
Encapsulamento de Comportamento: Em vez de um switch monolítico na classe UsinaNuclear, cada estado (EstadoOperacaoNormal, EstadoAlertaAmarelo, etc.) é uma classe separada. Toda a lógica de o que fazer neste estado e para qual estado transicionar é encapsulada dentro da própria classe de estado.

Transições Seguras (SRP): As regras de transição são impostas pelo design. A restrição "EMERGENCIA só pode vir de ALERTA_VERMELHO" é garantida porque somente a classe EstadoAlertaVermelho possui a lógica (onSistemaResfriamentoFalha()) para transicionar para EstadoEmergencia.

Extensibilidade (OCP): O modo EstadoManutencao é simplesmente mais uma classe que implementa EstadoUsina. Ele pode sobrescrever o comportamento (como ignorar leituras de sensor) sem afetar os outros estados.

Estrutura de Arquivos
Código-Chave (Highlights)
A UsinaNuclear (Contexto) delega todas as ações para o estado atual.

Cada classe de estado contém sua própria lógica de transição.

📄 Questão 4: Validação de NF-e em Cadeia (Chain of Responsibility + Command)
Contexto: Desenvolva um sistema de validação de documentos fiscais eletrônicos (NF-e) que precisa aplicar múltiplas regras de validação em cadeia.

Problema:

Validadores especializados em cadeia.

Suporte a validações condicionais (se X falhar, pule Y).

Implementar "circuit breaker" (interromper após 3 falhas).

Capacidade de "rollback" para validadores que modificam o documento.

Timeout individual para cada validador.

Padrões de Design: Chain of Responsibility (CoR) e Command
Este problema é resolvido com uma versão gerenciada do Chain of Responsibility, onde um Orquestrador central controla a cadeia, em vez de cada "elo" chamar o próximo. O padrão Command é usado para implementar o rollback.

Justificativa da Escolha
Chain of Responsibility (Gerenciado): Um CoR "puro" (onde A chama B, B chama C) não permite regras complexas como "pular Y" ou "circuit breaker". Ao usar um ValidationOrchestrator, centralizamos essa lógica. O Orquestrador itera sobre a lista de Validador e decide se deve chamar, pular ou interromper a cadeia.

Command (para Rollback): A restrição de rollback é resolvida com o padrão Command. Criamos um ValidationContext que é passado por toda a cadeia. Este contexto possui uma Stack<Runnable>. Quando um validador (como ValidadorBancoDados) faz uma alteração, ele adiciona um "Comando de Desfazer" (um Runnable) à pilha. Se a validação falhar em qualquer ponto, o Orquestrador executa todos os Runnable na pilha, revertendo as alterações.

Regras Complexas: O Orquestrador é o local ideal para implementar:

Circuit Breaker: Checando context.getFailureCount() >= 3 após cada validador.

Condicionais: Checando context.hasFailed() antes de executar validadores 3 e 5.

Timeouts: Usando ExecutorService e Future.get(timeout) para invocar cada validador.

Estrutura de Arquivos
Código-Chave (Highlights)
O ValidationContext armazena o estado e a pilha de Comandos (Runnables).

O ValidadorBancoDados usa o padrão Command para adicionar uma ação de rollback.

O ValidationOrchestrator gerencia a cadeia, timeouts e o rollback.

🚀 Como Executar
O projeto é composto por 4 pacotes (módulos) independentes. Cada módulo possui sua própria classe Demo (ex: Q1Demo.java, Q2Demo.java) com um método main.

Clone o repositório:https://github.com/joaovbressan/PROVA.git

Abra o projeto em sua IDE Java favorita (IntelliJ, Eclipse, VS Code).

Navegue até o arquivo Demo da questão que deseja testar (ex: questao_1_strategy/Q1Demo.java).

Execute o método main() diretamente pela IDE.


