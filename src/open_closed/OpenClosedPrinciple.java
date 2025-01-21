package open_closed;

/**
 * Open-Closed Principle (Princípio Aberto-Fechado)
 *
 * Este princípio, parte dos SOLID, estabelece que:
 * **Os objetos ou entidades devem estar abertos para extensão, mas fechados para modificação.**
 *
 * Isso significa que, ao precisar adicionar novos comportamentos ou funcionalidades, o código
 * existente não deve ser alterado. Em vez disso, devemos estender o comportamento de forma
 * que o código atual permaneça intacto e menos suscetível a introdução de bugs.
 *
 * <p>Exemplo do problema:</p>
 * Imagine uma empresa que inicialmente trabalha com dois tipos de funcionários:
 * CLT (Contrato de Trabalho) e Trainee. O cálculo da remuneração para esses funcionários
 * está concentrado em uma única classe, e cada novo tipo de funcionário requer a
 * modificação do código já existente. Isso quebra o princípio do Open-Closed.
 *
 * <p>Exemplo da solução:</p>
 * Para resolver esse problema, usamos uma abordagem baseada em abstração, onde cada
 * tipo de funcionário implementa uma interface que define como calcular sua remuneração.
 * Assim, novos tipos de funcionários podem ser adicionados sem alterar o código existente.
 */
public class OpenClosedPrinciple {

    /**
     * Classe que representa um funcionário CLT (Contrato de Trabalho).
     */
    class ContractCLT {
        private double salary;

        public double getSalary() {
            return salary;
        }
    }

    /**
     * Classe que representa um Trainee.
     */
    class Treinee {
        private double bolsaAuxilio;

        public double getBolsaAuxilio() {
            return bolsaAuxilio;
        }
    }

    /**
     * Classe que quebra o Open-Closed Principle.
     *
     * Este método verifica explicitamente o tipo do funcionário para calcular
     * sua remuneração. Para cada novo tipo de funcionário (exemplo: PJ), será
     * necessário adicionar mais lógica condicional. Isso torna o código difícil
     * de manter e propenso a erros.
     */
    class Income {
        protected double saldo;

        public double calculate(Object funcionario) {
            double saldo = 0.0;
            if (funcionario instanceof ContractCLT) {
                saldo = ((ContractCLT) funcionario).getSalary();
            } else if (funcionario instanceof Treinee) {
                saldo = ((Treinee) funcionario).getBolsaAuxilio();
            }
            return saldo;
        }
    }

    /**
     * Interface que define o comportamento para cálculo de remuneração.
     *
     * Cada tipo de funcionário implementará essa interface, fornecendo sua própria
     * lógica para o cálculo.
     */
    interface Remuneracao {
        double remuneracao();
    }

    /**
     * Classe que implementa o comportamento de remuneração para funcionários CLT.
     */
    class ContratoCLT implements Remuneracao {
        private double salario;

        public ContratoCLT(double salario) {
            this.salario = salario;
        }

        @Override
        public double remuneracao() {
            return salario;
        }
    }

    /**
     * Classe que implementa o comportamento de remuneração para Trainees.
     */
    class Treinee2 implements Remuneracao {
        private double bolsaAuxilio;

        public Treinee2(double bolsaAuxilio) {
            this.bolsaAuxilio = bolsaAuxilio;
        }

        @Override
        public double remuneracao() {
            return bolsaAuxilio;
        }
    }

    /**
     * Classe que implementa o comportamento de remuneração para funcionários PJ.
     */
    class FuncionarioPJ implements Remuneracao {
        private double valorHora;
        private int horasTrabalhadas;

        public FuncionarioPJ(double valorHora, int horasTrabalhadas) {
            this.valorHora = valorHora;
            this.horasTrabalhadas = horasTrabalhadas;
        }

        @Override
        public double remuneracao() {
            return valorHora * horasTrabalhadas;
        }
    }

    /**
     * Classe que calcula a remuneração sem quebrar o Open-Closed Principle.
     *
     * Esta classe depende apenas da interface {@code Remuneracao}, o que significa
     * que qualquer nova implementação da interface será automaticamente suportada,
     * sem precisar modificar o código aqui.
     */
    class IncomeCorrect {
        double calcular(Remuneracao tipoFuncionario) {
            return tipoFuncionario.remuneracao();
        }
    }

    /**
     * Exemplo prático de aplicação do Open-Closed Principle.
     *
     * Neste exemplo, criamos diferentes tipos de funcionários e calculamos suas
     * remunerações utilizando a classe {@code IncomeCorrect}, que respeita o OCP.
     */
    public class OpenClosedPrincipleExample {
        public void main(String[] args) {
            IncomeCorrect income = new IncomeCorrect();

            // Criando diferentes tipos de funcionários
            Remuneracao funcionarioCLT = new ContratoCLT(5000.0);
            Remuneracao trainee = new Treinee2(1500.0);
            Remuneracao funcionarioPJ = new FuncionarioPJ(100.0, 160);

            // Calculando as remunerações
            System.out.println("CLT: " + income.calcular(funcionarioCLT)); // Saída: CLT: 5000.0
            System.out.println("Trainee: " + income.calcular(trainee)); // Saída: Trainee: 1500.0
            System.out.println("PJ: " + income.calcular(funcionarioPJ)); // Saída: PJ: 16000.0
        }
    }
}
