package single_responsibility;

/**
 * Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
 *
 * Esse princípio estabelece que **uma classe, método ou função deve ter apenas uma razão para mudar**.
 * Ou seja, cada classe ou método deve se especializar em uma única responsabilidade ou tarefa,
 * sendo coeso e focado no que foi projetado para fazer.
 *
 * Exemplo:
 * - Um repositório deve ser responsável apenas por interagir com o banco de dados.
 * - Um serviço deve ser responsável por regras de negócio e delegar tarefas para repositórios ou outras classes.
 */
public class SingleResponsibilityPrinciple {

    /**
     * Classe `EmployerService` demonstra como organizar responsabilidades corretamente.
     * Ela é focada apenas em lidar com a lógica relacionada ao cálculo de rendimentos de um empregado.
     */
    static class EmployerService {

        private final Repository repository;

        /**
         * Construtor da classe recebe um repositório para evitar dependências rígidas e facilitar testes.
         *
         * @param repository Repositório para busca de dados de empregados.
         */
        public EmployerService(Repository repository) {
            this.repository = repository;
        }

        /**
         * Método **errado**: `calculateIncomeWrong`.
         *
         * Este método viola o SRP porque acumula múltiplas responsabilidades:
         * 1. Busca os dados do empregado no banco (dever do repositório).
         * 2. Calcula o rendimento (dever de um método específico de cálculo).
         * 3. Busca os descontos no banco (dever do repositório).
         *
         * Problema: Caso uma dessas responsabilidades mude, é necessário alterar este método,
         * o que aumenta o risco de bugs.
         */
        public double calculateIncomeWrong(Long employerRegistryNumber, int totalHours) {
            var employer = repository.getEmployer(employerRegistryNumber);
            var discounts = repository.getEmployerDiscounts(employer.getRegistryNumber());
            return employer.getValueHour() * totalHours - discounts;
        }

        /**
         * Método **correto**: `calculateIncomeCorrect`.
         *
         * Esse método tem uma única responsabilidade: calcular o rendimento final.
         * Ele recebe os parâmetros necessários para realizar o cálculo, sem acessar diretamente o banco ou outros serviços.
         *
         * @param valueHour Valor da hora trabalhada.
         * @param totalHours Total de horas trabalhadas.
         * @param discounts Descontos aplicáveis.
         * @return Valor final do rendimento.
         */
        public double calculateIncomeCorrect(double valueHour, int totalHours, double discounts) {
            return valueHour * totalHours - discounts;
        }

        /**
         * Método principal: `calculateEmployerIncome`.
         *
         * Este método organiza a lógica de cálculo de rendimento:
         * - Busca os dados necessários do repositório.
         * - Chama o método `calculateIncomeCorrect` para realizar o cálculo final.
         *
         * Responsabilidades bem definidas:
         * - O repositório lida com a persistência.
         * - O método `calculateIncomeCorrect` é responsável pelo cálculo.
         *
         * @param employerRegistryNumber Registro do empregado.
         * @param totalHours Total de horas trabalhadas.
         * @return Rendimento final calculado.
         */
        public double calculateEmployerIncome(Long employerRegistryNumber, int totalHours) {
            var employer = repository.getEmployer(employerRegistryNumber);
            var discounts = repository.getEmployerDiscounts(employerRegistryNumber);
            return calculateIncomeCorrect(employer.getValueHour(), totalHours, discounts);
        }
    }

    /**
     * Classe que **viola o SRP**: `ClassGod`.
     *
     * Essa classe acumula responsabilidades desconexas (persistência, cálculos, envio de e-mails, etc.).
     * Isso torna o código difícil de manter, testar e evoluir.
     */
    static class ClassGod {
        public void saveEmployer() {}
        public double calculateSalary() { return 0; }
        public Employee getEmployerDetails() { return null; }
        public double calculateDiscount() { return 0; }
        public void sendMail() {}
        public void printFile() {}
        public void publishRabbitMQMessage() {}
    }

    /**
     * Correção: Divisão de responsabilidades.
     *
     * Aqui, distribuímos as responsabilidades da `ClassGod` em classes especializadas,
     * cada uma com um propósito único.
     */
    class EmployerServiceCorrect {
        public void saveEmployer() {}
        public double calculateSalary() { return 0; }
        public Employee getEmployerDetails() { return null; }
        public double calculateDiscount() { return 0; }
    }

    class FileService {
        public void printFile() {}
    }

    class MailService {
        public void sendMail() {}
    }

    class RabbitMQService {
        public void publishMessage() {}
    }


    /**
     * Classe `Employee`: Representa um empregado com os dados básicos necessários para cálculos e operações.
     */
    class Employee {
        private String name;
        private Long registryNumber;
        private Double valueHour;

        public Employee(String name, Long registryNumber, Double valueHour) {
            this.name = name;
            this.registryNumber = registryNumber;
            this.valueHour = valueHour;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getRegistryNumber() {
            return registryNumber;
        }

        public void setRegistryNumber(Long registryNumber) {
            this.registryNumber = registryNumber;
        }

        public Double getValueHour() {
            return valueHour;
        }

        public void setValueHour(Double valueHour) {
            this.valueHour = valueHour;
        }
    }

    /**
     * Classe `Repository`: Responsável pela persistência e recuperação de dados relacionados a empregados.
     */
    class Repository {
        public Employee getEmployer(Long registryNumber) {
            return new Employee("Fulano", registryNumber, 8.0);
        }

        public double getEmployerDiscounts(Long registryNumber) {
            return 50.0;
        }
    }

}
