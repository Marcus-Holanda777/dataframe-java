# DataFrame Java 📊

Uma biblioteca de manipulação de dados em formato tabular (DataFrame) desenvolvida em **Java 21** e gerenciada via **Maven**.

Este projeto tem o objetivo duplo de construir um ecossistema robusto para processamento e análise de dados em Java (semelhante ao Pandas em Python ou Tablesaw em Java) e servir como um ambiente prático de aprendizado dos conceitos avançados da linguagem Java.

---

## 🎯 Metodologia de Desenvolvimento e Aprendizado

O projeto segue um modelo de **mentoria orientada à prática**:
- **AI Mentor**: Explica os conceitos de engenharia de software, decisões de arquitetura e teoria do Java (Orientação a Objetos, Generics, Encapsulamento, Design Patterns).
- **Desenvolvedor**: Escreve o código de forma ativa, testa e implementa as funcionalidades.

---

## 🏗️ Arquitetura e Design Patterns

### 1. Polimorfismo e Segurança de Tipos (`Generics`)
A biblioteca utiliza a interface genérica `Column<T>` para representar colunas de dados tipadas. Isso permite a manipulção segura de tipos em tempo de compilação (*type safety*) mantendo flexibilidade polimórfica para tabelas heterogêneas.

```
                  ┌──────────────────────┐
                  │    interface Column  │
                  └──────────▲───────────┘
                             │
                  ┌──────────┴───────────┐
                  │   AbstractColumn     │
                  └──────────▲───────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
┌────────┴─────────┐ ┌───────┴────────┐ ┌────────┴────────┐
│   StringColumn   │ │   IntColumn    │ │  DoubleColumn   │
└──────────────────┘ └────────────────┘ └─────────────────┘
```

### 2. Conceitos-Chave Aplicados
- **Interface (`Column<T>`)**: Define o contrato público e uniforme de operações de coluna.
- **Classe Abstrata (`AbstractColumn<T>`)**: Reutiliza código comum (`getName()`, `size()`, `values()`) e gerencia os metadados da coluna.
- **Enum (`DataType`)**: Garante segurança de tipo e autocompletar para mapeamento de colunas (`INTEGER`, `DOUBLE`, `BOOLEAN`, `STRING`, `DATE`, `DATETIME`).
- **Encapsulamento e Imutabilidade**:
  - Uso de `Collections.unmodifiableList()` no método `values()` para impedir mutação indevida por código externo.
  - Atributos imutáveis (`private final`) para nome e metadados.
- **Injeção de Conversão por Polimorfismo**: O método abstrato `addFromString(String value)` força cada coluna concreta a definir sua própria lógica de parsing ao carregar arquivos CSV.

---

## 📦 Componentes Implementados Até o Momento

- [x] **`pom.xml`**: Configurado com Java 21 e JUnit 5 (`junit-jupiter`).
- [x] **`DataType.java`**: Enumeração com os tipos de dados suportados (`INTEGER`, `DOUBLE`, `BOOLEAN`, `DATE`, `DATETIME`, `STRING`).
- [x] **`Column.java`**: Interface genérica contendo operações principais e o método `addFromString`.
- [x] **`AbstractColumn.java`**: Classe abstrata base com gerenciamento de lista de valores e encapsulamento.
- [x] **`StringColumn.java`**: Implementação concreta para textos.
- [x] **`IntColumn.java`**: Implementação concreta para números inteiros (utilizando `Integer.parseInt`).
- [x] **`DoubleColumn.java`**: Implementação concreta para números de ponto flutuante.
- [x] **`BooleanColumn.java`**: Implementação concreta para valores booleanos.
- [x] **`DateColumn.java`**: Implementação concreta para datas (`LocalDate`, formatação customizável via `DateTimeFormatter`).
- [x] **`DateTimeColumn.java`**: Implementação concreta para data e hora (`LocalDateTime`, formatação customizável via `DateTimeFormatter`).
- [x] **`DataFrame.java`**: Estrutura tabular que agrega colunas heterogêneas (`addColumn`, `shape`, `getColumn`), com validação de nomes duplicados e de contagem de linhas.
- [x] **Suíte de testes**: 31 testes JUnit 5 cobrindo todos os tipos de coluna e o `DataFrame`.

---

## 🛣️ Roteiro do Projeto (Roadmap)

- [x] **Módulo 1**: Estrutura Base de Colunas e Tipagem
- [x] **Módulo 2**: Suporte a Novas Colunas Concretas (`DoubleColumn`, `BooleanColumn`, `DateColumn`, `DateTimeColumn`) e Testes Unitários com JUnit 5
- [x] **Módulo 3**: Classe `DataFrame` (Gerenciamento de conjunto de colunas, contagem de registros, acesso por nome)
- [ ] **Módulo 4**: Exibição Formatada em Tabela (`toString()`/`show()`)
- [ ] **Módulo 5**: Leitor de Arquivos CSV com Inferência Automática de Tipos
- [ ] **Módulo 6**: Operações Avançadas (Filtros, Projeções, GroupBy, Métricas Estatísticas)

---

## 🧪 Como Compilar e Testar

### Requisitos
- Java 21 JDK
- Apache Maven 3.8+

### Comandos Maven
```bash
# Compilar o projeto
mvn compile

# Executar testes unitários
mvn test
```
