# Sistema de Gerenciamento de Funcionários e Folha de Pagamento

Atividade avaliativa de Programação Orientada a Objetos em Java: cadastro de funcionários
de diferentes categorias, cálculo de remuneração, relatório e total da folha de pagamento.

## Tecnologias
Java 17 · Maven · JUnit 5 · Git/GitHub

## Estrutura (arquitetura em camadas)
```
src/main/java
 ├── Main.java
 └── br/edu/folha
      ├── model/        Funcionario (abstrata), FuncionarioCLT, FuncionarioPJ,
      │                 FuncionarioComissionado, Desenvolvedor, Gerente,
      │                 Tributavel, Identificavel
      ├── repository/   FuncionarioRepository (armazenamento em memória)
      ├── service/      FuncionarioService (regras de negócio)
      ├── exception/    FuncionarioNaoEncontradoException, FuncionarioJaCadastradoException,
      │                 CpfInvalidoException
      ├── util/         CpfValidator
      └── view/         FuncionarioView (menu de console)
src/test/java          uma classe de teste para cada classe de produção (exceto view e Main)
```

## Conceitos de POO aplicados
- **Encapsulamento:** atributos `private`, getters e setters com validação.
- **Herança e abstração:** `Funcionario` é abstrata e declara `calcularRemuneracao()`.
- **Interfaces:** `Identificavel` e `Tributavel` (o PJ não é tributável nesta regra).
- **Polimorfismo por sobrescrita:** cada subclasse implementa sua própria remuneração.
- **Polimorfismo por sobrecarga:** `FuncionarioService.buscarFuncionario(String codigo)` e
  `buscarFuncionario(String nome, boolean buscaExata)`.
- **Exceções personalizadas:** lançadas no repository e no model e tratadas na view.

## Regras de remuneração
| Categoria | Remuneração | Tributos |
|---|---|---|
| CLT | salário-base + benefício | 10% |
| PJ | salário-base + (projetos × valor por projeto) | não tributável |
| Comissionado | salário-base + (vendas × percentual / 100) | 15% |
| Desenvolvedor | salário-base + (horas extras × valor da hora) | 10% |
| Gerente | salário-base + participação nos lucros | 20% |

O total da folha soma as remunerações de todos os funcionários.

## Como executar
Pela IDE: execute a classe `Main`. Pelo terminal (Maven instalado):
```bash
mvn compile exec:java
```

## Como executar os testes
Pela IDE: botão direito no projeto > *Run As* > *JUnit Test* (ou *Maven test*). Pelo terminal:
```bash
mvn test
```

## Validações e exceções
- CPF inválido (dígitos verificadores) → `CpfInvalidoException`.
- Código já cadastrado → `FuncionarioJaCadastradoException`.
- Busca, remoção ou cálculo para funcionário inexistente → `FuncionarioNaoEncontradoException`.
- Valores negativos, percentual fora de 0–100 e campos obrigatórios vazios → `IllegalArgumentException`.

## TDD
O desenvolvimento seguiu o ciclo Red → Green → Refactor com JUnit 5. O histórico de commits do
repositório mostra os testes sendo escritos antes das implementações nas etapas novas.
