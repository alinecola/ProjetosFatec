# Sistema de Gestão de Quartos e Reservas de Pousada

Trabalho Prático em Dupla desenvolvido para a disciplina de **Programação Orientada a Objetos** (Prof. Mestre Marcos Roberto de Moraes - Maromo) — Fatec Mogi Mirim.

---

## Identificação da Dupla
* **Aline Fabiane Cola** 
* **Caure Tagliaferro Dantis** 

---

## Tema Escolhido
* **Tema 06 — Gestão de Quartos e Reservas de Pousada**
  * **Entidade Principal (`Comparable`):** `ReservaHospede` (ordenação por data de check-in ou nome)
  * **Interface Obrigatória:** `Faturavel` (método `emitirDemonstrativoConsumo()`)
  * **Exceção Customizada:** `QuartoOcupadoException`
  * **Operações em Memória (`List<ReservaHospede>`):** Reserva de acomodação, check-in, lançamento de consumo extra e encerramento com extrato detalhado.

---

## Arquitetura das Classes
O projeto segue o padrão de organização em pacotes para garantir o desacoplamento e a aplicação dos conceitos de Orientação a Objetos:

* **`model` (Domínio):** Contém a classe `ReservaHospede`, responsável pelos atributos encapsulados, getters/setters, construtores, sobrescrita de `toString()`, `equals()` e `hashCode()`, além da implementação de `Comparable<ReservaHospede>` e `Faturavel`.
* **`interfaces`:** Contém o contrato polimórfico `Faturavel` com o método abstrato `emitirDemonstrativoConsumo()` e métodos auxiliares (`default`/`static`).
* **`exception`:** Contém a exceção verificada (*Checked Exception*) `QuartoOcupadoException`, herdando diretamente de `Exception`.
* **`view` (GUI Swing):** Contém a interface gráfica principal desenvolvida com `javax.swing.*` (`JFrame`, `JTextField`, `JButton`, `JTextArea`, `JScrollPane`), tratando eventos com `ActionListener` e `WindowListener`, além de exibir alertas e erros via `JOptionPane`.
* **`Main` (Classe Principal):** Ponto de entrada (`public static void main`) que inicializa a aplicação gráfica.

---

## Instruções de Execução
1. Clone este repositório para a sua máquina local:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```
2. Abra o projeto em sua IDE Java de preferência (Eclipse, IntelliJ IDEA ou NetBeans) configurada com **JDK 17 ou superior**.
3. Localize a classe principal que contém o método `public static void main(String[] args)`.
4. Execute o projeto para abrir a interface gráfica da pousada. *(Nota: Os dados são mantidos 100% em memória RAM durante a execução).*

---

## Histórico de Prompts de IA Utilizados (Regra de Transparência)

Conforme estabelecido nas diretrizes do trabalho prático, abaixo está o registro cronológico e detalhado das interações realizadas com a Inteligência Artificial Generativa (Gemini) durante o planejamento e a estruturação do projeto.

### Prompt 1 — Planejamento Geral do Projeto
* **O que foi pedido:**
  > *"Leia e analise o pdf. Para esse trabalho escolhi o tema do projeto 06 - Gestão de Quartos e Reservas de Pousada. Vamos desenvolve-lo passo a passo. Você só criara os códigos apenas quando eu solicitar. Primeira tarefa: crie uma passo a passo lógico para a implementação desse projeto."*
* **Resumo da resposta obtida:**
  A IA analisou o arquivo `leiame.pdf` e organizou o desenvolvimento em 4 fases lógicas:
  * **Fase 1:** Modelagem do Domínio e Exceções (criação de `QuartoOcupadoException`, interface `Faturavel` e entidade `ReservaHospede` com `Comparable`, `equals`, `hashCode` e `toString`).
  * **Fase 2:** Estrutura de Armazenamento e Regras de Negócio (gerenciamento em memória usando `List<ReservaHospede> = new ArrayList<>()` e métodos de reserva, check-in, consumo extra, check-out e ordenação com `Collections.sort()`).
  * **Fase 3:** Construção da Interface Gráfica (GUI Swing com `JFrame`, `JTextField`, `JButton`, `JTextArea`/`JScrollPane`, `ActionListener`, `WindowListener` e tratamento de erros com `JOptionPane`).
  * **Fase 4:** Documentação e Organização Final (separação de pacotes, classe `Main` e elaboração do `README.md`).
* **Refinamento:** A divisão em fases foi aprovada como guia mestre para conduzir o desenvolvimento de forma incremental, sem gerar código prematuramente.

---

### Prompt 2 — Detalhamento da Fase 1 (Domínio e Exceções)
* **O que foi pedido:**
  > *"Vamos começar a Fase 1: Modelagem do Domínio e Exceções (Camadas Base). Crie um passo a passo lógico para eu implementar a fase 1."*
* **Resumo da resposta obtida:**
  A IA detalhou os três passos fundamentais da camada base:
  1. Criação do pacote `exception` com a classe `QuartoOcupadoException` estendendo `Exception` e possuindo construtores com mensagem.
  2. Criação do pacote `interfaces` com a interface `Faturavel`, definindo o contrato `double emitirDemonstrativoConsumo()` e suporte a métodos `default`/`static`.
  3. Criação da classe `ReservaHospede` no pacote `model`, encapsulando atributos (código, hóspede, quarto, datas de check-in/out, diária, consumo extra e status) e implementando os contratos de ordenação (`Comparable`), igualdade (`equals`/`hashCode`), representação textual (`toString`) e faturamento (`Faturavel`).
* **Refinamento:** O roteiro técnico foi validado em conformidade com os requisitos do documento de especificação do trabalho.

---

### Prompt 3 — Detalhamento da Fase 2 (Armazenamento e Regras de Negócio)
* **O que foi pedido:**
  > *"Vamos começar a Fase 2: Estrutura de Armazenamento e Regras de Negócio. Crie um passo a passo lógico para eu implementar a fase 2."*
* **Resumo da resposta obtida:**
  A IA estruturou a lógica de manipulação em memória em duas etapas:
  1. Definição da lista dinâmica `List<ReservaHospede> listaReservas = new ArrayList<>()` para persistência exclusiva em memória RAM.
  2. Especificação dos métodos de regra de negócio: cadastro de reserva (com validação de disponibilidade de quarto e lançamento de `QuartoOcupadoException`), realização de check-in, lançamento de consumo extra, encerramento de estadia (check-out com geração de extrato via `Faturavel`) e ordenação da lista em memória utilizando `Collections.sort()`.
* **Refinamento:** O fluxo das operações foi alinhado exatamente com as operações exigidas na tabela do Tema 06.

---

### Prompt 4 — Detalhamento da Fase 3 (Interface Gráfica Swing)
* **O que foi pedido:**
  > *"Vamos começar a Fase 3: Construção da Interface Gráfica (GUI Swing). Crie um passo a passo lógico para eu implementar a fase 3."*
* **Resumo da resposta obtida:**
  A IA apresentou o roteiro de construção da camada visual:
  1. Criação da janela principal estendendo `JFrame`.
  2. Organização de rótulos (`JLabel`), campos de entrada (`JTextField`), botões de operação (`JButton`) e área de relatórios (`JTextArea` dentro de `JScrollPane`).
  3. Associação de ouvintes de eventos (`ActionListener` para botões e `WindowListener` para janela).
  4. Tratamento de exceções na camada visual (`NumberFormatException` e `QuartoOcupadoException`) com exibição de feedback ao usuário via `JOptionPane.showMessageDialog()`.
* **Refinamento:** A estrutura de componentes e tratamento de erros foi validada para atender ao padrão solicitado em sala de aula.

---

### Prompt 5 — Detalhamento da Fase 4 (Documentação e Organização Final)
* **O que foi pedido:**
  > *"Vamos começar a Fase 4: Documentação e Organização Final. Crie um passo a passo lógico para eu implementar a fase 4."*
* **Resumo da resposta obtida:**
  A IA listou as etapas finais de entrega:
  1. Revisão da estrutura de pacotes (`model`, `interfaces`, `exception`, `view`).
  2. Criação da classe principal (`Main`) com `public static void main(String[] args)` instanciando a tela inicial com `setVisible(true)`.
  3. Preparação do arquivo `README.md` contendo identificação da dupla, tema, arquitetura, instruções de execução e histórico completo de prompts utilizados.
* **Refinamento:** O checklist final foi aprovado para preparar a documentação do repositório no GitHub.

---

### Prompt 6 — Geração do Arquivo `README.md`
* **O que foi pedido:**
  > *"Crie um arquivo README.md e coloque todos os prompts que eu escrevi e as suas respostas."*
* **Resumo da resposta obtida:**
  A IA gerou este arquivo `README.md` consolidando a estrutura padrão exigida pelo professor e documentando fielmente todas as solicitações feitas e respostas obtidas até o momento.
* **Refinamento:** Arquivo pronto para ser atualizado com os nomes da dupla e complementado com os futuros prompts referentes à geração de código.
